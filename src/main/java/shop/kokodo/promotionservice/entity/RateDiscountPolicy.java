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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long rateDiscountPolicyId;

    @Column( nullable = false )
//@NotNull
    private String name;

    @Column( nullable = false )
//@NotNull
    private LocalDateTime regDate;

    @Column( nullable = false )
//@NotNull
    private LocalDateTime startDate;

    @Column( nullable = false )
//@NotNull
    private LocalDateTime endDate;

    @Column( nullable = false )
//@NotNull
    private Integer rate;

    @Column( nullable = false )
//@NotNull
    private Integer minPrice;

    @Column( nullable = false )
//@NotNull
    private Long productId;

}
