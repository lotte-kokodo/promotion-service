package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RateCouponRepository extends JpaRepository<RateCoupon, Long> {

    @Query(value = "select r from RateCoupon r " +
            "where r.id in (select u.rateCoupon from UserCoupon u " +
                            "where u.userId = :userId and u.usageStatus = 0 and u.rateCoupon is not null ) " +
            "and r.productId = :productId " +
            "and r.startDate <= :now and :now < r.endDate")
    public List<RateCoupon> findUserNotUsedRateCouponByproductId(long userId, long productId, LocalDateTime now);

    public List<RateCoupon> findBySellerId(long sellerId);

    @Query(value = "select r from RateCoupon r group by r.name")
    public List<RateCoupon> findDistinctRateCouponBySellerId(long sellerId);

    @Query(value = "select r from RateCoupon r " +
            "where r.productId = :productId and " +
            " startDate <= :now and :now <=endDate order by r.rate desc")
    public List<RateCoupon> findByProductId(long productId, LocalDateTime now);

    @Query(value = "select r.productId from RateCoupon r "+
                "where r.name = :name ")
    public List<Long> findProductIdByName(String name);

}
