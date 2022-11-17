package shop.kokodo.promotionservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.kokodo.promotionservice.dto.ArRateCouponFindDto;
import shop.kokodo.promotionservice.dto.ArRateCouponInfo;
import shop.kokodo.promotionservice.entity.*;
import shop.kokodo.promotionservice.repository.ArRateCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

import java.util.List;
import java.util.stream.Collectors;

import static shop.kokodo.promotionservice.entity.ArRateCoupon.createArCoupon;

/**
 * packageName    : shop.kokodo.calculateservice.service
 * fileName       : ArCouponService
 * author         : namhyeop
 * date           : 2022/11/02
 * description    :
 * ARRateCoupon Service
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/11/02        namhyeop       최초 생성
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArRateCouponService {

    private final ArRateCouponRepository arRateCouponRepository;
    private final RateCouponRepository rateCouponRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public void createCoupon(ArRateCouponInfo arRateCouponInfo){
//        RateCoupon rateCoupon = RateCoupon.builder()
//                .name(arRateCouponInfo.getCouponName())
//                .regdate(arRateCouponInfo.getRegDate())
//                .rate(arRateCouponInfo.getRate())
//                .minPrice(arRateCouponInfo.getMinPrice())
//                .startDate(arRateCouponInfo.getStartDate())
//                .endDate(arRateCouponInfo.getEndDate())
//                .productId(arRateCouponInfo.getProductId())
//                .sellerId(arRateCouponInfo.getSellerId()).build();
//        for(int i = 0; i < arRateCouponInfo.getProductId().size(); i++){
//
//        }
        List<Long> productIdList = arRateCouponInfo.getProductId();
        log.info("productIdList = {}", productIdList);
        List<RateCoupon> rateCoupons = productIdList.stream().map((productId) -> new RateCoupon(
                arRateCouponInfo.getCouponName(),
                arRateCouponInfo.getRegDate(),
                arRateCouponInfo.getRate(),
                arRateCouponInfo.getMinPrice(),
                arRateCouponInfo.getStartDate(),
                arRateCouponInfo.getEndDate(),
                productId,
                arRateCouponInfo.getSellerId())).collect(Collectors.toList());
        log.info("rateCoupons = {}", rateCoupons);
        List<ArRateCoupon> arRateCouponList = rateCoupons.stream().map((rCoupon) -> createArCoupon(rCoupon, arRateCouponInfo)).collect(Collectors.toList());
        log.info("arRateCouponList = {}", arRateCouponList);
        arRateCouponRepository.saveAll(arRateCouponList);
    }

    public List<ArClientRateCouponDto> findAllArCoupon(){
        return ArRateCoupon.toDto(arRateCouponRepository.findAll());
    }

    @Transactional
    public void saveArCoupon(ArRateCouponFindDto arRateCouponFindDto){
        RateCoupon arRateCoupon = rateCouponRepository.findById(arRateCouponFindDto.getCouponId()).get();
        UserCoupon userCoupon = UserCoupon.builder()
                .rateCoupon(arRateCoupon)
                .userId(arRateCouponFindDto.getClientId())
                .usageStatus(UsageStatus.NOT_USED)
                .build();
        userCouponRepository.save(userCoupon);
    }
}
