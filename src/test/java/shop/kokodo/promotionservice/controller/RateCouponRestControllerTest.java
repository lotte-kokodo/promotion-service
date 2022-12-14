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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import shop.kokodo.promotionservice.dto.RateCouponDto;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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
    @DisplayName("?????? ?????? ?????? ?????? api ?????????")
    public void save() throws Exception {

        this.mockMvc.perform(
                        post("/rateCoupon")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(rateCouponDto))
                                .header("sellerId","1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-coupon-rest-controller/save",
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("rate").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("minPrice").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ????????????"),
                                        fieldWithPath("startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ??????"),
                                        fieldWithPath("endDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ?????? ??????"),
                                        fieldWithPath("productList[]").type(JsonFieldType.ARRAY).description("?????? ?????? ?????? ?????? ?????? ?????????"),
                                        fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ?????? ?????????")
                                ),
                                requestHeaders(
                                        headerWithName("sellerId").description("seller id")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("????????????"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("????????????")
                                )
                        )
                );
    }

    @Test
    @DisplayName("seller id??? ?????? ?????? ?????? ??????")
    public void findBySellerId() throws Exception {


        this.mockMvc.perform(
                        get("/rateCoupon/seller")
                                .param("page","1")
                                .header("sellerId",sellerId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-coupon-rest-controller/seller",
                                requestParameters(
                                        parameterWithName("page").description("????????? ??????")
                                ),
                                requestHeaders(
                                        headerWithName("sellerId").description("seller id")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("????????????"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("????????????"),
                                        fieldWithPath("result.data.totalCount").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????").optional(),
                                        fieldWithPath("result.data.rateCouponList[]").type(JsonFieldType.ARRAY).description("?????? ?????? ?????? ?????????").optional(),
                                        fieldWithPath("result.data.rateCouponList[].id").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? id").optional(),
                                        fieldWithPath("result.data.rateCouponList[].name").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????").optional(),
                                        fieldWithPath("result.data.rateCouponList[].regdate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
                                        fieldWithPath("result.data.rateCouponList[].rate").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ??????").optional(),
                                        fieldWithPath("result.data.rateCouponList[].minPrice").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ??????").optional(),
                                        fieldWithPath("result.data.rateCouponList[].startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ????????????").optional(),
                                        fieldWithPath("result.data.rateCouponList[].endDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ?????? ??????").optional(),
                                        fieldWithPath("result.data.rateCouponList[].productId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ?????? id").optional(),
                                        fieldWithPath("result.data.rateCouponList[].sellerId").type(JsonFieldType.NUMBER).description("seller id").optional()

                                )
                        )
                );
    }

    @Test
    @DisplayName("?????? id??? ???????????? ?????? ?????? ?????? ??????")
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
                                        parameterWithName("productId").description("?????? ID")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("????????????"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("????????????"),
                                        fieldWithPath("result.data[]").type(JsonFieldType.ARRAY).description("?????? ?????? ?????? ?????????").optional(),
                                        fieldWithPath("result.data[].name").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????").optional(),
                                        fieldWithPath("result.data[].regdate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
                                        fieldWithPath("result.data[].rate").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ??????").optional(),
                                        fieldWithPath("result.data[].minPrice").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ??????").optional(),
                                        fieldWithPath("result.data[].startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ????????????").optional(),
                                        fieldWithPath("result.data[].endDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ?????? ??????").optional(),
                                        fieldWithPath("result.data[].productId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ?????? id").optional(),
                                        fieldWithPath("result.data[].sellerId").type(JsonFieldType.NUMBER).description("seller id").optional()

                                )
                        )
                );
    }

    @Test
    @DisplayName("?????? ???????????? ?????? ?????? ?????? ??????")
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
                                        parameterWithName("name").description("?????? ??????")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("????????????"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("????????????"),
                                        fieldWithPath("result.data[]").type(JsonFieldType.ARRAY).description("?????? ?????????").optional(),
                                        fieldWithPath("result.data[].id").type(JsonFieldType.NUMBER).description("?????? id").optional(),
                                        fieldWithPath("result.data[].categoryId").type(JsonFieldType.NUMBER).description("?????? ???????????? id").optional(),
                                        fieldWithPath("result.data[].name").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                        fieldWithPath("result.data[].price").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                        fieldWithPath("result.data[].displayName").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                        fieldWithPath("result.data[].stock").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                        fieldWithPath("result.data[].deadline").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                        fieldWithPath("result.data[].thumbnail").type(JsonFieldType.STRING).description("?????? ???????????????").optional(),
                                        fieldWithPath("result.data[].sellerId").type(JsonFieldType.NUMBER).description("seller id").optional(),
                                        fieldWithPath("result.data[].deliveryFee").type(JsonFieldType.NUMBER).description("?????? ?????????").optional()

                                )
                        )
                );
    }

//    @Test
//    @DisplayName("?????? id ???????????? ?????? ?????? ?????? ??????(productId - RateCoupon)")
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
//                                        parameterWithName("couponIdList").description("?????? ????????? ?????????")
//                                ),
//                                responseFields(
//
//
//                                )
//                        )
//                );
//    }

}
