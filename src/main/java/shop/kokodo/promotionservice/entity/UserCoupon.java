package shop.kokodo.promotionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class UserCoupon extends BaseEntity{

    @Id
    @GeneratedValue
    private long userCouponId;

    @ManyToOne
    @JoinColumn(name="rate_coupon_id")
    private RateCoupon rateCoupon;

    @ManyToOne
    @JoinColumn(name="fixCouponId")

    private FixCoupon fixCoupon;

    private long userId;

//    @Enumerated(EnumType.STRING)
    private int usageStatus;

    @Builder
    public UserCoupon(long userCouponId, RateCoupon rateCoupon, FixCoupon fixCoupon, long userId, int usageStatus) {
        this.userCouponId = userCouponId;
        this.rateCoupon = rateCoupon;
        this.fixCoupon = fixCoupon;
        this.userId = userId;
        this.usageStatus = usageStatus;
    }

}
