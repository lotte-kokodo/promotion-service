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
 * description : 고정 할인 정책 rest docs 생성을 위한 Test Controller
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-08           SSOsh              최초 생성
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
                .name("고정할인정책1")
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
                .name("고정할인정책2")
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
                .name("고정할인정책3")
                .minPrice(5000)
                .price(3000)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .regDate(LocalDateTime.now())
                .productId(3L)
                .sellerId(1L)
                .build();

    }

    @Test
    @DisplayName("고정 할인 정책 저장")
    public void save() throws Exception {

        this.mockMvc.perform(
                        post("/fix-discount/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(fixDiscountPolicy1))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("fix-discount-policy-rest-controller/save",
                                responseFields(
                                        List.of(
                                                fieldWithPath("createdDate").type(JsonFieldType.STRING).description("비율 할인 정책 생성 날짜").optional(),
                                                fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인 정책 수정 날짜").optional(),
                                                fieldWithPath("fixDiscountPolicyId").type(JsonFieldType.NUMBER).description("비율 할인 정책 아이디").optional(),
                                                fieldWithPath("name").type(JsonFieldType.STRING).description("비율 할인 정책 이름").optional(),
                                                fieldWithPath("regDate").type(JsonFieldType.STRING).description("비율 할인 정책 등록일자").optional(),
                                                fieldWithPath("startDate").type(JsonFieldType.STRING).description("비율 할인 정책 시작일자").optional(),
                                                fieldWithPath("endDate").type(JsonFieldType.STRING).description("비율 할인 정책 종료일자").optional(),
                                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("비율 할인 정책 비율").optional(),
                                                fieldWithPath("minPrice").type(JsonFieldType.NUMBER).description("비율 할인 정책 최소 금액").optional(),
                                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("비율 할인 정책 상품ID").optional(),
                                                fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("비율 할인 정책 셀러ID").optional()
                                        )
                                ))
                );
    }

    @Test
    @DisplayName("고정 할인 정책 전체 조회 API 테스트")
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
                                                fieldWithPath("[].createdDate").type(JsonFieldType.STRING).description("비율 할인 정책 생성 날짜").optional(),
                                                fieldWithPath("[].lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인 정책 수정 날짜").optional(),
                                                fieldWithPath("[].fixDiscountPolicyId").type(JsonFieldType.NUMBER).description("비율 할인 정책 아이디").optional(),
                                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("비율 할인 정책 이름").optional(),
                                                fieldWithPath("[].regDate").type(JsonFieldType.STRING).description("비율 할인 정책 등록일자").optional(),
                                                fieldWithPath("[].startDate").type(JsonFieldType.STRING).description("비율 할인 정책 시작일자").optional(),
                                                fieldWithPath("[].endDate").type(JsonFieldType.STRING).description("비율 할인 정책 종료일자").optional(),
                                                fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("비율 할인 정책 비율").optional(),
                                                fieldWithPath("[].minPrice").type(JsonFieldType.NUMBER).description("비율 할인 정책 최소 금액").optional(),
                                                fieldWithPath("[].productId").type(JsonFieldType.NUMBER).description("비율 할인 정책 상품ID").optional(),
                                                fieldWithPath("[].sellerId").type(JsonFieldType.NUMBER).description("비율 할인 정책 셀러ID").optional()
                                        )
                                ))
                );
    }

    @Test
    @DisplayName("고정 할인 정책 상품ID로 조회 API 테스트")
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
                                                fieldWithPath("createdDate").type(JsonFieldType.STRING).description("비율 할인 정책 생성 날짜").optional(),
                                                fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인 정책 수정 날짜").optional(),
                                                fieldWithPath("fixDiscountPolicyId").type(JsonFieldType.NUMBER).description("비율 할인 정책 아이디").optional(),
                                                fieldWithPath("name").type(JsonFieldType.STRING).description("비율 할인 정책 이름").optional(),
                                                fieldWithPath("regDate").type(JsonFieldType.STRING).description("비율 할인 정책 등록일자").optional(),
                                                fieldWithPath("startDate").type(JsonFieldType.STRING).description("비율 할인 정책 시작일자").optional(),
                                                fieldWithPath("endDate").type(JsonFieldType.STRING).description("비율 할인 정책 종료일자").optional(),
                                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("비율 할인 정책 비율").optional(),
                                                fieldWithPath("minPrice").type(JsonFieldType.NUMBER).description("비율 할인 정책 최소 금액").optional(),
                                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("비율 할인 정책 상품ID").optional(),
                                                fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("비율 할인 정책 셀러ID").optional()
                                        )
                                ))
                );
    }

    @Test
    @DisplayName("productIdList로 고정 할인 리스트 조회 API 테스트")
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
                                        fieldWithPath("*").type(JsonFieldType.OBJECT).description("고정 할인 정책").optional(),
                                        fieldWithPath("*.fixDiscountPolicyId").type(JsonFieldType.NUMBER).description("고정 할인 정책 아이디").optional(),
                                        fieldWithPath("*.name").type(JsonFieldType.STRING).description("고정 할인 정책 이름").optional(),
                                        fieldWithPath("*.regDate").type(JsonFieldType.STRING).description("고정 할인 정책 등록일자").optional(),
                                        fieldWithPath("*.startDate").type(JsonFieldType.STRING).description("고정 할인 정책 시작일자").optional(),
                                        fieldWithPath("*.endDate").type(JsonFieldType.STRING).description("고정 할인 정책 종료일자").optional(),
                                        fieldWithPath("*.price").type(JsonFieldType.NUMBER).description("고정 할인 정책 금액").optional(),
                                        fieldWithPath("*.minPrice").type(JsonFieldType.NUMBER).description("고정 할인 정책 최소 금액").optional(),
                                        fieldWithPath("*.productId").type(JsonFieldType.NUMBER).description("고정 할인 정책 상품ID").optional(),
                                        fieldWithPath("*.sellerId").type(JsonFieldType.NUMBER).description("고정 할인 정책 셀러ID").optional()
                                ))
                );
    }

    @Test
    @DisplayName("productIdList와 sellerIdList로 고정 할인 여부 조회 API 테스트")
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
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data.*").type(JsonFieldType.BOOLEAN).description("상품 아이디 리스트")
                                ))
                );
    }

    @Test
    @DisplayName("productIdList와 sellerIdList로 고정 할인 여부 페인요청 조회 API 테스트")
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
                                        fieldWithPath("*").type(JsonFieldType.BOOLEAN).description("상품 아이디 리스트, 고정 할인 가능여부")
