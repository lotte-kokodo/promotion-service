package shop.kokodo.promotionservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.annotation.BeforeStep;
import shop.kokodo.promotionservice.dto.UserCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
@MockitoSettings(strictness = Strictness.WARN)
@ExtendWith(MockitoExtension.class)
public class UserCouponServiceTest {

    @InjectMocks
    UserCouponServiceImpl userCouponService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    UserCouponRepository userCouponRepository;

    @Mock
    FixCouponRepository fixCouponRepository;

    @Mock
    RateCouponRepository rateCouponRepository;

    UserCouponDto fixUserCouponDto;
    UserCouponDto rateUserCouponDto;
    UserCoupon fixUserCoupon;
    UserCoupon rateUserCoupon;

    UserCouponDto failUserCouponDto;

    final long fixCouponId=100L;
    final long rateCouponId=200L;

    @BeforeEach
    public void setUp(){
        fixUserCouponDto=UserCouponDto.builder()
                .id(1L)
                .userId(2L)
                .usageStatus(0)
                .fixCouponId(fixCouponId)
                .build();

        rateUserCouponDto=UserCouponDto.builder()
                .id(2L)
                .userId(2L)
                .usageStatus(0)
                .rateCouponId(rateCouponId)
                .build();

        fixUserCoupon = UserCoupon.builder()
                .id(1L)
                .userId(fixUserCouponDto.getUserId())
                .usageStatus(0)
                .fixCoupon(new FixCoupon())
                .build();

        rateUserCoupon=UserCoupon.builder()
                .id(1L)
                .userId(fixUserCouponDto.getUserId())
                .usageStatus(0)
                .rateCoupon(new RateCoupon())
                .build();

        failUserCouponDto = UserCouponDto.builder()
                .id(1L)
                .userId(2L)
                .usageStatus(0)
                .build();
    }

    @Test
    @DisplayName("고정 할인 쿠폰 생성 성공")
    public void fixCouponSaveSuccess(){
        doReturn(Optional.of(new FixCoupon())).when(fixCouponRepository).findById(1L);
        doReturn(fixUserCoupon).when(userCouponRepository).save(any());

        UserCoupon saveCoupon = userCouponRepository.save(fixUserCoupon);

        Assertions.assertEquals(saveCoupon.getUserId(),fixUserCoupon.getUserId());
        Assertions.assertEquals(saveCoupon.getUsageStatus(),fixUserCoupon.getUsageStatus());

    }

    @Test
    @DisplayName("비율 할인 쿠폰 생성 성공")
    public void rateCouponSaveSuccess(){
        doReturn(Optional.of(new RateCoupon())).when(rateCouponRepository).findById(1L);
        doReturn(rateUserCoupon).when(userCouponRepository).save(any());

        UserCoupon saveCoupon = userCouponRepository.save(fixUserCoupon);

        Assertions.assertEquals(saveCoupon.getUserId(),rateUserCoupon.getUserId());
        Assertions.assertEquals(saveCoupon.getUsageStatus(),rateUserCoupon.getUsageStatus());

    }

    @Test
    @DisplayName("고정 할인 쿠폰 생성 실패 : 존재하지 않는 고정 할인 쿠폰")
    public void fixUserCouponSaveFail(){
        doReturn(Optional.empty()).when(fixCouponRepository).findById(any());

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->  userCouponService.save(fixUserCouponDto));
    }

    @Test
    @DisplayName("비율 할인 쿠폰 생성 실패 : 존재하지 않는 비율 할인 쿠폰")
    public void rateUserCouponSaveFail(){
        doReturn(Optional.empty()).when(rateCouponRepository).findById(any());

        Assertions.assertThrows(IllegalArgumentException.class,
                ()->  userCouponService.save(rateUserCouponDto));
    }

    @Test
    @DisplayName("쿠폰 아이디 둘 다 없는 경우 실패")
    public void noCouponIdFail(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> userCouponService.save(failUserCouponDto));
    }

}
