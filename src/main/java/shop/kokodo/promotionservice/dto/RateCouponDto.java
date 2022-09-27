package shop.kokodo.promotionservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RateCouponDto {

    String name;
    String regdate;
    int rate;
    int minPrice;
    String startDate;
    String endDate;
    long sellerId;
}
