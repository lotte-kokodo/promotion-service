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

    private LocalDateTime regDate;

    private int rate;

    private int minPrice;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private long sellerId;

    @Builder
    public RateCoupon(long id, String name, LocalDateTime regDate, int rate, int minPrice, LocalDateTime startDate, LocalDateTime endDate, long sellerId) {
        this.id = id;
        this.name = name;
        this.regDate = regDate;
        this.rate = rate;
        this.minPrice = minPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sellerId = sellerId;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }
}