//                                        fieldWithPath("*.*").type(JsonFieldType.BOOLEAN).description("고정 할인 가능여부")
                                ))
                );
    }


    @Test
    @DisplayName("sellerID로 고정 할인 정책 조회 API 테스트")
    public void getFixDiscountPolicyBySellerId() throws Exception {
        fixDiscountPolicyRepository.save(fixDiscountPolicy1);
        fixDiscountPolicyRepository.save(fixDiscountPolicy2);
        fixDiscountPolicyRepository.save(fixDiscountPolicy3);

        Long sellerId = 1L;
        this.mockMvc.perform(
                        get("/fix-discount/seller/{sellerId}", sellerId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("fix-discount-policy-rest-controller/get-fix-discount-policy-by-seller-id",
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data.[].fixDiscountPolicyId").type(JsonFieldType.NUMBER).description("비율 할인 정책 아이디"),
                                        fieldWithPath("result.data.[].name").type(JsonFieldType.STRING).description("비율 할인 정책 이름"),
                                        fieldWithPath("result.data.[].minPrice").type(JsonFieldType.NUMBER).description("비율 할인 정책 최소 금액"),
                                        fieldWithPath("result.data.[].price").type(JsonFieldType.NUMBER).description("비율 할인 정책 비율"),
                                        fieldWithPath("result.data.[].startDate").type(JsonFieldType.STRING).description("비율 할인 정책 시작 날짜"),
                                        fieldWithPath("result.data.[].endDate").type(JsonFieldType.STRING).description("비율 할인 정책 종료 날짜"),
                                        fieldWithPath("result.data.[].regDate").type(JsonFieldType.STRING).description("비율 할인 정책 등록 날짜"),
                                        fieldWithPath("result.data.[].productId").type(JsonFieldType.NUMBER).description("비율 할인 정책 상품 아이디"),
                                        fieldWithPath("result.data.[].sellerId").type(JsonFieldType.NUMBER).description("비율 할인 정책 셀러 아이디"),
                                        fieldWithPath("result.data.[].createdDate").type(JsonFieldType.STRING).description("비율 할인 정책 생성 날짜"),
                                        fieldWithPath("result.data.[].lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인 정책 최근 수정 날짜")
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
