package shop.kokodo.promotionservice.repository;

import org.apache.kafka.common.metrics.stats.Rate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UsageStatus;
import shop.kokodo.promotionservice.entity.UserCoupon;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class RateCouponRepositoryTest {

    @Autowired RateCouponRepository rateCouponRepository;
    @Autowired
    UserCouponRepository userCouponRepository;

    RateCoupon rateCoupon;
    RateCoupon rateCoupon2;
    RateCoupon rateCoupon3;
    RateCoupon rateCoupon4;

    UserCoupon userCoupon;
    UserCoupon userCoupon2;
    UserCoupon userCoupon3;
    final Long userId=1L;
    final Long productId=1L;
    final LocalDateTime now=LocalDateTime.of(2022,9,25,0,0);
    final LocalDateTime startDate=LocalDateTime.of(2022,9,25,0,0);
    final LocalDateTime endDate=LocalDateTime.of(2023,9,25,0,0);

    final Long selectSellerId = 2L;

    @BeforeEach
    public void setUp(){
        rateCoupon= RateCoupon.builder()
                .name("rateCoupon")
                .regdate(LocalDateTime.now())
                .rate(5)
                .minPrice(10000)
                .startDate(startDate)
                .endDate(endDate)
                .productId(1)
                .sellerId(1)
                .build();

        rateCoupon2=RateCoupon.builder()
                .name("rateCoupon2")
                .regdate(LocalDateTime.now())
                .rate(10)
                .minPrice(10000)
                .startDate(startDate)
                .endDate(endDate)
                .productId(1)
                .sellerId(1)
                .build();

        rateCoupon3=RateCoupon.builder()
                .name("rateCoupon3")
                .regdate(LocalDateTime.now())
                .rate(1)
                .minPrice(10000)
                .startDate(startDate)
                .endDate(endDate)
                .productId(2)
                .sellerId(selectSellerId)
                .build();

        rateCoupon4= RateCoupon.builder()
                .name("rateCoupon")
                .regdate(LocalDateTime.now())
                .rate(5)
                .minPrice(10000)
                .startDate(startDate)
                .endDate(endDate)
                .productId(3)
                .sellerId(1)
                .build();

        userCoupon = UserCoupon.builder()
                .rateCoupon(rateCoupon)
                .userId(1)
                .usageStatus(UsageStatus.NOT_USED)
                .build();

        userCoupon2 = UserCoupon.builder()
                .rateCoupon(rateCoupon2)
                .userId(1)
                .usageStatus(UsageStatus.NOT_USED)
                .build();

        userCoupon3 = UserCoupon.builder()
                .rateCoupon(rateCoupon3)
                .userId(1)
                .usageStatus(UsageStatus.NOT_USED)
                .build();
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ??????")
    public void rateCouponSaveSuccess(){
        RateCoupon saveCoupon=rateCouponRepository.save(rateCoupon);
        checkRateCoupon(rateCoupon,saveCoupon);
    }

    private void checkRateCoupon(RateCoupon expected, RateCoupon actual){
        Assertions.assertEquals(expected.getName(),actual.getName());
        Assertions.assertEquals(expected.getProductId(),actual.getProductId());
        Assertions.assertEquals(expected.getMinPrice(), actual.getMinPrice());
        Assertions.assertEquals(expected.getRate(), actual.getRate());
        Assertions.assertEquals(expected.getSellerId(), actual.getSellerId());
        Assertions.assertEquals(expected.getStartDate(), actual.getStartDate());
        Assertions.assertEquals(expected.getEndDate(), actual.getEndDate());
        Assertions.assertEquals(expected.getRegdate(),actual.getRegdate());

    }

    @Test
    @DisplayName("??????ID??? ????????? ???????????? ?????? ???????????? ?????? ??????")
    public void findUserNotUsedFixCouponByproductIdSuccess(){
        saveRateCoupon(rateCoupon,rateCoupon2,rateCoupon3);
        saveUserCoupon(userCoupon,userCoupon2,userCoupon3);

        List<RateCoupon> list=rateCouponRepository.findUserNotUsedRateCouponByproductId(userId, productId,now);

        Assertions.assertEquals(list.size(),2);
    }

    @Test
    @DisplayName("Seller ID??? ?????? ?????? ??????")
    public void findBySellerIdSuccess(){
        saveRateCoupon(rateCoupon,rateCoupon2,rateCoupon3);

        List<RateCoupon> coupons = rateCouponRepository.findBySellerId(selectSellerId);

        Assertions.assertEquals(coupons.size(),1);
        Assertions.assertEquals(coupons.get(0).getName(),"rateCoupon3");
    }

//    @Test
//    @DisplayName("seller Id??? Rate Coupon ?????? ?????? ??????")
//    public void findDistinctRateCouponBySellerId(){
//        saveRateCoupon(rateCoupon,rateCoupon2,rateCoupon3,rateCoupon4);
//
//        List<RateCoupon> rateCoupons = rateCouponRepository.findDistinctRateCouponBySellerId(1L);
//
//        Assertions.assertEquals(rateCoupons.size(),2);
//
//    }

    @Test
    @DisplayName("product id??? ????????? rate coupon ??????")
    public void findByProductId(){
        saveRateCoupon(rateCoupon,rateCoupon2,rateCoupon3,rateCoupon4);

        final long productId = 1L;

        List<RateCoupon> rateCoupons = rateCouponRepository.findByProductId(productId,LocalDateTime.now());

        for (RateCoupon coupon : rateCoupons) {
            Assertions.assertEquals(coupon.getProductId(),productId);
        }

    }
    @Test
    @DisplayName("?????? ?????? ?????? ????????? ???????????? product Id ????????? ??????")
    public void findProductIdByName(){
        saveRateCoupon(rateCoupon,rateCoupon2,rateCoupon3,rateCoupon4);

        List<Long> selectedProductId = rateCouponRepository.findProductIdByName("rateCoupon");

        Assertions.assertEquals(2, selectedProductId.size());
    }

    @Test
    @DisplayName("?????? ???????????? rate coupon ??????")
    public void findByName(){
        saveRateCoupon(rateCoupon,rateCoupon2,rateCoupon3,rateCoupon4);
        final String couponName = "rateCoupon";

        List<RateCoupon> rateCoupons = rateCouponRepository.findByName(couponName);

        for (RateCoupon coupon : rateCoupons) {
            Assertions.assertEquals(coupon.getName(),couponName);
        }
    }

    @Test
    @DisplayName("rate coupon id ???????????? rate coupon ????????????")
    public void findByIdList(){
        List<Long> idList = new ArrayList<>();
        idList.add(rateCouponRepository.save(rateCoupon).getId());
        idList.add(rateCouponRepository.save(rateCoupon2).getId());

        List<RateCoupon> rateCoupons = rateCouponRepository.findByIdList(idList);

        Assertions.assertEquals(rateCoupons.size(),2);
    }


    private void saveRateCoupon(RateCoupon ... rateCoupons){
        for(int i=0;i<rateCoupons.length;++i)
            rateCouponRepository.save(rateCoupons[i]);
    }

    private void saveUserCoupon(UserCoupon ... userCoupons){
        for(int i=0;i<userCoupons.length;++i)
            userCouponRepository.save(userCoupons[i]);
    }

}
