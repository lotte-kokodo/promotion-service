package shop.kokodo.promotionservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.repository.FixDiscountPolicyRepository;

@Service
public class FixDiscountPolicyServiceImpl implements FixDiscountPolicyService {
    private FixDiscountPolicyRepository fixDiscountPolicyRepository;

    @Autowired
    public FixDiscountPolicyServiceImpl(FixDiscountPolicyRepository fixDiscountPolicyRepository) {
        this.fixDiscountPolicyRepository = fixDiscountPolicyRepository;
    }
}
