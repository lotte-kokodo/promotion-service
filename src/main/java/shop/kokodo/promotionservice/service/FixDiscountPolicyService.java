package shop.kokodo.promotionservice.service;

import java.util.Map;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.FixDiscountPolicySaveDto;
import shop.kokodo.promotionservice.dto.PagingFixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;

import java.util.List;

/**
 * packageName : shop.kokodo.promotionservice.service
 * fileName : FixDiscountPolicyService
 * author : SSOsh
 * date : 2022-11-03
 * description : 고정 할인 쿠폰 관리 서비스 인터페이스
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-03           SSOsh              최초 생성
 */
public interface FixDiscountPolicyService {
    public List<FixDiscountPolicy> save(FixDiscountPolicySaveDto fixDiscountPolicySaveDto);
    public List<FixDiscountPolicy> getAll();
    public FixDiscountPolicy getFixDiscountPolicy(Long productId);

    public Map<Long, FixDiscountPolicyDto> findAllByProductIdList(List<Long> productIdList);

    public PagingFixDiscountPolicyDto findBySellerId(Long sellerId, int page);

    public Map<Long, Boolean> getFixDiscountPolicyStatus(List<Long> productIdList, List<Long> sellerIdList);

    public List<ProductDto> findByProductByName(String name);

}