package shop.kokodo.promotionservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.service.RateCouponService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/rateCoupon")
@RequiredArgsConstructor
public class RateCouponController {
    private final ModelMapper modelMapper;
    private final RateCouponService rateCouponService;

    @PostMapping("/save")
    public Response save(@RequestBody RateCouponDto rateCouponDto){

        RateCoupon rateCoupon=modelMapper.map(rateCouponDto,RateCoupon.class);
        rateCoupon.setRegDate(LocalDateTime.now());

        rateCouponService.save(rateCoupon);

        return Response.success();
    }







}
