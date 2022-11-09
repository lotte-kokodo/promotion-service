package shop.kokodo.promotionservice.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
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
@ActiveProfiles("test")
public class FixCouponRepositoryTest {

   @Autowired FixCouponRepository fixCouponRepository;
   @Autowired UserCouponRepository userCouponRepository;
   @Autowired FixDiscountPolicyRepository fixDiscountPolicyRepository;

   FixCoupon fixCoupon;
   FixCoupon fixCoupon2;
   FixCoupon fixCoupon3;
   UserCoupon userCoupon;
   UserCoupon userCoupon2;
   UserCoupon userCoupon3;
   FixDiscountPolicy fixDiscountPolicy;
   final Long userId=1L;
   final Long productId=1L;
   final LocalDateTime now=LocalDateTime.of(2022,9,25,0,0);
   final LocalDateTime startDate = LocalDateTime.of(2022,9,25,0,0);
   final LocalDateTime endDate = LocalDateTime.of(2023,9,25,0,0);
   final Long selectSellerId = 2L;
   @BeforeEach
   public void setUp(){
      fixCoupon=FixCoupon.builder()
              .name("fixCoupon1")
              .regdate(LocalDateTime.now())
              .price(1000)
              .minPrice(10000)
              .startDate(startDate)
              .endDate(endDate)
              .productId(1)
              .sellerId(1)
              .freeDelivery(true)
              .build();

      fixCoupon2=FixCoupon.builder()
              .name("fixCoupon2")
              .regdate(LocalDateTime.now())
              .price(1000)
              .minPrice(10000)
              .startDate(startDate)
              .endDate(endDate)
              .productId(1)
              .sellerId(1)
              .freeDelivery(true)
              .build();

      fixCoupon3=FixCoupon.builder()
              .name("fixCoupon3")
              .regdate(LocalDateTime.now())
              .price(1000)
              .minPrice(10000)
              .startDate(startDate)
              .endDate(endDate)
              .productId(2)
              .sellerId(selectSellerId)
              .freeDelivery(true)
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

      fixDiscountPolicy = FixDiscountPolicy.builder()
              .name("fixDiscountPolicy")
              .regDate(now)
              .startDate(startDate)
              .endDate(endDate)
              .price(1000)
              .minPrice(10000)
              .productId(1L)
              .sellerId(selectSellerId)
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
      saveFixCoupons(fixCoupon,fixCoupon2,fixCoupon3);
      saveUserCoupons(userCoupon,userCoupon2,userCoupon3);

      List<FixCoupon> list=fixCouponRepository.findUserNotUsedFixCouponByproductId(userId, productId,now);

      Assertions.assertEquals(list.size(),2);
   }

   @Test
   @DisplayName("Seller ID로 쿠폰 조회 성공")
   public void findBySellerIdSuccess(){
      saveFixCoupons(fixCoupon,fixCoupon2,fixCoupon3);

      List<FixCoupon> coupons = fixCouponRepository.findBySellerId(1L);
      for (FixCoupon coupon : coupons) {
         System.out.println(coupon.toString());
      }

      Assertions.assertEquals(coupons.size(),2);
   }

   @Test
   @DisplayName("고정쿠폰 이름으로 product id 리스트 조회")
   public void findProductIdByName(){

      saveFixCoupons(fixCoupon,fixCoupon2);

      final String couponName = fixCoupon.getName();

      List<Long> productIdList =fixCouponRepository.findProductIdByName(couponName);

      Assertions.assertEquals(productIdList.size(),1);
      Assertions.assertEquals(fixCoupon.getProductId(),productIdList.get(0));
   }


   @Test
   @DisplayName("유저의 사용 가능한 무료배송 쿠폰 조회")
   public void findValidFixCoupon(){
      final long memberId = 1L;

      saveFixCoupons(fixCoupon,fixCoupon2,fixCoupon3);
      saveUserCoupons(userCoupon,userCoupon2,userCoupon3);
      fixDiscountPolicyRepository.save(fixDiscountPolicy);

      List<Long> productIdList = new ArrayList<>();
      productIdList.add(1L);
      productIdList.add(2L);

      List<FixCoupon> fixCoupons = fixCouponRepository.findValidFixCoupon(memberId,productIdList,LocalDateTime.now());

      Assertions.assertEquals(fixCoupons.size(),1);
      for (FixCoupon coupon : fixCoupons) {
         Assertions.assertEquals(coupon.getProductId()==1L || coupon.getProductId()==2L, true);
      }
   }

   @Test
   @DisplayName("이름으로 고정 할인 쿠폰 조회")
   public void findByName(){
      saveFixCoupons(fixCoupon,fixCoupon2,fixCoupon3);

      final String couponName = fixCoupon.getName();

      FixCoupon selectFixCoupon = fixCouponRepository.findByName(couponName).get();

      Assertions.assertEquals(selectFixCoupon.getName(),couponName);
   }

   @Test
   @DisplayName("couponId 리스트로 고정할인 쿠폰 조회")
   public void findByCouponIdList(){
      List<Long> fixCouponIdList = new ArrayList<>();

      fixCouponIdList.add(fixCouponRepository.save(fixCoupon).getId());
      fixCouponIdList.add(fixCouponRepository.save(fixCoupon2).getId());
      fixCouponRepository.save(fixCoupon3);

      List<FixCoupon> fixCoupons = fixCouponRepository.findByCouponIdList(fixCouponIdList);

      Assertions.assertEquals(fixCoupons.size(),2);
   }

   private void saveFixCoupons(FixCoupon ... fixCoupons){
      for(int i=0;i<fixCoupons.length;++i){
         fixCouponRepository.save(fixCoupons[i]);
      }
   }
   private void saveUserCoupons(UserCoupon ... userCoupons){
      for(int i=0;i<userCoupons.length;++i){
         userCouponRepository.save(userCoupons[i]);
      }
   }



}
