package shop.kokodo.promotionservice.service;

import java.util.stream.IntStream;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.ProductSeller;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.repository.FixDiscountPolicyRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * packageName : shop.kokodo.promotionservice.service
 * fileName : FixDiscountPolicyService
 * author : SSOsh
 * date : 2022-11-03
 * description : 고정 할인 쿠폰 관리 서비스
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-03           SSOsh              최초 생성
 */
@Service
public class FixDiscountPolicyServiceImpl implements FixDiscountPolicyService {
    private final FixDiscountPolicyRepository fixDiscountPolicyRepository;

    @Autowired
    public FixDiscountPolicyServiceImpl(FixDiscountPolicyRepository fixDiscountPolicyRepository) {
        this.fixDiscountPolicyRepository = fixDiscountPolicyRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public FixDiscountPolicy save(FixDiscountPolicyDto fixDiscountPolicyDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        FixDiscountPolicy fixDiscountPolicy = mapper.map(fixDiscountPolicyDto, FixDiscountPolicy.class);
        return fixDiscountPolicyRepository.save(fixDiscountPolicy);
    }

    @Override
    @Transactional(readOnly = false)
    public List<FixDiscountPolicy> getAll() {
        return fixDiscountPolicyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public FixDiscountPolicy getFixDiscountPolicy(Long productId) {
        return fixDiscountPolicyRepository.findByProductId(productId).orElse(new FixDiscountPolicy());
    }

    @Override
    @Transactional(readOnly = false)
    public Map<Long, FixDiscountPolicyDto> findAllByProductIdList(List<Long> productIdList) {
        ModelMapper mapper = new ModelMapper();

        List<FixDiscountPolicy> result = fixDiscountPolicyRepository.findAllByProductId(productIdList);

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        List<FixDiscountPolicyDto> list = result.stream()
                .map(source -> mapper.map(source, FixDiscountPolicyDto.class))
                .collect(Collectors.toList());

        Map<Long, FixDiscountPolicyDto> map = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            map.put(productIdList.get(i), list.get(i));
        }

        return map;
    }

    @Override
    @Transactional(readOnly = false)
    public Response getFixDiscountPolicyStatus(List<Long> productIdList, List<Long> sellerIdList) {
        Map<Long, Boolean> response = new HashMap<Long, Boolean>();

        if (productIdList.size() != sellerIdList.size()) {
            throw new IllegalArgumentException("상품-셀러 아이디 리스트 크기 불일치");
        }

        List<ProductSeller> productSellerList = IntStream.range(0, productIdList.size()).boxed()
            .map(idx -> new ProductSeller(productIdList.get(idx), sellerIdList.get(idx)) )
            .collect(Collectors.toList());

        productSellerList
            .forEach(productSeller -> {
                response.put(productSeller.getSellerId(), false);
            });
        productSellerList
            .forEach(productSeller -> {
                FixDiscountPolicy fixDiscountPolicy = fixDiscountPolicyRepository.findAllByProductIdAndSellerIdIn(productSeller.getProductId(), productSeller.getSellerId());
                if ((fixDiscountPolicy != null) && !response.get(productSeller.getSellerId())) {
                    response.put(productSeller.getSellerId(), true);
                }
            });

        return Response.success(response);
    }

    @Override
    @Transactional(readOnly = false)
    public Map<Long, Boolean> getFixDiscountPolicyStatusForFeign(List<Long> productIdList, List<Long> sellerIdList) {
        Map<Long, Boolean> response = new HashMap<Long, Boolean>();

        if (productIdList.size() != sellerIdList.size()) {
            throw new IllegalArgumentException("상품-셀러 아이디 리스트 크기 불일치");
        }

        List<ProductSeller> productSellerList = IntStream.range(0, productIdList.size()).boxed()
            .map(idx -> new ProductSeller(productIdList.get(idx), sellerIdList.get(idx)) )
            .collect(Collectors.toList());

        productSellerList
            .forEach(productSeller -> {
                response.put(productSeller.getSellerId(), false);
            });
        productSellerList
            .forEach(productSeller -> {
                FixDiscountPolicy fixDiscountPolicy = fixDiscountPolicyRepository.findAllByProductIdAndSellerIdIn(productSeller.getProductId(), productSeller.getSellerId());
                if ((fixDiscountPolicy != null) && !response.get(productSeller.getSellerId())) {
                    response.put(productSeller.getSellerId(), true);
                }
            });

        return response;
    }

    @Override
    @Transactional(readOnly = false)
    public Response findBySellerId(Long sellerId) {
        return Response.success(fixDiscountPolicyRepository.findAllBySellerId(sellerId));
    }

}
