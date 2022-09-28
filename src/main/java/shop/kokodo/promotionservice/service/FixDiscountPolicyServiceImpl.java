package shop.kokodo.promotionservice.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.repository.FixDiscountPolicyRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class FixDiscountPolicyServiceImpl implements FixDiscountPolicyService {
    private FixDiscountPolicyRepository fixDiscountPolicyRepository;

    @Autowired
    public FixDiscountPolicyServiceImpl(FixDiscountPolicyRepository fixDiscountPolicyRepository) {
        this.fixDiscountPolicyRepository = fixDiscountPolicyRepository;
    }

    @Override
    @Transactional
    public FixDiscountPolicy save(FixDiscountPolicyDto fixDiscountPolicyDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        FixDiscountPolicy fixDiscountPolicy = mapper.map(fixDiscountPolicyDto, FixDiscountPolicy.class);
        return fixDiscountPolicyRepository.save(fixDiscountPolicy);
    }

    @Override
    @Transactional
    public List<FixDiscountPolicy> getAll() {
        return fixDiscountPolicyRepository.findAll();
    }

    @Override
    @Transactional
    public FixDiscountPolicy getFixDiscountPolicy(Long productId) {
        return fixDiscountPolicyRepository.findByProductId(productId).orElse(new FixDiscountPolicy());
    }
}
