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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;
import shop.kokodo.promotionservice.dto.FixDiscountPolicyDto;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.entity.FixDiscountPolicy;
import shop.kokodo.promotionservice.repository.FixDiscountPolicyRepository;
import shop.kokodo.promotionservice.service.FixDiscountPolicyService;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName : shop.kokodo.promotionservice.controller
 * fileName : FixDiscountPolicyRestControllerTest
 * author : SSOsh
 * date : 2022-11-08
 * description : ?????? ?????? ?????? rest docs ????????? ?????? Test Controller
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-08           SSOsh              ?????? ??????
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class FixDiscountPolicyRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    FixDiscountPolicy fixDiscountPolicy1;
    FixDiscountPolicy fixDiscountPolicy2;
    FixDiscountPolicy fixDiscountPolicy3;

    @Autowired
    FixDiscountPolicyRepository fixDiscountPolicyRepository;

    final Long productId = 1L;
    final Long sellerId = 1L;

    protected RestDocumentationResultHandler document;

    @BeforeEach
    public void setUp() {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint()));

        fixDiscountPolicy1 = FixDiscountPolicy.builder()
                .fixDiscountPolicyId(1L)
                .name("??????????????????1")
                .minPrice(1000)
                .price(1000)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .regDate(LocalDateTime.now())
                .productId(1L)
                .sellerId(1L)
                .build();

        fixDiscountPolicy2 = FixDiscountPolicy.builder()
                .fixDiscountPolicyId(2L)
                .name("??????????????????2")
                .minPrice(2000)
                .price(15000)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .regDate(LocalDateTime.now())
                .productId(2L)
                .sellerId(1L)
                .build();

        fixDiscountPolicy3 = FixDiscountPolicy.builder()
                .fixDiscountPolicyId(3L)
                .name("??????????????????3")
                .minPrice(5000)
                .price(3000)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .regDate(LocalDateTime.now())
                .productId(3L)
                .sellerId(1L)
                .build();

    }

