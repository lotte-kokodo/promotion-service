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
import shop.kokodo.promotionservice.dto.PagingRateCouponDto;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.feign.MemberServiceClient;
import shop.kokodo.promotionservice.feign.ProductServiceClient;
import shop.kokodo.promotionservice.feign.SellerServiceClient;
import shop.kokodo.promotionservice.repository.RateCouponRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.doReturn;
@MockitoSettings(strictness = Strictness.WARN)
@ExtendWith(MockitoExtension.class)
public class RateCouponServiceMockTest {

    @InjectMocks
    RateCouponServiceImpl rateCouponService;

    @Mock
    RateCouponRepository rateCouponRepository;
    @Mock
    MemberServiceClient memberServiceClient;
    @Mock
    ProductServiceClient productServiceClient;
    @Mock
    SellerServiceClient sellerServiceClient;

    RateCouponDto rateCouponDto;
    List<RateCoupon> coupons;

    @BeforeEach
    public void setUp(){
        List<Long> productList=new ArrayList<>();
        for(long i=0;i<10;++i) productList.add(i);

        rateCouponDto=RateCouponDto.builder()
                .name("testCoupon")
                .rate(10)
                .minPrice(10000)
                .startDate("2022-01-01 00:00:00")
                .endDate("2023-01-08 00:00:00")
                .productList(productList)
                .build();
    }
//    @Test
//    @DisplayName("비율할인 쿠폰 생성 성공")
//    public void save(){
//        RateCoupon rateCoupon=RateCoupon.builder().build();
//        doReturn(rateCoupon).when(rateCouponRepository).save(rateCoupon);
//
//        rateCouponService.save(rateCouponDto);
//    }

    @Test
    @DisplayName("사용되지 않은 유저의 비율 할인 쿠폰 product id로 조회 성공")
    public void findUserNotUsedFixCouponByproductIdSuccess(){
        final long userId=100L;
        final long productId=1L;
        final LocalDateTime day= LocalDateTime.of(2022,10,1,0,0);
        coupons = new ArrayList<>();
        boolean memberFlag = true;
        boolean productFlag = true;
        doReturn(coupons).when(rateCouponRepository)
                .findUserNotUsedRateCouponByproductId(userId,productId,day);
        doReturn(memberFlag).when(memberServiceClient).getMember(userId);
        doReturn(productFlag).when(productServiceClient).findProductById(productId);

        List<RateCoupon> getCoupons=rateCouponService.findUserNotUsedRateCouponByproductId(userId,productId);
        Assertions.assertEquals(getCoupons.size(),coupons.size());

    }

//    @Test
//    @DisplayName("seller id로 비율 할인 쿠폰 조회 성공")
//    public void findBySellerIdSuccess(){
//        final long sellerId= 10L;
//        coupons=new ArrayList<>();
//        boolean sellerFlag = true;
//
//        doReturn(sellerFlag).when(sellerServiceClient).getSeller(sellerId);
//        doReturn(coupons).when(rateCouponRepository).findBySellerId(sellerId);
//
//        PagingRateCouponDto getCoupons = rateCouponService.findBySellerId(sellerId,0);
//        Assertions.assertEquals(getCoupons.getTotalCount(),coupons.size());
//    }
}
