package shop.kokodo.promotionservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.repository.RateCouponRepository;

import java.time.LocalDateTime;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RateCouponServiceTest {

    @InjectMocks
    RateCouponServiceImpl rateCouponService;

    @Mock
    RateCouponRepository rateCouponRepository;

    RateCoupon rateCoupon;

    @BeforeEach
    public void setUp(){
        rateCoupon=new RateCoupon(1L, "testCoupon", LocalDateTime.now(),5,
                10000,LocalDateTime.of(2022,9,1, 0,0),
                LocalDateTime.of(2022,9,8, 0,0),100L);
    }

    @DisplayName("save mock success")
    @Test
    public void save(){
        doReturn(rateCoupon).when(rateCouponRepository).save(rateCoupon);

        rateCouponService.save(rateCoupon);
    }
}
