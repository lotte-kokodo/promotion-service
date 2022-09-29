package shop.kokodo.promotionservice.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.service.FixCouponService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fixCoupon")
public class FixCouponController {

    private final FixCouponService fixCouponService;

    @GetMapping("/save")
    public Response save(@RequestBody FixCouponDto fixCouponDto){

        fixCouponService.save(fixCouponDto);

        return Response.success();
    }

<<<<<<< HEAD
    @GetMapping("/{productId}")
    public Response findUserNotUsedFixCouponByproductId( @RequestParam("userId") long userId, @PathVariable("productId") long productId){


        return Response.success(fixCouponList);
    }
=======
    @GetMapping("/seller")
    public Response findBySellerId(@RequestParam long sellerId){

        List<FixCoupon> coupons = fixCouponService.findBySellerId(sellerId);

        return Response.success(coupons);

    }

>>>>>>> dev


}
