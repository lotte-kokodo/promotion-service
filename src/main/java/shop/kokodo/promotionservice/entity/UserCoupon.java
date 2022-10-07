package shop.kokodo.promotionservice.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@ToString
public class UserCoupon extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon_id")
    private long id;

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
    public UserCoupon(long id, RateCoupon rateCoupon, FixCoupon fixCoupon, long userId, int usageStatus) {
        this.id = id;
        this.rateCoupon = rateCoupon;
        this.fixCoupon = fixCoupon;
        this.userId = userId;
        this.usageStatus = usageStatus;
    }

    public void useCoupon(){
        this.usageStatus=1;
    }

}
