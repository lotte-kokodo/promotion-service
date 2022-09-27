package shop.kokodo.promotionservice.controller;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.service.RateCouponService;

@RestController
@RequestMapping("/rateCoupon")
public class RateCouponController {

    @Autowired
    RateCouponService rateCouponService;

    @PostMapping("/save")
    public void save(@RequestBody RateCouponDto rateCouponDto){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RateCoupon rateCoupon=mapper.map(rateCouponDto, RateCoupon.class);

    }



}