//    @Test
//    @DisplayName("?????? ?????? ?????? ??????")
//    public void save() throws Exception {
//
//        this.mockMvc.perform(
//                        post("/fix-discount/save")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(fixDiscountPolicy1))
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(
//                        document("fix-discount-policy-rest-controller/save",
//                                responseFields(
//                                        List.of(
//                                                fieldWithPath("createdDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ??????").optional(),
//                                                fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ??????").optional(),
//                                                fieldWithPath("fixDiscountPolicyId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????").optional(),
//                                                fieldWithPath("name").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????").optional(),
//                                                fieldWithPath("regDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
//                                                fieldWithPath("startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
//                                                fieldWithPath("endDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
//                                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????").optional(),
//                                                fieldWithPath("minPrice").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ??????").optional(),
//                                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????ID").optional(),
//                                                fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????ID").optional()
//                                        )
//                                ))
//                );
//    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ?????? API ?????????")
    public void getFixDiscountList() throws Exception {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
        fixDiscountPolicyRepository.save(fixDiscountPolicy2);
        fixDiscountPolicyRepository.save(fixDiscountPolicy3);

        this.mockMvc.perform(
                        get("/fix-discount/")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("fix-discount-policy-rest-controller/find-all",
                                responseFields(
                                        List.of(
                                                fieldWithPath("[].createdDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ??????").optional(),
                                                fieldWithPath("[].lastModifiedDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ??????").optional(),
                                                fieldWithPath("[].fixDiscountPolicyId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????").optional(),
                                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????").optional(),
                                                fieldWithPath("[].regDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
                                                fieldWithPath("[].startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
                                                fieldWithPath("[].endDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
                                                fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????").optional(),
                                                fieldWithPath("[].minPrice").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ??????").optional(),
                                                fieldWithPath("[].productId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????ID").optional(),
                                                fieldWithPath("[].sellerId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????ID").optional()
                                        )
                                ))
                );
    }

    @Test
    @DisplayName("?????? ?????? ?????? ??????ID??? ?????? API ?????????")
    public void getFixDiscountPolicy() throws Exception {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
        fixDiscountPolicyRepository.save(fixDiscountPolicy2);
        fixDiscountPolicyRepository.save(fixDiscountPolicy3);

        this.mockMvc.perform(
                        get("/fix-discount/{productId}", fixDiscountPolicy1.getProductId())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("fix-discount-policy-rest-controller/find-by-productId",
                                responseFields(
                                        List.of(
                                                fieldWithPath("createdDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ??????").optional(),
                                                fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ??????").optional(),
                                                fieldWithPath("fixDiscountPolicyId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????").optional(),
                                                fieldWithPath("name").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????").optional(),
                                                fieldWithPath("regDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
                                                fieldWithPath("startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
                                                fieldWithPath("endDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
                                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????").optional(),
                                                fieldWithPath("minPrice").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ??????").optional(),
                                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????ID").optional(),
                                                fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????ID").optional()
                                        )
                                ))
                );
    }

    @Test
    @DisplayName("productIdList??? ?????? ?????? ????????? ?????? API ?????????")
    public void getFixDiscountPolicyIdList() throws Exception {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
        fixDiscountPolicyRepository.save(fixDiscountPolicy2);
        fixDiscountPolicyRepository.save(fixDiscountPolicy3);
        List<Long> productIdList = new ArrayList<>();
        productIdList.add(1L);
        productIdList.add(2L);
        productIdList.add(3L);

        this.mockMvc.perform(
                        get("/fix-discount/list")
                                .param("productIdList", ListToString(productIdList))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("fix-discount-policy-rest-controller/get-fix-discount-policy-list",
                                responseFields(
                                        fieldWithPath("*").type(JsonFieldType.OBJECT).description("?????? ?????? ??????").optional(),
                                        fieldWithPath("*.fixDiscountPolicyId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????").optional(),
                                        fieldWithPath("*.name").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????").optional(),
                                        fieldWithPath("*.regDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
                                        fieldWithPath("*.startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
                                        fieldWithPath("*.endDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ????????????").optional(),
                                        fieldWithPath("*.price").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????").optional(),
                                        fieldWithPath("*.minPrice").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ??????").optional(),
                                        fieldWithPath("*.productId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????ID").optional(),
                                        fieldWithPath("*.sellerId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????ID").optional()
                                ))
                );
    }

    @Test
    @DisplayName("productIdList??? sellerIdList??? ?????? ?????? ?????? ?????? API ?????????")
    public void getFixDiscountPolicyStatus() throws Exception {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
        fixDiscountPolicyRepository.save(fixDiscountPolicy2);
        fixDiscountPolicyRepository.save(fixDiscountPolicy3);
        List<Long> productIdList = new ArrayList<>();
        List<Long> sellerIdList = new ArrayList<>();

        productIdList.add(1L);
        productIdList.add(2L);
        productIdList.add(3L);

        sellerIdList.add(1L);
        sellerIdList.add(2L);
        sellerIdList.add(3L);

        this.mockMvc.perform(
                        get("/fix-discount/status")
                                .param("productIdList", ListToString(productIdList))
                                .param("sellerIdList", ListToString(sellerIdList))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("fix-discount-policy-rest-controller/status",
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("????????????"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("????????????"),
                                        fieldWithPath("result.data.*").type(JsonFieldType.BOOLEAN).description("?????? ????????? ?????????")
                                ))
                );
    }

    @Test
    @DisplayName("productIdList??? sellerIdList??? ?????? ?????? ?????? ???????????? ?????? API ?????????")
    public void getFixDiscountPolicyStatusForFeign() throws Exception {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
        fixDiscountPolicyRepository.save(fixDiscountPolicy2);
        fixDiscountPolicyRepository.save(fixDiscountPolicy3);
        List<Long> productIdList = new ArrayList<>();
        List<Long> sellerIdList = new ArrayList<>();

        productIdList.add(1L);
        productIdList.add(2L);
        productIdList.add(3L);

        sellerIdList.add(1L);
        sellerIdList.add(2L);
        sellerIdList.add(3L);

        this.mockMvc.perform(
                        get("/feign/fix-discount/status")
                                .param("productIdList", ListToString(productIdList))
                                .param("sellerIdList", ListToString(sellerIdList))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("fix-discount-policy-rest-controller/feign-status",
                                responseFields(
                                        fieldWithPath("*").type(JsonFieldType.BOOLEAN).description("?????? ????????? ?????????, ?????? ?????? ????????????")
//                                        fieldWithPath("*.*").type(JsonFieldType.BOOLEAN).description("?????? ?????? ????????????")
                                ))
                );
    }


    @Test
    @DisplayName("sellerID??? ?????? ?????? ?????? ?????? API ?????????")
    public void getFixDiscountPolicyBySellerId() throws Exception {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
        fixDiscountPolicyRepository.save(fixDiscountPolicy2);
        fixDiscountPolicyRepository.save(fixDiscountPolicy3);

        Long sellerId = 1L;
        int page = 1;
        this.mockMvc.perform(
                        get("/fix-discount/seller/")
                                .header("sellerId", sellerId)
                                .param("page", String.valueOf(page))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("fix-discount-policy-rest-controller/get-fix-discount-policy-by-seller-id",
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("????????????"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("????????????"),
                                        fieldWithPath("result.data.totalCount").type(JsonFieldType.NUMBER).description("??? ??????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList").type(JsonFieldType.ARRAY).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList.[].fixDiscountPolicyId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList.[].name").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList.[].minPrice").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ??????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList.[].price").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList.[].startDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ??????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList.[].endDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ??????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList.[].regDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ??????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList.[].productId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ?????????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList.[].sellerId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????? ?????????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList.[].createdDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ??????"),
                                        fieldWithPath("result.data.fixDiscountPolicyList.[].lastModifiedDate").type(JsonFieldType.STRING).description("?????? ?????? ?????? ?????? ?????? ??????")
                                ))
                );
    }

    public String ListToString(List<Long> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size() - 1; i++) {
            result.append(list.get(i)).append(",");
        }
        if (!list.isEmpty()) result.append(list.get(list.size() - 1));
        return result.toString();
    }
}
