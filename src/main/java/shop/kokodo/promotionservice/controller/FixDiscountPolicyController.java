package shop.kokodo.promotionservice.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.service.FixDiscountPolicyService;

import java.util.List;

/**
 * packageName : shop.kokodo.promotionservice.controller
 * fileName : FixDiscountPolicyController
 * author : SSOsh
 * date : 2022-11-03
 * description : 고정 할인 정책을 관리하는 컨트롤러
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-03           SSOsh              최초 생성
 */
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
    public Map<Long, FixDiscountPolicyDto> getFixDiscountPolicyIdList(@RequestParam List<Long> productIdList) {
        return fixDiscountPolicyService.findAllByProductIdList(productIdList);
    }

    @GetMapping(value="/fix-discount/status")
    public Response getFixDiscountPolicyStatus(@RequestParam List<Long> productIdList, @RequestParam List<Long> sellerIdList) {
        return fixDiscountPolicyService.getFixDiscountPolicyStatus(productIdList, sellerIdList);
    }

    @GetMapping(value="/feign/fix-discount/status")
    public ResponseEntity<Map<Long, Boolean>> getFixDiscountPolicyStatusForFeign(@RequestParam List<Long> productIdList, @RequestParam List<Long> sellerIdList) {
        return ResponseEntity.ok(fixDiscountPolicyService.getFixDiscountPolicyStatusForFeign(productIdList, sellerIdList));
    }

    @GetMapping(value="/fix-discount/seller/{sellerId}")
    public Response getFixDiscountPolicyBySellerId(@PathVariable("sellerId")String sellerId) {
        return fixDiscountPolicyService.findBySellerId(Long.parseLong(sellerId));
    }

    @GetMapping(value="/fix-discount/{name}/product")
    public Response findProductByName(@PathVariable("name")String name) {
        List<ProductDto> productDtos = fixDiscountPolicyService.findByProductByName(name);
        return Response.success(productDtos);
    }
}
