package shop.kokodo.promotionservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.service.RateCouponService;

import java.util.List;

@RestController
@RequestMapping("/rate-coupon")
@RequiredArgsConstructor
public class RateCouponController {
    private final RateCouponService rateCouponService;

    @PostMapping("/save")
    public Response save(@RequestBody RateCouponDto rateCouponDto){

        rateCouponService.save(rateCouponDto);

        return Response.success();
    }
    @GetMapping("/seller")
    public Response findBySellerId(@RequestParam long sellerId){
        List<RateCoupon> coupons = rateCouponService.findBySellerId(sellerId);

        return Response.success(coupons);
    }

}
