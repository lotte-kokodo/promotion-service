package shop.kokodo.promotionservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CouponNameDto {

    long memberId;

    List<String> fixCouponNames;

    List<String> rateCouponNames;


    @Builder
    public CouponNameDto(long memberId, List<String> fixCouponNames, List<String> rateCouponNames) {
        this.memberId = memberId;
        this.fixCouponNames = fixCouponNames;
        this.rateCouponNames = rateCouponNames;
    }
}
