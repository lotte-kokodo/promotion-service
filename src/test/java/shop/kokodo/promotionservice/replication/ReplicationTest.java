package shop.kokodo.promotionservice.replication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.service.FixCouponService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReplicationTest {

    @Autowired
    private FixCouponService fixCouponService;

    FixCouponDto fixCouponDto;
    final Long sellerId=1L;
    final Long productId=1L;
    final LocalDateTime now=LocalDateTime.of(2022,9,25,0,0);

    @BeforeEach
    public void setUp(){
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);

        fixCouponDto = FixCouponDto.builder()
                .name("fixCoupon")
                .price(1000)
                .minPrice(10000)
                .startDate("2022-09-22 00:00")
                .endDate("2022-10-22 00:00")
                .productList(list)
                .sellerId(sellerId)
                .build();
    }

    @Test
    @DisplayName("Save 시 데이터 소스는 Master 선택")
    void saveFixCoupon_Master(){

        fixCouponService.save(fixCouponDto);
    }

    @Test
    @DisplayName("Slave DB에서 데이터 조회")
    void findFixcoupon_Slave(){
        System.out.println("===== save ======");
        fixCouponService.save(fixCouponDto);
        System.out.println("===== select 1 =====");
        fixCouponService.findBySellerId(sellerId);
        System.out.println("===== select 2 =====");
        fixCouponService.findBySellerId(sellerId);
        System.out.println("===== select 3 =====");
        fixCouponService.findBySellerId(sellerId);
        System.out.println("===== select 4 =====");
        fixCouponService.findBySellerId(sellerId);
    }


}