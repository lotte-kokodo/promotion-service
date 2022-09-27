package shop.kokodo.promotionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateDiscountPolicyRepository extends JpaRepository<RateDiscountPolicyDto, Long> {
    RateDiscountPolicyDto save(RateDiscountPolicyDto fixCoupon);
    Optional<RateDiscountPolicyDto> findById(Long id);
    Optional<RateDiscountPolicyDto> findByName(String name);
    Optional<RateDiscountPolicyDto> findByProductId(Long productId);
    List<RateDiscountPolicyDto> findAll();
}
