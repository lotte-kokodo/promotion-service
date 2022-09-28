package shop.kokodo.promotionservice.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@NoArgsConstructor
public class RateCoupon extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rate_coupon_id")
    private long id;

    private String name;

    private LocalDateTime regdate;

    private int rate;

    private int minPrice;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private long productId;

    @Builder
    public RateCoupon(long id, String name, LocalDateTime regdate, int rate, int minPrice, LocalDateTime startDate, LocalDateTime endDate, long productId) {
        this.id = id;
        this.name = name;
        this.regdate = regdate;
        this.rate = rate;
        this.minPrice = minPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productId = productId;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regdate = regDate;
    }

}
