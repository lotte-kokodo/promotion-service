package shop.kokodo.promotionservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.PagingRateCouponDto;
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

    @PostMapping
    public Response save(@RequestBody RateCouponDto rateCouponDto, @RequestHeader long sellerId){
        System.out.println("RateCouponController.save");
        rateCouponDto.setSellerId(sellerId);
        rateCouponService.save(rateCouponDto);

        return Response.success();
    }

    @GetMapping("/seller")
    public Response findBySellerId(@RequestHeader long sellerId, @RequestParam int page){
        System.out.println("RateCouponController.findBySellerId "+ sellerId);
        PagingRateCouponDto pagingRateCouponDto = rateCouponService.findBySellerId(sellerId, page-1);
        log.info("coupons = {}" + pagingRateCouponDto);
        return Response.success(pagingRateCouponDto);
    }

    @GetMapping("/{productId}")
    public Response findByProductId(@PathVariable long productId){
        System.out.println("RateCouponController.findByProductId");
        List<RateCoupon> coupons =rateCouponService.findByProductId(productId);

        System.out.println(coupons.size());

        return Response.success(coupons);
    }

    @GetMapping("/{name}/product")
    public Response findProductByCouponName(@PathVariable String name){
        List<ProductDto> products = rateCouponService.findProductByRateCouponName(name);

        System.out.println(products.size());

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
