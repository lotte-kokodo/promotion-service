package shop.kokodo.promotionservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.kokodo.promotionservice.dto
 * fileName       : ArRateCouponFindDto
 * author         : namhyeop
 * date           : 2022/11/15
 * description    :
 * view에서 Client가 쿠폰을 찾는 이벤트 발생시 쿠폰을 저장해주기 위해 필요한 정보를 담은 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/11/15        namhyeop       최초 생성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArRateCouponFindDto {
    private Long clientId;
    private Long couponId;
}
