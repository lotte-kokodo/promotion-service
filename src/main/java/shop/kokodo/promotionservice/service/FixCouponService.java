package shop.kokodo.promotionservice.service;

import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.entity.FixCoupon;

import java.time.LocalDateTime;
import java.util.List;

public interface FixCouponService {
    void save(FixCouponDto fixCouponDto);

    List<FixCoupon> findUserNotUsedFixCouponByproductId(long userId, long productId);

    List<FixCoupon> findBySellerId(long sellerId);

    List<ProductDto> findProductByName(String name);

    List<Long> findByCouponIdList(List<Long> couponIdList);

}
