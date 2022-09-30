package shop.kokodo.promotionservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.dto.UserCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

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

            userCoupon = convertToUserFixCoupon(userCouponDto, fixCouponOp.get());
        }
        else if(userCouponDto.getRateCouponId()!=null && userCouponDto.getFixCouponId()==null){ // rateCoupon 받는 경우
            Optional<RateCoupon> rateCouponOp= rateCouponRepository.findById(userCouponDto.getRateCouponId());
            if(rateCouponOp.isEmpty()) throw new IllegalArgumentException("존재하지 않는 쿠폰");

            userCoupon= convertToUserRateCoupon(userCouponDto, rateCouponOp.get());
        }
        else {
            throw new IllegalArgumentException("쿠폰 아이디 없음");
        }

        return userCouponRepository.save(userCoupon);
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
