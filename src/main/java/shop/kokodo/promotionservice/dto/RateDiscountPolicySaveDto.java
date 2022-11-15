package shop.kokodo.promotionservice.dto;

import lombok.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateDiscountPolicySaveDto {
    Long rateDiscountPolicyId;
    String name;
    LocalDateTime regDate;
    LocalDateTime startDate;
    LocalDateTime endDate;
    int rate;
    int minPrice;
    List<Long> productId;
    Long sellerId;
}
