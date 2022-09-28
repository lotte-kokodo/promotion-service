package shop.kokodo.promotionservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RateDiscountPolicy extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rateDiscountPolicyId;

    private String name;

    private LocalDateTime regDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int rate;

    private int minPrice;

    private long productId;


}
