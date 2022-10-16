package shop.kokodo.promotionservice.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.repository.RateDiscountPolicyRepository;

import javax.transaction.Transactional;
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

    @Transactional
    public List<RateDiscountPolicy> getAll() {
        return rateDiscountPolicyRepository.findAll();
    }

    @Transactional
    public RateDiscountPolicy createRateDiscountPolicy(RateDiscountPolicyDto rateDiscountPolicyDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        RateDiscountPolicy rateDiscountPolicy = mapper.map(rateDiscountPolicyDto, RateDiscountPolicy.class);
        return rateDiscountPolicyRepository.save(rateDiscountPolicy);
    }

    @Override
    public List<RateDiscountPolicy> getRateDiscountPolicyByDate() {
        return rateDiscountPolicyRepository.findDateRangeRateDiscountPolicy(LocalDateTime.now());
    }

    @Override
    public Map<Long, RateDiscountPolicyDto> findAllByProductIdList(List<Long> productIdList) {
        ModelMapper mapper = new ModelMapper();

        List<RateDiscountPolicy> result = rateDiscountPolicyRepository.findAllByProductId(productIdList);

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        List<RateDiscountPolicyDto> list = result.stream()
                .map(source -> mapper.map(source, RateDiscountPolicyDto.class))
                .collect(Collectors.toList());

        Map<Long, RateDiscountPolicyDto> map = new HashMap<>();

        for(int i=0;i<list.size();i++) {
            map.put(productIdList.get(i), list.get(i));
        }

        return map;
    }

    @Transactional
    public RateDiscountPolicy findByProductId(Long productId) {
        return rateDiscountPolicyRepository.findByProductId(productId).orElse(null);
    }
}
