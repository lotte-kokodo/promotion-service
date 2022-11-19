package shop.kokodo.promotionservice.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.service.FixCouponService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fixCoupon")
public class FixCouponController {

    private final FixCouponService fixCouponService;

    @PostMapping
    public Response save(@RequestBody FixCouponDto fixCouponDto,@RequestHeader long sellerId){
        fixCouponDto.setSellerId(sellerId);
        fixCouponService.save(fixCouponDto);

        return Response.success();
    }
    @GetMapping("/seller")
    public Response findBySellerId(@RequestHeader long sellerId,@RequestParam("page")int page){

        System.out.println("FixCouponController.findBySellerId");

        return Response.success(fixCouponService.findBySellerId(sellerId,page-1));
    }

    @GetMapping("/{name}/product")
    public Response findProductByName(@PathVariable String name){
        List<ProductDto> products = fixCouponService.findProductByName(name);
        return Response.success(products);
    }

    @GetMapping("/coupon/list")
    public ResponseEntity<List<Long>> findFixCouponByCouponIdList(@RequestParam List<Long> couponIdList){
        return ResponseEntity.ok(fixCouponService.findByCouponIdList(couponIdList));
    }

    @GetMapping("/{productId}")
    public Response findByProductId(@PathVariable long productId){
        System.out.println(productId);
        return Response.success(fixCouponService.findByProductId(productId));
    }

}
