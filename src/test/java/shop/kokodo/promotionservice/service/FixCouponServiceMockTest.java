package shop.kokodo.promotionservice.service;

import org.junit.jupiter.api.Assertions;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class FixCouponServiceMockTest {

    @InjectMocks
    FixCouponServiceImpl fixCouponServiceImpl;

    @Mock
    FixCouponRepository fixCouponRepository;

    FixCouponDto fixCouponDto;
    List<FixCoupon> coupons;

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


    @DisplayName("고정할인 쿠폰 생성 성공")
    @Test
    public void save(){
        FixCoupon fixCoupon=FixCoupon.builder().build();
        doReturn(fixCoupon).when(fixCouponRepository).save(fixCoupon);

        fixCouponServiceImpl.save(fixCouponDto);
    }

    @Test
    @DisplayName("사용되지 않은 유저의 고정 할인 쿠폰 product id로 조회 성공")
    public void findUserNotUsedFixCouponByproductIdSuccess(){
        final long userId=100L;
        final long productId=1L;
        final LocalDateTime day= LocalDateTime.of(2022,10,1,0,0);
        coupons = new ArrayList<>();

        doReturn(coupons).when(fixCouponRepository)
                .findUserNotUsedFixCouponByproductId(userId,productId,day);

        List<FixCoupon> getCoupons=fixCouponServiceImpl.findUserNotUsedFixCouponByproductId(userId,productId);
        Assertions.assertEquals(getCoupons.size(),coupons.size());

    }

    @Test
    @DisplayName("seller id로 고정할인 쿠폰 조회 성공")
    public void findBySellerIdSuccess(){
        final long sellerId= 10L;
        coupons=new ArrayList<>();
        doReturn(coupons).when(fixCouponRepository).findBySellerId(sellerId);

        List<FixCoupon> getCoupons = fixCouponServiceImpl.findBySellerId(sellerId);
        Assertions.assertEquals(getCoupons.size(),coupons.size());
    }


}
