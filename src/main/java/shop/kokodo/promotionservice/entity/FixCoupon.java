package shop.kokodo.promotionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class FixCoupon extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name="fix_coupon_id")
    private long id;

    private String name;

    private LocalDateTime regdate;

    private int price;

    private int minPrice;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private long productId;

    @Builder
    public FixCoupon(long id, String name, LocalDateTime regdate, int price, int minPrice, LocalDateTime startDate, LocalDateTime endDate, long productId) {
        this.id = id;
        this.name = name;
        this.regdate = regdate;
        this.price = price;
        this.minPrice = minPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productId = productId;
    }
}
