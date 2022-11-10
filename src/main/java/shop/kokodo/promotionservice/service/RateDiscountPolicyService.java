package shop.kokodo.promotionservice.service;

import java.util.Map;

import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;

import java.util.List;

/**
 * packageName : shop.kokodo.promotionservice.service
 * fileName : RateDiscountPolicyService
 * author : SSOsh
 * date : 2022-11-03
 * description : 비율 할인 쿠폰 관리 서비스 인터페이스
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-03           SSOsh              최초 생성
 */
public interface RateDiscountPolicyService {
    public List<RateDiscountPolicy> getAll();
    public Response findByProductId(Long productId);

    public RateDiscountPolicy createRateDiscountPolicy(RateDiscountPolicyDto rateDiscountPolicyDto);

    public List<RateDiscountPolicy> getRateDiscountPolicyByDate();

    public Map<Long, RateDiscountPolicyDto> findAllByProductIdList(List<Long> productIdList);

    public Response findBySellerId(Long sellerId);

    public List<ProductDto> findByProductByName(String name);
}