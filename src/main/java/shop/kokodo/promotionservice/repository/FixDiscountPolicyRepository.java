package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;

import java.util.List;
import java.util.Optional;

@Repository
public interface FixDiscountPolicyRepository extends JpaRepository<FixDiscountPolicy, Long> {
    FixDiscountPolicy save(FixDiscountPolicy fixCoupon);
    Optional<FixDiscountPolicy> findById(Long id);
    Optional<FixDiscountPolicy> findByName(String name);
    Optional<FixDiscountPolicy> findByProductId(Long productId);
    List<FixDiscountPolicy> findAll();
}
