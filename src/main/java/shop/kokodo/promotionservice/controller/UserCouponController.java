package shop.kokodo.promotionservice.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.pool.TypePool;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.ProductIdAndFixCouponDto;
import shop.kokodo.promotionservice.dto.ProductIdAndRateCouponDto;
import shop.kokodo.promotionservice.dto.UpdateUserCouponDto;
import shop.kokodo.promotionservice.dto.UserCouponDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UsageStatus;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.service.FixCouponService;
import shop.kokodo.promotionservice.service.RateCouponService;
import shop.kokodo.promotionservice.service.UserCouponService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userCoupon")
@RequiredArgsConstructor
public class UserCouponController {

    private final FixCouponService fixCouponService;
    private final RateCouponService rateCouponService;
    private final UserCouponService userCouponService;

    @GetMapping("/{productId}/fixCoupon")
    public Response findUserNotUsedFixCouponByproductId(@RequestHeader("memberId") long userId, @PathVariable("productId") long productId){

        List<FixCoupon> fixCouponList =fixCouponService.findUserNotUsedFixCouponByproductId(userId, productId);

        return Response.success(fixCouponList);
    }

    @GetMapping("/{productId}/rateCoupon")
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

    @PutMapping("/usageStatus")
    public Response updateUsageStatus(@RequestHeader(value = "memberId")long memberId,
                                      @RequestBody UpdateUserCouponDto updateUserCouponDto){

        UserCoupon userCoupon = userCouponService.updateUsageStatus(updateUserCouponDto,memberId);

        return Response.success(userCoupon);
    }

    @PostMapping("/list")
    public Response saveRateCouponList(@RequestParam List<Long> rateIdList, @RequestHeader long memberId){
        System.out.println("UserCouponController.saveRateCouponList");

        for (Long rate : rateIdList) {
            UserCouponDto userCouponDto = UserCouponDto.builder()
                    .userId(memberId)
                    .usageStatus(UsageStatus.NOT_USED.toString())
                    .rateCouponId(rate)
                    .build();
            try {
                userCouponService.save(userCouponDto);
            }
            catch (IllegalArgumentException e){
                if(e.getMessage().equals("?????? ???????????? ??????")) continue;
                else throw e;
            }
        }

        return Response.success();
    }

    @PostMapping("/list/fixCoupon")
    public Response saveFixCouponList(@RequestParam List<Long> fixCouponList, @RequestHeader long memberId){
        System.out.println("UserCouponController.saveFixCouponList");
        System.out.println("fixCouponList = " + fixCouponList + ", memberId = " + memberId);

        for (Long fix : fixCouponList) {
            UserCouponDto userCouponDto = UserCouponDto.builder()
                    .userId(memberId)
                    .usageStatus(UsageStatus.NOT_USED.toString())
                    .fixCouponId(fix)
                    .build();
            try {
                userCouponService.save(userCouponDto);
            }
            catch (IllegalArgumentException e){
                if(e.getMessage().equals("?????? ???????????? ??????")) continue;
                else throw e;
            }
        }

        return Response.success();
    }
    // productId - List<RateCoupon> ??????
    @GetMapping("/rateCoupon/list")
    public Response rateCouponList(@RequestParam List<Long> productIdList, @RequestHeader long memberId){
        return Response.success(userCouponService.findRateCouponByMemberIdAndProductId(productIdList,memberId));

    }

    /**
     * productList?????? ????????????????????? ?????? sellerId??? ??????
     * @param productIdList
     * @param memberId
     * @return
     */
    @GetMapping("/fixCoupon/list")
    public Response fixCouponList(@RequestParam List<Long> productIdList, @RequestHeader long memberId){
        return Response.success(userCouponService.findFixCouponByMemberIdAndProductId(productIdList,memberId));
    }

    @GetMapping("/count")
    public Response countUserCoupon(@RequestHeader(value="memberId") long memberId){

        List<UserCoupon> list = userCouponService.findValidCouponByMemberIdGroupByCouponName(memberId);

        return Response.success(list.size());
    }

    @GetMapping("/dashboard")
    public Response findBestCoupon(@RequestHeader("sellerId") long sellerId){
        return Response.success(userCouponService.findBestCoupon(sellerId));
    }


}
