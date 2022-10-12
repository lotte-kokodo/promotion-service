package shop.kokodo.promotionservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductIdAndFixCouponDto {
    long productId;

    List<FixCoupon> fixCouponList;

    @Builder
    public ProductIdAndFixCouponDto(long productId, List<FixCoupon> fixCouponList) {
        this.productId = productId;
        this.fixCouponList = fixCouponList;
    }
}
