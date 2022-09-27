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
public class RateDiscountPolicy extends BaseEntity{

    @Id
    @GeneratedValue
    private long rateDiscountPolicyId;

    private String name;

    private LocalDateTime regdate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int rate;

    private int minPrice;

    private long productId;


}
