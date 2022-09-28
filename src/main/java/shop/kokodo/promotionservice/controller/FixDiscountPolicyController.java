package shop.kokodo.promotionservice.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.service.FixDiscountPolicyService;

@RestController
public class FixDiscountPolicyController {
    private FixDiscountPolicyService fixDiscountPolicyService;

    @Autowired
    public FixDiscountPolicyController(FixDiscountPolicyService fixDiscountPolicyService) {
        this.fixDiscountPolicyService = fixDiscountPolicyService;
    }

    @PostMapping("/save")
    public FixDiscountPolicy save(@RequestBody FixDiscountPolicyDto fixDiscountPolicyDto){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        FixDiscountPolicy fixDiscountPolicy = mapper.map(fixDiscountPolicyDto, FixDiscountPolicy.class);
        return fixDiscountPolicy;
    }
}
