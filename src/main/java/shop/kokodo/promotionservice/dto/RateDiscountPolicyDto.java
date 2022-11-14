package shop.kokodo.promotionservice.dto;

import lombok.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateDiscountPolicyDto {
    Long rateDiscountPolicyId;
    String name;
    LocalDateTime regDate;
    LocalDateTime startDate;
    LocalDateTime endDate;
    int rate;
    int minPrice;
    Long productId;
    Long sellerId;
}
