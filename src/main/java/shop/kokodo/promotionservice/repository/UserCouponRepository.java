package shop.kokodo.promotionservice.repository;

import org.h2.engine.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon,Long> {

    List<UserCoupon> findByUserId(long userId);

    @Query(value = "select u " +
            "from UserCoupon u join fetch u.fixCoupon f " +
            "where f.startDate <= :now and :now < f.endDate " +
            " and u.usageStatus = 0 " +
            " and u.userId= :memberId " +
            "group by f.name" )
    public List<UserCoupon> findFixCouponByMemberId(long memberId, LocalDateTime now);

    @Query(value = "select u " +
            "from UserCoupon u join fetch u.rateCoupon r " +
            "where r.startDate <= :now and :now < r.endDate " +
            " and u.usageStatus = 0 " +
            " and u.userId= :memberId " +
            "group by r.name" )
    public List<UserCoupon> findRateCouponByMemberId(long memberId, LocalDateTime now);

    public Optional<UserCoupon> findByUserIdAndRateCoupon(long userId, RateCoupon rateCoupon);

    public Optional<UserCoupon> findByUserIdAndFixCoupon(long userId, FixCoupon fixCoupon);
}

