package shop.kokodo.promotionservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.kokodo.promotionservice.entity.FixCoupon;

import java.util.List;

@Data
@NoArgsConstructor
public class PagingFixCouponDto {

    long totalCount;

    List<FixCoupon> fixCouponList;

    @Builder
    public PagingFixCouponDto(long totalCount, List<FixCoupon> fixCouponList) {
        this.totalCount = totalCount;
        this.fixCouponList = fixCouponList;
    }
}
