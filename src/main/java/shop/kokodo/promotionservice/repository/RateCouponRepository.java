package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.entity.RateCoupon;

@Repository
public interface RateCouponRepository extends JpaRepository<RateCoupon, Long> {

}
