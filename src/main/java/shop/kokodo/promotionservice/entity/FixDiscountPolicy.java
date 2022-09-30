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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long fixDiscountPolicyId;

    @Column( nullable = false )
    private String name;

    @Column( nullable = false )
    private LocalDateTime regDate;

    @Column( nullable = false )
    private LocalDateTime startDate;

    @Column( nullable = false )
    private LocalDateTime endDate;

    @Column( nullable = false )
    private Integer price;

    @Column( nullable = false )
    private Integer minPrice;

    @Column( nullable = false )
    private Long productId;

}
