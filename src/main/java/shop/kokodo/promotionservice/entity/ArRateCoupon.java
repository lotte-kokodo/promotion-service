package shop.kokodo.promotionservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shop.kokodo.promotionservice.dto.ArRateCouponInfo;
import shop.kokodo.promotionservice.dto.RateCouponDtoV2;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : shop.kokodo.calculateservice.entity
 * fileName       : ArCoupon
 * author         : namhyeop
 * date           : 2022/11/02
 * description    :
 * ArCoupon Entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/11/02        namhyeop       최초 생성
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ArRateCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rate_coupon_id")
    @JsonIgnore
    private RateCoupon rateCoupon;

    private Double xPos;

    private Double yPos;

    private Double zPos;
    public static ArRateCoupon createArCoupon(RateCoupon rateCoupon, ArRateCouponInfo arRateCouponInfo) {
        ArRateCoupon arRateCoupon = ArRateCoupon.builder()
                .rateCoupon(rateCoupon)
                .xPos(arRateCouponInfo.getX())
                .yPos(arRateCouponInfo.getY())
                .zPos(arRateCouponInfo.getZ())
                .build();
        arRateCoupon.rateCoupon.setArRateCoupon(arRateCoupon);
        return arRateCoupon;
    }




    public static List<ArClientRateCouponDto> toDto(List<ArRateCoupon> arRateCoupons){
        return arRateCoupons.stream().map(c -> new ArClientRateCouponDto(
                new RateCouponDtoV2(c.getRateCoupon().getId()
                        ,c.getRateCoupon().getName()
                        ,c.getRateCoupon().getRegdate()
                ,c.getRateCoupon().getRate()
                ,c.getRateCoupon().getMinPrice()
                ,c.getRateCoupon().getStartDate()
                ,c.getRateCoupon().getEndDate()
                ,c.getRateCoupon().getProductId()
                ,c.getRateCoupon().getSellerId())
                , c.getXPos(), c.getYPos(), c.getZPos())).collect(Collectors.toList());
    }
}
