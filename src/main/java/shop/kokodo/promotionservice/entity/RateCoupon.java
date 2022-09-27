package shop.kokodo.promotionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RateCoupon extends BaseEntity{

    @Id
    @GeneratedValue
    private long rateCouponId;

    private String name;

    private LocalDateTime regDate;

    private int rate;

    private int minPrice;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
