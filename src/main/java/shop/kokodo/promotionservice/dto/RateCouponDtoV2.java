package shop.kokodo.promotionservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * packageName    : shop.kokodo.promotionservice.dto
 * fileName       : RateCouponDtoV2
 * author         : namhyeop
 * date           : 2022/11/14
 * description    :
 * ClientSeller에 Ar 쿠폰 정보를 제공하는 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/11/14        namhyeop       최초 생성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RateCouponDtoV2 {
    private long id;
    private String name;
    private LocalDateTime regdate;
    private int rate;
    private int minPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long productId;
    private long sellerId;
}
