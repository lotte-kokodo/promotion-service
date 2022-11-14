package shop.kokodo.promotionservice.repository;

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

    @Query(value = "select u " +
            "from UserCoupon u join fetch u.rateCoupon r " +
            "where r.startDate <= :now and :now < r.endDate " +
            " and u.usageStatus = 0 " +
            " and r.productId = :productId " +
            " and u.userId= :memberId ")
    public List<UserCoupon> findRateCouponByMemberIdAndProductId(long memberId, long productId, LocalDateTime now);

    public Optional<UserCoupon> findByUserIdAndRateCoupon(long userId, RateCoupon rateCoupon);

    public Optional<UserCoupon> findByUserIdAndFixCoupon(long userId, FixCoupon fixCoupon);

    @Query(value = "select u " +
            "from UserCoupon u join fetch u.fixCoupon f " +
            "where f.startDate <= :now and :now < f.endDate " +
            " and u.usageStatus = 0 " +
            " and f.productId = :productId " +
            " and u.userId= :memberId ")
    public List<UserCoupon> findFixCouponByMemberIdAndProductId(long memberId, long productId, LocalDateTime now);

    @Query(value = "select u " +
            "from UserCoupon u join fetch u.rateCoupon r " +
            "where r.startDate <= :now and :now < r.endDate " +
            " and u.usageStatus = 0 " +
            " and r.productId in :productIdList " +
            " and u.userId= :memberId ")
    public List<UserCoupon> findByInProductIdAndMemberId(List<Long> productIdList, long memberId, LocalDateTime now);

    @Query(value =" select u from UserCoupon u where u.id in :userCouponIdList")
    List<UserCoupon> findByUserCouponIdList(List<Long> userCouponIdList);

    Optional<UserCoupon> findById(Long id);

    @Query(value = "select t.name " +
            "from ( " +
            "select distinct r.name " +
            "from rate_coupon r " +
            "where r.rate_coupon_id in ( " +
            "select u.rate_coupon_id " +
            "from user_coupon u " +
            "where :start <= u.last_modified_date and u.last_modified_date<=:end " +
            "and u.usage_status=1 and u.rate_coupon_id is not null " +
            ") and r.seller_id= :sellerId ) t  " +
            "group by t.name " +
            "order by count(t.name) " +
            "limit 1 ", nativeQuery = true)
    String findBestCoupon(long sellerId, LocalDateTime start, LocalDateTime end );

    @Query(value = "select u " +
            "from userCoupon u " +
            "where u.userId=:userId " +
            "and u.rateCoupon.id in ( " +
            "select r.id " +
            "from rateCoupon r where r.name in :rateCouponList ) " +
            "and u.usageStatus=0")
    List<UserCoupon> findByRateCouponNameList(List<String> rateCouponList, long userId);

    @Query(value = "select u " +
            "from userCoupon u " +
            "where u.userId=:userId " +
            "and u.fixCoupon.id in ( " +
            "select f.id " +
            "from fixCoupon f where f.name in :fixCouponList ) " +
            "and u.usageStatus=0")
    List<UserCoupon> findByFixCouponNameList(List<String> fixCouponList, long userId);
}
