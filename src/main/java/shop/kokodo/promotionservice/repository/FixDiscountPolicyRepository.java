package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface FixDiscountPolicyRepository extends JpaRepository<FixDiscountPolicy, Long> {
    FixDiscountPolicy save(FixDiscountPolicy fixCoupon);
    Optional<FixDiscountPolicy> findById(Long id);
    Optional<FixDiscountPolicy> findByName(String name);
    Optional<FixDiscountPolicy> findByProductId(Long productId);
    List<FixDiscountPolicy> findAll();

    @Query(value = "SELECT f FROM FixDiscountPolicy f " +
            "WHERE f.productId IN (:productIdList)")
    List<RateDiscountPolicy> findAllByProductId(List<Long> productIdList);

    Optional<FixDiscountPolicy> findByProductIdAndSellerId(Long productId, Long sellerId);
}
