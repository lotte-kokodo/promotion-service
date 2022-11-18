package shop.kokodo.promotionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.kokodo.promotionservice.dto.RateCouponDtoV2;

/**
 * packageName    : shop.kokodo.promotionservice.entity
 * fileName       : ArClientRateCouponDto
 * author         : namhyeop
 * date           : 2022/11/14
 * description    : ClientAR에 제공되는 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/11/14        namhyeop       최초 생성
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArClientRateCouponDto {
    private RateCouponDtoV2 rateCoupon;
    private Double xPos;
    private Double yPos;
    private Double zPos;
}
