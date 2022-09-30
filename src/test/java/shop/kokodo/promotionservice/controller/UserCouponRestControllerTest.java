package shop.kokodo.promotionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import shop.kokodo.promotionservice.dto.UserCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class UserCouponRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FixCouponRepository fixCouponRepository;

    @Autowired
    RateCouponRepository rateCouponRepository;

    UserCouponDto userRateCouponDto;
    UserCouponDto userFixCouponDto;

    FixCoupon fixCoupon;
    RateCoupon rateCoupon;

    @BeforeEach
    public void setUp(){
        fixCoupon= FixCoupon.builder()
                .name("fixCouponName")
                .regdate(LocalDateTime.now())
                .price(1000)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,25,0,0))
                .endDate(LocalDateTime.of(2022,9,25,0,0))
                .productId(1L)
                .sellerId(2L)
                .build();

        rateCoupon=RateCoupon.builder()
                .name("rateCouponName")
                .regdate(LocalDateTime.now())
                .rate(1)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,25,0,0))
                .endDate(LocalDateTime.of(2022,9,25,0,0))
                .productId(1L)
                .sellerId(2L)
                .build();

        fixCoupon=fixCouponRepository.save(fixCoupon);
        rateCoupon=rateCouponRepository.save(rateCoupon);

        userRateCouponDto = UserCouponDto.builder()
                .userId(1L)
                .rateCouponId(rateCoupon.getId())
                .build();

        userFixCouponDto = UserCouponDto.builder()
                .userId(1L)
                .fixCouponId(fixCoupon.getId())
                .build();

    }

    @Test
    @DisplayName("사용자 고정 할인 쿠폰 생성(다운로드) ")
    public void saveFixCoupon() throws Exception {

        this.mockMvc.perform(
                        post("/userCoupon/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userFixCouponDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("user-coupon-rest-controller/saveFixCoupon",
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 아이디"),
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 ID"),
                                        fieldWithPath("usageStatus").type(JsonFieldType.NUMBER).description("쿠폰 사용 여부"),
                                        fieldWithPath("rateCouponId").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 ID").optional(),
                                        fieldWithPath("fixCouponId").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 ID").optional()
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data.id").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.userId").type(JsonFieldType.NUMBER).description("저장된 쿠폰 유저 아이디"),
                                        fieldWithPath("result.data.usageStatus").type(JsonFieldType.NUMBER).description("저장된 쿠폰 사용 여부"),
                                        fieldWithPath("result.data.fixCoupon.id").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon.name").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon.regdate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon.price").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon.minPrice").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon.startDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon.endDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon.productId").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon.sellerId").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon.freeDelivery").type(JsonFieldType.BOOLEAN).description("무료배송쿠폰 여부"),
                                        fieldWithPath("result.data.fixCoupon.couponFlag").type(JsonFieldType.NUMBER).description("비율(1)/고정(2) 할인 플래그"),
                                        fieldWithPath("result.data.fixCoupon.createdDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon.lastModifiedDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.rateCoupon").type(JsonFieldType.NULL).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.createdDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.lastModifiedDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디")
                                        )
                        )
                );
    }


    @Test
    @DisplayName("사용자 비율 할인 쿠폰 생성(다운로드) ")
    public void saveRateCoupon() throws Exception {

        this.mockMvc.perform(
                        post("/userCoupon/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRateCouponDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("user-coupon-rest-controller/saveRateCoupon",
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 아이디"),
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 ID"),
                                        fieldWithPath("usageStatus").type(JsonFieldType.NUMBER).description("쿠폰 사용 여부"),
                                        fieldWithPath("rateCouponId").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 ID").optional(),
                                        fieldWithPath("fixCouponId").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 ID").optional()
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data.id").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.userId").type(JsonFieldType.NUMBER).description("저장된 쿠폰 유저 아이디"),
                                        fieldWithPath("result.data.usageStatus").type(JsonFieldType.NUMBER).description("저장된 쿠폰 사용 여부"),
                                        fieldWithPath("result.data.rateCoupon.id").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.rateCoupon.name").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.rateCoupon.regdate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.rateCoupon.rate").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.rateCoupon.minPrice").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.rateCoupon.startDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.rateCoupon.endDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.rateCoupon.productId").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.rateCoupon.sellerId").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.rateCoupon.couponFlag").type(JsonFieldType.NUMBER).description("비율(1)/고정(2) 할인 플래그"),
                                        fieldWithPath("result.data.rateCoupon.createdDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.rateCoupon.lastModifiedDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon").type(JsonFieldType.NULL).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.createdDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.lastModifiedDate").type(JsonFieldType.STRING).description("저장된 쿠폰 아이디")
                                )
                        )
                );
    }
}
