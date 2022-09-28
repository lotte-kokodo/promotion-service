package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;

import java.util.List;
import java.util.Optional;

@Repository
public interface FixDiscountPolicyRepository extends JpaRepository<FixDiscountPolicyDto, Long> {
    FixDiscountPolicyDto save(FixDiscountPolicy fixCoupon);
    Optional<FixDiscountPolicyDto> findById(Long id);
    Optional<FixDiscountPolicyDto> findByName(String name);
    Optional<FixDiscountPolicyDto> findByProductId(Long productId);
    List<FixDiscountPolicyDto> findAll();
}
