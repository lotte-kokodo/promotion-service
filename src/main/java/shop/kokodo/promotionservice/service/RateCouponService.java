package shop.kokodo.promotionservice.service;

import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.entity.RateCoupon;

import java.time.LocalDateTime;
import java.util.List;

public interface RateCouponService {

    public void save(RateCouponDto rateCouponDto);

    public List<RateCoupon> findUserNotUsedRateCouponByproductId(long userId, long productId);
}
