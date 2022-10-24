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

    @Column( nullable = false )
    private String name;

    @Column( nullable = false )
    private LocalDateTime regDate;

    @Column( nullable = false )
    private LocalDateTime startDate;

    @Column( nullable = false )
    private LocalDateTime endDate;

    @Column( nullable = false )
    private Integer rate;

    @Column( nullable = false )
    private Integer minPrice;

    @Column( nullable = false )
    private Long productId;

    @Column( nullable = false )
    private Long sellerId;
}
