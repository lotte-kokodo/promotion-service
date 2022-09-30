package shop.kokodo.promotionservice.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.UpdateUserCouponDto;
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
    public Response findUserNotUsedFixCouponByproductId(@RequestHeader("memberId") long userId, @PathVariable("productId") long productId){

        List<FixCoupon> fixCouponList =fixCouponService.findUserNotUsedFixCouponByproductId(userId, productId);

        return Response.success(fixCouponList);
    }

    @GetMapping("/{productId}/rate-coupon")
    public Response findUserNotUsedRateCouponByproductId(@RequestHeader("memberId") long userId, @PathVariable("productId")long productId){
        List<RateCoupon> rateCouponList = rateCouponService.findUserNotUsedRateCouponByproductId(userId, productId);

        return Response.success(rateCouponList);
    }

    @PostMapping
    public Response save(@RequestBody UserCouponDto userCouponDto, @RequestHeader("memberId")long memberId){
        userCouponDto.setUserId(memberId);
        UserCoupon userCoupon= userCouponService.save(userCouponDto);

        return Response.success(userCoupon);
    }
    @GetMapping
    public Response findByMemberId(@RequestHeader(value="memberId") long memberId){

        List<UserCoupon> list = userCouponService.findValidCouponByMemberIdGroupByCouponName(memberId);

        return Response.success(list);

    }

    @PutMapping("/usage-status")
    public Response updateUsageStatus(@RequestHeader(value = "member-id")long memberId,
                                      @RequestBody UpdateUserCouponDto updateUserCouponDto){

        UserCoupon userCoupon = userCouponService.updateUsageStatus(updateUserCouponDto,memberId);

        return Response.success(userCoupon);
    }


}
