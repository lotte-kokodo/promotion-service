package shop.kokodo.promotionservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RateCouponRepository extends JpaRepository<RateCoupon, Long> {

    @Query(value = "select r from RateCoupon r " +
            "where r.id in (select u.rateCoupon from UserCoupon u " +
                            "where u.userId = :userId and u.usageStatus = 'NOT_USED' and u.rateCoupon is not null ) " +
            "and r.productId = :productId " +
            "and r.startDate <= :now and :now < r.endDate")
    public List<RateCoupon> findUserNotUsedRateCouponByproductId(long userId, long productId, LocalDateTime now);

    public List<RateCoupon> findBySellerId(long sellerId);

    @Query(value = "select r from RateCoupon r where sellerId = :sellerId group by r.name ")
    public Page<RateCoupon> findDistinctRateCouponBySellerId(long sellerId, Pageable pageable);

    @Query(value = "select * from rate_coupon where product_id= :productId " +
            "and start_date <= :now and  :now <= end_date",nativeQuery = true)
    public List<RateCoupon> findByProductId(long productId, LocalDateTime now);

    @Query(value = "select r.productId from RateCoupon r "+
            "where r.name = :name ")
    public List<Long> findProductIdByName(String name);

    List<RateCoupon> findByName(String name);

    @Query(value="select r from RateCoupon r " +
            "where r.id in :rateCouponIdList ")
    List<RateCoupon> findByIdList(List<Long> rateCouponIdList);



}
