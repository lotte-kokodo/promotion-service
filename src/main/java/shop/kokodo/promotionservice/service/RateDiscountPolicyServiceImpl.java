package shop.kokodo.promotionservice.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.repository.RateDiscountPolicyRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        RateDiscountPolicy rateDiscountPolicy = mapper.map(rateDiscountPolicyDto, RateDiscountPolicy.class);
        return rateDiscountPolicyRepository.save(rateDiscountPolicy);
    }

    @Override
    public List<RateDiscountPolicy> getRateDiscountPolicyByDate() {
        return rateDiscountPolicyRepository.findDateRangeRateDiscountPolicy(LocalDateTime.now());
    }

    @Transactional
    public RateDiscountPolicy getRateDiscountPolicy(Long productId) {
        return rateDiscountPolicyRepository.findByProductId(productId).orElse(new RateDiscountPolicy());
    }
}
