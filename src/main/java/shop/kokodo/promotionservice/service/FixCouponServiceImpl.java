package shop.kokodo.promotionservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import shop.kokodo.promotionservice.circuitbreaker.AllCircuitBreaker;
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.dto.PagingFixCouponDto;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.exception.DuplicateCouponNameException;
import shop.kokodo.promotionservice.exception.NoMemberException;
import shop.kokodo.promotionservice.exception.NoProductException;
import shop.kokodo.promotionservice.exception.NoSellerException;
import shop.kokodo.promotionservice.feign.MemberServiceClient;
import shop.kokodo.promotionservice.feign.ProductServiceClient;
import shop.kokodo.promotionservice.feign.SellerServiceClient;
import shop.kokodo.promotionservice.repository.FixCouponRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FixCouponServiceImpl implements FixCouponService{

    private final FixCouponRepository fixCouponRepository;
    private final ProductServiceClient productServiceClient;
    private final SellerServiceClient sellerServiceClient;
    private final MemberServiceClient memberServiceClient;

    private final CircuitBreaker circuitBreaker = AllCircuitBreaker.createSellerCircuitBreaker();

    @Transactional
    @Override
    public void save(FixCouponDto fixCouponDto) {
        if(fixCouponRepository.findByName(fixCouponDto.getName()).isPresent())
            throw new DuplicateCouponNameException();

        FixCoupon fixCoupon;
        for (Long productId : fixCouponDto.getProductList()) {
            fixCoupon=convertToFixCoupon(fixCouponDto,productId);
            fixCouponRepository.save(fixCoupon);
        }

    }

    @Override
    public List<FixCoupon> findUserNotUsedFixCouponByproductId(long memberId, long productId){

        Boolean memberFlag = circuitBreaker.run(()-> memberServiceClient.getMember(memberId)
                ,throwable -> true);
        Boolean productFlag = circuitBreaker.run(()-> productServiceClient.findProductById(productId)
                ,throwable -> true);
        if(!memberFlag) throw new NoMemberException();

        if(!productFlag) throw new NoProductException();

        return fixCouponRepository.findUserNotUsedFixCouponByproductId(memberId,productId,LocalDateTime.now());
    }
    @Transactional(readOnly = true)
    @Override
    public PagingFixCouponDto findBySellerId(long sellerId, int page) {

        Boolean sellerFlag = circuitBreaker.run(()-> sellerServiceClient.getSeller(sellerId)
                ,throwable -> true);

        if (!sellerFlag)
            throw new NoSellerException();

        Page<FixCoupon> fixCoupons =  fixCouponRepository.findBySellerId(sellerId, PageRequest.of(page,7));

        PagingFixCouponDto pagingFixCouponDto = PagingFixCouponDto.builder()
                .fixCouponList(fixCoupons.toList())
                .totalCount(fixCoupons.getTotalElements())
                .build();

        return pagingFixCouponDto;
    }

    @Override
    public List<ProductDto> findProductByName(String name) {
        List<Long> productIdList = fixCouponRepository.findProductIdByName(name);

        return circuitBreaker.run(()-> productServiceClient.findProductByName(productIdList)
                ,throwable -> new ArrayList<>());
    }

    /*
        fixcoupon id list로 seller id 리스트 리턴
     */
    @Override
    public List<Long> findByCouponIdList(List<Long> couponIdList) {
        List<FixCoupon> fixCouponList = fixCouponRepository.findByCouponIdList(couponIdList);

        List<Long> sellerIdList = new ArrayList<>();

        for (FixCoupon fixCoupon : fixCouponList) {
            long sellerId = fixCoupon.getSellerId();
            if(!sellerIdList.contains(sellerId)) sellerIdList.add(sellerId);
        }
        return sellerIdList;
    }


    private FixCoupon convertToFixCoupon(FixCouponDto fixCouponDto,long productId){
        return FixCoupon.builder()
                .name(fixCouponDto.getName())
                .regdate(LocalDateTime.now())
                .price(fixCouponDto.getPrice())
                .minPrice(fixCouponDto.getMinPrice())
                .startDate(LocalDateTime.parse(fixCouponDto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .endDate(LocalDateTime.parse(fixCouponDto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .productId(productId)
                .sellerId(fixCouponDto.getSellerId())
                .freeDelivery(true)
                .build();
    }


}