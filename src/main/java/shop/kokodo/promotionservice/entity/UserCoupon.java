package shop.kokodo.promotionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @Enumerated(EnumType.STRING)
    private UsageStatus usageStatus;
}
