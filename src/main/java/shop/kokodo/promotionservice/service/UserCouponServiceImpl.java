package shop.kokodo.promotionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCouponServiceImpl implements UserCouponService{

    private final UserCouponRepository userCouponRepository;

    @Override
    public List<UserCoupon> findByUserId(long userId) {
        return userCouponRepository.findByUserId(userId);
    }
}
