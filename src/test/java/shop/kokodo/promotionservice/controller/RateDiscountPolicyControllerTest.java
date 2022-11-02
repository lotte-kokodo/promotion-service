package shop.kokodo.promotionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.repository.RateDiscountPolicyRepository;
import shop.kokodo.promotionservice.service.RateDiscountPolicyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@WebAppConfiguration
class RateDiscountPolicyControllerTest {
//    @Autowired
//    private RateDiscountPolicyRepository rateDiscountPolicyRepository;
//
//    @BeforeEach
//    public void init() {
//
//    }
//
//    @Test
//    void 모든_비용할인정책_가져오기() {
//
//    }
//
//    @Test
//    void 셀러_비용할인정책_가져오기() {
//
//    }
//
//    @Test
//    void 셀러_비용할인정책_실패_sellerId_없음() {
//
//    }
//
//    @Test
//    void 상품_비용할인정책_가져오기() {
//
//    }
//
//    @Test
//    void 상품_비용할인정책_실패_productId_없음() {
//
//    }
//
//    @Test
//    void 비용할인정책_등록_성공() {
//
//    }
//
//    @Test
//    void 비용할인정책_등록_실패() {
//
//    }
}