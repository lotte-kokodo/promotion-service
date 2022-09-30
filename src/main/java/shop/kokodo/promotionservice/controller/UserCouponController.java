package shop.kokodo.promotionservice.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.UserCouponDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.service.FixCouponService;
import shop.kokodo.promotionservice.service.RateCouponService;
import shop.kokodo.promotionservice.service.UserCouponService;

import java.util.List;

@RestController
@RequestMapping("/user-coupon")
@RequiredArgsConstructor
public class UserCouponController {

    private final FixCouponService fixCouponService;
    private final RateCouponService rateCouponService;
    private final UserCouponService userCouponService;

    @GetMapping("/{productId}/fix-coupon")
    public Response findUserNotUsedFixCouponByproductId(@RequestParam("userId") long userId, @PathVariable("productId") long productId){

        List<FixCoupon> fixCouponList =fixCouponService.findUserNotUsedFixCouponByproductId(userId, productId);

        return Response.success(fixCouponList);
    }

    @GetMapping("/{productId}/rate-coupon")
    public Response findUserNotUsedRateCouponByproductId(@RequestParam("userId") long userId, @PathVariable("productId")long productId){
        List<RateCoupon> rateCouponList = rateCouponService.findUserNotUsedRateCouponByproductId(userId, productId);

        return Response.success(rateCouponList);
    }

    @PostMapping("/save")
    public Response save(@RequestBody UserCouponDto userCouponDto){
        UserCoupon userCoupon= userCouponService.save(userCouponDto);

        return Response.success(userCoupon);
    }


}
