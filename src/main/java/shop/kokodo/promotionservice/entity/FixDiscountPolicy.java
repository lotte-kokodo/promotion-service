package shop.kokodo.promotionservice.entity;

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
public class FixDiscountPolicy extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fixDiscountPolicyId;

    private String name;

    private LocalDateTime regDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer price;

    private Integer minPrice;

    private Long productId;

    private Long sellerId;

}
