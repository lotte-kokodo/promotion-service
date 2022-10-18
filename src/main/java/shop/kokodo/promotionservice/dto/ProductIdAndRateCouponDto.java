package shop.kokodo.promotionservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.kokodo.promotionservice.entity.RateCoupon;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductIdAndRateCouponDto {

    long productId;

    List<RateCoupon> rateCouponList;

    @Builder
    public ProductIdAndRateCouponDto(long productId, List<RateCoupon> rateCouponList) {
        this.productId = productId;
        this.rateCouponList = rateCouponList;
    }

    public static ProductIdAndRateCouponDto createProductIdAndRateCouponDto(long productId, List<RateCoupon> rateCouponList){
        return ProductIdAndRateCouponDto.builder()
                .productId(productId)
                .rateCouponList(rateCouponList)
                .build();
    }

}
