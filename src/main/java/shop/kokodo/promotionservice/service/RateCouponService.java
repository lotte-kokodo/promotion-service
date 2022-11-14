package shop.kokodo.promotionservice.service;

import shop.kokodo.promotionservice.dto.PagingRateCouponDto;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.dto.ProductIdAndRateCouponDto;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.entity.RateCoupon;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface RateCouponService {

    public void save(RateCouponDto rateCouponDto);

    public List<RateCoupon> findUserNotUsedRateCouponByproductId(long userId, long productId);
    public PagingRateCouponDto findBySellerId(long sellerId, int page);

    public List<RateCoupon> findByProductId(long productId);

    public List<ProductDto> findProductByRateCouponName(String name);

    Map<Long, RateCoupon> findByCouponIdList(List<Long> couponIdList);

}
