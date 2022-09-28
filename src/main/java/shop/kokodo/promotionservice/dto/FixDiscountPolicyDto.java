package shop.kokodo.promotionservice.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FixDiscountPolicyDto {
    String name;
    Date regDate;
    Date startDate;
    Date endDate;
    int rate;
    int minPrice;
    int productId;
}
