package shop.kokodo.promotionservice.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.repository.RateDiscountPolicyRepository;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RateDiscountPolicyServiceImpl implements RateDiscountPolicyService {
    private RateDiscountPolicyRepository rateDiscountPolicyRepository;

    @Autowired
    public RateDiscountPolicyServiceImpl(RateDiscountPolicyRepository rateDiscountPolicyRepository) {
        this.rateDiscountPolicyRepository = rateDiscountPolicyRepository;
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

        for(int i=0;i<rateDiscountPolicyDtoList.size();i++) {
            map.put(productIdList.get(i), rateDiscountPolicyDtoList.get(i));
        }

        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Response findBySellerId(Long sellerId) {
        return Response.success(rateDiscountPolicyRepository.findAllBySellerId(sellerId));
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

        List<RateDiscountPolicyDto> resultList = list.stream()
                .map(source -> mapper.map(source, RateDiscountPolicyDto.class))
                .collect(Collectors.toList());

        return resultList;
    }

    public RateDiscountPolicy makeDtoToEntity(RateDiscountPolicyDto dto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        RateDiscountPolicy rateDiscountPolicy;
        rateDiscountPolicy = mapper.map(dto, RateDiscountPolicy.class);
        return rateDiscountPolicy;
    }
}
