package shop.kokodo.promotionservice.service;

import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.repository.RateDiscountPolicyRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateDiscountPolicyServiceImplTest {
    @InjectMocks
    RateDiscountPolicyServiceImpl rateDiscountPolicyServiceImpl;

    @Mock
    RateDiscountPolicyRepository rateDiscountPolicyRepository;

    RateDiscountPolicy rateDiscountPolicy;
    RateDiscountPolicyDto rateDiscountPolicyDto;
    Optional<RateDiscountPolicy> rateDiscountPolicyOptional;
    List<RateDiscountPolicy> rateDiscountPolicyList;

    @BeforeEach
    void setUp() {
        rateDiscountPolicy = new RateDiscountPolicy(1L, "one"
                , LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()
                , 5, 10000, 1L);
        rateDiscountPolicyDto = new RateDiscountPolicyDto(1L, "one"
                , LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()
                , 5, 10000, 1L);
        rateDiscountPolicyList = new ArrayList<>();
        rateDiscountPolicyList.add(rateDiscountPolicy);
    }

    @Test
    void getAll() {
        doReturn(rateDiscountPolicyList).when(rateDiscountPolicyRepository).findAll();

        rateDiscountPolicyServiceImpl.getAll();
    }

    @Test
    void getRateDiscountPolicy() {
        doReturn(rateDiscountPolicyOptional).when(rateDiscountPolicyRepository).findByProductId(1L);

        rateDiscountPolicyServiceImpl.getRateDiscountPolicy(1L);
    }

    @Test
    @DisplayName("비용할인정책생성")
    void createRateDiscountPolicy() {
        //doReturn(rateDiscountPolicyDto).when(rateDiscountPolicyRepository).save(rateDiscountPolicyDto);
        RateDiscountPolicyDto testPolicy = new RateDiscountPolicyDto();
        when(rateDiscountPolicyRepository.save(any(RateDiscountPolicyDto.class))).thenReturn(rateDiscountPolicyDto);

    }
}