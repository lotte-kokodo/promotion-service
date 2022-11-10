package shop.kokodo.promotionservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;

// import문을 잘 확인해야 합니다.
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@ActiveProfiles("test")
public class FixCouponRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    FixCouponDto fixCouponDto;
    final long sellerId = 1L;
    protected RestDocumentationResultHandler document;

    @BeforeEach
    public void setUp(){
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint()));

        List<Long> list = new ArrayList<>();
        list.add(1L);

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
    @DisplayName("고정 할인 쿠폰 생성 api 테스트 성공")
    public void save() throws Exception {

        this.mockMvc.perform(
                post("/fixCoupon")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fixCouponDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("fix-coupon-rest-controller/save",
                            requestFields(
                                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 아이디"),
                                    fieldWithPath("name").type(JsonFieldType.STRING).description("고정 할인 쿠폰 이름"),
                                    fieldWithPath("price").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 가격"),
                                    fieldWithPath("minPrice").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 적용 최소가격"),
                                    fieldWithPath("startDate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 시작 시간"),
                                    fieldWithPath("endDate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 적용 마감 시간"),
                                    fieldWithPath("productList[]").type(JsonFieldType.ARRAY).description("고정 할인 쿠폰 적용 상품 리스트"),
                                    fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 생성 셀러 아이디")
                            ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드")
                                )
                        )
                );
    }

//    @Test
//    @DisplayName("seller id로 고정 할인 쿠폰 조회")
//    public void findBySellerId() throws Exception {
//
//        this.mockMvc.perform(
//                        get("/fixCoupon/seller")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .param("sellerId", String.valueOf(sellerId))
//
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(
//                        document("fix-coupon-rest-controller/find-by-seller-id",
//                                requestParameters(
//                                        parameterWithName("sellerId").description("셀러 ID")
//                                ),
//                                responseFields(
//                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
//                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
//                                        fieldWithPath("result.data[]").type(JsonFieldType.ARRAY).description("seller 쿠폰"),
//                                        fieldWithPath("result.data[].createdDate").type(JsonFieldType.NULL).description("seller 쿠폰").optional(),
//                                        fieldWithPath("result.data[].lastModifiedDate").type(JsonFieldType.NULL).description("seller 쿠폰").optional(),
//                                        fieldWithPath("result.data[].id").type(JsonFieldType.NUMBER).description("seller 쿠폰"),
//                                        fieldWithPath("result.data[].name").type(JsonFieldType.STRING).description("seller 쿠폰"),
//                                        fieldWithPath("result.data[].regdate").type(JsonFieldType.STRING).description("seller 쿠폰"),
//                                        fieldWithPath("result.data[].price").type(JsonFieldType.NUMBER).description("seller 쿠폰"),
//                                        fieldWithPath("result.data[].minPrice").type(JsonFieldType.NUMBER).description("seller 쿠폰"),
//                                        fieldWithPath("result.data[].startDate").type(JsonFieldType.STRING).description("seller 쿠폰"),
//                                        fieldWithPath("result.data[].endDate").type(JsonFieldType.STRING).description("seller 쿠폰"),
//                                        fieldWithPath("result.data[].productId").type(JsonFieldType.NUMBER).description("seller 쿠폰"),
//                                        fieldWithPath("result.data[].sellerId").type(JsonFieldType.NUMBER).description("seller 쿠폰"),
//                                        fieldWithPath("result.data[].freeDelivery").type(JsonFieldType.BOOLEAN).description("seller 쿠폰"),
//                                        fieldWithPath("result.data[].couponFlag").type(JsonFieldType.NUMBER).description("seller 쿠폰")
//                                )
//                        )
//                );
//    }
}
