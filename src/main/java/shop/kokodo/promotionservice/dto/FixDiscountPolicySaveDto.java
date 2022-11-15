package shop.kokodo.promotionservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FixDiscountPolicySaveDto {
    Long fixDiscountPolicyId;
    String name;
    LocalDateTime regDate;
    LocalDateTime startDate;
    LocalDateTime endDate;
    int price;
    int minPrice;
    List<Long> productId;
    Long sellerId;
}
