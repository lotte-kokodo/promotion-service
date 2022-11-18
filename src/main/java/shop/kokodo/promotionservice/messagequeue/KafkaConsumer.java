package shop.kokodo.promotionservice.messagequeue;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.messagequeue.KafkaOrderDto.KafkaCouponNameDto;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KafkaProducer kafkaProducer;
    private final KafkaMessageParser kafkaMessageParser;
    private final UserCouponRepository userCouponRepository;

    @KafkaListener(topics = "promotion-coupon-status")
    public void updateUserCouponStatus(String kafkaMessage){
        log.info("Kafka Message : {} ",kafkaMessage);

        try {
            KafkaOrderDto kafkaOrderDto
                = kafkaMessageParser.readMessageValue(kafkaMessage, new TypeReference<KafkaOrderDto>() {});

            KafkaCouponNameDto couponNameDto = kafkaOrderDto.getKafkaCouponNameDto();
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
        catch (Exception exception) {
            kafkaProducer.send("product-decrease-stock-rollback", kafkaMessage);
        }

    }
}