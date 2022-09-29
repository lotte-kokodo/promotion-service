package shop.kokodo.promotionservice.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class FixCouponRepositoryTest {

   @Autowired FixCouponRepository fixCouponRepository;
   @Autowired UserCouponRepository userCouponRepository;

   FixCoupon fixCoupon;
   FixCoupon fixCoupon2;
   FixCoupon fixCoupon3;

   UserCoupon userCoupon;
   UserCoupon userCoupon2;
   UserCoupon userCoupon3;

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
              .endDate(LocalDateTime.of(2022,10,1,0,0))
              .productId(1)
              .sellerId(1)
              .build();

      fixCoupon2=FixCoupon.builder()
              .name("fixCoupon2")
              .regdate(LocalDateTime.now())
              .price(1000)
              .minPrice(10000)
              .startDate(LocalDateTime.of(2022,9,20,0,0))
              .endDate(LocalDateTime.of(2022,10,1,0,0))
              .productId(1)
              .sellerId(1)
              .build();

      fixCoupon3=FixCoupon.builder()
              .name("fixCoupon3")
              .regdate(LocalDateTime.now())
              .price(1000)
              .minPrice(10000)
              .startDate(LocalDateTime.of(2022,9,20,0,0))
              .endDate(LocalDateTime.of(2022,10,1,0,0))
              .productId(2)
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
   }

   @Test
   @DisplayName("고정 할인 쿠폰 생성 성공")
   public void fixCouponSaveSuccess(){
      FixCoupon saveCoupon=fixCouponRepository.save(fixCoupon);
      checkFixCoupon(fixCoupon,saveCoupon);
   }

   private void checkFixCoupon(FixCoupon expected, FixCoupon actual){
      Assertions.assertEquals(expected.getName(),actual.getName());
      Assertions.assertEquals(expected.getProductId(),actual.getProductId());
      Assertions.assertEquals(expected.getMinPrice(), actual.getMinPrice());
      Assertions.assertEquals(expected.getPrice(), actual.getPrice());
      Assertions.assertEquals(expected.getSellerId(), actual.getSellerId());
      Assertions.assertEquals(expected.getStartDate(), actual.getStartDate());
      Assertions.assertEquals(expected.getEndDate(), actual.getEndDate());
      Assertions.assertEquals(expected.getRegdate(),actual.getRegdate());

   }

   @Test
   @DisplayName("상품ID로 유저의 사용하지 않은 고정할인 쿠폰 조회 성공")
   public void findUserNotUsedFixCouponByproductIdSuccess(){
      fixCouponRepository.save(fixCoupon);
      fixCouponRepository.save(fixCoupon2);
      fixCouponRepository.save(fixCoupon3);
      userCouponRepository.save(userCoupon);
      userCouponRepository.save(userCoupon2);
      userCouponRepository.save(userCoupon3);

      List<FixCoupon> list=fixCouponRepository.findUserNotUsedFixCouponByproductId(userId, productId,now);

      Assertions.assertEquals(list.size(),2);
   }

   @Test
   @DisplayName("Seller ID로 쿠폰 조회 성공")
   public void findBySellerIdSuccess(){
      fixCouponRepository.save(fixCoupon);
      fixCouponRepository.save(fixCoupon2);
      fixCouponRepository.save(fixCoupon3);

      List<FixCoupon> coupons = fixCouponRepository.findBySellerId(selectSellerId);

      Assertions.assertEquals(coupons.size(),1);
      Assertions.assertEquals(coupons.get(0).getName(),"fixCoupon3");
   }

}
