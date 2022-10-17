package shop.kokodo.promotionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.kokodo.promotionservice.dto.ProductDto;
import shop.kokodo.promotionservice.dto.RateCouponDto;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.feign.ProductServiceClient;
import shop.kokodo.promotionservice.repository.RateCouponRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class RateCouponServiceImpl implements RateCouponService{
    private final ProductServiceClient productServiceClient;
    private final RateCouponRepository rateCouponRepository;


    public RateCouponServiceImpl(ProductServiceClient productServiceClient, RateCouponRepository rateCouponRepository) {
        this.productServiceClient = productServiceClient;
        this.rateCouponRepository = rateCouponRepository;
    }

    @Override
    public void save(RateCouponDto rateCouponDto) {

        for (Long productId : rateCouponDto.getProductList()) {
            RateCoupon rateCoupon=convertToRateCoupon(rateCouponDto,productId);
            rateCouponRepository.save(rateCoupon);
        }
    }

    @Override
    public List<RateCoupon> findUserNotUsedRateCouponByproductId(long userId, long productId) {
        return rateCouponRepository.findUserNotUsedRateCouponByproductId(userId,productId, LocalDateTime.now());
    }

    @Override
    public List<RateCoupon> findBySellerId(long sellerId) {
        return rateCouponRepository.findDistinctRateCouponBySellerId(sellerId);
    }

    @Override
    public List<RateCoupon> findByProductId(long productId) {
        return rateCouponRepository.findByProductId(productId, LocalDateTime.now());
    }

    @Override
    public List<ProductDto> findProductByRateCouponName(String name) {

        List<Long> productIdList = rateCouponRepository.findProductIdByName(name);

        return productServiceClient.findProductByName(productIdList);
    }

    private RateCoupon convertToRateCoupon(RateCouponDto rateCouponDto, long productId){
        return RateCoupon.builder()
                .name(rateCouponDto.getName())
                .regdate(LocalDateTime.now())
                .rate(rateCouponDto.getRate())
                .minPrice(rateCouponDto.getMinPrice())
                .startDate(LocalDateTime.parse(rateCouponDto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .endDate(LocalDateTime.parse(rateCouponDto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .productId(productId)
                .sellerId(rateCouponDto.getSellerId())
                .build();
    }
}
