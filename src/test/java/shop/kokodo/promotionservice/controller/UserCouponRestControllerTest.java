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
import shop.kokodo.promotionservice.dto.UpdateUserCouponDto;
import shop.kokodo.promotionservice.dto.UserCouponDto;
import shop.kokodo.promotionservice.entity.FixCoupon;
import shop.kokodo.promotionservice.entity.RateCoupon;
import shop.kokodo.promotionservice.entity.UsageStatus;
import shop.kokodo.promotionservice.entity.UserCoupon;
import shop.kokodo.promotionservice.repository.FixCouponRepository;
import shop.kokodo.promotionservice.repository.RateCouponRepository;
import shop.kokodo.promotionservice.repository.UserCouponRepository;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
    @Autowired
    UserCouponRepository userCouponRepository;

    UserCouponDto userRateCouponDto;
    UserCouponDto userFixCouponDto;

    FixCoupon fixCoupon;
    RateCoupon rateCoupon;

    List<Long> rateIdList;

    final long memberId=1L;
    final long productId =1L;

    @BeforeEach
    public void setUp(){

        rateIdList = new ArrayList<>();
        fixCoupon= FixCoupon.builder()
                .name("fixCouponName")
                .regdate(LocalDateTime.now())
                .price(1000)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,25,0,0))
                .endDate(LocalDateTime.of(2023,9,25,0,0))
                .productId(productId)
                .sellerId(2L)
                .freeDelivery(true)
                .build();

        rateCoupon=RateCoupon.builder()
                .name("rateCouponName")
                .regdate(LocalDateTime.now())
                .rate(1)
                .minPrice(10000)
                .startDate(LocalDateTime.of(2022,9,25,0,0))
                .endDate(LocalDateTime.of(2023,9,25,0,0))
                .productId(productId)
                .sellerId(2L)
                .build();

        fixCoupon=fixCouponRepository.save(fixCoupon);
        rateCoupon=rateCouponRepository.save(rateCoupon);

        rateIdList.add(rateCoupon.getId());

        userRateCouponDto = UserCouponDto.builder()
                .userId(memberId)
                .usageStatus(UsageStatus.NOT_USED.toString())
                .rateCouponId(rateCoupon.getId())
                .build();

        userFixCouponDto = UserCouponDto.builder()
                .userId(memberId)
                .usageStatus(UsageStatus.NOT_USED.toString())
                .fixCouponId(fixCoupon.getId())
                .build();

        UserCoupon userRateCoupon = UserCoupon.builder()
                .userId(memberId)
                .rateCoupon(rateCoupon)
                .usageStatus(UsageStatus.NOT_USED)
                .build();

        UserCoupon userFixCoupon = UserCoupon.builder()
                .userId(memberId)
                .fixCoupon(fixCoupon)
                .usageStatus(UsageStatus.NOT_USED)
                .build();

        userCouponRepository.save(userRateCoupon);
        userCouponRepository.save(userFixCoupon);

    }

    @Test
    @DisplayName("사용자 고정 할인 쿠폰 생성(다운로드) ")
    public void saveFixCoupon() throws Exception {

        this.mockMvc.perform(
                        post("/userCoupon")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userFixCouponDto))
                                .header("memberId", 100L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("user-coupon-rest-controller/saveFixCoupon",
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 아이디"),
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 ID"),
                                        fieldWithPath("usageStatus").type(JsonFieldType.STRING).description("쿠폰 사용 여부"),
                                        fieldWithPath("rateCouponId").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 ID").optional(),
                                        fieldWithPath("fixCouponId").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 ID").optional()
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data.id").type(JsonFieldType.NUMBER).description("저장된 쿠폰 아이디"),
                                        fieldWithPath("result.data.userId").type(JsonFieldType.NUMBER).description("저장된 쿠폰 유저 아이디"),
                                        fieldWithPath("result.data.usageStatus").type(JsonFieldType.STRING).description("저장된 쿠폰 사용 여부"),
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
    @DisplayName("사용하지 않은 product id의 고정 할인 쿠폰 조회")
    public void findUserNotUsedFixCouponByproductId() throws Exception {

        this.mockMvc.perform(
                        get("/userCoupon/{productId}/fixCoupon",productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("memberId", memberId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("user-coupon-rest-controller/find-user-notUsed-fixCoupon-by-productId",
                                requestHeaders(
                                        headerWithName("memberId").description("사용자 id")
                                ),
                                pathParameters(
                                        parameterWithName("productId").description("상품 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data[].id").type(JsonFieldType.NUMBER).description("고정 할인쿠폰 id"),
                                        fieldWithPath("result.data[].name").type(JsonFieldType.STRING).description("고정 할인쿠폰 이름"),
                                        fieldWithPath("result.data[].createdDate").type(JsonFieldType.STRING).description("고정 할인쿠폰 생성 날짜"),
                                        fieldWithPath("result.data[].lastModifiedDate").type(JsonFieldType.STRING).description("고정 할인쿠폰 마지막 수정 날짜"),
                                        fieldWithPath("result.data[].regdate").type(JsonFieldType.STRING).description("고정 할인쿠폰 등록날짜"),
                                        fieldWithPath("result.data[].price").type(JsonFieldType.NUMBER).description("고정 할인쿠폰 가격"),
                                        fieldWithPath("result.data[].minPrice").type(JsonFieldType.NUMBER).description("고정할인쿠폰 적용 최소비용"),
                                        fieldWithPath("result.data[].startDate").type(JsonFieldType.STRING).description("고정 할인쿠폰 적용 시작날짜"),
                                        fieldWithPath("result.data[].endDate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 적용 마감날짜"),
                                        fieldWithPath("result.data[].productId").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 상품 id"),
                                        fieldWithPath("result.data[].sellerId").type(JsonFieldType.NUMBER).description("셀러 id"),
                                        fieldWithPath("result.data[].freeDelivery").type(JsonFieldType.BOOLEAN).description("무료배송 여부"),
                                        fieldWithPath("result.data[].couponFlag").type(JsonFieldType.NUMBER).description("쿠폰 구분")
                                )
                        )
                );
    }

    @Test
    @DisplayName("사용하지 않은 product id의 비율 할인 쿠폰 조회")
    public void findUserNotUsedRateCouponByproductId() throws Exception {

        this.mockMvc.perform(
                        get("/userCoupon/{productId}/rateCoupon",productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("memberId", memberId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("user-coupon-rest-controller/find-user-notUsed-rateCoupon-by-productId",
                                requestHeaders(
                                        headerWithName("memberId").description("사용자 id")
                                ),
                                pathParameters(
                                        parameterWithName("productId").description("상품 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data[].id").type(JsonFieldType.NUMBER).description("비율 할인쿠폰 id"),
                                        fieldWithPath("result.data[].name").type(JsonFieldType.STRING).description("비율 할인쿠폰 이름"),
                                        fieldWithPath("result.data[].createdDate").type(JsonFieldType.STRING).description("비율 할인쿠폰 생성 날짜"),
                                        fieldWithPath("result.data[].lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인쿠폰 마지막 수정 날짜"),
                                        fieldWithPath("result.data[].regdate").type(JsonFieldType.STRING).description("비율 할인쿠폰 등록날짜"),
                                        fieldWithPath("result.data[].rate").type(JsonFieldType.NUMBER).description("비율 할인쿠폰 가격"),
                                        fieldWithPath("result.data[].minPrice").type(JsonFieldType.NUMBER).description("비율 할인쿠폰 적용 최소비용"),
                                        fieldWithPath("result.data[].startDate").type(JsonFieldType.STRING).description("비율 할인쿠폰 적용 시작날짜"),
                                        fieldWithPath("result.data[].endDate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 적용 마감날짜"),
                                        fieldWithPath("result.data[].productId").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 상품 id"),
                                        fieldWithPath("result.data[].sellerId").type(JsonFieldType.NUMBER).description("셀러 id"),
                                        fieldWithPath("result.data[].couponFlag").type(JsonFieldType.NUMBER).description("쿠폰 구분")
                                )
                        )
                );
    }

    @Test
    @DisplayName("유저 id로 조회 ")
    public void findByMemberId() throws Exception {

        this.mockMvc.perform(
                        get("/userCoupon")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("memberId", memberId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("user-coupon-rest-controller/find-by-memberId",
                                requestHeaders(
                                        headerWithName("memberId").description("사용자 id")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data[].id").type(JsonFieldType.NUMBER).description("유저 쿠폰 id"),
                                        fieldWithPath("result.data[].createdDate").type(JsonFieldType.STRING).description("유저 쿠폰 생성 날짜"),
                                        fieldWithPath("result.data[].lastModifiedDate").type(JsonFieldType.STRING).description("유저 쿠폰 마지막 수정 날짜"),
                                        fieldWithPath("result.data[].userId").type(JsonFieldType.NUMBER).description("유저 id"),
                                        fieldWithPath("result.data[].usageStatus").type(JsonFieldType.STRING).description("쿠폰 사용 여부"),
                                        fieldWithPath("result.data[].rateCoupon").type(JsonFieldType.OBJECT).description("비율 할인쿠폰 데이터").optional(),
                                        fieldWithPath("result.data[].rateCoupon.id").type(JsonFieldType.NUMBER).description("비율 할인쿠폰 id"),
                                        fieldWithPath("result.data[].rateCoupon.name").type(JsonFieldType.STRING).description("비율 할인쿠폰 이름"),
                                        fieldWithPath("result.data[].rateCoupon.createdDate").type(JsonFieldType.STRING).description("비율 할인쿠폰 생성일"),
                                        fieldWithPath("result.data[].rateCoupon.lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인쿠폰 최종 수정일"),
                                        fieldWithPath("result.data[].rateCoupon.regdate").type(JsonFieldType.STRING).description("비율 할인쿠폰 등록일"),
                                        fieldWithPath("result.data[].rateCoupon.rate").type(JsonFieldType.NUMBER).description("비율 할인쿠폰 할인 비율"),
                                        fieldWithPath("result.data[].rateCoupon.minPrice").type(JsonFieldType.NUMBER).description("비율 할인쿠폰 적용 최소 가격"),
                                        fieldWithPath("result.data[].rateCoupon.startDate").type(JsonFieldType.STRING).description("비율 할인쿠폰 적용 시작일"),
                                        fieldWithPath("result.data[].rateCoupon.endDate").type(JsonFieldType.STRING).description("비율 할인쿠폰 적용 마감일"),
                                        fieldWithPath("result.data[].rateCoupon.productId").type(JsonFieldType.NUMBER).description("비율 할인쿠폰 상품 id"),
                                        fieldWithPath("result.data[].rateCoupon.sellerId").type(JsonFieldType.NUMBER).description("셀러 id"),
                                        fieldWithPath("result.data[].rateCoupon.couponFlag").type(JsonFieldType.NUMBER).description("쿠폰 구분"),
                                        fieldWithPath("result.data[].fixCoupon").type(JsonFieldType.OBJECT).description("고정 할인쿠폰 데이터").optional(),
                                        fieldWithPath("result.data[].fixCoupon.id").type(JsonFieldType.NUMBER).description("고정 할인쿠폰 아이디"),
                                        fieldWithPath("result.data[].fixCoupon.name").type(JsonFieldType.STRING).description("고정 할인쿠폰 이름"),
                                        fieldWithPath("result.data[].fixCoupon.createdDate").type(JsonFieldType.STRING).description("고정 할인쿠폰 생성날짜"),
                                        fieldWithPath("result.data[].fixCoupon.lastModifiedDate").type(JsonFieldType.STRING).description("고정 할인쿠폰 마지막 수정 날짜"),
                                        fieldWithPath("result.data[].fixCoupon.regdate").type(JsonFieldType.STRING).description("고정 할인쿠폰 등록 날짜"),
                                        fieldWithPath("result.data[].fixCoupon.price").type(JsonFieldType.NUMBER).description("고정 할인쿠폰 가격"),
                                        fieldWithPath("result.data[].fixCoupon.minPrice").type(JsonFieldType.NUMBER).description("고정 할인쿠폰 적용 최저 가격"),
                                        fieldWithPath("result.data[].fixCoupon.startDate").type(JsonFieldType.STRING).description("고정 할인쿠폰 시작일"),
                                        fieldWithPath("result.data[].fixCoupon.endDate").type(JsonFieldType.STRING).description("고정 할인쿠폰 마감일"),
                                        fieldWithPath("result.data[].fixCoupon.productId").type(JsonFieldType.NUMBER).description("고정 할인쿠폰 상품 id"),
                                        fieldWithPath("result.data[].fixCoupon.sellerId").type(JsonFieldType.NUMBER).description("셀러 id"),
                                        fieldWithPath("result.data[].fixCoupon.freeDelivery").type(JsonFieldType.BOOLEAN).description("고정 할인쿠폰 무료배송여부"),
                                        fieldWithPath("result.data[].fixCoupon.couponFlag").type(JsonFieldType.NUMBER).description("쿠폰 구분")
                                )
                        )
                );
    }

    @Test
    @DisplayName("쿠폰 사용")
    public void updateUsageStatus() throws Exception {

        UpdateUserCouponDto updateUserCouponDto = UpdateUserCouponDto.builder()
                .couponFlag(1)
                .fixCouponId(fixCoupon.getId())
                .build();

        this.mockMvc.perform(
                        put("/userCoupon/usageStatus")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateUserCouponDto))
                                .header("memberId", memberId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("user-coupon-rest-controller/update-usageStatus",
                                requestHeaders(
                                        headerWithName("memberId").description("사용자 id")
                                ),
                                requestFields(
                                        fieldWithPath("couponFlag").type(JsonFieldType.NUMBER).description("쿠폰 구분 flag"),
                                        fieldWithPath("rateCouponId").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 ID").optional(),
                                        fieldWithPath("fixCouponId").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 ID").optional()
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data.id").type(JsonFieldType.NUMBER).description("유저 쿠폰 id"),
                                        fieldWithPath("result.data.createdDate").type(JsonFieldType.STRING).description("유저 쿠폰 생성 날짜"),
                                        fieldWithPath("result.data.lastModifiedDate").type(JsonFieldType.STRING).description("유저 쿠폰 마지막 수정 날짜"),
                                        fieldWithPath("result.data.userId").type(JsonFieldType.NUMBER).description("유저 id"),
                                        fieldWithPath("result.data.usageStatus").type(JsonFieldType.STRING).description("쿠폰 사용 여부"),
                                        fieldWithPath("result.data.rateCoupon").type(JsonFieldType.OBJECT).description("비율 할인쿠폰 데이터").optional(),
                                        fieldWithPath("result.data.rateCoupon.id").type(JsonFieldType.NUMBER).description("비율 할인쿠폰 id"),
                                        fieldWithPath("result.data.rateCoupon.name").type(JsonFieldType.STRING).description("비율 할인쿠폰 이름"),
                                        fieldWithPath("result.data.rateCoupon.createdDate").type(JsonFieldType.STRING).description("비율 할인쿠폰 생성일"),
                                        fieldWithPath("result.data.rateCoupon.lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인쿠폰 최종 수정일"),
                                        fieldWithPath("result.data.rateCoupon.regdate").type(JsonFieldType.STRING).description("비율 할인쿠폰 등록일"),
                                        fieldWithPath("result.data.rateCoupon.rate").type(JsonFieldType.NUMBER).description("비율 할인쿠폰 할인 비율"),
                                        fieldWithPath("result.data.rateCoupon.minPrice").type(JsonFieldType.NUMBER).description("비율 할인쿠폰 적용 최소 가격"),
                                        fieldWithPath("result.data.rateCoupon.startDate").type(JsonFieldType.STRING).description("비율 할인쿠폰 적용 시작일"),
                                        fieldWithPath("result.data.rateCoupon.endDate").type(JsonFieldType.STRING).description("비율 할인쿠폰 적용 마감일"),
                                        fieldWithPath("result.data.rateCoupon.productId").type(JsonFieldType.NUMBER).description("비율 할인쿠폰 상품 id"),
                                        fieldWithPath("result.data.rateCoupon.sellerId").type(JsonFieldType.NUMBER).description("셀러 id"),
                                        fieldWithPath("result.data.rateCoupon.couponFlag").type(JsonFieldType.NUMBER).description("쿠폰 구분"),
                                        fieldWithPath("result.data.fixCoupon").type(JsonFieldType.OBJECT).description("고정 할인쿠폰 데이터").optional(),
                                        fieldWithPath("result.data.fixCoupon.id").type(JsonFieldType.NUMBER).description("고정 할인쿠폰 아이디"),
                                        fieldWithPath("result.data.fixCoupon.name").type(JsonFieldType.STRING).description("고정 할인쿠폰 이름"),
                                        fieldWithPath("result.data.fixCoupon.createdDate").type(JsonFieldType.STRING).description("고정 할인쿠폰 생성날짜"),
                                        fieldWithPath("result.data.fixCoupon.lastModifiedDate").type(JsonFieldType.STRING).description("고정 할인쿠폰 마지막 수정 날짜"),
                                        fieldWithPath("result.data.fixCoupon.regdate").type(JsonFieldType.STRING).description("고정 할인쿠폰 등록 날짜"),
                                        fieldWithPath("result.data.fixCoupon.price").type(JsonFieldType.NUMBER).description("고정 할인쿠폰 가격"),
                                        fieldWithPath("result.data.fixCoupon.minPrice").type(JsonFieldType.NUMBER).description("고정 할인쿠폰 적용 최저 가격"),
                                        fieldWithPath("result.data.fixCoupon.startDate").type(JsonFieldType.STRING).description("고정 할인쿠폰 시작일"),
                                        fieldWithPath("result.data.fixCoupon.endDate").type(JsonFieldType.STRING).description("고정 할인쿠폰 마감일"),
                                        fieldWithPath("result.data.fixCoupon.productId").type(JsonFieldType.NUMBER).description("고정 할인쿠폰 상품 id"),
                                        fieldWithPath("result.data.fixCoupon.sellerId").type(JsonFieldType.NUMBER).description("셀러 id"),
                                        fieldWithPath("result.data.fixCoupon.freeDelivery").type(JsonFieldType.BOOLEAN).description("고정 할인쿠폰 무료배송여부"),
                                        fieldWithPath("result.data.fixCoupon.couponFlag").type(JsonFieldType.NUMBER).description("쿠폰 구분")
                                )
                        )
                );
    }

    @Test
    @DisplayName("고정 할인 쿠폰 리스트 다운로드 ")
    public void saveRateCouponList() throws Exception {


        String param = "";
        for (Long id : rateIdList) {
            param+=id+",";
        }

        param = param.substring(0,param.length()-1);

        this.mockMvc.perform(
                        post("/userCoupon/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("memberId", 100L)
                                .param("rateIdList",param)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("user-coupon-rest-controller/save-rateCoupon-list",
                                requestHeaders(
                                        headerWithName("memberId").description("사용자 id")
                                ),
                                requestParameters(
                                        parameterWithName("rateIdList").description("비율 할인 쿠폰 아이디 리스트")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드")
                                )
                        )
                );
    }

    @Test
    @DisplayName("product에 해당하는 비율 할인 쿠폰 조회 (productId - List<RateCoupon>)")
    public void rateCouponList() throws Exception {

        List<Long> param = new ArrayList<>();
        param.add(productId);
        String productIdList = "";
        for (Long productId : param) {
            productIdList+=productId+",";
        }

        productIdList = productIdList.substring(0,productIdList.length()-1);

        this.mockMvc.perform(
                        get("/userCoupon/rateCoupon/list")
                                .param("productIdList",productIdList)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("memberId", memberId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("user-coupon-rest-controller/rateCoupon-list",
                                requestHeaders(
                                        headerWithName("memberId").description("사용자 id")
                                ),
                                requestParameters(
                                        parameterWithName("productIdList").description("상품 아이디 리스트")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data.*").type(JsonFieldType.ARRAY).description("상품 id"),
                                        fieldWithPath("result.data.*[].createdDate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 생성 날짜"),
                                        fieldWithPath("result.data.*[].lastModifiedDate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 마지막 수정 날짜"),
                                        fieldWithPath("result.data.*[].id").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 id"),
                                        fieldWithPath("result.data.*[].name").type(JsonFieldType.STRING).description("비율 할인 쿠폰 이름"),
                                        fieldWithPath("result.data.*[].regdate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 등록날짜"),
                                        fieldWithPath("result.data.*[].rate").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 할인 비율"),
                                        fieldWithPath("result.data.*[].minPrice").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 적용 최소 비용"),
                                        fieldWithPath("result.data.*[].startDate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 적용 시작일"),
                                        fieldWithPath("result.data.*[].endDate").type(JsonFieldType.STRING).description("비율 할인 쿠폰 적용 마감일"),
                                        fieldWithPath("result.data.*[].productId").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 적용 상품 id"),
                                        fieldWithPath("result.data.*[].sellerId").type(JsonFieldType.NUMBER).description("비율 할인 쿠폰 seller id"),
                                        fieldWithPath("result.data.*[].couponFlag").type(JsonFieldType.NUMBER).description("쿠폰 구분")

                                )
                        )
                );
    }

    @Test
    @DisplayName("product에 해당하는 고정 할인 쿠폰 조회 (sellerId - List<FixCoupon>)")
    public void fixCouponList() throws Exception {

        List<Long> param = new ArrayList<>();
        param.add(productId);
        String productIdList = "";
        for (Long productId : param) {
            productIdList+=productId+",";
        }

        productIdList = productIdList.substring(0,productIdList.length()-1);

        this.mockMvc.perform(
                        get("/userCoupon/fixCoupon/list")
                                .param("productIdList",productIdList)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("memberId", memberId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("user-coupon-rest-controller/fixCoupon-list",
                                requestHeaders(
                                        headerWithName("memberId").description("사용자 id")
                                ),
                                requestParameters(
                                        parameterWithName("productIdList").description("상품 아이디 리스트")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data.*").type(JsonFieldType.OBJECT).description("상품 id"),
                                        fieldWithPath("result.data.*.createdDate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 생성 날짜"),
                                        fieldWithPath("result.data.*.lastModifiedDate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 마지막 수정 날짜"),
                                        fieldWithPath("result.data.*.id").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 id"),
                                        fieldWithPath("result.data.*.name").type(JsonFieldType.STRING).description("고정 할인 쿠폰 이름"),
                                        fieldWithPath("result.data.*.regdate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 등록날짜"),
                                        fieldWithPath("result.data.*.price").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 할인 가격"),
                                        fieldWithPath("result.data.*.minPrice").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 적용 최소 비용"),
                                        fieldWithPath("result.data.*.startDate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 적용 시작일"),
                                        fieldWithPath("result.data.*.endDate").type(JsonFieldType.STRING).description("고정 할인 쿠폰 적용 마감일"),
                                        fieldWithPath("result.data.*.productId").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 적용 상품 id"),
                                        fieldWithPath("result.data.*.sellerId").type(JsonFieldType.NUMBER).description("고정 할인 쿠폰 seller id"),
                                        fieldWithPath("result.data.*.freeDelivery").type(JsonFieldType.BOOLEAN).description("고정 할인 쿠폰 seller id"),
                                        fieldWithPath("result.data.*.couponFlag").type(JsonFieldType.NUMBER).description("쿠폰 구분")

                                )
                        )
                );
    }

    @Test
    @DisplayName("product에 해당하는 비율 할인 쿠폰 조회 (productId - List<RateCoupon>)")
    public void countUserCoupon() throws Exception {



        this.mockMvc.perform(
                        get("/userCoupon/count")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("memberId", memberId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("user-coupon-rest-controller/count-userCoupon",
                                requestHeaders(
                                        headerWithName("memberId").description("사용자 id")
                                ),
                                
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("result.data").type(JsonFieldType.NUMBER).description("쿠폰 갯수")

                                )
                        )
                );
    }

}
