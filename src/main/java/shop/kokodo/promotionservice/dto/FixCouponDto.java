package shop.kokodo.promotionservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class FixCouponDto {
    long id;
    String name;
    int price;
    int minPrice;
    String startDate;
    String endDate;
    List<Long> productList;

    long sellerId;

    @Builder
    public FixCouponDto(long id, String name, int price, int minPrice, String startDate, String endDate, List<Long> productList, long sellerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.minPrice = minPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productList = productList;
        this.sellerId=sellerId;
    }
}
