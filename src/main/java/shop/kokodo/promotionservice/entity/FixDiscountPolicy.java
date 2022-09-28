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
public class FixDiscountPolicy extends BaseEntity{

    @Id
    @GeneratedValue
    private long fixDiscountPolicyId;

    private String name;

    private LocalDateTime regdate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int price;

    private int minPrice;

    private long productId;


}
