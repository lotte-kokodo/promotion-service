package shop.kokodo.promotionservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FixCouponDto {
    long id;
    String name;
    String regdate;
    int price;
    int minPrice;
    String startDate;
    String endDate;
    long sellerId;

    @Builder
    public FixCouponDto(long id, String name, String regdate, int price, int minPrice, String startDate, String endDate, long sellerId) {
        this.id = id;
        this.name = name;
        this.regdate = regdate;
        this.price = price;
        this.minPrice = minPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sellerId = sellerId;
    }
}
