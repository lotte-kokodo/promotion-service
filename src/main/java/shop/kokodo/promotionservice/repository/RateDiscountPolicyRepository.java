package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;

import java.util.List;
import java.util.Optional;

public interface RateDiscountPolicyRepository extends JpaRepository<RateDiscountPolicy, Long> {
    RateDiscountPolicyDto save(RateDiscountPolicyDto fixCoupon);
    Optional<RateDiscountPolicy> findById(Long id);
    Optional<RateDiscountPolicy> findByName(String name);
    Optional<RateDiscountPolicy> findByProductId(Long productId);
    List<RateDiscountPolicy> findAll();
}
