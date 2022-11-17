package shop.kokodo.promotionservice.service;

import java.util.stream.IntStream;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.kokodo.promotionservice.circuitbreaker.AllCircuitBreaker;
import shop.kokodo.promotionservice.dto.*;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.feign.ProductServiceClient;
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
    private final ProductServiceClient productServiceClient;

    private final CircuitBreaker circuitBreaker = AllCircuitBreaker.createSellerCircuitBreaker();

    @Autowired
    public FixDiscountPolicyServiceImpl(FixDiscountPolicyRepository fixDiscountPolicyRepository
            , ProductServiceClient productServiceClient) {
        this.fixDiscountPolicyRepository = fixDiscountPolicyRepository;
        this.productServiceClient = productServiceClient;
    }

    @Override
    @Transactional(readOnly = false)
    public List<FixDiscountPolicy> save(FixDiscountPolicySaveDto fixDiscountPolicySaveDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        List<FixDiscountPolicy> fixDiscountPolicyList = saveDtoToDto(fixDiscountPolicySaveDto);
        return fixDiscountPolicyRepository.saveAll(fixDiscountPolicyList);
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
    public Map<Long, Boolean> getFixDiscountPolicyStatus(List<Long> productIdList, List<Long> sellerIdList) {
        Map<Long, Boolean> response = new HashMap<>();

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
    public List<ProductDto> findByProductByName(String name) {
        List<Long> productIdList = fixDiscountPolicyRepository.findProductIdByName(name);

        return circuitBreaker.run(() -> productServiceClient.findProductByName(productIdList)
                , throwable -> new ArrayList<>());
    }

    @Override
    @Transactional(readOnly = false)
    public PagingFixDiscountPolicyDto findBySellerId(Long sellerId, int page) {
        Page<FixDiscountPolicy> fixDiscountPolicyPage = fixDiscountPolicyRepository.findBySellerId(sellerId, PageRequest.of(page,5));
        System.out.println("*********");
        System.out.println(fixDiscountPolicyPage.toList());
        return PagingFixDiscountPolicyDto.builder()
                .fixDiscountPolicyList(fixDiscountPolicyPage.toList())
                .totalCount(fixDiscountPolicyPage.getTotalElements())
                .build();
    }

    public List<FixDiscountPolicy> saveDtoToDto(FixDiscountPolicySaveDto dto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        List<FixDiscountPolicy> fixDiscountPolicyList = new ArrayList<>();
        for(Long productId : dto.getProductId()) {
            fixDiscountPolicyList.add(
                    FixDiscountPolicy.builder()
                            .fixDiscountPolicyId(dto.getFixDiscountPolicyId())
                            .name(dto.getName())
                            .regDate(dto.getRegDate())
                            .startDate(dto.getStartDate())
                            .endDate(dto.getEndDate())
                            .price(dto.getPrice())
                            .minPrice(dto.getMinPrice())
                            .productId(productId)
                            .sellerId(dto.getSellerId())
                            .build()

            );
        }
        return fixDiscountPolicyList;
    }
}
