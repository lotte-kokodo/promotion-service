package shop.kokodo.promotionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
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
import shop.kokodo.promotionservice.dto.RateDiscountPolicyDto;
import shop.kokodo.promotionservice.entity.RateDiscountPolicy;
import shop.kokodo.promotionservice.repository.RateDiscountPolicyRepository;

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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName : shop.kokodo.promotionservice.controller
 * fileName : RateDiscountPolicyRestControllerTest
 * author : SSOsh
 * date : 2022-11-08
 * description : 비율 할인 정책 rest docs 생성을 위한 Test Controller
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-08           SSOsh              최초 생성
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class RateDiscountPolicyRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    RateDiscountPolicy rateDiscountPolicy1;
    RateDiscountPolicy rateDiscountPolicy2;
    RateDiscountPolicy rateDiscountPolicy3;
    RateDiscountPolicyDto rateDiscountPolicyDto1;
    RateDiscountPolicyDto rateDiscountPolicyDto2;

    @Autowired
    RateDiscountPolicyRepository rateDiscountPolicyRepository;

    final Long productId = 1L;

    protected RestDocumentationResultHandler document;

    @BeforeEach
    public void setUp() {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint()));

        rateDiscountPolicy1 = RateDiscountPolicy.builder()
                .rateDiscountPolicyId(1L)
                .name("비용할인정책1")
                .minPrice(1000)
                .rate(5)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .regDate(LocalDateTime.now())
                .productId(1L)
                .sellerId(1L)
                .build();

        rateDiscountPolicy2 = RateDiscountPolicy.builder()
                .rateDiscountPolicyId(2L)
                .name("비용할인정책2")
                .minPrice(10000)
                .rate(8)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .regDate(LocalDateTime.now())
                .productId(1L)
                .sellerId(1L)
                .build();

        rateDiscountPolicy3 = RateDiscountPolicy.builder()
                .rateDiscountPolicyId(3L)
                .name("비용할인정책3")
                .minPrice(2000)
                .rate(3)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .regDate(LocalDateTime.now())
                .productId(2L)
                .sellerId(1L)
                .build();

        rateDiscountPolicyDto1 = RateDiscountPolicyDto.builder()
                .rateDiscountPolicyId(1L)
                .name("비용할인정책DTO1")
                .minPrice(1000)
                .rate(5)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .regDate(LocalDateTime.now())
                .productId(2L)
                .sellerId(1L)
                .build();

        rateDiscountPolicyDto2 = RateDiscountPolicyDto.builder()
                .rateDiscountPolicyId(2L)
                .name("비용할인정책DTO2")
                .minPrice(10000)
                .rate(8)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .regDate(LocalDateTime.now())
                .productId(1L)
                .sellerId(1L)
                .build();
    }

    @Test
    @DisplayName("비율 할인 정책 생성 API 테스트")
    public void save() throws Exception {
        this.mockMvc.perform(
                        post("/rate-discount/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(rateDiscountPolicyDto1))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-discount-policy-controller/save",
                                requestFields(
                                        fieldWithPath("rateDiscountPolicyId").type(JsonFieldType.NUMBER).description("비율 할인 정책 아이디"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("비율 할인 정책 이름"),
                                        fieldWithPath("minPrice").type(JsonFieldType.NUMBER).description("비율 할인 정책 최소 금액"),
                                        fieldWithPath("rate").type(JsonFieldType.NUMBER).description("비율 할인 정책 비율"),
                                        fieldWithPath("startDate").type(JsonFieldType.STRING).description("비율 할인 정책 시작일자"),
                                        fieldWithPath("endDate").type(JsonFieldType.STRING).description("비율 할인 정책 종료일자"),
                                        fieldWithPath("regDate").type(JsonFieldType.STRING).description("비율 할인 정책 등록일자"),
                                        fieldWithPath("productId").type(JsonFieldType.NUMBER).description("비율 할인 정책 상품ID"),
                                        fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("비율 할인 정책 셀러ID")
                                ),
                                responseFields(
                                        fieldWithPath("rateDiscountPolicyId").type(JsonFieldType.NUMBER).description("비율 할인 정책 아이디"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("비율 할인 정책 이름"),
                                        fieldWithPath("minPrice").type(JsonFieldType.NUMBER).description("비율 할인 정책 최소 금액"),
                                        fieldWithPath("rate").type(JsonFieldType.NUMBER).description("비율 할인 정책 비율"),
                                        fieldWithPath("startDate").type(JsonFieldType.STRING).description("비율 할인 정책 시작 날짜"),
                                        fieldWithPath("endDate").type(JsonFieldType.STRING).description("비율 할인 정책 종료 날짜"),
                                        fieldWithPath("regDate").type(JsonFieldType.STRING).description("비율 할인 정책 등록 날짜"),
                                        fieldWithPath("productId").type(JsonFieldType.NUMBER).description("비율 할인 정책 상품 아이디"),
                                        fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("비율 할인 정책 셀러 아이디"),
                                        fieldWithPath("createdDate").type(JsonFieldType.STRING).description("비율 할인 정책 생성 날짜"),
                                        fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인 정책 최근 수정 날짜")
                                ))
                );
    }

    @Test
    @DisplayName("비율 할인 정책 생성 API 테스트")
    public void getRateDiscountPolicyList() throws Exception {
        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
        rateDiscountPolicyRepository.save(rateDiscountPolicy2);
        rateDiscountPolicyRepository.save(rateDiscountPolicy3);

        this.mockMvc.perform(
                        get("/rate-discount/")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-discount-policy-controller/get-all",
                                responseFields(
                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("비율 할인 정책").optional(),
                                        fieldWithPath("[].createdDate").type(JsonFieldType.STRING).description("비율 할인 정책 생성 날짜").optional(),
                                        fieldWithPath("[].lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인 정책 수정 날짜").optional(),
                                        fieldWithPath("[].rateDiscountPolicyId").type(JsonFieldType.NUMBER).description("비율 할인 정책 아이디").optional(),
                                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("비율 할인 정책 이름").optional(),
                                        fieldWithPath("[].regDate").type(JsonFieldType.STRING).description("비율 할인 정책 등록일자").optional(),
                                        fieldWithPath("[].startDate").type(JsonFieldType.STRING).description("비율 할인 정책 시작일자").optional(),
                                        fieldWithPath("[].endDate").type(JsonFieldType.STRING).description("비율 할인 정책 종료일자").optional(),
                                        fieldWithPath("[].rate").type(JsonFieldType.NUMBER).description("비율 할인 정책 비율").optional(),
                                        fieldWithPath("[].minPrice").type(JsonFieldType.NUMBER).description("비율 할인 정책 최소 금액").optional(),
                                        fieldWithPath("[].productId").type(JsonFieldType.NUMBER).description("비율 할인 정책 상품ID").optional(),
                                        fieldWithPath("[].sellerId").type(JsonFieldType.NUMBER).description("비율 할인 정책 셀러ID").optional()
                                ))
                );
    }

    @Test
    @DisplayName("가장 비율 높은 비율 할인 정책 상품ID로 조회 API 테스트")
    public void getRateDiscountPolicy() throws Exception {
        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
        rateDiscountPolicyRepository.save(rateDiscountPolicy2);
        rateDiscountPolicyRepository.save(rateDiscountPolicy3);

        this.mockMvc.perform(
                        get("/rate-discount/{productId}", rateDiscountPolicy1.getProductId())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-discount-policy-controller/find-by-productId",
                                responseFields(
                                        List.of(
                                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부").optional(),
                                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드").optional(),
                                                fieldWithPath("result.data.createdDate").type(JsonFieldType.STRING).description("비율 할인 정책 생성 날짜").optional(),
                                                fieldWithPath("result.data.lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인 정책 수정 날짜").optional(),
                                                fieldWithPath("result.data.rateDiscountPolicyId").type(JsonFieldType.NUMBER).description("비율 할인 정책 아이디").optional(),
                                                fieldWithPath("result.data.name").type(JsonFieldType.STRING).description("비율 할인 정책 이름").optional(),
                                                fieldWithPath("result.data.regDate").type(JsonFieldType.STRING).description("비율 할인 정책 등록일자").optional(),
                                                fieldWithPath("result.data.startDate").type(JsonFieldType.STRING).description("비율 할인 정책 시작일자").optional(),
                                                fieldWithPath("result.data.endDate").type(JsonFieldType.STRING).description("비율 할인 정책 종료일자").optional(),
                                                fieldWithPath("result.data.rate").type(JsonFieldType.NUMBER).description("비율 할인 정책 비율").optional(),
                                                fieldWithPath("result.data.minPrice").type(JsonFieldType.NUMBER).description("비율 할인 정책 최소 금액").optional(),
                                                fieldWithPath("result.data.productId").type(JsonFieldType.NUMBER).description("비율 할인 정책 상품ID").optional(),
                                                fieldWithPath("result.data.sellerId").type(JsonFieldType.NUMBER).description("비율 할인 정책 셀러ID").optional()
                                        )
                                ))
                );
    }

    @Test
    @DisplayName("비율 할인 정책 상품ID 리스트 조회 API")
    public void getRateDiscountPolicyIdList() throws Exception {
        List<Long> productIdList = new ArrayList<>();
        productIdList.add(1L);
        productIdList.add(2L);
        productIdList.add(3L);
        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
        rateDiscountPolicyRepository.save(rateDiscountPolicy2);
        rateDiscountPolicyRepository.save(rateDiscountPolicy3);

        this.mockMvc.perform(
                        get("/rate-discount/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("productIdList" , ListToString(productIdList))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-discount-policy-controller/product-id-list",
                                requestParameters(
                                        parameterWithName("productIdList").description("상품 ID 리스트")
                                ),
                                responseFields(
                                        fieldWithPath("*").type(JsonFieldType.OBJECT).description("비율 할인 정책 아이디").optional(),
                                        fieldWithPath("*.rateDiscountPolicyId").type(JsonFieldType.NUMBER).description("비율 할인 정책 아이디").optional(),
                                        fieldWithPath("*.name").type(JsonFieldType.STRING).description("비율 할인 정책 이름").optional(),
                                        fieldWithPath("*.regDate").type(JsonFieldType.STRING).description("비율 할인 정책 등록일자").optional(),
                                        fieldWithPath("*.startDate").type(JsonFieldType.STRING).description("비율 할인 정책 시작일자").optional(),
                                        fieldWithPath("*.endDate").type(JsonFieldType.STRING).description("비율 할인 정책 종료일자").optional(),
                                        fieldWithPath("*.rate").type(JsonFieldType.NUMBER).description("비율 할인 정책 비율").optional(),
                                        fieldWithPath("*.minPrice").type(JsonFieldType.NUMBER).description("비율 할인 정책 최소 금액").optional(),
                                        fieldWithPath("*.productId").type(JsonFieldType.NUMBER).description("비율 할인 정책 상품ID").optional(),
                                        fieldWithPath("*.sellerId").type(JsonFieldType.NUMBER).description("비율 할인 정책 셀러ID").optional()
                                ))
                );
    }

    @Test
    @DisplayName("비율 할인 정책 생성 api 테스트 성공")
    public void getRateDiscountPolicyByDate() throws Exception {
        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
        rateDiscountPolicyRepository.save(rateDiscountPolicy2);
        rateDiscountPolicyRepository.save(rateDiscountPolicy3);
        this.mockMvc.perform(
                        get("/rate-discount/date")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-discount-policy-controller/date",
                                responseFields(
                                        fieldWithPath("[].createdDate").type(JsonFieldType.STRING).description("비율 할인 정책 생성 날짜").optional(),
                                        fieldWithPath("[].lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인 정책 수정 날짜").optional(),
                                        fieldWithPath("[].rateDiscountPolicyId").type(JsonFieldType.NUMBER).description("비율 할인 정책 아이디").optional(),
                                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("비율 할인 정책 이름").optional(),
                                        fieldWithPath("[].regDate").type(JsonFieldType.STRING).description("비율 할인 정책 등록일자").optional(),
                                        fieldWithPath("[].startDate").type(JsonFieldType.STRING).description("비율 할인 정책 시작일자").optional(),
                                        fieldWithPath("[].endDate").type(JsonFieldType.STRING).description("비율 할인 정책 종료일자").optional(),
                                        fieldWithPath("[].rate").type(JsonFieldType.NUMBER).description("비율 할인 정책 비율").optional(),
                                        fieldWithPath("[].minPrice").type(JsonFieldType.NUMBER).description("비율 할인 정책 최소 금액").optional(),
                                        fieldWithPath("[].productId").type(JsonFieldType.NUMBER).description("비율 할인 정책 상품ID").optional(),
                                        fieldWithPath("[].sellerId").type(JsonFieldType.NUMBER).description("비율 할인 정책 셀러ID").optional()
                                ))
                );
    }

    @Test
    @DisplayName("비율 할인 정책 생성 api 테스트 성공")
    public void getRateDiscountPolicyBySellerId() throws Exception {
        Long sellerId = 1L;
        rateDiscountPolicyRepository.save(rateDiscountPolicy1);
        rateDiscountPolicyRepository.save(rateDiscountPolicy2);
        rateDiscountPolicyRepository.save(rateDiscountPolicy3);
        this.mockMvc.perform(
                        get("/rate-discount/{sellerId}", sellerId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("rate-discount-policy-controller/seller-id",
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data.createdDate").type(JsonFieldType.STRING).description("비율 할인 정책 생성 날짜").optional(),
                                        fieldWithPath("result.data.lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인 정책 수정 날짜").optional(),
                                        fieldWithPath("result.data.rateDiscountPolicyId").type(JsonFieldType.NUMBER).description("비율 할인 정책 아이디").optional(),
                                        fieldWithPath("result.data.name").type(JsonFieldType.STRING).description("비율 할인 정책 이름").optional(),
                                        fieldWithPath("result.data.regDate").type(JsonFieldType.STRING).description("비율 할인 정책 등록일자").optional(),
                                        fieldWithPath("result.data.startDate").type(JsonFieldType.STRING).description("비율 할인 정책 시작일자").optional(),
                                        fieldWithPath("result.data.endDate").type(JsonFieldType.STRING).description("비율 할인 정책 종료일자").optional(),
                                        fieldWithPath("result.data.rate").type(JsonFieldType.NUMBER).description("비율 할인 정책 비율").optional(),
                                        fieldWithPath("result.data.minPrice").type(JsonFieldType.NUMBER).description("비율 할인 정책 최소 금액").optional(),
                                        fieldWithPath("result.data.productId").type(JsonFieldType.NUMBER).description("비율 할인 정책 상품ID").optional(),
                                        fieldWithPath("result.data.sellerId").type(JsonFieldType.NUMBER).description("비율 할인 정책 셀러ID").optional()
                                ))
                );
    }
    public String ListToString(List<Long> list) {
        StringBuilder result  = new StringBuilder();
        for(int i=0;i<list.size() - 1;i++) {
            result.append(list.get(i)).append(",");
        }
        if(!list.isEmpty()) result.append(list.get(list.size() - 1));
        return result.toString();
    }
}