package shop.kokodo.promotionservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserCouponRepositoryTest {
    @Autowired
    FixCouponRepository fixCouponRepository;
    @Autowired UserCouponRepository userCouponRepository;
    @Autowired RateCouponRepository rateCouponRepository;
    FixCoupon fixCoupon;
    FixCoupon fixCoupon2;
    FixCoupon fixCoupon3;

    RateCoupon rateCoupon;
    RateCoupon rateCoupon2;
    RateCoupon rateCoupon3;

    UserCoupon userCoupon;
    UserCoupon userCoupon2;
    UserCoupon userCoupon3;
    UserCoupon userCoupon4;
    UserCoupon userCoupon5;
    UserCoupon userCoupon6;
    final Long userId=1L;
    final Long productId=1L;
    final LocalDateTime now=LocalDateTime.of(2022,9,25,0,0);
    final Long selectSellerId = 2L;
    @BeforeEach
    public void setUp(){
        fixCoupon=FixCoupon.builder()
                .name("fixCoupon1")
                .regdate(LocalDateTime.now())
                .price(1000)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2023,10,1,0,0))
                .productId(1)
                .sellerId(1)
                .build();

        fixCoupon2=FixCoupon.builder()
                .name("fixCoupon2")
                .regdate(LocalDateTime.now())
                .price(1000)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2023,10,1,0,0))
                .productId(1)
                .sellerId(1)
                .build();

        fixCoupon3=FixCoupon.builder()
                .name("fixCoupon3")
                .regdate(LocalDateTime.now())
                .price(1000)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2023,10,1,0,0))
                .productId(2)
                .sellerId(selectSellerId)
                .build();

        rateCoupon=RateCoupon.builder()
                .name("RateCoupon1")
                .regdate(LocalDateTime.now())
                .rate(10)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2023,10,1,0,0))
                .productId(2)
                .sellerId(selectSellerId)
                .build();

        rateCoupon2=RateCoupon.builder()
                .name("RateCoupon2")
                .regdate(LocalDateTime.now())
                .rate(10)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2023,10,1,0,0))
                .productId(2)
                .sellerId(selectSellerId)
                .build();

        rateCoupon3=RateCoupon.builder()
                .name("RateCoupon3")
                .regdate(LocalDateTime.now())
                .rate(10)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2023,10,1,0,0))
                .productId(3)
                .sellerId(selectSellerId)
                .build();


        userCoupon = UserCoupon.builder()
                .fixCoupon(fixCoupon)
                .userId(1)
                .usageStatus(0)
                .build();

        userCoupon2 = UserCoupon.builder()
                .fixCoupon(fixCoupon2)
                .userId(1)
                .usageStatus(0)
                .build();

        userCoupon3 = UserCoupon.builder()
                .fixCoupon(fixCoupon3)
                .userId(1)
                .usageStatus(0)
                .build();

        userCoupon4=UserCoupon.builder()
                .rateCoupon(rateCoupon)
                .userId(userId)
                .usageStatus(0)
                .build();

        userCoupon5=UserCoupon.builder()
                .rateCoupon(rateCoupon2)
                .userId(userId)
                .usageStatus(0)
                .build();

        userCoupon6=UserCoupon.builder()
                .rateCoupon(rateCoupon3)
                .userId(userId)
                .usageStatus(0)
                .build();
    }

    @Test
    @DisplayName("user id로 user coupon 조회")
    public void findByUserId(){
        saveRateCoupon(rateCoupon,rateCoupon2,rateCoupon3);
        saveUserCoupon(userCoupon4, userCoupon5, userCoupon6);

        List<UserCoupon> userCoupons = userCouponRepository.findByUserId(userId);

        Assertions.assertEquals(userCoupons.size(),3);
        for (UserCoupon coupon : userCoupons) {
            Assertions.assertEquals(coupon.getUserId(), userId);
        }

    }

    @Test
    @DisplayName("Member ID로 고정 할인 쿠폰 조회 성공")
    public void findFixCouponByMemberId(){
        saveFixCoupon(fixCoupon,fixCoupon2,fixCoupon3);
        saveUserCoupon(userCoupon,userCoupon2,userCoupon3);

        List<UserCoupon> coupons = userCouponRepository.findFixCouponByMemberId(userId,now);

        Assertions.assertEquals(coupons.size(),3);
    }


    @Test
    @DisplayName("Member ID로 비율 할인 쿠폰 조회 성공")
    public void findRateCouponByMemberId(){
        saveFixCoupon(fixCoupon,fixCoupon2,fixCoupon3);
        saveRateCoupon(rateCoupon);
        saveUserCoupon(userCoupon,userCoupon2,userCoupon3,userCoupon4);

        List<UserCoupon> coupons = userCouponRepository.findRateCouponByMemberId(userId,now);

        Assertions.assertEquals(coupons.size(),1);
    }

    @Test
    @DisplayName("user id와 rate-coupon으로 조회 성공")
    public void findByUserIdAndRateCoupon(){
        saveRateCoupon(rateCoupon);
        saveUserCoupon(userCoupon4);

        Optional<UserCoupon> couponOp = userCouponRepository.findByUserIdAndRateCoupon(userId,rateCoupon);

        Assertions.assertEquals(true,couponOp.isPresent());
    }

    @Test
    @DisplayName("user id와 fix-coupon으로 조회 성공")
    public void findByUserIdAndFixCoupon(){
        saveFixCoupon(fixCoupon);
        saveUserCoupon(userCoupon);

        Optional<UserCoupon> couponOp = userCouponRepository.findByUserIdAndFixCoupon(userId,fixCoupon);

        Assertions.assertEquals(true,couponOp.isPresent());
    }

    @Test
    @DisplayName("memberId와 productList로 RateCoupon(UserCoupon) 조회")
    public void findByInProductIdAndMemberId(){
        List<Long> productIdList = new ArrayList<>();
        productIdList.add(rateCouponRepository.save(rateCoupon).getId());
        productIdList.add(rateCouponRepository.save(rateCoupon2).getId());

        saveUserCoupon(userCoupon4,userCoupon5);

        List<UserCoupon> list = userCouponRepository.findByInProductIdAndMemberId(productIdList,userId,now);

        Assertions.assertEquals(list.size(),productIdList.size());
    }

    @Test
    @DisplayName("userCouponId 리스트로 UserCoupon 조회하기_성공")
    public void findByUserCouponIdList(){
        saveFixCoupon(fixCoupon,fixCoupon2,fixCoupon3);

        List<Long> idList = new ArrayList<>();
        idList.add(userCouponRepository.save(userCoupon).getId());
        idList.add(userCouponRepository.save(userCoupon2).getId());
        userCouponRepository.save(userCoupon3).getId();

        List<UserCoupon> list = userCouponRepository.findByUserCouponIdList(idList);

        Assertions.assertEquals(list.size(),2);
    }

    private void saveFixCoupon(FixCoupon ... fixCoupons){
        for(int i=0;i<fixCoupons.length;++i){
            fixCouponRepository.save(fixCoupons[i]);
        }
    }
    private void saveRateCoupon(RateCoupon ... rateCoupons){
        for(int i=0;i<rateCoupons.length;++i){
            rateCouponRepository.save(rateCoupons[i]);
        }
    }
    private void saveUserCoupon(UserCoupon ... userCoupons){
        for(int i=0;i<userCoupons.length;++i){
            userCouponRepository.save(userCoupons[i]);
        }
    }
}