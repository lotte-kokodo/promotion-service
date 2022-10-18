package shop.kokodo.promotionservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSeller {
    Long productId;
    Long sellerId;
}
