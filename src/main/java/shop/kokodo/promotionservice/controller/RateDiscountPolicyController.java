package shop.kokodo.promotionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.kokodo.promotionservice.service.RateDiscountPolicyService;

@RestController
public class RateDiscountPolicyController {
    private RateDiscountPolicyService rateDiscountPolicyService;

    @Autowired
    public RateDiscountPolicyController(RateDiscountPolicyService rateDiscountPolicyService) {
        this.rateDiscountPolicyService = rateDiscountPolicyService;
    }
}

