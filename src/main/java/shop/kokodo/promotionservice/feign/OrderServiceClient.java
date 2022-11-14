package shop.kokodo.promotionservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * packageName : shop.kokodo.promotionservice.feign
 * fileName : OrderServiceClient
 * author : BTC-N24
 * date : 2022-11-10
 * description :
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-10           BTC-N24              최초 생성
 */
@FeignClient(name="order-service")
public interface OrderServiceClient {
    @GetMapping("orders/feign/product")
    Map<Long, List<Integer>> findByProductId(@RequestParam List<Long> productIdList);
}
