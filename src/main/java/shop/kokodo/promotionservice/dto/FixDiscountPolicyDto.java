package shop.kokodo.promotionservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FixDiscountPolicyDto {
    Long fixDiscountPolicyId;
    String name;
    LocalDateTime regDate;
    LocalDateTime startDate;
    LocalDateTime endDate;
    int price;
    int minPrice;
    int productId;
}
