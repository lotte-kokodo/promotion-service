package shop.kokodo.promotionservice.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class RateDiscountPolicyRepositoryTest {
//    @Autowired
//    RateDiscountPolicyRepository rateDiscountPolicyRepository;
//
//    RateDiscountPolicy rateDiscountPolicy1;
//    RateDiscountPolicy rateDiscountPolicy2;
//    RateDiscountPolicy rateDiscountPolicy3;
//
//    final LocalDateTime now=LocalDateTime.of(2022,9,25,0,0);
//
//    @BeforeEach
//    void setUp() {
//        rateDiscountPolicy1 = RateDiscountPolicy.builder()
//                .name("rateDiscountPolicy1")
//                .regDate(now)
//                .startDate(now.minus(3, ChronoUnit.DAYS))
//                .endDate(now.plus(3, ChronoUnit.DAYS))
//                .rate(5)
//                .minPrice(10000)
//                .productId(1L)
//                .build();
//        rateDiscountPolicy2 = RateDiscountPolicy.builder()
//                .name("rateDiscountPolicy2")
//                .regDate(now)
//                .startDate(now.minus(3, ChronoUnit.DAYS))
//                .endDate(now.plus(3, ChronoUnit.DAYS))
//                .rate(6)
//                .minPrice(20000)
//                .productId(2L)
//                .build();
//        rateDiscountPolicy3 = RateDiscountPolicy.builder()
//                .name("rateDiscountPolicy3")
//                .regDate(null)
//                .startDate(null)
//                .endDate(null)
//                .rate(null)
//                .minPrice(null)
//                .productId(null)
//                .build();
//    }
//
//    @AfterEach
//    void afterAll() {
//        if(rateDiscountPolicyRepository.findAll().size() != 0) {
//            rateDiscountPolicyRepository.deleteAll();
//        }
//    }
//
//    @Test
//    @DisplayName("할인정책 save 성공")
//    void save_성공() {
//        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
//        rateDiscountPolicyRepository.save(rateDiscountPolicy2);
//
//        List<RateDiscountPolicy> list = rateDiscountPolicyRepository.findAll();
//
//        Assertions.assertEquals(list.size(), 2);
//    }
//
//    @Test
//    @DisplayName("할인정책 findById 성공")
//    void findById_성공() {
//        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
//
//        Optional<RateDiscountPolicy> value = rateDiscountPolicyRepository.findById(rateDiscountPolicy1.getRateDiscountPolicyId());
//
//        Assertions.assertEquals(rateDiscountPolicy1.getRateDiscountPolicyId(), value.get().getRateDiscountPolicyId());
//    }
//
//    @Test
//    @DisplayName("할인정책 findById 실패")
//    void findById_실패() {
//        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
//
//        assertThrows(Exception.class, () -> {
//            rateDiscountPolicyRepository.findById(rateDiscountPolicy1.getRateDiscountPolicyId() - 1).get();
//            }, "예외가 발생하지 않았습니다."
//        );
//    }
//
//    @Test
//    @DisplayName("할인정책 findByName 성공")
//    void findByName_성공() {
//        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
//
//        Optional<RateDiscountPolicy> value = rateDiscountPolicyRepository.findByName(rateDiscountPolicy1.getName());
//
//        Assertions.assertEquals(rateDiscountPolicy1.getRateDiscountPolicyId(), value.get().getRateDiscountPolicyId());
//    }
//
//    @Test
//    @DisplayName("할인정책 findByName 실패")
//    void findByName_실패() {
//        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
//
//        assertThrows(Exception.class, () -> {
//                    rateDiscountPolicyRepository.findByName(null).get();
//                }, "예외가 발생하지 않았습니다."
//        );
//    }
//
//    @Test
//    @DisplayName("할인정책 findByProductId 성공")
//    void findByProductId_성공() {
//        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
//
//        Optional<RateDiscountPolicy> value = rateDiscountPolicyRepository.findById(rateDiscountPolicy1.getProductId());
//
//        Assertions.assertEquals(rateDiscountPolicy1.getRateDiscountPolicyId(), value.get().getRateDiscountPolicyId());
//    }
//
//    @Test
//    @DisplayName("할인정책 findByProductId 실패")
//    void findByProductId_실패() {
//        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
//
//        assertThrows(Exception.class, () -> {
//                    rateDiscountPolicyRepository.findByName(null).get();
//                }, "예외가 발생하지 않았습니다."
//        );
//    }
//
//    @Test
//    @DisplayName("할인정책 findAll 성공")
//    void findAll_성공() {
//        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
//        rateDiscountPolicyRepository.save(rateDiscountPolicy2);
//
//        List<RateDiscountPolicy> list = rateDiscountPolicyRepository.findAll();
//        Assertions.assertEquals(list.size(), 2);
//    }
//
//    @Test
//    @DisplayName("할인정책 findAll 실패")
//    void findAll_실패() {
//        assertThrows(Exception.class, () -> {
//            List<RateDiscountPolicy> list = rateDiscountPolicyRepository.findAll();
//            list.get(0);
//                }, "예외가 발생하지 않았습니다."
//        );
//    }

}