package shop.kokodo.promotionservice.service;

import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;

import java.util.List;

public interface FixCouponService {
    public void save(FixCouponDto fixCouponDto);
    public List<FixCoupon> findUserNotUsedFixCouponByproductId(long userId, long productId);

}
