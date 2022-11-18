package shop.kokodo.promotionservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.ArRateCouponFindDto;
import shop.kokodo.promotionservice.dto.ArRateCouponInfo;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.service.ArRateCouponService;

/**
 * packageName    : shop.kokodo.calculateservice.controller
 * fileName       : ArCouponController
 * author         : namhyeop
 * date           : 2022/11/02
 * description    :
 * AR 관련 Controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/11/02        namhyeop       최초 생성
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/arCoupon")
public class ArCouponController {

    private final ArRateCouponService arCouponService;

    //Seller가 AR Coupon 적용시 발생하는 API
    @PostMapping("/seller/saveCoupon")
    public void saveCoupon(@RequestBody ArRateCouponInfo couponInfo){
        log.info("current saveCoupon");
        arCouponService.createCoupon(couponInfo);
    }

    //AR의 모든 Coupon을 찾는다.
    @GetMapping("/client/searchCoupon")
    public Response getCoupon(){
        return Response.success(arCouponService.findAllArCoupon());
    }

    @PostMapping("/client/arFindEvent")
    public void arCouponFindEvent(@RequestBody ArRateCouponFindDto arRateCouponFindDto){
        arCouponService.saveArCoupon(arRateCouponFindDto);
    }
}
