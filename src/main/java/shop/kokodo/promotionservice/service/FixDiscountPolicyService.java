package shop.kokodo.promotionservice.service;

import java.util.Map;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;

import java.util.List;

public interface FixDiscountPolicyService {
    public FixDiscountPolicy save(FixDiscountPolicyDto fixDiscountPolicyDto);
    public List<FixDiscountPolicy> getAll();
    public FixDiscountPolicy getFixDiscountPolicy(Long productId);

    public Map<Long, FixDiscountPolicyDto> findAllByProductIdList(List<Long> productIdList);

    public Response getFixDiscountPolicyStatus(Long productId, Long sellerId);
}