package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.entity.FixCoupon;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FixCouponRepository extends JpaRepository<FixCoupon,Long> {

    @Query(value = "select f from FixCoupon f " +
            "where f.id in (select u.fixCoupon from UserCoupon u " +
                            "where u.userId = :userId and u.usageStatus = 0 and u.fixCoupon is not null ) " +
            "and f.productId = :productId " +
            "and f.startDate <= :now and :now < f.endDate")
    public List<FixCoupon> findUserNotUsedFixCouponByproductId(long userId, long productId, LocalDateTime now);

    public List<FixCoupon> findBySellerId(long sellerId);

}
