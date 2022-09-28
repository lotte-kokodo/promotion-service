package shop.kokodo.promotionservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.repository.RateCouponRepository;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RateCouponServiceMockTest {

    @InjectMocks
    RateCouponServiceImpl rateCouponService;

    @Mock
    RateCouponRepository rateCouponRepository;

    RateCouponDto rateCouponDto;

    @BeforeEach
    public void setUp(){
        List<Long> productList=new ArrayList<>();
        for(long i=0;i<10;++i) productList.add(i);

        rateCouponDto=RateCouponDto.builder()
                .name("testCoupon")
                .rate(10)
                .minPrice(10000)
                .startDate("2022-01-01 12:00")
                .endDate("2022-01-08 12:00")
                .productList(productList)
                .build();
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @DisplayName("save rate coupon success")
    @Test
    public void save(){
        RateCoupon rateCoupon=RateCoupon.builder().build();
        doReturn(rateCoupon).when(rateCouponRepository).save(rateCoupon);

        rateCouponService.save(rateCouponDto);
    }
}
