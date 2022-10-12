package shop.kokodo.promotionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.dto.ProductIdAndRateCouponDto;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.repository.RateCouponRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RateCouponServiceImpl implements RateCouponService{

    private final RateCouponRepository rateCouponRepository;

    @Override
    public void save(RateCouponDto rateCouponDto) {

        for (Long productId : rateCouponDto.getProductList()) {
            RateCoupon rateCoupon=convertToRateCoupon(rateCouponDto,productId);
            rateCouponRepository.save(rateCoupon);
        }
    }

    @Override
    public List<RateCoupon> findUserNotUsedRateCouponByproductId(long userId, long productId) {
        return rateCouponRepository.findUserNotUsedRateCouponByproductId(userId,productId, LocalDateTime.now());
    }

    @Override
    public List<RateCoupon> findBySellerId(long sellerId) {
        return rateCouponRepository.findBySellerId(sellerId);
    }

    @Override
    public List<RateCoupon> findByProductId(long productId) {
        return rateCouponRepository.findByProductId(productId, LocalDateTime.now());
    }

    private RateCoupon convertToRateCoupon(RateCouponDto rateCouponDto, long productId){
        return RateCoupon.builder()
                .name(rateCouponDto.getName())
                .regdate(LocalDateTime.now())
                .rate(rateCouponDto.getRate())
                .minPrice(rateCouponDto.getMinPrice())
                .startDate(LocalDateTime.parse(rateCouponDto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .endDate(LocalDateTime.parse(rateCouponDto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .productId(productId)
                .sellerId(rateCouponDto.getSellerId())
                .build();
    }
}
