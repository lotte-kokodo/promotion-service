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
import java.util.Optional;
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

    @Override
    @Transactional
    public Response findBySellerId(Long sellerId) {
        return Response.success(rateDiscountPolicyRepository.findAllBySellerId(sellerId));
    }

    @Transactional
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
}
