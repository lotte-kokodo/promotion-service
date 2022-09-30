package shop.kokodo.promotionservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.dto.UpdateUserCouponDto;
import shop.kokodo.promotionservice.dto.UserCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCouponServiceImpl implements UserCouponService{

    private ModelMapper modelMapper;
    private final UserCouponRepository userCouponRepository;
    private final FixCouponRepository fixCouponRepository;
    private final RateCouponRepository rateCouponRepository;

    @Override
    public List<UserCoupon> findByUserId(long userId) {
        return userCouponRepository.findByUserId(userId);
    }

    @Override
    public UserCoupon save(UserCouponDto userCouponDto) {

        UserCoupon userCoupon = null;

        if(userCouponDto.getFixCouponId()!=null && userCouponDto.getRateCouponId()==null){ // fixCoupon 받는 경우
            Optional<FixCoupon> fixCouponOp = fixCouponRepository.findById(userCouponDto.getFixCouponId());
            if(fixCouponOp.isEmpty()) throw new IllegalArgumentException("존재하지 않는 쿠폰");

            FixCoupon fixCoupon = fixCouponOp.get(); 
            if(userCouponRepository.findByUserIdAndFixCoupon(userCouponDto.getUserId(), fixCoupon).isPresent())
                throw new IllegalArgumentException("이미 다운받은 쿠폰");
                

            userCoupon = convertToUserFixCoupon(userCouponDto, fixCouponOp.get());
        }
        else if(userCouponDto.getRateCouponId()!=null && userCouponDto.getFixCouponId()==null){ // rateCoupon 받는 경우
            Optional<RateCoupon> rateCouponOp= rateCouponRepository.findById(userCouponDto.getRateCouponId());
            if(rateCouponOp.isEmpty()) throw new IllegalArgumentException("존재하지 않는 쿠폰");

            RateCoupon rateCoupon = rateCouponOp.get();
            // 이미 다운 받은 쿠폰인 경우
            if(userCouponRepository.findByUserIdAndRateCoupon(userCouponDto.getUserId(),rateCoupon).isPresent())
                throw new IllegalArgumentException("이미 다운받은 쿠폰");

            userCoupon= convertToUserRateCoupon(userCouponDto, rateCoupon);
        }
        else {
            throw new IllegalArgumentException("쿠폰 아이디 없음");
        }

        return userCouponRepository.save(userCoupon);
    }

    @Override
    public List<UserCoupon> findValidCouponByMemberIdGroupByCouponName(long memberId) {
        LocalDateTime now = LocalDateTime.now();
        List<UserCoupon> userCouponList=userCouponRepository.findFixCouponByMemberId(memberId, now);
        userCouponList.addAll(userCouponRepository.findRateCouponByMemberId(memberId,now));

        return userCouponList;
    }

    @Override
    public UserCoupon updateUsageStatus(UpdateUserCouponDto updateUserCouponDto, long userId) {

        UserCoupon updateUserCoupon=null;
        if(updateUserCouponDto.getCouponFlag()==1){ // fixCoupon 사용하는 경우
            Optional<FixCoupon> fixCoupon = fixCouponRepository.findById(updateUserCouponDto.getFixCouponId());

            if(fixCoupon.isEmpty()) throw new IllegalArgumentException("존재하지 않는 쿠폰");

            Optional<UserCoupon> userCouponOp = userCouponRepository.findByUserIdAndFixCoupon(userId, fixCoupon.get());

            if(userCouponOp.isEmpty()) throw new IllegalArgumentException("다운받지 않은 쿠폰은 사용이 불가능합니다.");

            updateUserCoupon = userCouponOp.get();
        }
        else if(updateUserCouponDto.getCouponFlag()==2) {

            Optional<RateCoupon> rateCoupon = rateCouponRepository.findById(updateUserCouponDto.getRateCouponId());

            if(rateCoupon.isEmpty()) throw new IllegalArgumentException("존재하지 않는 쿠폰");

            Optional<UserCoupon> userCouponOp = userCouponRepository.findByUserIdAndRateCoupon(userId, rateCoupon.get());

            if(userCouponOp.isEmpty()) throw new IllegalArgumentException("다운받지 않은 쿠폰은 사용이 불가능합니다.");

            updateUserCoupon = userCouponOp.get();
        }
        else{
            throw new IllegalArgumentException("잘못된 쿠폰 플래그");
        }
        updateUserCoupon.useCoupon();
        return userCouponRepository.save(updateUserCoupon);
    }

    private UserCoupon convertToUserFixCoupon(UserCouponDto userCouponDto, FixCoupon fixCoupon){
        return UserCoupon.builder()
                .userId(userCouponDto.getUserId())
                .usageStatus(0)
                .fixCoupon(fixCoupon)
                .build();
    }

    private UserCoupon convertToUserRateCoupon(UserCouponDto userCouponDto, RateCoupon rateCoupon){
        return UserCoupon.builder()
                .userId(userCouponDto.getUserId())
                .usageStatus(0)
                .rateCoupon(rateCoupon)
                .build();
    }


}
