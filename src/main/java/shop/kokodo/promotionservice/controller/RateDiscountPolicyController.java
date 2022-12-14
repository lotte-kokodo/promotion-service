package shop.kokodo.promotionservice.controller;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.RateDiscountPolicySaveDto;
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
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/rate-discount")
public class RateDiscountPolicyController {
    private RateDiscountPolicyService rateDiscountPolicyService;

    @Autowired
    public RateDiscountPolicyController(RateDiscountPolicyService rateDiscountPolicyService) {
        this.rateDiscountPolicyService = rateDiscountPolicyService;
    }

    @PostMapping("/save")
    public List<RateDiscountPolicy> save(@RequestBody RateDiscountPolicySaveDto rateDiscountPolicySaveDto){
        return rateDiscountPolicyService.createRateDiscountPolicy(rateDiscountPolicySaveDto);
    }

    @GetMapping(value="/")
    public List<RateDiscountPolicy> getRateDiscountPolicyList() {
        List<RateDiscountPolicy> rateDiscountPolicy = rateDiscountPolicyService.getAll();
        return rateDiscountPolicy;
    }

    @GetMapping(value="/{productId}")
    public Response getRateDiscountPolicy(@PathVariable("productId")Long productId) {
        return rateDiscountPolicyService.findByProductId(productId);
    }

    @GetMapping(value="/list")
    public Response getRateDiscountPolicyIdList(@RequestParam List<Long> productIdList) {
        Map<Long, RateDiscountPolicyDto> rateDiscountPolicyMap = rateDiscountPolicyService.findAllByProductIdList(productIdList);
        return Response.success(rateDiscountPolicyMap);
    }

    @GetMapping(value="/feign/rate-discount/list")
    public ResponseEntity<Map<Long, RateDiscountPolicyDto>> getRateDiscountPolicyIdListForFeign(@RequestParam List<Long> productIdList) {
        Map<Long, RateDiscountPolicyDto> rateDiscountPolicyMap = rateDiscountPolicyService.findAllByProductIdList(productIdList);
        return ResponseEntity.ok(rateDiscountPolicyMap);
    }

    @GetMapping(value="/date")
    public List<RateDiscountPolicy> getRateDiscountPolicyByDate() {
        return rateDiscountPolicyService.getRateDiscountPolicyByDate();
    }

    @GetMapping(value="/seller")
    public Response getRateDiscountPolicyBySellerId(@RequestHeader long sellerId, @RequestParam int page) {
        return Response.success(rateDiscountPolicyService.findBySellerId(sellerId, page - 1));
    }

    @GetMapping(value="/{name}/product")
    public Response findProductByName(@PathVariable("name")String name) {
        List<ProductDto> productDtos = rateDiscountPolicyService.findByProductByName(name);
        return Response.success(productDtos);
    }

    @GetMapping(value="/seller/{sellerId}/week")
    public Response findProductBySellerId(@PathVariable("sellerId")Long sellerId) {
        Integer result = rateDiscountPolicyService.findProductBySellerId(sellerId);
        return Response.success(result);
    }
}

