package shop.kokodo.promotionservice.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
//@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RateDiscountPolicyRepositoryTest{
    @Autowired
    RateDiscountPolicyRepository rateDiscountPolicyRepository;

    Long sellerId;
    Long productId;

    List<Long> productIdList;

    RateDiscountPolicy rateDiscountPolicy1;
    RateDiscountPolicy rateDiscountPolicy2;
    RateDiscountPolicy rateDiscountPolicy3;

    final LocalDateTime now= LocalDateTime.now();


    @BeforeEach
    @Transactional
    void setUp() {
        rateDiscountPolicy1 = RateDiscountPolicy.builder()
                .rateDiscountPolicyId(1L)
                .name("rateDiscountPolicy1")
                .regDate(now)
                .startDate(LocalDateTime.now().minusDays(3))
                .endDate(LocalDateTime.now().minusDays(3))
                .rate(5)
                .minPrice(10000)
                .productId(1L)
                .sellerId(1L)
                .build();
        rateDiscountPolicy2 = RateDiscountPolicy.builder()
                .rateDiscountPolicyId(2L)
                .name("rateDiscountPolicy2")
                .regDate(now)
                .startDate(LocalDateTime.now().minusDays(3))
                .endDate(LocalDateTime.now().minusDays(3))
                .rate(6)
                .minPrice(20000)
                .productId(2L)
                .sellerId(1L)
                .build();
        rateDiscountPolicy3 = RateDiscountPolicy.builder()
                .rateDiscountPolicyId(3L)
                .name("rateDiscountPolicy3")
                .regDate(null)
                .startDate(null)
                .endDate(null)
                .rate(null)
                .minPrice(null)
                .productId(null)
                .sellerId(null)
                .build();
    }

    @AfterEach
    void afterAll() {
        if(rateDiscountPolicyRepository.findAll().size() > 0) {
            rateDiscountPolicyRepository.deleteAll();
        }
    }

    @Test
    @DisplayName("???????????? save ??????")
    void save_??????() {
        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
        rateDiscountPolicyRepository.save(rateDiscountPolicy2);

        List<RateDiscountPolicy> list = rateDiscountPolicyRepository.findAll();

        Assertions.assertEquals(list.size(), 2);
    }

    @Test
    @DisplayName("???????????? findByName ??????")
    void findByName_??????() {
        rateDiscountPolicyRepository.save(rateDiscountPolicy1);

        Optional<RateDiscountPolicy> value = rateDiscountPolicyRepository.findByName(rateDiscountPolicy1.getName());

        Assertions.assertEquals(rateDiscountPolicy1.getName(), value.get().getName());
    }

    @Test
    @DisplayName("???????????? findByName ??????")
    void findByName_??????() {
        rateDiscountPolicyRepository.save(rateDiscountPolicy1);

        assertThrows(Exception.class, () -> {
                    rateDiscountPolicyRepository.findByName(null).get();
                }, "????????? ???????????? ???????????????."
        );
    }

//    @Test
//    @DisplayName("???????????? findAll ??????")
//    void findAll_??????() {
//        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
//        rateDiscountPolicyRepository.save(rateDiscountPolicy2);
//
//        List<RateDiscountPolicy> list = rateDiscountPolicyRepository.findAll();
//        Assertions.assertEquals(list.size(), 2);
//    }

    @Test
    @DisplayName("???????????? findAll ??????")
    void findAll_??????() {
        assertThrows(Exception.class, () -> {
            List<RateDiscountPolicy> list = rateDiscountPolicyRepository.findAll();
            list.get(0);
                }, "????????? ???????????? ???????????????."
        );
    }

    @Test
    @DisplayName("sellerId??? ???????????? findAll ??????")
    void findAllBySellerId_??????() {
        sellerId = 1L;
        int page = 0;
        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
        rateDiscountPolicyRepository.save(rateDiscountPolicy2);

        Page<RateDiscountPolicy> rateDiscountPolicyList= rateDiscountPolicyRepository.findAllBySellerId(sellerId, PageRequest.of(page,5));
        Assertions.assertEquals(rateDiscountPolicyList.getTotalElements(), 2);
    }

//    @Test
//    @DisplayName("productId??? ???????????? findAll ??????")
//    void findAllByProductId_??????() {
//        productId = 1L;
//        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
//        rateDiscountPolicyRepository.save(rateDiscountPolicy2);
//
//        List<RateDiscountPolicy> rateDiscountPolicyList = rateDiscountPolicyRepository.findAllByProductId(productId);
//        Assertions.assertEquals(rateDiscountPolicyList.size(), 1);
//    }

//    @Test
//    @DisplayName("????????? ???????????? findAll ??????")
//    void findDateRangeRateDiscountPolicy_??????() {
//        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
//        rateDiscountPolicyRepository.save(rateDiscountPolicy2);
//
//        List<RateDiscountPolicy> rateDiscountPolicyList = rateDiscountPolicyRepository.findDateRangeRateDiscountPolicy(LocalDateTime.now());
//
//        Assertions.assertEquals(rateDiscountPolicyList.size(), 2);
//    }

    @Test
    @DisplayName("productIdList??? ???????????? findAll ??????")
    void findAllByProductIdList_??????() {
        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
        rateDiscountPolicyRepository.save(rateDiscountPolicy2);

        productIdList = new ArrayList<>();
        productIdList.add(1L);
        productIdList.add(2L);

        List<RateDiscountPolicy> rateDiscountPolicyList = rateDiscountPolicyRepository.findAllByProductId(productIdList);
        Assertions.assertEquals(rateDiscountPolicyList.size(), 2);
    }


}