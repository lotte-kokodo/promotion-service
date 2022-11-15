package shop.kokodo.promotionservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.dto.CouponNameDto;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final UserCouponRepository userCouponRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "promotion-coupon-status")
    public void updateUserCouponStatus(String kafkaMessage){
        log.info("Kafka Message : {} ",kafkaMessage);

        CouponNameDto couponNameDto = null;

        try {
            couponNameDto = objectMapper.readValue(kafkaMessage, new TypeReference<CouponNameDto>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        long memberId = couponNameDto.getMemberId();
        List<UserCoupon> rateUserCoupon = userCouponRepository.findByRateCouponNameList(couponNameDto.getRateCouponNames(),memberId);
        List<UserCoupon> fixUserCoupon = userCouponRepository.findByFixCouponIdList(couponNameDto.getFixCouponIdList(),memberId);

        for (UserCoupon userCoupon : fixUserCoupon) {
            userCoupon.useCoupon();
            userCouponRepository.save(userCoupon);
        }

        for (UserCoupon userCoupon : rateUserCoupon) {
            userCoupon.useCoupon();
            userCouponRepository.save(userCoupon);
        }
    }
}