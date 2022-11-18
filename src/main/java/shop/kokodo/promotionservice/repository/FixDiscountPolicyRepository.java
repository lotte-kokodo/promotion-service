package shop.kokodo.promotionservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;

import java.util.List;
import java.util.Optional;

/**
 * packageName : shop.kokodo.promotionservice.repository
 * fileName : FixDiscountPolicyRepository
 * author : SSOsh
 * date : 2022-11-03
 * description : 고정 할인 쿠폰 관리 레포지토리
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-03           SSOsh              최초 생성
 */

public interface FixDiscountPolicyRepository extends JpaRepository<FixDiscountPolicy, Long> {
    FixDiscountPolicy save(FixDiscountPolicy fixDiscountPolicy);
    Optional<FixDiscountPolicy> findById(Long id);
    Optional<FixDiscountPolicy> findByName(String name);
    Optional<FixDiscountPolicy> findByProductId(Long productId);
    List<FixDiscountPolicy> findAll();

    @Query(value = "SELECT f FROM FixDiscountPolicy f " +
            "WHERE f.productId IN (:productIdList)")
    List<FixDiscountPolicy> findAllByProductId(List<Long> productIdList);

    @Query(value = "SELECT f FROM FixDiscountPolicy f " +
            "WHERE f.productId = :productId AND f.sellerId = :sellerId")
    FixDiscountPolicy findAllByProductIdAndSellerIdIn(Long productId, Long sellerId);

//    @Query(value = "SELECT f FROM FixDiscountPolicy f " +
//            "WHERE f.sellerId IN (:sellerId)")
//    List<FixDiscountPolicy> findAllBySellerId(Long sellerId);

//    @Query(value = "SELECT f FROM FixDiscountPolicy f WHERE f.sellerId = :sellerId " +
//            "AND current_timestamp BETWEEN f.startDate AND f.endDate GROUP BY f.name ")
//    Page<FixDiscountPolicy> findAllBySellerId(Long sellerId);

    @Query(value = "SELECT f FROM FixDiscountPolicy f " +
            "WHERE f.sellerId = :sellerId GROUP BY f.name")
    Page<FixDiscountPolicy> findBySellerId(Long sellerId, Pageable pageable);

    @Query(value = "SELECT f.productId FROM FixDiscountPolicy f " +
            "WHERE f.name = :name")
    List<Long> findProductIdByName(String name);

    boolean existsByName(String name);
}
