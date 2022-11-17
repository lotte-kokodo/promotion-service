package shop.kokodo.promotionservice.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Long rateDiscountPolicyId;

    private String name;

    private LocalDateTime regDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer rate;

    private Integer minPrice;

    private Long productId;

    private Long sellerId;

//    @Transient
//    private final int couponFlag=1;
}
