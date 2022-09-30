package shop.kokodo.promotionservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserCouponDto {

    int couponFlag;
    long rateCouponId;
    long fixCouponId;

    @Builder
    public UpdateUserCouponDto(int couponFlag, long rateCouponId, long fixCouponId) {
        this.couponFlag = couponFlag;
        this.rateCouponId = rateCouponId;
        this.fixCouponId = fixCouponId;
    }
}
