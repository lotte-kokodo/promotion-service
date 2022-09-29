package shop.kokodo.promotionservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class RateCouponRepositoryTest {

    @Autowired RateCouponRepository rateCouponRepository;
    @Autowired
    UserCouponRepository userCouponRepository;

    RateCoupon rateCoupon;
    RateCoupon rateCoupon2;
    RateCoupon rateCoupon3;

    UserCoupon userCoupon;
    UserCoupon userCoupon2;
    UserCoupon userCoupon3;
    final Long userId=1L;
    final Long productId=1L;
    final LocalDateTime now=LocalDateTime.of(2022,9,25,0,0);

    final Long selectSellerId = 2L;

    @BeforeEach
    public void setUp(){
        rateCoupon= RateCoupon.builder()
                .name("rateCoupon")
                .regdate(LocalDateTime.now())
                .rate(5)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2022,10,1,0,0))
                .productId(1)
                .sellerId(1)
                .build();

        rateCoupon2=RateCoupon.builder()
                .name("rateCoupon2")
                .regdate(LocalDateTime.now())
                .rate(10)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2022,10,1,0,0))
                .productId(1)
                .sellerId(1)
                .build();

        rateCoupon3=RateCoupon.builder()
                .name("rateCoupon3")
                .regdate(LocalDateTime.now())
                .rate(1)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2022,10,1,0,0))
                .productId(2)
                .sellerId(selectSellerId)
                .build();

        userCoupon = UserCoupon.builder()
                .rateCoupon(rateCoupon)
                .userId(1)
                .usageStatus(0)
                .build();

        userCoupon2 = UserCoupon.builder()
                .rateCoupon(rateCoupon2)
                .userId(1)
                .usageStatus(0)
                .build();

        userCoupon3 = UserCoupon.builder()
                .rateCoupon(rateCoupon3)
                .userId(1)
                .usageStatus(0)
                .build();
    }

    @Test
    @DisplayName("비율 할인 쿠폰 생성 성공")
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
    @DisplayName("상품ID로 유저의 사용하지 않은 비율할인 쿠폰 조회")
    public void findUserNotUsedFixCouponByproductIdSuccess(){
        rateCouponRepository.save(rateCoupon);
        rateCouponRepository.save(rateCoupon2);
        rateCouponRepository.save(rateCoupon3);
        userCouponRepository.save(userCoupon);
        userCouponRepository.save(userCoupon2);
        userCouponRepository.save(userCoupon3);

        List<RateCoupon> list=rateCouponRepository.findUserNotUsedRateCouponByproductId(userId, productId,now);

        Assertions.assertEquals(list.size(),2);
    }

    @Test
    @DisplayName("Seller ID로 쿠폰 조회 성공")
    public void findBySellerIdSuccess(){
        rateCouponRepository.save(rateCoupon);
        rateCouponRepository.save(rateCoupon2);
        rateCouponRepository.save(rateCoupon3);

        List<RateCoupon> coupons = rateCouponRepository.findBySellerId(selectSellerId);

        Assertions.assertEquals(coupons.size(),1);
        Assertions.assertEquals(coupons.get(0).getName(),"rateCoupon3");
    }

}
