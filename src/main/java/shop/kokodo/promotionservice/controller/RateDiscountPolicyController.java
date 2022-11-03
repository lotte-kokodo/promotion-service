package shop.kokodo.promotionservice.controller;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.service.RateDiscountPolicyService;

import java.util.List;

/**
 * packageName : shop.kokodo.promotionservice.controller
 * fileName : RateDiscountPolicyController
 * author : SSOsh
 * date : 2022-11-03
 * description : 비율 할인 정책을 관리 컨트롤러
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-03           SSOsh              최초 생성
 */
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
    public Map<Long, RateDiscountPolicyDto> getRateDiscountPolicyIdList(@RequestParam List<Long> productIdList) {
        return rateDiscountPolicyService.findAllByProductIdList(productIdList);
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

