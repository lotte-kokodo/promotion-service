package shop.kokodo.promotionservice.service;

import shop.kokodo.promotionservice.dto.ProductIdAndRateCouponDto;
import shop.kokodo.promotionservice.dto.UpdateUserCouponDto;
import shop.kokodo.promotionservice.dto.UserCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;

import java.util.List;

public interface UserCouponService {

    public List<UserCoupon> findByUserId(long userId);

    public UserCoupon save(UserCouponDto userCouponDto);
    public List<UserCoupon> findValidCouponByMemberIdGroupByCouponName(long memberId);

    public UserCoupon updateUsageStatus(UpdateUserCouponDto updateUserCouponDto, long userId);

    public List<ProductIdAndRateCouponDto> findRateCouponByMemberIdAndProductId(List<Long> productIdList, long memberId);
    public List<FixCoupon> findFixCouponByMemberIdAndProductId(long productId, long memberId);
}
