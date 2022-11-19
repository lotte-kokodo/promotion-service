package shop.kokodo.promotionservice.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.circuitbreaker.AllCircuitBreaker;
import shop.kokodo.promotionservice.dto.PagingRateDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.RateDiscountPolicySaveDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.exception.DuplicateDiscountPolicyNameException;
import shop.kokodo.promotionservice.feign.OrderServiceClient;
import shop.kokodo.promotionservice.feign.ProductServiceClient;
import shop.kokodo.promotionservice.repository.RateDiscountPolicyRepository;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * packageName : shop.kokodo.promotionservice.service
 * fileName : RateDiscountPolicyServiceImpl
 * author : SSOsh
 * date : 2022-11-03
 * description : 비율 할인 쿠폰 관리 서비스
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-03           SSOsh              최초 생성
 */
@Service
public class RateDiscountPolicyServiceImpl implements RateDiscountPolicyService {
    private RateDiscountPolicyRepository rateDiscountPolicyRepository;
    private final ProductServiceClient productServiceClient;
    private final OrderServiceClient orderServiceClient;

    private final CircuitBreaker circuitBreaker = AllCircuitBreaker.createSellerCircuitBreaker();

    @Autowired
    public RateDiscountPolicyServiceImpl(RateDiscountPolicyRepository rateDiscountPolicyRepository
    , ProductServiceClient productServiceClient
    , OrderServiceClient orderServiceClient) {
        this.rateDiscountPolicyRepository = rateDiscountPolicyRepository;
        this.productServiceClient = productServiceClient;
        this.orderServiceClient = orderServiceClient;
    }


    @Transactional(readOnly = true)
    public List<RateDiscountPolicy> getAll() {
        return rateDiscountPolicyRepository.findAll();
    }

    @Transactional(readOnly = false)
    public List<RateDiscountPolicy> createRateDiscountPolicy(RateDiscountPolicySaveDto rateDiscountPolicySaveDto) {

        boolean flag = rateDiscountPolicyRepository.existsByName(rateDiscountPolicySaveDto.getName());
        if(flag) {
            throw new DuplicateDiscountPolicyNameException("같은 이름의 정책이 존재합니다!");
        }
        List<RateDiscountPolicy> rateDiscountPolicyList = saveDtoToDto(rateDiscountPolicySaveDto);

        return rateDiscountPolicyRepository.saveAll(rateDiscountPolicyList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RateDiscountPolicy> getRateDiscountPolicyByDate() {
        return rateDiscountPolicyRepository.findDateRangeRateDiscountPolicy(LocalDateTime.now());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, RateDiscountPolicyDto> findAllByProductIdList(List<Long> productIdList) {

        List<RateDiscountPolicy> result = rateDiscountPolicyRepository.findAllByProductId(productIdList);

        List<RateDiscountPolicyDto> rateDiscountPolicyDtoList = makeEntityListToDtoList(result);

        Map<Long, RateDiscountPolicyDto> map = new HashMap<>();

        for (RateDiscountPolicyDto rateDiscountPolicyDto : rateDiscountPolicyDtoList) {
            map.put(rateDiscountPolicyDto.getProductId(), rateDiscountPolicyDto);
        }

        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public PagingRateDiscountPolicyDto findBySellerId(Long sellerId, int page) {
        Page<RateDiscountPolicy> rateDiscountPolicyPage = rateDiscountPolicyRepository.findAllBySellerId(sellerId, PageRequest.of(page,5));

        return PagingRateDiscountPolicyDto.builder()
                .rateDiscountPolicyList(rateDiscountPolicyPage.toList())
                .totalCount(rateDiscountPolicyPage.getTotalElements())
                .build();
    }

    @Override
    public List<ProductDto> findByProductByName(String name) {
        List<Long> productIdList = rateDiscountPolicyRepository.findProductIdByName(name);

        return circuitBreaker.run(() -> productServiceClient.findProductByName(productIdList)
        , throwable -> new ArrayList<>());
    }

    @Override
    public Integer findProductBySellerId(Long sellerId) {
        List<RateDiscountPolicy> rateDiscountPolicyList = rateDiscountPolicyRepository.findBySellerId(sellerId);
        log.info("rateDiscountPolicyList : " + rateDiscountPolicyList);
        List<Long> productIdList = rateDiscountPolicyList.stream()
                        .map(RateDiscountPolicy::getProductId)
                        .collect(Collectors.toList());

        Map<Long, List<Integer>> discountPrice = circuitBreaker.run(() -> orderServiceClient.findByProductId(productIdList)
                , throwable -> new HashMap<Long, List<Integer>>());

        Map<Long, Integer> discountRate = rateDiscountPolicyList.stream()
                .collect(Collectors.toMap(
                        RateDiscountPolicy::getProductId,
                        RateDiscountPolicy::getRate,
                        (oldRate, newRate) -> (oldRate > newRate) ? oldRate : newRate
                ));

        Integer result = 0;
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(0);
        for(Long productId : productIdList) {
            result += calculateDiscountPrice(discountRate.getOrDefault(productId, 0), discountPrice.getOrDefault(productId, list));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public Response findByProductId(Long productId) {
        List<RateDiscountPolicy> rateDiscountPolicy = rateDiscountPolicyRepository.findAllByProductId(productId);
        RateDiscountPolicy result;
        if(rateDiscountPolicy.isEmpty()) {
            result = new RateDiscountPolicy();
            result.setRate(0);
            return Response.success(result);
        }

        int index = -1;
        int max = -1;
        for(int i=0;i<rateDiscountPolicy.size();i++) {
            if(rateDiscountPolicy.get(i).getRate() > max) {
                index = i;
                max = rateDiscountPolicy.get(i).getRate();
            }
        }
        result = rateDiscountPolicy.get(index);

        return Response.success(result);
    }

    public List<RateDiscountPolicyDto> makeEntityListToDtoList(List<RateDiscountPolicy> list) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

        return list.stream()
                .map(source -> mapper.map(source, RateDiscountPolicyDto.class))
                .collect(Collectors.toList());
    }

    public RateDiscountPolicy makeDtoToEntity(RateDiscountPolicyDto dto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        RateDiscountPolicy rateDiscountPolicy;
        rateDiscountPolicy = mapper.map(dto, RateDiscountPolicy.class);
        return rateDiscountPolicy;
    }

    public Integer calculateDiscountPrice(Integer rate, List<Integer> priceAndQty) {
        Integer price = priceAndQty.get(0);
        Integer qty = priceAndQty.get(1);
        return ((price * 100 / (100 - rate)) - price) * qty;
    }

    public List<RateDiscountPolicy> saveDtoToDto(RateDiscountPolicySaveDto dto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        List<RateDiscountPolicy> rateDiscountPolicyList = new ArrayList<>();
        for(Long productId : dto.getProductId()) {
            rateDiscountPolicyList.add(
                    RateDiscountPolicy.builder()
                            .rateDiscountPolicyId(dto.getRateDiscountPolicyId())
                            .name(dto.getName())
                            .regDate(dto.getRegDate())
                            .startDate(dto.getStartDate())
                            .endDate(dto.getEndDate())
                            .rate(dto.getRate())
                            .minPrice(dto.getMinPrice())
                            .productId(productId)
                            .sellerId(dto.getSellerId())
                            .build()

            );
        }
        return rateDiscountPolicyList;
    }
}
