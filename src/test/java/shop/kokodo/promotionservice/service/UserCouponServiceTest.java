package shop.kokodo.promotionservice.service;

import org.apache.kafka.common.metrics.stats.Rate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.kokodo.promotionservice.dto.ProductIdAndRateCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
public class UserCouponServiceTest {

    @Autowired UserCouponService userCouponService;
    @Autowired
    UserCouponRepository userCouponRepository;
    @Autowired
    RateCouponRepository rateCouponRepository;
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
    @DisplayName("사용자가 가진 productId와 RateCoupon 리스트 조회")
    public void rateCouponList(){
        List<Long> productIdList = new ArrayList<>();
        productIdList.add(2L);
        productIdList.add(3L);

        rateCouponRepository.save(rateCoupon);
        rateCouponRepository.save(rateCoupon2);
        rateCouponRepository.save(rateCoupon3);
        userCouponRepository.save(userCoupon4);
        userCouponRepository.save(userCoupon5);
        userCouponRepository.save(userCoupon6);


        Map<Long, List<RateCoupon>> productIdAndRateCouponDtos
            =userCouponService.findRateCouponByMemberIdAndProductId(productIdList,userId);

        System.out.println(productIdAndRateCouponDtos.size());

//        for (ProductIdAndRateCouponDto productIdAndRateCouponDto : productIdAndRateCouponDtos) {
//            System.out.println(productIdAndRateCouponDto.getProductId()+" "+productIdAndRateCouponDto.getRateCouponList());
//        }
    }
}
