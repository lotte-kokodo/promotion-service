package shop.kokodo.promotionservice.service;

import java.time.ZoneId;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.kokodo.promotionservice.circuitbreaker.AllCircuitBreaker;
import shop.kokodo.promotionservice.dto.UpdateUserCouponDto;
import shop.kokodo.promotionservice.dto.UserCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.exception.*;
import shop.kokodo.promotionservice.feign.MemberServiceClient;
import shop.kokodo.promotionservice.feign.ProductServiceClient;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserCouponServiceImpl implements UserCouponService{

    private ModelMapper modelMapper;
    private final UserCouponRepository userCouponRepository;
    private final FixCouponRepository fixCouponRepository;
    private final RateCouponRepository rateCouponRepository;
    private final MemberServiceClient memberServiceClient;
    private final ProductServiceClient productServiceClient;

    private final CircuitBreaker circuitBreaker = AllCircuitBreaker.createSellerCircuitBreaker();


    @Override
    public List<UserCoupon> findByUserId(long userId) {
        return userCouponRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public UserCoupon save(UserCouponDto userCouponDto) {

        UserCoupon userCoupon = null;

        if(userCouponDto.getFixCouponId()!=null && userCouponDto.getRateCouponId()==null){ // fixCoupon 받는 경우
            FixCoupon fixCoupon = fixCouponRepository.findById(userCouponDto.getFixCouponId())
                    .orElseThrow( ()-> {throw new NoCouponException();} );

            if(userCouponRepository.findByUserIdAndFixCoupon(userCouponDto.getUserId(), fixCoupon).isPresent())
                throw new DownloadedCouponException();

            userCoupon = convertToUserFixCoupon(userCouponDto, fixCoupon);
        }
        else if(userCouponDto.getRateCouponId()!=null && userCouponDto.getFixCouponId()==null){ // rateCoupon 받는 경우
            RateCoupon rateCoupon= rateCouponRepository.findById(userCouponDto.getRateCouponId())
                    .orElseThrow(()->new NoCouponException());

            // 이미 다운 받은 쿠폰인 경우
            if(userCouponRepository.findByUserIdAndRateCoupon(userCouponDto.getUserId(),rateCoupon).isPresent())
                throw new DownloadedCouponException();

            userCoupon= convertToUserRateCoupon(userCouponDto, rateCoupon);
        }
        else {
            throw new NoCouponException();
        }

        return userCouponRepository.save(userCoupon);
    }

    @Override
    public List<UserCoupon> findValidCouponByMemberIdGroupByCouponName(long memberId) {

        Boolean memberFlag = circuitBreaker.run(()-> memberServiceClient.getMember(memberId)
                ,throwable -> true);
        if(!memberFlag) throw new NoMemberException();

        final LocalDateTime now = LocalDateTime.now();
        List<UserCoupon> userCouponList=userCouponRepository.findFixCouponByMemberId(memberId, now);
        List<UserCoupon> userCouponList2 = userCouponRepository.findRateCouponByMemberId(memberId, now);
        userCouponList.addAll(userCouponList2);
        return userCouponList;
    }

    @Transactional
    @Override
    public UserCoupon updateUsageStatus(UpdateUserCouponDto updateUserCouponDto, long userId) {

        UserCoupon updateUserCoupon=null;
        if(updateUserCouponDto.getCouponFlag()==1){ // fixCoupon 사용하는 경우
            FixCoupon fixCoupon = fixCouponRepository.findById(updateUserCouponDto.getFixCouponId())
                    .orElseThrow(()->new NoCouponException());

            UserCoupon userCouponOp = userCouponRepository.findByUserIdAndFixCoupon(userId, fixCoupon)
                    .orElseThrow(() -> new NoDownloadedCouponException("다운받지 않은 쿠폰은 사용이 불가능합니다."));

            updateUserCoupon = userCouponOp;
        }
        else if(updateUserCouponDto.getCouponFlag()==2) {

           RateCoupon rateCoupon = rateCouponRepository.findById(updateUserCouponDto.getRateCouponId())
                   .orElseThrow(()->new NoCouponException());

           UserCoupon userCouponOp = userCouponRepository.findByUserIdAndRateCoupon(userId, rateCoupon)
                   .orElseThrow(()-> new NoDownloadedCouponException("다운받지 않은 쿠폰은 사용이 불가능합니다."));

            updateUserCoupon = userCouponOp;
        }
        else{
            throw new IllegalArgumentException("잘못된 쿠폰 플래그");
        }
        updateUserCoupon.useCoupon();
        return userCouponRepository.save(updateUserCoupon);
    }

    @Override
    public Map<Long, List<RateCoupon>> findRateCouponByMemberIdAndProductId(List<Long> productIdList, long memberId) {
        Boolean memberFlag = circuitBreaker.run(()-> memberServiceClient.getMember(memberId)
                ,throwable -> true);

        if(!memberFlag) throw new NoMemberException();

        List<UserCoupon> list = userCouponRepository.findByInProductIdAndMemberId(productIdList,memberId, LocalDateTime.now());

        Map<Long, List<RateCoupon>> productIdAndRateCouponListMap = new HashMap<>();

        for (UserCoupon userCoupon : list) {
            long productId = userCoupon.getRateCoupon().getProductId();

            if(productIdAndRateCouponListMap.containsKey(productId)) {
                List<RateCoupon> rateCouponList = productIdAndRateCouponListMap.get(productId);
                rateCouponList.add(userCoupon.getRateCoupon());
                productIdAndRateCouponListMap.put(productId,rateCouponList);
            }
            else {
                ArrayList<RateCoupon> rateCouponList = new ArrayList<>();
                rateCouponList.add(userCoupon.getRateCoupon());

                productIdAndRateCouponListMap.put(productId,rateCouponList);
            }
        }

        return productIdAndRateCouponListMap;
    }

    @Override
    public Map<Long, FixCoupon> findFixCouponByMemberIdAndProductId(List<Long> productIds, long memberId) {

        Boolean memberFlag = circuitBreaker.run(()-> memberServiceClient.getMember(memberId)
                ,throwable -> true);

        if(!memberFlag) throw new NoMemberException();

        for (Long productId : productIds) {
            Boolean productFlag = circuitBreaker.run(()-> productServiceClient.findProductById(productId)
                    ,throwable -> true);
            if(!productFlag) throw new NoProductException();
        }

       List<FixCoupon> fixCouponList = fixCouponRepository.findValidFixCoupon( memberId, productIds, LocalDateTime.now());
        return fixCouponList.stream()
           .collect(Collectors.toMap(FixCoupon::getSellerId, Function.identity()));
    }

    @Override
    public String findBestCoupon(long sellerId) {

        Calendar cal = Calendar.getInstance(Locale.KOREA);
        LocalDateTime startDate = getDate("start");
        LocalDateTime endDate = getDate("end");

        return userCouponRepository.findBestCoupon(sellerId,startDate,endDate);
    }

    LocalDateTime getDate(String flag) {
        Calendar cal = Calendar.getInstance(Locale.KOREA);
        //금주 시작 날짜
        if(flag.equals("start")) {
            cal.add(Calendar.DATE, 2 - cal.get(Calendar.DAY_OF_WEEK));
        }
        //금주 종료 날짜
        else if(flag.equals("end")){
            cal.add(Calendar.DATE, 8 - cal.get(Calendar.DAY_OF_WEEK));
        }
        TimeZone tz = cal.getTimeZone();
        ZoneId zoneId = tz.toZoneId();

        return LocalDateTime.ofInstant(cal.toInstant(), zoneId);
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
