package shop.kokodo.promotionservice.service;

import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.kokodo.promotionservice.circuitbreaker.AllCircuitBreaker;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.exception.DuplicateCouponNameException;
import shop.kokodo.promotionservice.exception.NoMemberException;
import shop.kokodo.promotionservice.exception.NoProductException;
import shop.kokodo.promotionservice.exception.NoSellerException;
import shop.kokodo.promotionservice.feign.MemberServiceClient;
import shop.kokodo.promotionservice.feign.ProductServiceClient;
import shop.kokodo.promotionservice.feign.SellerServiceClient;
import shop.kokodo.promotionservice.repository.RateCouponRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class RateCouponServiceImpl implements RateCouponService{
    private final ProductServiceClient productServiceClient;
    private final RateCouponRepository rateCouponRepository;
    private final MemberServiceClient memberServiceClient;
    private final SellerServiceClient sellerServiceClient;

    private final CircuitBreaker circuitBreaker = AllCircuitBreaker.createSellerCircuitBreaker();



    public RateCouponServiceImpl(ProductServiceClient productServiceClient, RateCouponRepository rateCouponRepository,
                                 MemberServiceClient memberServiceClient, SellerServiceClient sellerServiceClient) {
        this.productServiceClient = productServiceClient;
        this.rateCouponRepository = rateCouponRepository;
        this.memberServiceClient = memberServiceClient;
        this.sellerServiceClient = sellerServiceClient;

    }

    @Transactional
    @Override
    public void save(RateCouponDto rateCouponDto) {
        if(rateCouponRepository.findByName(rateCouponDto.getName()).size()>0)
            throw new DuplicateCouponNameException();

        for (Long productId : rateCouponDto.getProductList()) {
            RateCoupon rateCoupon=convertToRateCoupon(rateCouponDto,productId);
            rateCouponRepository.save(rateCoupon);
        }
    }

    @Override
    public List<RateCoupon> findUserNotUsedRateCouponByproductId(long memberId, long productId) {

        Boolean memberFlag = circuitBreaker.run(()-> memberServiceClient.getMember(memberId)
                ,throwable -> true);
        Boolean productFlag = circuitBreaker.run(()-> productServiceClient.findProductById(productId)
                ,throwable -> true);

        if(!memberFlag) throw new NoMemberException();
        if(!productFlag) throw new NoProductException();

        return rateCouponRepository.findUserNotUsedRateCouponByproductId(memberId,productId, LocalDateTime.now());
    }

    @Override
    public List<RateCoupon> findBySellerId(long sellerId) {

        Boolean sellerFlag = circuitBreaker.run(()-> sellerServiceClient.getSeller(sellerId)
                ,throwable -> true);

        if(!sellerFlag) throw new NoSellerException();
        return rateCouponRepository.findDistinctRateCouponBySellerId(sellerId);
    }

    @Override
    public List<RateCoupon> findByProductId(long productId) {

        Boolean productFlag = circuitBreaker.run(()-> productServiceClient.findProductById(productId)
                ,throwable -> true);

        if(!productFlag) throw new NoProductException();
        return rateCouponRepository.findByProductId(productId, LocalDateTime.now());
    }

    @Override
    public List<ProductDto> findProductByRateCouponName(String name) {

        List<Long> productIdList = rateCouponRepository.findProductIdByName(name);
        List<ProductDto> dto = productServiceClient.findProductByName(productIdList);

        return dto;
    }

    @Override
    public  Map<Long, RateCoupon>  findByCouponIdList(List<Long> couponIdList) {
        List<RateCoupon> rateCouponList = rateCouponRepository.findByIdList(couponIdList);
        // productId - rateCoupon 리스트
//        Map<Long, List<RateCoupon>> productIdAndRateCouponListMap = new HashMap<>();
//
//        for (RateCoupon rateCoupon : rateCouponList) {
//            long productId =rateCoupon.getProductId();
//            if(productIdAndRateCouponListMap.containsKey(productId)){
//                List<RateCoupon> tmp = productIdAndRateCouponListMap.get(productId);
//                tmp.add(rateCoupon);
//                productIdAndRateCouponListMap.put(productId,tmp);
//            }
//            else{
//                List<RateCoupon> tmp = new ArrayList<>();
//                tmp.add(rateCoupon);
//                productIdAndRateCouponListMap.put(productId,tmp);
//            }
//        }

        return rateCouponList.stream().collect(Collectors.toMap(RateCoupon::getProductId,
            Function.identity()));
    }


    private RateCoupon convertToRateCoupon(RateCouponDto rateCouponDto, long productId){
        return RateCoupon.builder()
                .name(rateCouponDto.getName())
                .regdate(LocalDateTime.now())
                .rate(rateCouponDto.getRate())
                .minPrice(rateCouponDto.getMinPrice())
                .startDate(LocalDateTime.parse(rateCouponDto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .endDate(LocalDateTime.parse(rateCouponDto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .productId(productId)
                .sellerId(rateCouponDto.getSellerId())
                .build();
    }
}
