package shop.kokodo.promotionservice.service;

import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;

import java.util.List;

public interface FixDiscountPolicyService {
    public FixDiscountPolicy save(FixDiscountPolicyDto fixDiscountPolicyDto);
    public List<FixDiscountPolicy> getAll();
    public FixDiscountPolicy getFixDiscountPolicy(Long productId);
}