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
    private long fixDiscountPolicyId;

    @Column( nullable = false )
    private String name;

    @Column( nullable = false )
    private LocalDateTime regDate;

    @Column( nullable = false )
    private LocalDateTime startDate;

    @Column( nullable = false )
    private LocalDateTime endDate;

    @Column( nullable = false )
    private int price;

    @Column( nullable = false )
    private int minPrice;

    @Column( nullable = false )
    private long productId;

}
