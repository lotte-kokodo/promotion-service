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

    @BeforeEach
    public void setUp(){
        rateCoupon= RateCoupon.builder()
                .name("fixCoupon1")
                .regdate(LocalDateTime.now())
                .rate(5)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2022,10,1,0,0))
                .productId(1)
                .sellerId(1)
                .build();

        rateCoupon2=RateCoupon.builder()
                .name("fixCoupon2")
                .regdate(LocalDateTime.now())
                .rate(10)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2022,10,1,0,0))
                .productId(1)
                .sellerId(1)
                .build();

        rateCoupon3=RateCoupon.builder()
                .name("fixCoupon3")
                .regdate(LocalDateTime.now())
                .rate(1)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,20,0,0))
                .endDate(LocalDateTime.of(2022,10,1,0,0))
                .productId(2)
                .sellerId(1)
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
    @DisplayName("상품ID로 유저의 사용하지 않은 비율할인 쿠폰 조회")
    public void findUserNotUsedFixCouponByproductId(){
        rateCouponRepository.save(rateCoupon);
        rateCouponRepository.save(rateCoupon2);
        rateCouponRepository.save(rateCoupon3);
        userCouponRepository.save(userCoupon);
        userCouponRepository.save(userCoupon2);
        userCouponRepository.save(userCoupon3);

        List<RateCoupon> list=rateCouponRepository.findUserNotUsedRateCouponByproductId(userId, productId,now);

        Assertions.assertEquals(list.size(),2);
    }

}
