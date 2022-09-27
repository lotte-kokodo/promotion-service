package shop.kokodo.promotionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import shop.kokodo.promotionservice.service.FixDiscountPolicyService;

@RestController
public class FixDiscountPolicyController {
    private FixDiscountPolicyService fixDiscountPolicyService;

    @Autowired
    public FixDiscountPolicyController(FixDiscountPolicyService fixDiscountPolicyService) {
        this.fixDiscountPolicyService = fixDiscountPolicyService;
    }
}
