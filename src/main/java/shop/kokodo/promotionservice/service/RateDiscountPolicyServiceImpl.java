package shop.kokodo.promotionservice.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.circuitbreaker.AllCircuitBreaker;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.feign.ProductServiceClient;
import shop.kokodo.promotionservice.repository.RateDiscountPolicyRepository;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final CircuitBreaker circuitBreaker = AllCircuitBreaker.createSellerCircuitBreaker();

    @Autowired
    public RateDiscountPolicyServiceImpl(RateDiscountPolicyRepository rateDiscountPolicyRepository
    , ProductServiceClient productServiceClient) {
        this.rateDiscountPolicyRepository = rateDiscountPolicyRepository;
        this.productServiceClient = productServiceClient;
    }


    @Transactional(readOnly = true)
    public List<RateDiscountPolicy> getAll() {
        return rateDiscountPolicyRepository.findAll();
    }

    @Transactional(readOnly = false)
    public RateDiscountPolicy createRateDiscountPolicy(RateDiscountPolicyDto rateDiscountPolicyDto) {
        RateDiscountPolicy rateDiscountPolicy = makeDtoToEntity(rateDiscountPolicyDto);
        return rateDiscountPolicyRepository.save(rateDiscountPolicy);
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
    public Response findBySellerId(Long sellerId) {
        return Response.success(rateDiscountPolicyRepository.findAllBySellerId(sellerId));
    }

    @Override
    public List<ProductDto> findByProductByName(String name) {
        List<Long> productIdList = rateDiscountPolicyRepository.findProductIdByName(name);

        return circuitBreaker.run(() -> productServiceClient.findProductByName(productIdList)
        , throwable -> new ArrayList<>());
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
}
