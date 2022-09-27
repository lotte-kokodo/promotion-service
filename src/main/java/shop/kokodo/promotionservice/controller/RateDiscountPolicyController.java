package shop.kokodo.promotionservice.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.service.RateDiscountPolicyService;

@RestController
public class RateDiscountPolicyController {
    private RateDiscountPolicyService rateDiscountPolicyService;

    @Autowired
    public RateDiscountPolicyController(RateDiscountPolicyService rateDiscountPolicyService) {
        this.rateDiscountPolicyService = rateDiscountPolicyService;
    }

    @PostMapping("/save")
    public FixDiscountPolicy save(@RequestBody FixDiscountPolicyDto fixDiscountPolicyDto){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        FixDiscountPolicy fixDiscountPolicy = mapper.map(fixDiscountPolicyDto, FixDiscountPolicy.class);
        return fixDiscountPolicy;
    }

    @GetMapping(value="/ratediscount/{productId}")
    public RateDiscountPolicy getRateDiscountPolicy(@PathVariable("productId")Long productId) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        RateDiscountPolicyDto rateDiscountPolicyDto = rateDiscountPolicyService.getRateDiscountPolicy(productId);
        RateDiscountPolicy rateDiscountPolicy = mapper.map(rateDiscountPolicyDto, RateDiscountPolicy.class);
        return rateDiscountPolicy;
    }
}

