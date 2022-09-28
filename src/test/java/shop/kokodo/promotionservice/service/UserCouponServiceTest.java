package shop.kokodo.promotionservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.entity.UserCoupon;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserCouponServiceTest {

    @Autowired UserCouponService userCouponService;
    @Autowired RateCouponService rateCouponService;

    RateCouponDto rateCouponDto1;
    RateCouponDto rateCouponDto2;
    RateCouponDto rateCouponDto3;

    UserCoupon userCoupon1;
    UserCoupon userCoupon2;
    UserCoupon userCoupon3;
    List<Long> productList;

    @BeforeEach
    public void setUp(){
        productList=new ArrayList<>();
        for(long i=0;i<10;++i) productList.add(i);

        rateCouponDto1=RateCouponDto.builder()
                .name("testCoupon1")
                .rate(10)
                .minPrice(10000)
                .startDate("2022-01-01 12:00")
                .endDate("2022-01-08 12:00")
                .productList(productList)
                .build();

        rateCouponDto2=RateCouponDto.builder()
                .name("testCoupon2")
                .rate(10)
                .minPrice(10000)
                .startDate("2022-02-01 12:00")
                .endDate("2022-02-08 12:00")
                .productList(productList)
                .build();

        rateCouponDto3=RateCouponDto.builder()
                .name("testCoupon3")
                .rate(10)
                .minPrice(10000)
                .startDate("2022-03-01 12:00")
                .endDate("2022-03-08 12:00")
                .productList(productList)
                .build();


//        userCoupon1=UserCoupon.builder()
//                .rateCoupon()
//                .build();
    }

    @Test
    public void test(){
        rateCouponDto1=RateCouponDto.builder()
                .name("testCoupon1")
                .rate(10)
                .minPrice(10000)
                .startDate("2022-01-01 12:00")
                .endDate("2022-01-08 12:00")
                .productList(productList)
                .build();
        rateCouponService.save(rateCouponDto1);
//        rateCouponService.save(rateCouponDto2);
//        rateCouponService.save(rateCouponDto3);
        Assertions.assertEquals(1,1);
    }


}
