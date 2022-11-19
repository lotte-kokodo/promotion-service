package shop.kokodo.promotionservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.entity.FixCoupon;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FixCouponRepository extends JpaRepository<FixCoupon,Long> {

    @Query(value = "select f from FixCoupon f " +
            "where f.id in (select u.fixCoupon from UserCoupon u " +
                            "where u.userId = :userId and u.usageStatus = 'NOT_USED' and u.fixCoupon is not null ) " +
            "and f.productId = :productId " +
            "and f.startDate <= :now and :now < f.endDate")
    public List<FixCoupon> findUserNotUsedFixCouponByproductId(long userId, long productId, LocalDateTime now);

    @Query(value = "select f from FixCoupon f where f.sellerId= :sellerId group by name ")
    public Page<FixCoupon> findBySellerId(long sellerId, Pageable pageable);

    @Query(value = "select f.productId from FixCoupon f "+
            "where f.name = :name ")
    public List<Long> findProductIdByName(String name);

    @Query(value= " select f " +
            "from FixCoupon f " +
            "where f.freeDelivery = true and f.startDate<=:now and :now <=f.endDate " +
            "and f.productId not in( select d.productId from FixDiscountPolicy d where d.startDate <= :now and :now <=d.endDate and d.productId in :productIdList ) " +
            "and f.id in (select u.fixCoupon.id from UserCoupon u where u.usageStatus= 'NOT_USED' and u.fixCoupon is not null and u.userId=:memberId ) ")
    List<FixCoupon> findValidFixCoupon(Long memberId, List<Long> productIdList, LocalDateTime now);

    Optional<FixCoupon> findByName(String name);

    @Query(value="select f from FixCoupon f where f.id in :couponIdList ")
    List<FixCoupon> findByCouponIdList(List<Long> couponIdList);

    @Query(value = "select f from FixCoupon f where f.productId = :productId")
    List<FixCoupon> findByProductId(Long productId);


}
