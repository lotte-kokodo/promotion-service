package shop.kokodo.promotionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.repository.RateCouponRepository;

@Service
@RequiredArgsConstructor
public class RateCouponServiceImpl implements RateCouponService{

    private final RateCouponRepository rateCouponRepository;
    @Override
    public void save(RateCoupon rateCoupon) {

        RateCoupon rc=rateCouponRepository.save(rateCoupon);


    }
}
