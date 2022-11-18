package shop.kokodo.promotionservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : shop.kokodo.calculateservice.dto.ardto
 * fileName       : CouponInfo
 * author         : namhyeop
 * date           : 2022/11/02
 * description    :
 * AR 쿠폰 자체 정보를 담은 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/11/02        namhyeop       최초 생성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArRateCouponInfo {
    private long sellerId;
    private List<Long> productId;
    private String couponName;
    private int rate;
    private Double x;
    private Double y;
    private Double z;
    private int minPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime regDate;
}
