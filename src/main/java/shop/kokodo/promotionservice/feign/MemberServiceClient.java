package shop.kokodo.promotionservice.feign;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    @GetMapping("/member/id")
    Boolean getMember(@RequestParam(value = "memberId") long memberId);
}
