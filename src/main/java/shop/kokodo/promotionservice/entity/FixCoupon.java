package shop.kokodo.promotionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FixCoupon extends BaseEntity {

    @Id
    @GeneratedValue
    private long fixCouponId;

    private String name;

    private LocalDateTime regdate;

    private int price;

    private int minPrice;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
