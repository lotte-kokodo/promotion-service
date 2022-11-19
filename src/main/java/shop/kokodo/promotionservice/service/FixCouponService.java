package shop.kokodo.promotionservice.service;

import org.springframework.data.domain.Page;
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.dto.PagingFixCouponDto;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.entity.FixCoupon;

import java.time.LocalDateTime;
import java.util.List;

public interface FixCouponService {
    void save(FixCouponDto fixCouponDto);


    List<FixCoupon> findUserNotUsedFixCouponByproductId(long userId, long productId);

    PagingFixCouponDto findBySellerId(long sellerId, int page);

    List<ProductDto> findProductByName(String name);

    List<Long> findByCouponIdList(List<Long> couponIdList);

    List<FixCoupon> findByProductId(Long productId);
}
