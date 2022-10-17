package shop.kokodo.promotionservice.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.repository.FixDiscountPolicyRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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

    @Override
    public Map<Long, FixDiscountPolicyDto> findAllByProductIdList(List<Long> productIdList) {
        ModelMapper mapper = new ModelMapper();

        List<RateDiscountPolicy> result = fixDiscountPolicyRepository.findAllByProductId(productIdList);

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        List<FixDiscountPolicyDto> list = result.stream()
                .map(source -> mapper.map(source, FixDiscountPolicyDto.class))
                .collect(Collectors.toList());

        Map<Long, FixDiscountPolicyDto> map = new HashMap<>();

        for(int i=0;i<list.size();i++) {
            map.put(productIdList.get(i), list.get(i));
        }

        return map;
    }

    @Override
    public Response getFixDiscountPolicyStatus(Long productId, Long sellerId) {
        Boolean result = true;
        try {
            FixDiscountPolicy fixDiscountPolicy = fixDiscountPolicyRepository.findByProductIdAndSellerId(productId, sellerId).orElseThrow();
        }catch(Exception e) {
            result = false;
        }

        Map<Long, Boolean> response = new HashMap<Long, Boolean>();
        response.put(sellerId, result);
        return Response.success(response);
    }
}
