package shop.kokodo.promotionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.feign.ProductServiceClient;
import shop.kokodo.promotionservice.repository.FixCouponRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FixCouponServiceImpl implements FixCouponService{

    private final FixCouponRepository fixCouponRepository;
    private final ProductServiceClient productServiceClient;
    @Override
    public void save(FixCouponDto fixCouponDto) {
        if(fixCouponRepository.findByName(fixCouponDto.getName()).isPresent()) throw new IllegalArgumentException("이미 존재하는 쿠폰 이름");

        FixCoupon fixCoupon;
        for (Long productId : fixCouponDto.getProductList()) {
            fixCoupon=convertToFixCoupon(fixCouponDto,productId);
            fixCouponRepository.save(fixCoupon);
        }

    }

    public List<FixCoupon> findUserNotUsedFixCouponByproductId(long userId, long productId){
        return fixCouponRepository.findUserNotUsedFixCouponByproductId(userId,productId,LocalDateTime.now());
    }

    @Override
    public List<FixCoupon> findBySellerId(long sellerId) {
        
        // TODO: sellerId  SELLER MS에서 확인해서 예외처리
        return fixCouponRepository.findBySellerId(sellerId);
    }

    @Override
    public List<ProductDto> findProductByName(String name) {
        List<Long> productIdList = fixCouponRepository.findProductIdByName(name);

        return productServiceClient.findProductByName(productIdList);
    }

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
