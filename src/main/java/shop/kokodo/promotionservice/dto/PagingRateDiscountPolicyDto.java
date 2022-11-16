package shop.kokodo.promotionservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;

import java.util.List;

/**
 * packageName : shop.kokodo.promotionservice.dto
 * fileName : PagingRateDiscountPolicyDto
 * author : BTC-N24
 * date : 2022-11-15
 * description :
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-15           BTC-N24              최초 생성
 */
@Data
@NoArgsConstructor
public class PagingRateDiscountPolicyDto {
    long totalCount;

    List<RateDiscountPolicy> rateDiscountPolicyList;

    @Builder
    public PagingRateDiscountPolicyDto(long totalCount, List<RateDiscountPolicy> rateDiscountPolicyList) {
        this.totalCount = totalCount;
        this.rateDiscountPolicyList = rateDiscountPolicyList;
    }
}
