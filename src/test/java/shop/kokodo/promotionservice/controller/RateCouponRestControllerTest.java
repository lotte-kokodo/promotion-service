package shop.kokodo.promotionservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.metrics.stats.Rate;
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
import shop.kokodo.promotionservice.dto.RateCouponDto;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class RateCouponRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    RateCouponDto rateCouponDto;
    final long sellerId = 1L;
    final String productName = "rateCoupon";
    final long productId = 1L;
    List<Long> productList;

    @BeforeEach
    public void setUp(){
        productList = new ArrayList<>();
        productList.add(productId);
        productList.add(2L);

        rateCouponDto = RateCouponDto.builder()
                .name(productName)
                .rate(5)
                .minPrice(10000)
                .startDate("2022-06-10 16:21:24")
                .endDate("2022-06-10 16:21:24")
                .productList(productList)
                .sellerId(sellerId)
                .build();

    }


    @Test
    @DisplayName("비율 할인 쿠폰 생성 api 테스트")
    public void save() throws Exception {

        this.mockMvc.perform(
                        post("/rateCoupon")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(rateCouponDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-coupon-rest-controller/save",
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 아이디"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("비율 할인 쿠폰 이름"),
                                        fieldWithPath("rate").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 비율"),
                                        fieldWithPath("minPrice").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 적용 최소가격"),
                                        fieldWithPath("startDate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 시작 시간"),
                                        fieldWithPath("endDate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 적용 마감 시간"),
                                        fieldWithPath("productList[]").type(JsonFieldType.ARRAY).description("비율 할인 쿠폰 적용 상품 리스트"),
                                        fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 생성 셀러 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드")
                                )
                        )
                );
    }

    @Test
    @DisplayName("seller id로 비율 할인 쿠폰 조회")
    public void findBySellerId() throws Exception {

        this.mockMvc.perform(
                        get("/rateCoupon/seller")
                                .param("sellerId", String.valueOf(sellerId))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-coupon-rest-controller/seller",
                                requestParameters(
                                        parameterWithName("sellerId").description("셀러 ID")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data[]").type(JsonFieldType.ARRAY).description("비율 할인 쿠폰 데이터").optional(),
                                        fieldWithPath("result.data[].id").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 id").optional(),
                                        fieldWithPath("result.data[].name").type(JsonFieldType.STRING).description("비율 할인 쿠폰 이름").optional(),
                                        fieldWithPath("result.data[].regdate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 등록날짜").optional(),
                                        fieldWithPath("result.data[].rate").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 할인 비율").optional(),
                                        fieldWithPath("result.data[].minPrice").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 최소 비용").optional(),
                                        fieldWithPath("result.data[].startDate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 적용 시작날짜").optional(),
                                        fieldWithPath("result.data[].endDate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 적용 마감 날짜").optional(),
                                        fieldWithPath("result.data[].productId").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 적용 상품 id").optional(),
                                        fieldWithPath("result.data[].sellerId").type(JsonFieldType.NUMBER).description("seller id").optional()

                                )
                        )
                );
    }

    @Test
    @DisplayName("상품 id에 해당하는 비율 할인 쿠폰 조회")
    public void findByProductId() throws Exception {

        this.mockMvc.perform(
                        get("/rateCoupon/{productId}",productId)
                                .param("productId", String.valueOf(productId))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-coupon-rest-controller/find-by-productId",
                                requestParameters(
                                        parameterWithName("productId").description("상품 ID")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data[]").type(JsonFieldType.ARRAY).description("비율 할인 쿠폰 데이터").optional(),
                                        fieldWithPath("result.data[].name").type(JsonFieldType.STRING).description("비율 할인 쿠폰 이름").optional(),
                                        fieldWithPath("result.data[].regdate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 등록날짜").optional(),
                                        fieldWithPath("result.data[].rate").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 할인 비율").optional(),
                                        fieldWithPath("result.data[].minPrice").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 최소 비용").optional(),
                                        fieldWithPath("result.data[].startDate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 적용 시작날짜").optional(),
                                        fieldWithPath("result.data[].endDate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 적용 마감 날짜").optional(),
                                        fieldWithPath("result.data[].productId").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 적용 상품 id").optional(),
                                        fieldWithPath("result.data[].sellerId").type(JsonFieldType.NUMBER).description("seller id").optional()

                                )
                        )
                );
    }

    @Test
    @DisplayName("쿠폰 이름으로 비율 할인 쿠폰 조회")
    public void findProductByCouponName() throws Exception {

        this.mockMvc.perform(
                        get("/rateCoupon/{name}/product",productName)
                                .param("name", productName)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-coupon-rest-controller/find-product-by-couponName",
                                requestParameters(
                                        parameterWithName("name").description("쿠폰 이름")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data[]").type(JsonFieldType.ARRAY).description("상품 데이터").optional(),
                                        fieldWithPath("result.data[].id").type(JsonFieldType.NUMBER).description("상품 id").optional(),
                                        fieldWithPath("result.data[].categoryId").type(JsonFieldType.NUMBER).description("상품 카테고리 id").optional(),
                                        fieldWithPath("result.data[].name").type(JsonFieldType.STRING).description("상품 이름").optional(),
                                        fieldWithPath("result.data[].price").type(JsonFieldType.NUMBER).description("상품 가격").optional(),
                                        fieldWithPath("result.data[].displayName").type(JsonFieldType.STRING).description("상품 노출명").optional(),
                                        fieldWithPath("result.data[].stock").type(JsonFieldType.NUMBER).description("상품 재고").optional(),
                                        fieldWithPath("result.data[].deadline").type(JsonFieldType.STRING).description("상품 유효기간").optional(),
                                        fieldWithPath("result.data[].thumbnail").type(JsonFieldType.STRING).description("상품 대표이미지").optional(),
                                        fieldWithPath("result.data[].sellerId").type(JsonFieldType.NUMBER).description("seller id").optional(),
                                        fieldWithPath("result.data[].deliveryFee").type(JsonFieldType.NUMBER).description("상품 배송비").optional()

                                )
                        )
                );
    }

//    @Test
//    @DisplayName("쿠폰 id 리스트로 비율 할인 쿠폰 조회(productId - RateCoupon)")
//    public void findRateCouponByCouponIdList() throws Exception {
//
//        List<Long> couponIdList = new ArrayList<>();
//        couponIdList.add(1L);
//
//        String param = "";
//        for (Long id : couponIdList) {
//            param+=id+",";
//        }
//
//        param = param.substring(0,param.length()-1);
//
//        this.mockMvc.perform(
//                        get("/rateCoupon/coupon/list")
//                                .param("couponIdList",param)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(
//                        document("rate-coupon-rest-controller/find-rateCoupon-by-couponIdList",
//                                requestParameters(
//                                        parameterWithName("couponIdList").description("쿠폰 아이디 리스트")
//                                ),
//                                responseFields(
//
//
//                                )
//                        )
//                );
//    }

}
