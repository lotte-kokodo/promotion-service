package shop.kokodo.promotionservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.service.FixCouponService;
import shop.kokodo.promotionservice.service.RateCouponService;

import java.util.List;

@RestController
@RequestMapping("/userCoupon")
@RequiredArgsConstructor
public class UserCouponController {

    private final FixCouponService fixCouponService;
    private final RateCouponService rateCouponService;

    @GetMapping("/{productId}/fixCoupon")
    public Response findUserNotUsedFixCouponByproductId(@RequestParam("userId") long userId, @PathVariable("productId") long productId){

        List<FixCoupon> fixCouponList =fixCouponService.findUserNotUsedFixCouponByproductId(userId, productId);

        return Response.success(fixCouponList);
    }

    @GetMapping("/{productId}/rateCoupon")
    public Response findUserNotUsedRateCouponByproductId(@RequestParam("userId") long userId, @PathVariable("productId")long productId){
        List<RateCoupon> rateCouponList = rateCouponService.findUserNotUsedRateCouponByproductId(userId, productId);

        return Response.success(rateCouponList);
    }
}
