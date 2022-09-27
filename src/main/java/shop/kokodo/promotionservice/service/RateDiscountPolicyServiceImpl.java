package shop.kokodo.promotionservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.repository.RateDiscountPolicyRepository;

import java.util.List;

@Service
public class RateDiscountPolicyServiceImpl implements RateDiscountPolicyService {
    private RateDiscountPolicyRepository rateDiscountPolicyRepository;

    @Autowired
    public RateDiscountPolicyServiceImpl(RateDiscountPolicyRepository rateDiscountPolicyRepository) {
        this.rateDiscountPolicyRepository = rateDiscountPolicyRepository;
    }

    @GetMapping(value="/ratediscountpolicy/getall")
    public List<RateDiscountPolicyDto> getAll() {
        return rateDiscountPolicyRepository.findAll();
    }

    @GetMapping(value="/ratediscountpolicy/getdiscountpolicy/{productId}")
    public RateDiscountPolicyDto getRateDiscountPolicy(@PathVariable("productId")Long productId) {
        return rateDiscountPolicyRepository.findByProductId(productId).get();
    }

    @PostMapping(value="/ratediscountpolicy/")
    public RateDiscountPolicyDto createRateDiscountPolicy(@RequestBody RateDiscountPolicyDto rateDiscountPolicy) {
        return rateDiscountPolicyRepository.save(rateDiscountPolicy);
    }

}
