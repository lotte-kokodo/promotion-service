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
    @JoinColumn(name="rateCouponId")
    private RateCoupon rateCoupon;

    @ManyToOne
    @JoinColumn(name="rateCouponId")
    private FixCoupon fixCoupon;

    private long userId;

    private UsageStatus usageStatus;
}
