package shop.kokodo.promotionservice.dto;

import lombok.*;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;

@Getter
@Setter
@NoArgsConstructor
public class UserCouponDto {

    long id;
    long userId;
    int usageStatus;
    Long rateCouponId;
    Long fixCouponId;

    @Builder
    public UserCouponDto(long id, long userId, int usageStatus, Long rateCouponId, Long fixCouponId) {
        this.id = id;
        this.userId = userId;
        this.usageStatus = usageStatus;
        this.rateCouponId = rateCouponId;
        this.fixCouponId = fixCouponId;
    }
}
