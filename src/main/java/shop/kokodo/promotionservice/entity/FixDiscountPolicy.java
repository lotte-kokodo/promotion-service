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
public class FixDiscountPolicy extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long fixDiscountPolicyId;

    private String name;

    private LocalDateTime regDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int price;

    private int minPrice;

    private long productId;


}
