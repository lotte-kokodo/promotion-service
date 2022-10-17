package shop.kokodo.promotionservice.service;

import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.ProductSeller;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;

import java.util.List;

public interface FixDiscountPolicyService {
    public FixDiscountPolicy save(FixDiscountPolicyDto fixDiscountPolicyDto);
    public List<FixDiscountPolicy> getAll();
    public FixDiscountPolicy getFixDiscountPolicy(Long productId);

    public Response findAllByProductIdList(List<Long> productIdList);

    public Response getFixDiscountPolicyStatus(List<ProductSeller> productSellerList);

    public Response findBySellerId(Long sellerId);
}