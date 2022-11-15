package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.kokodo.promotionservice.dto.ArRateCouponInfo;
import shop.kokodo.promotionservice.entity.ArRateCoupon;

import java.util.List;

/**
 * packageName    : shop.kokodo.calculateservice.repository
 * fileName       : ArCouponRepository
 * author         : namhyeop
 * date           : 2022/11/02
 * description    :
 * ArRateCoupon Repository
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/11/02        namhyeop       최초 생성
 */
public interface ArRateCouponRepository extends JpaRepository<ArRateCoupon, Long> {
    @Query("select ar from ArRateCoupon ar")
    List<ArRateCouponInfo> findCouponBySellerId();

    @Query("select ar from ArRateCoupon ar join fetch ar.rateCoupon")
    List<ArRateCoupon> findArCoupon();
}
