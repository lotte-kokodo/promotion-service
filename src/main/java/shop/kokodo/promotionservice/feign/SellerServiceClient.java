package shop.kokodo.promotionservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="seller-service")
public interface SellerServiceClient {

//    @GetMapping("/seller-service/")
//    Optional<>
}
