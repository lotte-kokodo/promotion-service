package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RateDiscountPolicyRepository extends JpaRepository<RateDiscountPolicy, Long> {
    RateDiscountPolicy save(RateDiscountPolicy rateDiscountPolicy);
    Optional<RateDiscountPolicy> findById(Long id);
    Optional<RateDiscountPolicy> findByName(String name);

    @Query(value = "SELECT r FROM RateDiscountPolicy r " +
            "WHERE r.sellerId IN (:sellerId)")
    List<RateDiscountPolicy> findAllBySellerId(Long sellerId);
    Optional<RateDiscountPolicy> findByProductId(Long productId);
    List<RateDiscountPolicy> findAll();

    @Query(value = "select r from RateDiscountPolicy r " +
            "where  r.createdDate <= :localDateTime and :localDateTime < r.endDate")
    List<RateDiscountPolicy> findDateRangeRateDiscountPolicy(LocalDateTime localDateTime);

    @Query(value = "SELECT r FROM RateDiscountPolicy r " +
            "WHERE r.productId IN (:productIdList)")
    List<RateDiscountPolicy> findAllByProductId(List<Long> productIdList);



}
