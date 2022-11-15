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
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import shop.kokodo.promotionservice.dto.FixCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.service.FixCouponService;

// import문을 잘 확인해야 합니다.
import javax.transaction.Transactional;

import java.time.LocalDateTime;
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

    @Autowired
    FixCouponRepository fixCouponRepository;

    FixCouponDto fixCouponDto;
    final long sellerId = 1L;
    final String name = "fixCoupon";
    protected RestDocumentationResultHandler document;

    List<Long> couponIdList;

    @BeforeEach
    public void setUp(){
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint()));

        List<Long> list = new ArrayList<>();
        list.add(1L);

        couponIdList = new ArrayList<>();

        fixCouponDto = FixCouponDto.builder()
                .name(name)
                .price(1000)
                .minPrice(10000)
                .startDate("2022-09-22 00:00")
                .endDate("2022-10-22 00:00")
                .productList(list)
                .sellerId(sellerId)
                .build();

        FixCoupon fixCoupon = FixCoupon.builder()
                .minPrice(10000)
                .name("fixCouponName")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .sellerId(sellerId)
                .productId(1000)
                .regdate(LocalDateTime.now())
                .price(100)
                .minPrice(10000)
                .freeDelivery(true)
                .build();
        couponIdList.add(fixCouponRepository.save(fixCoupon).getId());
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

    @Test
    @DisplayName("seller id로 고정 할인 쿠폰 조회")
    public void findBySellerId() throws Exception {

        MultiValueMap<String,String> params =new LinkedMultiValueMap<>();

        params.add("sellerId",String.valueOf(sellerId));
        params.add("page","1");

        this.mockMvc.perform(
                        get("/fixCoupon/seller")
                                .contentType(MediaType.APPLICATION_JSON)
                                .params(params)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("fix-coupon-rest-controller/find-by-seller-id",
                                requestParameters(
                                        parameterWithName("sellerId").description("셀러 ID"),
                                        parameterWithName("page").description("페이지 번호")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data.totalCount").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 데이터"),
                                        fieldWithPath("result.data.fixCouponList[]").type(JsonFieldType.ARRAY).description("고정 할인 쿠폰 데이터"),
                                        fieldWithPath("result.data.fixCouponList[].createdDate").type(JsonFieldType.STRING).description("고정할인 쿠폰 생성 날짜").optional(),
                                        fieldWithPath("result.data.fixCouponList[].lastModifiedDate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 마지막 수정 날짜").optional(),
                                        fieldWithPath("result.data.fixCouponList[].id").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 id"),
                                        fieldWithPath("result.data.fixCouponList[].name").type(JsonFieldType.STRING).description("고정 할인 쿠폰 이름"),
                                        fieldWithPath("result.data.fixCouponList[].regdate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 등록일"),
                                        fieldWithPath("result.data.fixCouponList[].price").type(JsonFieldType.NUMBER).description("고정 할인 비용"),
                                        fieldWithPath("result.data.fixCouponList[].minPrice").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 적용 최소 비용"),
                                        fieldWithPath("result.data.fixCouponList[].startDate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 적용 시작 날짜"),
                                        fieldWithPath("result.data.fixCouponList[].endDate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 적용 마감 날짜"),
                                        fieldWithPath("result.data.fixCouponList[].productId").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 적용 상품 id"),
                                        fieldWithPath("result.data.fixCouponList[].sellerId").type(JsonFieldType.NUMBER).description("셀러 id"),
                                        fieldWithPath("result.data.fixCouponList[].freeDelivery").type(JsonFieldType.BOOLEAN).description("무료배송 쿠폰 여부"),
                                        fieldWithPath("result.data.fixCouponList[].couponFlag").type(JsonFieldType.NUMBER).description("쿠폰 구분")
                                )
                        )
                );
    }

    @Test
    @DisplayName("고정 할인 쿠폰 이름으로 해당 상품 조회")
    public void findProductByName() throws Exception {

        this.mockMvc.perform(
                        get("/fixCoupon/{name}/product",name)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("fix-coupon-rest-controller/find-product-by-name",
                                pathParameters(
                                        parameterWithName("name").description("쿠폰 이름")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data[]").type(JsonFieldType.ARRAY).description("seller 쿠폰").optional(),
                                        fieldWithPath("result.data[].id").type(JsonFieldType.NUMBER).description("seller 쿠폰").optional(),
                                        fieldWithPath("result.data[].categoryId").type(JsonFieldType.NUMBER).description("seller 쿠폰").optional(),
                                        fieldWithPath("result.data[].name").type(JsonFieldType.STRING).description("seller 쿠폰").optional(),
                                        fieldWithPath("result.data[].price").type(JsonFieldType.NUMBER).description("seller 쿠폰").optional(),
                                        fieldWithPath("result.data[].displayName").type(JsonFieldType.STRING).description("seller 쿠폰").optional(),
                                        fieldWithPath("result.data[].stock").type(JsonFieldType.NUMBER).description("seller 쿠폰").optional(),
                                        fieldWithPath("result.data[].deadline").type(JsonFieldType.STRING).description("seller 쿠폰").optional(),
                                        fieldWithPath("result.data[].thumbnail").type(JsonFieldType.STRING).description("seller 쿠폰").optional(),
                                        fieldWithPath("result.data[].sellerId").type(JsonFieldType.NUMBER).description("seller 쿠폰").optional(),
                                        fieldWithPath("result.data[].deliveryFee").type(JsonFieldType.NUMBER).description("seller 쿠폰").optional()
                                )
                        )
                );
    }

//    @Test
//    @DisplayName("고정 할인 쿠폰 id 리스트로 한번에 조회")
//    public void findFixCouponByCouponIdList() throws Exception {
//
//        String param = "";
//        for (Long id : couponIdList) {
//            param+=id+",";
//        }
//
//        param = param.substring(0,param.length()-1);
//
//        this.mockMvc.perform(
//                        get("/fixCoupon/coupon/list")
//                                .param("couponIdList",param)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(
//                        document("fix-coupon-rest-controller/find-fixCoupon-by-couponIdList",
//                                requestParameters(
//                                        parameterWithName("couponIdList").description("쿠폰 id 리스트")
//                                ),
//                                responseFields(
//                                        fieldWithPath("*").type(JsonFieldType.NUMBER).description("")
//                                )
//                        )
//                );
//    }
}
