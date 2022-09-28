package shop.kokodo.promotionservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class FixCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fix_coupon_id")
    private long id;
    private String name;

    private LocalDateTime regdate;

    private int price;

    private int minPrice;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private long productId;

    private long sellerId;

    @Builder
    public FixCoupon(long id, String name, LocalDateTime regdate, int price, int minPrice, LocalDateTime startDate, LocalDateTime endDate, long productId, long sellerId) {
        this.id = id;
        this.name = name;
        this.regdate = regdate;
        this.price = price;
        this.minPrice = minPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productId = productId;
        this.sellerId=sellerId;
    }

}
