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
import shop.kokodo.promotionservice.dto.UserCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UsageStatus;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.exception.NoCouponException;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
@MockitoSettings(strictness = Strictness.WARN)
@ExtendWith(MockitoExtension.class)
public class UserCouponServiceMockTest {

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
                .usageStatus(UsageStatus.NOT_USED.toString())
                .fixCouponId(fixCouponId)
                .build();

        rateUserCouponDto=UserCouponDto.builder()
                .id(2L)
                .userId(2L)
                .usageStatus(UsageStatus.NOT_USED.toString())
                .rateCouponId(rateCouponId)
                .build();

        fixUserCoupon = UserCoupon.builder()
                .id(1L)
                .userId(fixUserCouponDto.getUserId())
                .usageStatus(UsageStatus.NOT_USED)
                .fixCoupon(new FixCoupon())
                .build();

        rateUserCoupon=UserCoupon.builder()
                .id(1L)
                .userId(fixUserCouponDto.getUserId())
                .usageStatus(UsageStatus.NOT_USED)
                .rateCoupon(new RateCoupon())
                .build();

        failUserCouponDto = UserCouponDto.builder()
                .id(1L)
                .userId(2L)
                .usageStatus(UsageStatus.NOT_USED.toString())
                .build();
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ??????")
    public void fixCouponSaveSuccess(){
        doReturn(Optional.of(new FixCoupon())).when(fixCouponRepository).findById(1L);
        doReturn(fixUserCoupon).when(userCouponRepository).save(any());

        UserCoupon saveCoupon = userCouponRepository.save(fixUserCoupon);

        Assertions.assertEquals(saveCoupon.getUserId(),fixUserCoupon.getUserId());
        Assertions.assertEquals(saveCoupon.getUsageStatus(),fixUserCoupon.getUsageStatus());

    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ??????")
    public void rateCouponSaveSuccess(){
        doReturn(Optional.of(new RateCoupon())).when(rateCouponRepository).findById(1L);
        doReturn(rateUserCoupon).when(userCouponRepository).save(any());

        UserCoupon saveCoupon = userCouponRepository.save(fixUserCoupon);

        Assertions.assertEquals(saveCoupon.getUserId(),rateUserCoupon.getUserId());
        Assertions.assertEquals(saveCoupon.getUsageStatus(),rateUserCoupon.getUsageStatus());

    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ?????? : ???????????? ?????? ?????? ?????? ??????")
    public void fixUserCouponSaveFail(){
        doReturn(Optional.empty()).when(fixCouponRepository).findById(any());

        Assertions.assertThrows(NoCouponException.class,
                ()->  userCouponService.save(fixUserCouponDto));
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ?????? : ???????????? ?????? ?????? ?????? ??????")
    public void rateUserCouponSaveFail(){
        doReturn(Optional.empty()).when(rateCouponRepository).findById(any());

        Assertions.assertThrows(NoCouponException.class,
                ()->  userCouponService.save(rateUserCouponDto));
    }

    @Test
    @DisplayName("?????? ????????? ??? ??? ?????? ?????? ??????")
    public void noCouponIdFail(){
        Assertions.assertThrows(NoCouponException.class,
                () -> userCouponService.save(failUserCouponDto));
    }

    // TODO: ??????!!!
//    @Test
//    @DisplayName("member id??? ????????? ?????? ?????? ??????")
//    public void findByMemberId(){
//        final LocalDateTime now =LocalDateTime.of(2022,9,30,0,0);
//        List<UserCoupon> fixCouponList = new ArrayList<>();
//        fixCouponList.add(new UserCoupon());
//
//        List<UserCoupon> rateCouponList = new ArrayList<>();
//        rateCouponList.add(new UserCoupon());
//        rateCouponList.add(new UserCoupon());
//
//        doReturn(fixCouponList).when(userCouponRepository).findFixCouponByMemberId(1, now);
//        doReturn(rateCouponList).when(userCouponRepository).findRateCouponByMemberId(1, now);
//
//        List<UserCoupon> selList = userCouponService.findValidCouponByMemberIdGroupByCouponName(1L);
//
//        Assertions.assertEquals(fixCouponList.size()+rateCouponList.size(), selList.size());
//
//    }



}
