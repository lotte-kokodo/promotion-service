package shop.kokodo.promotionservice.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FixDiscountPolicyRepositoryTest {
//    @Autowired
//    FixDiscountPolicyRepository fixDiscountPolicyRepository;
//
//    FixDiscountPolicy fixDiscountPolicy1;
//    FixDiscountPolicy fixDiscountPolicy2;
//    FixDiscountPolicy fixDiscountPolicy3;
//
//    final LocalDateTime now=LocalDateTime.of(2022,9,25,0,0);
//
//    @BeforeEach
//    void setUp() {
//        fixDiscountPolicy1 = FixDiscountPolicy.builder()
//                .name("fixDiscountPolicy1")
//                .regDate(now)
//                .startDate(now.minus(3, ChronoUnit.DAYS))
//                .endDate(now.plus(3, ChronoUnit.DAYS))
//                .price(1000)
//                .minPrice(10000)
//                .productId(1L)
//                .build();
//        fixDiscountPolicy2 = FixDiscountPolicy.builder()
//                .name("FixDiscountPolicy2")
//                .regDate(now)
//                .startDate(now.minus(3, ChronoUnit.DAYS))
//                .endDate(now.plus(3, ChronoUnit.DAYS))
//                .price(3000)
//                .minPrice(20000)
//                .productId(2L)
//                .build();
//        fixDiscountPolicy3 = FixDiscountPolicy.builder()
//                .name("FixDiscountPolicy3")
//                .regDate(null)
//                .startDate(null)
//                .endDate(null)
//                .price(3000)
//                .minPrice(null)
//                .productId(null)
//                .build();
//    }
//    @AfterEach
//    void afterAll() {
//        if(fixDiscountPolicyRepository.findAll().size() != 0 ) {
//            fixDiscountPolicyRepository.deleteAll();
//        }
//    }
//
//    @Test
//    @DisplayName("?????????????????? save ??????")
//    void save_??????() {
//        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
//        fixDiscountPolicyRepository.save(fixDiscountPolicy2);
//
//        List<FixDiscountPolicy> list = fixDiscountPolicyRepository.findAll();
//
//        Assertions.assertEquals(list.size(), 2);
//    }
//
//    @Test
//    @DisplayName("?????????????????? findById ??????")
//    void findById_??????() {
//        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
//
//        Optional<FixDiscountPolicy> value = fixDiscountPolicyRepository.findById(fixDiscountPolicy1.getFixDiscountPolicyId());
//
//        Assertions.assertEquals(fixDiscountPolicy1.getFixDiscountPolicyId(), value.get().getFixDiscountPolicyId());
//    }
//
//    @Test
//    @DisplayName("?????????????????? findById ??????")
//    void findById_??????() {
//        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
//
//        assertThrows(Exception.class, () -> {
//                    fixDiscountPolicyRepository.findById(fixDiscountPolicy1.getFixDiscountPolicyId() - 1).get();
//                }, "????????? ???????????? ???????????????."
//        );
//    }
//
//    @Test
//    @DisplayName("?????????????????? findByName ??????")
//    void findByName_??????() {
//        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
//
//        Optional<FixDiscountPolicy> value = fixDiscountPolicyRepository.findByName(fixDiscountPolicy1.getName());
//
//        Assertions.assertEquals(fixDiscountPolicy1.getName(), value.get().getName());
//    }
//
//    @Test
//    @DisplayName("?????????????????? findByName ??????")
//    void findByName_??????() {
//        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
//
//        assertThrows(Exception.class, () -> {
//                    fixDiscountPolicyRepository.findByName(null).get();
//                }, "????????? ???????????? ???????????????."
//        );
//    }
//
//    @Test
//    @DisplayName("?????????????????? findByProductId ??????")
//    void findByProductId_??????() {
//        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
//
//        Optional<FixDiscountPolicy> value = fixDiscountPolicyRepository.findById(fixDiscountPolicy1.getProductId());
//
//        Assertions.assertEquals(fixDiscountPolicy1.getFixDiscountPolicyId(), value.get().getFixDiscountPolicyId());
//    }
//
//    @Test
//    @DisplayName("?????????????????? findByProductId ??????")
//    void findByProductId_??????() {
//        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
//
//        assertThrows(Exception.class, () -> {
//                    fixDiscountPolicyRepository.findByName(null).get();
//                }, "????????? ???????????? ???????????????."
//        );
//    }
//
//    @Test
//    @DisplayName("?????????????????? findAll ??????")
//    void findAll_??????() {
//        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
//        fixDiscountPolicyRepository.save(fixDiscountPolicy2);
//
//        List<FixDiscountPolicy> list = fixDiscountPolicyRepository.findAll();
//        Assertions.assertEquals(list.size(), 2);
//    }
//
//    @Test
//    @DisplayName("?????????????????? findAll ??????")
//    void findAll_??????() {
//        assertThrows(Exception.class, () -> {
//                    List<FixDiscountPolicy> list = fixDiscountPolicyRepository.findAll();
//                    list.get(0);
//                }, "????????? ???????????? ???????????????."
//        );
//    }
}