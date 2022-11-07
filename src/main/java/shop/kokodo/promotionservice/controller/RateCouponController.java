package shop.kokodo.promotionservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.service.RateCouponService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/rateCoupon")
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
        log.info("coupons = {}" + coupons);
        return Response.success(coupons);
    }

    @GetMapping("/{productId}")
    public Response findByProductId(@PathVariable long productId){
        List<RateCoupon> coupons =rateCouponService.findByProductId(productId);

        return Response.success(coupons);
    }

    @GetMapping("/{name}/product")
    public Response findProductByCouponName(@PathVariable String name){
        System.out.println(name);
        List<ProductDto> products = rateCouponService.findProductByRateCouponName(name);

        return Response.success(products);
    }

    /*
        productId - List<RateCoupon
     */
    @GetMapping("/coupon/list")
    public ResponseEntity<Map<Long, RateCoupon>> findRateCouponByCouponIdList(@RequestParam List<Long> couponIdList){
        return ResponseEntity.ok(rateCouponService.findByCouponIdList(couponIdList));
    }

}
