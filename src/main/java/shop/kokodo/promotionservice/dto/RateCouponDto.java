package shop.kokodo.promotionservice.dto;

import lombok.*;
import java.util.List;

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
    List<Long> productList;

    @Builder
    public RateCouponDto(long id, String name, int rate, int minPrice, String startDate, String endDate, List<Long> productList) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.minPrice = minPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productList = productList;
    }

}
