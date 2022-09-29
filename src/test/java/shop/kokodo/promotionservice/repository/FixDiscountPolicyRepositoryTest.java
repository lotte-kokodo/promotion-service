package shop.kokodo.promotionservice.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FixDiscountPolicyRepositoryTest {
    @Autowired
    FixDiscountPolicyRepository fixDiscountPolicyRepository;

    FixDiscountPolicy fixDiscountPolicy1;
    FixDiscountPolicy fixDiscountPolicy2;
    FixDiscountPolicy fixDiscountPolicy3;

    final Long fixDiscountPolicyId=1L;
    final Long productId=1L;
    final LocalDateTime now=LocalDateTime.of(2022,9,25,0,0);

    @BeforeEach
    void setUp() {
        fixDiscountPolicy1 = FixDiscountPolicy.builder()
                .name("fixDiscountPolicy1")
                .regDate(now)
                .startDate(now.minus(3, ChronoUnit.DAYS))
                .endDate(now.plus(3, ChronoUnit.DAYS))
                .price(1000)
                .minPrice(10000)
                .productId(1L)
                .build();
        fixDiscountPolicy2 = FixDiscountPolicy.builder()
                .name("FixDiscountPolicy2")
                .regDate(now)
                .startDate(now.minus(3, ChronoUnit.DAYS))
                .endDate(now.plus(3, ChronoUnit.DAYS))
                .price(3000)
                .minPrice(20000)
                .productId(2L)
                .build();
        fixDiscountPolicy3 = FixDiscountPolicy.builder()
                .name("FixDiscountPolicy3")
                .regDate(null)
                .startDate(null)
                .endDate(null)
                .price(3000)
                .minPrice(0)
                .productId(0)
                .build();
    }

    @AfterEach
    void afterAll() {
        if(fixDiscountPolicyRepository.findAll().size() != 0 ) {
            fixDiscountPolicyRepository.deleteAll();
        }
    }

    @Test
    @DisplayName("고정할인정책 save 성공")
    void save_성공() {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
        fixDiscountPolicyRepository.save(fixDiscountPolicy2);

        List<FixDiscountPolicy> list = fixDiscountPolicyRepository.findAll();

        Assertions.assertEquals(list.size(), 2);
    }

    @Test
    @DisplayName("고정할인정책 save 실패")
    void save_실패() {
        assertThrows(Exception.class, () -> {
                    fixDiscountPolicyRepository.save(fixDiscountPolicy3);
                }, "예외가 발생하지 않았습니다."
        );

    }

    @Test
    @DisplayName("고정할인정책 findById 성공")
    void findById_성공() {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);

        Optional<FixDiscountPolicy> value = fixDiscountPolicyRepository.findById(fixDiscountPolicy1.getFixDiscountPolicyId());

        Assertions.assertEquals(fixDiscountPolicy1.getFixDiscountPolicyId(), value.get().getFixDiscountPolicyId());
    }

    @Test
    @DisplayName("고정할인정책 findById 실패")
    void findById_실패() {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);

        assertThrows(Exception.class, () -> {
                    fixDiscountPolicyRepository.findById(fixDiscountPolicy1.getFixDiscountPolicyId() - 1).get();
                }, "예외가 발생하지 않았습니다."
        );
    }

    @Test
    @DisplayName("고정할인정책 findByName 성공")
    void findByName_성공() {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);

        Optional<FixDiscountPolicy> value = fixDiscountPolicyRepository.findByName(fixDiscountPolicy1.getName());

        Assertions.assertEquals(fixDiscountPolicy1.getName(), value.get().getName());
    }

    @Test
    @DisplayName("고정할인정책 findByName 실패")
    void findByName_실패() {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);

        assertThrows(Exception.class, () -> {
                    fixDiscountPolicyRepository.findByName(null).get();
                }, "예외가 발생하지 않았습니다."
        );
    }

    @Test
    @DisplayName("고정할인정책 findByProductId 성공")
    void findByProductId_성공() {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);

        Optional<FixDiscountPolicy> value = fixDiscountPolicyRepository.findById(fixDiscountPolicy1.getProductId());

        Assertions.assertEquals(fixDiscountPolicy1.getFixDiscountPolicyId(), value.get().getFixDiscountPolicyId());
    }

    @Test
    @DisplayName("고정할인정책 findByProductId 실패")
    void findByProductId_실패() {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);

        assertThrows(Exception.class, () -> {
                    fixDiscountPolicyRepository.findByName(null).get();
                }, "예외가 발생하지 않았습니다."
        );
    }

    @Test
    @DisplayName("고정할인정책 findAll 성공")
    void findAll_성공() {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
        fixDiscountPolicyRepository.save(fixDiscountPolicy2);

        List<FixDiscountPolicy> list = fixDiscountPolicyRepository.findAll();
        Assertions.assertEquals(list.size(), 2);
    }

    @Test
    @DisplayName("고정할인정책 findAll 실패")
    void findAll_실패() {
        assertThrows(Exception.class, () -> {
                    List<FixDiscountPolicy> list = fixDiscountPolicyRepository.findAll();
                    list.get(0);
                }, "예외가 발생하지 않았습니다."
        );
    }
}