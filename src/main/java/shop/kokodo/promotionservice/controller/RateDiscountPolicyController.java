package shop.kokodo.promotionservice.controller;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.service.RateDiscountPolicyService;

import java.util.List;

@RestController
@Slf4j
//@RequestMapping("/ratediscount")
public class RateDiscountPolicyController {
    private RateDiscountPolicyService rateDiscountPolicyService;

    @Autowired
    public RateDiscountPolicyController(RateDiscountPolicyService rateDiscountPolicyService) {
        this.rateDiscountPolicyService = rateDiscountPolicyService;
    }

    @PostMapping("/rate-discount/save")
    public RateDiscountPolicy save(@RequestBody RateDiscountPolicyDto rateDiscountPolicyDto){
        return rateDiscountPolicyService.createRateDiscountPolicy(rateDiscountPolicyDto);
    }

    @GetMapping(value="/rate-discount")
    public List<RateDiscountPolicy> getRateDiscountPolicyList() {
        List<RateDiscountPolicy> rateDiscountPolicy = rateDiscountPolicyService.getAll();
        return rateDiscountPolicy;
    }

    @GetMapping(value="/rate-discount/{productId}")
    public Response getRateDiscountPolicy(@PathVariable("productId")Long productId) {
        return rateDiscountPolicyService.findByProductId(productId);
    }

    @GetMapping(value="/rate-discount/list")
    public Response getRateDiscountPolicyIdList(@RequestParam List<Long> productIdList) {
        Map<Long, RateDiscountPolicyDto> rateDiscountPolicyMap = rateDiscountPolicyService.findAllByProductIdList(productIdList);
        return Response.success(rateDiscountPolicyMap);
    }

    @GetMapping(value="/feign/rate-discount/list")
    public ResponseEntity<Map<Long, RateDiscountPolicyDto>> getRateDiscountPolicyIdListForFeign(@RequestParam List<Long> productIdList) {
        Map<Long, RateDiscountPolicyDto> rateDiscountPolicyMap = rateDiscountPolicyService.findAllByProductIdList(productIdList);
        return ResponseEntity.ok(rateDiscountPolicyMap);
    }

    @GetMapping(value="/rate-discount/date")
    public List<RateDiscountPolicy> getRateDiscountPolicyByDate() {
        return rateDiscountPolicyService.getRateDiscountPolicyByDate();
    }

    @GetMapping(value="/rate-discount/seller/{sellerId}")
    public Response getRateDiscountPolicyBySellerId(@PathVariable("sellerId")String sellerId) {
        return rateDiscountPolicyService.findBySellerId(Long.parseLong(sellerId));
    }
}

