package shop.kokodo.promotionservice.service;

import shop.kokodo.promotionservice.entity.UserCoupon;

import java.util.List;

public interface UserCouponService {

    public List<UserCoupon> findByUserId(long userId);
}
