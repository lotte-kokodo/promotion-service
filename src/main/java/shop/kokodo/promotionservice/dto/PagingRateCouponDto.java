package shop.kokodo.promotionservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;

import java.util.List;


@Data
@NoArgsConstructor
public class PagingRateCouponDto {

    long totalCount;

    List<RateCoupon> rateCouponList;


    @Builder
    public PagingRateCouponDto(long totalCount, List<RateCoupon> rateCouponList) {
        this.totalCount = totalCount;
        this.rateCouponList = rateCouponList;
    }
}
