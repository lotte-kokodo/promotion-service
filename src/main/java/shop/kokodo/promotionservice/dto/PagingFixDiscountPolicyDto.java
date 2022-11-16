package shop.kokodo.promotionservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;

import java.util.List;

/**
 * packageName : shop.kokodo.promotionservice.dto
 * fileName : PagingFixDiscountPolicyDto
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
public class PagingFixDiscountPolicyDto {
    long totalCount;

    List<FixDiscountPolicy> fixDiscountPolicyList;

    @Builder
    public PagingFixDiscountPolicyDto(long totalCount, List<FixDiscountPolicy> fixDiscountPolicyList)  {
        this.totalCount = totalCount;
        this.fixDiscountPolicyList = fixDiscountPolicyList;
    }
}
