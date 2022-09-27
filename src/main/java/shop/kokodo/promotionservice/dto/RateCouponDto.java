package shop.kokodo.promotionservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RateCouponDto {

    long id;
    String name;
    int rate;
    int minPrice;
    String startDate;
    String endDate;
    long sellerId;

    @Builder
    public RateCouponDto(long id, String name, int rate, int minPrice, String startDate, String endDate, long sellerId) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.minPrice = minPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sellerId = sellerId;
    }
}
