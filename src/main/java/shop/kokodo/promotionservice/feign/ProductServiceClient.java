package shop.kokodo.promotionservice.feign;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.kokodo.promotionservice.dto.ProductDto;

import java.util.List;

@FeignClient(name="product-service")
public interface ProductServiceClient {

    @GetMapping("/products/feign/list")
    List<ProductDto> findProductByName(@RequestParam List<Long> productIdList);

    @GetMapping("/product/feign/id")
    Boolean findProductById(@RequestParam(value = "productId") Long productId);

}
