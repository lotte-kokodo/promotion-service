package shop.kokodo.promotionservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class FixCouponServiceTest {

   @Autowired
   FixCouponRepository fixCouponRepository;
   @Autowired
    RateCouponRepository rateCouponRepository;

    @Test
    public void test(){
        Long id=2L;
        List<FixCoupon> fixCouponList = fixCouponRepository.findUserNotUsedFixCouponByproductId(2,1, LocalDateTime.now());
        List<RateCoupon> rateCouponList = rateCouponRepository.findUserNotUsedRateCouponByproductId(2,1, LocalDateTime.now());


    }
}
