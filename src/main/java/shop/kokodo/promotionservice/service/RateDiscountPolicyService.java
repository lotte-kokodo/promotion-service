package shop.kokodo.promotionservice.service;

import org.springframework.web.bind.annotation.RequestBody;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;

import java.util.List;

public interface RateDiscountPolicyService {
    public List<RateDiscountPolicy> getAll();
    public RateDiscountPolicy findByProductId(Long productId);

    public RateDiscountPolicy createRateDiscountPolicy(RateDiscountPolicyDto rateDiscountPolicyDto);

    public List<RateDiscountPolicy> getRateDiscountPolicyByDate();

    public Response findAllByProductIdList(List<Long> productIdList);
}