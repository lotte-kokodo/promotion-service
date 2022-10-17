package shop.kokodo.promotionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.ProductSeller;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.service.FixDiscountPolicyService;

import java.util.List;

@RestController
public class FixDiscountPolicyController {
    private FixDiscountPolicyService fixDiscountPolicyService;

    @Autowired
    public FixDiscountPolicyController(FixDiscountPolicyService fixDiscountPolicyService) {
        this.fixDiscountPolicyService = fixDiscountPolicyService;
    }

    @PostMapping("fix-discount/save")
    public FixDiscountPolicy save(@RequestBody FixDiscountPolicyDto fixDiscountPolicyDto){
        return fixDiscountPolicyService.save(fixDiscountPolicyDto);
    }

    @GetMapping(value="/fix-discount")
    public List<FixDiscountPolicy> getFixDiscountList() {
        return fixDiscountPolicyService.getAll();
    }

    @GetMapping(value="/fix-discount/{productId}")
    public FixDiscountPolicy getFixDiscountPolicy(@PathVariable("productId")Long productId) {
        return fixDiscountPolicyService.getFixDiscountPolicy(productId);
    }

    @GetMapping(value="/fix-discount/list")
    public Response getFixDiscountPolicyIdList(@RequestParam List<Long> productIdList) {
        return fixDiscountPolicyService.findAllByProductIdList(productIdList);
    }

    @GetMapping(value="/fix-discount/status")
    public Response getFixDiscountPolicyStatus(@RequestBody List<ProductSeller> productSellerList) {
        return fixDiscountPolicyService.getFixDiscountPolicyStatus(productSellerList);
    }

    @GetMapping(value="/fix-discount/seller/{sellerId}")
    public Response getFixDiscountPolicyBySellerId(@PathVariable("sellerId")String sellerId) {
        return fixDiscountPolicyService.findBySellerId(Long.parseLong(sellerId));
    }
}
