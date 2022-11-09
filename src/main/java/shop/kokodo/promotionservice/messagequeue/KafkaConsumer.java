package shop.kokodo.promotionservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
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

        List<Long> list = new ArrayList<>();

        try {
            list = objectMapper.readValue(kafkaMessage, new TypeReference<List<Long>>() {});
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        for (Long userCouponId : list) {
            UserCoupon userCoupon = userCouponRepository.findById(userCouponId).orElseThrow();
            userCoupon.useCoupon();
            userCouponRepository.save(userCoupon);

        }
    }
}