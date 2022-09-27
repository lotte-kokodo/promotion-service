package shop.kokodo.promotionservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.service.FixCouponService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fixCoupon")
public class FixCouponController {

    private final ModelMapper modelMapper;
    private final FixCouponService fixCouponService;

    @GetMapping("/save")
    public Response save(@RequestBody FixCouponDto fixCouponDto){

        FixCoupon fixCoupon = modelMapper.map(fixCouponDto,FixCoupon.class);
        fixCouponService.save(fixCoupon);

        return Response.success();
    }


}
