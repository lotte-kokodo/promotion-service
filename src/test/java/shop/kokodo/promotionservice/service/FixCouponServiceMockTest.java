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
import org.springframework.data.domain.PageRequest;
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.exception.DuplicateCouponNameException;
import shop.kokodo.promotionservice.feign.MemberServiceClient;
import shop.kokodo.promotionservice.feign.ProductServiceClient;
import shop.kokodo.promotionservice.feign.SellerServiceClient;
import shop.kokodo.promotionservice.repository.FixCouponRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class FixCouponServiceMockTest {

    @InjectMocks
    FixCouponServiceImpl fixCouponServiceImpl;
    @Mock
    FixCouponRepository fixCouponRepository;

    @Mock
    MemberServiceClient memberServiceClient;
    @Mock
    ProductServiceClient productServiceClient;
    @Mock
    SellerServiceClient sellerServiceClient;
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


//    @DisplayName("???????????? ?????? ?????? ??????")
//    @Test
//    public void save_success(){
//        FixCoupon fixCoupon=FixCoupon.builder().build();
//        doReturn(fixCoupon).when(fixCouponRepository).save(fixCoupon);
//
//        fixCouponServiceImpl.save(fixCouponDto);
//    }
    @Test
    @DisplayName("????????? ?????? ????????? ?????? ?????? ??????")
    public void save_fail_couponName(){
        FixCoupon fixCoupon = FixCoupon.builder().name("testCoupon").build();
        Optional<FixCoupon> fixCouponOptional = Optional.of(fixCoupon);
        doReturn(fixCouponOptional).when(fixCouponRepository).findByName("testCoupon");

        Assertions.assertThrows(DuplicateCouponNameException.class, () -> fixCouponServiceImpl.save(fixCouponDto));
    }
    @Test
    @DisplayName("???????????? ?????? ????????? ?????? ?????? ?????? product id??? ?????? ??????")
    public void findUserNotUsedFixCouponByproductIdSuccess(){
        final long userId=100L;
        final long productId=1L;
        final LocalDateTime day= LocalDateTime.of(2022,10,1,0,0);
        coupons = new ArrayList<>();
        boolean memberFlag = true;
        boolean productFlag = true;

        doReturn(coupons).when(fixCouponRepository)
                .findUserNotUsedFixCouponByproductId(userId,productId,day);
        doReturn(memberFlag).when(memberServiceClient).getMember(userId);
        doReturn(productFlag).when(productServiceClient).findProductById(productId);


        List<FixCoupon> getCoupons=fixCouponServiceImpl.findUserNotUsedFixCouponByproductId(userId,productId);
        Assertions.assertEquals(getCoupons.size(),coupons.size());

    }

//    @Test
//    @DisplayName("seller id??? ???????????? ?????? ?????? ??????")
//    public void findBySellerIdSuccess(){
//        final long sellerId= 10L;
//        coupons=new ArrayList<>();
//        doReturn(true).when(sellerServiceClient).getSeller(sellerId);
//        doReturn(coupons).when(fixCouponRepository).findBySellerId(sellerId, PageRequest.of(1,10));
//
//        List<FixCoupon> getCoupons = fixCouponServiceImpl.findBySellerId(sellerId,1).getFixCouponList();
//        Assertions.assertEquals(getCoupons.size(),coupons.size());
//    }


}
