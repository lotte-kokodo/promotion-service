package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.entity.FixCoupon;
@Repository
public interface FixCouponRepository extends JpaRepository<FixCoupon,Long> {


}
