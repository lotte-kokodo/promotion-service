package shop.kokodo.promotionservice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.dto.ProductIdAndRateCouponDto;
import shop.kokodo.promotionservice.dto.UpdateUserCouponDto;
import shop.kokodo.promotionservice.dto.UserCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

import java.time.LocalDateTime;
import java.util.*;

import static shop.kokodo.promotionservice.dto.ProductIdAndRateCouponDto.createProductIdAndRateCouponDto;

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
            FixCoupon fixCoupon = fixCouponRepository.findById(userCouponDto.getFixCouponId())
                    .orElseThrow( ()->new IllegalArgumentException("존재하지 않는 쿠폰"));

            if(userCouponRepository.findByUserIdAndFixCoupon(userCouponDto.getUserId(), fixCoupon).isPresent())
                throw new IllegalArgumentException("이미 다운받은 쿠폰");

            userCoupon = convertToUserFixCoupon(userCouponDto, fixCoupon);
        }
        else if(userCouponDto.getRateCouponId()!=null && userCouponDto.getFixCouponId()==null){ // rateCoupon 받는 경우
            RateCoupon rateCoupon= rateCouponRepository.findById(userCouponDto.getRateCouponId())
                    .orElseThrow(()->new IllegalArgumentException("존재하지 않는 쿠폰"));

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
        List<UserCoupon> userCouponList2 = userCouponRepository.findRateCouponByMemberId(memberId, now);
        userCouponList.addAll(userCouponList2);
        return userCouponList;
    }

    @Override
    public UserCoupon updateUsageStatus(UpdateUserCouponDto updateUserCouponDto, long userId) {

        UserCoupon updateUserCoupon=null;
        if(updateUserCouponDto.getCouponFlag()==1){ // fixCoupon 사용하는 경우
            FixCoupon fixCoupon = fixCouponRepository.findById(updateUserCouponDto.getFixCouponId())
                    .orElseThrow(()->new IllegalArgumentException("존재하지 않는 쿠폰"));

            UserCoupon userCouponOp = userCouponRepository.findByUserIdAndFixCoupon(userId, fixCoupon)
                    .orElseThrow(() -> new IllegalArgumentException("다운받지 않은 쿠폰은 사용이 불가능합니다."));

            updateUserCoupon = userCouponOp;
        }
        else if(updateUserCouponDto.getCouponFlag()==2) {

           RateCoupon rateCoupon = rateCouponRepository.findById(updateUserCouponDto.getRateCouponId())
                   .orElseThrow(()->new IllegalArgumentException("존재하지 않는 쿠폰"));

           UserCoupon userCouponOp = userCouponRepository.findByUserIdAndRateCoupon(userId, rateCoupon)
                   .orElseThrow(()-> new IllegalArgumentException("다운받지 않은 쿠폰은 사용이 불가능합니다."));

            updateUserCoupon = userCouponOp;
        }
        else{
            throw new IllegalArgumentException("잘못된 쿠폰 플래그");
        }
        updateUserCoupon.useCoupon();
        return userCouponRepository.save(updateUserCoupon);
    }

    @Override
    public List<ProductIdAndRateCouponDto> findRateCouponByMemberIdAndProductId(List<Long> productIdList, long memberId) {

        List<UserCoupon> list = userCouponRepository.findByInProductIdAndMemberId(productIdList,memberId, LocalDateTime.now());

        List<ProductIdAndRateCouponDto> rateCoupons = new ArrayList<>();
        Map<Long, ArrayList<RateCoupon>> productIdAndRateCouponListMap = new HashMap<>();

        for (UserCoupon userCoupon : list) {
            long productId = userCoupon.getRateCoupon().getProductId();
            if(productIdAndRateCouponListMap.containsKey(productId)) {
                ArrayList<RateCoupon> rateCouponList = productIdAndRateCouponListMap.get(productId);
                rateCouponList.add(userCoupon.getRateCoupon());
                productIdAndRateCouponListMap.put(productId,rateCouponList);
            }
            else {
                ArrayList<RateCoupon> rateCouponList = new ArrayList<>();
                rateCouponList.add(userCoupon.getRateCoupon());

                productIdAndRateCouponListMap.put(productId,rateCouponList);
            }
        }

        for (Long productId : productIdAndRateCouponListMap.keySet()) {
            rateCoupons.add(createProductIdAndRateCouponDto(productId,productIdAndRateCouponListMap.get(productId)));
        }

        return rateCoupons;
    }

    @Override
    public List<FixCoupon> findFixCouponByMemberIdAndProductId(long productId, long memberId) {
        List<UserCoupon> list = userCouponRepository.findFixCouponByMemberIdAndProductId(memberId,productId, LocalDateTime.now());

        List<FixCoupon> rateCoupons = new ArrayList<>();

        for (UserCoupon userCoupon : list) {
            rateCoupons.add(userCoupon.getFixCoupon());
        }
        return rateCoupons;
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
