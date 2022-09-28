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
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class FixCouponServiceMockTest {

    @InjectMocks
    FixCouponServiceImpl fixCouponServiceImpl;

    @Mock
    FixCouponRepository fixCouponRepository;

    FixCouponDto fixCouponDto;

    @BeforeEach
    public void setUp(){
        List<Long> productList=new ArrayList<>();
        for(long i=0;i<10;++i) productList.add(i);

        fixCouponDto = FixCouponDto.builder()
                .name("testCoupon")
                .price(1000)
                .minPrice(10000)
                .startDate("2022-01-01 12:00")
                .endDate("2022-01-08 12:00")
                .productList(productList)
                .build();
    }

    @MockitoSettings(strictness = Strictness.WARN)
    @DisplayName("save fix coupon success")
    @Test
    public void save(){
        FixCoupon fixCoupon=FixCoupon.builder().build();
        doReturn(fixCoupon).when(fixCouponRepository).save(fixCoupon);

        fixCouponServiceImpl.save(fixCouponDto);
    }
}
