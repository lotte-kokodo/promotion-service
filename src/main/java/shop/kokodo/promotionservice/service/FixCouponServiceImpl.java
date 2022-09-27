package shop.kokodo.promotionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;

@Service
@RequiredArgsConstructor
public class FixCouponServiceImpl implements FixCouponService{

    private final FixCouponRepository fixCouponRepository;

    @Override
    public void save(FixCoupon fixCoupon) {
        fixCouponRepository.save(fixCoupon);
    }
}
