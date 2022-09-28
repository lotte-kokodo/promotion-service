package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.kokodo.promotionservice.entity.UserCoupon;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon,Long> {

    List<UserCoupon> findByUserId(long userId);
}
