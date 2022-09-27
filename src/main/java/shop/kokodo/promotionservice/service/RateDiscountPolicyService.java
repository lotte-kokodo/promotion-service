package shop.kokodo.promotionservice.service;

import org.springframework.web.bind.annotation.RequestBody;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;

import java.util.List;

public interface RateDiscountPolicyService {
    public List<RateDiscountPolicyDto> getAll();
    public RateDiscountPolicyDto getRateDiscountPolicy(Long productId);

    public RateDiscountPolicyDto createRateDiscountPolicy(RateDiscountPolicyDto rateDiscountPolicy);
}