package shop.kokodo.promotionservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.kokodo.promotionservice.dto.ProductDto;

import java.util.List;

@FeignClient(name="product-service")
public interface ProductServiceClient {

    @GetMapping("/product/list")
    List<ProductDto> findProductByName(@RequestParam List<Long> productIdList);
}
