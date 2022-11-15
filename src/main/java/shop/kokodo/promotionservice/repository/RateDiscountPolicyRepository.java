package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * packageName : shop.kokodo.promotionservice.repository
 * fileName : RateDiscountPolicyRepository
 * author : SSOsh
 * date : 2022-11-03
 * description : 비율 할인 쿠폰 관리 레포지토리
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-03           SSOsh              최초 생성
 */
public interface RateDiscountPolicyRepository extends JpaRepository<RateDiscountPolicy, Long> {
    RateDiscountPolicy save(RateDiscountPolicy rateDiscountPolicy);

    Optional<RateDiscountPolicy> findByName(String name);

    @Query(value = "SELECT r FROM RateDiscountPolicy r " +
            "WHERE r.sellerId = :sellerId ORDER BY r.name")
    List<RateDiscountPolicy> findAllBySellerId(Long sellerId);

    @Query(value = "select r from RateDiscountPolicy r " +
            "where r.productId = :productId and " +
            " r.startDate <= current_timestamp and current_timestamp <= r.endDate order by r.rate desc")
    List<RateDiscountPolicy> findAllByProductId(Long productId);

    List<RateDiscountPolicy> findAll();

    @Query(value = "select r from RateDiscountPolicy r " +
            "where  r.createdDate <= :localDateTime and :localDateTime < r.endDate")
    List<RateDiscountPolicy> findDateRangeRateDiscountPolicy(LocalDateTime localDateTime);

    @Query(value = "SELECT r FROM RateDiscountPolicy r " +
            "WHERE r.productId IN (:productIdList)")
    List<RateDiscountPolicy> findAllByProductId(List<Long> productIdList);


    @Query(value = "SELECT r.productId FROM RateDiscountPolicy r " +
            "WHERE r.name = :name")
    List<Long> findProductIdByName(String name);

    @Query(value = "SELECT r FROM RateDiscountPolicy r WHERE r.sellerId = :sellerId " +
            "AND current_timestamp  BETWEEN r.startDate AND r.endDate")
    List<RateDiscountPolicy> findBySellerId(Long sellerId);
}
