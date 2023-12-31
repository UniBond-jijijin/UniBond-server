package com.unibond.unibond.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibond.unibond.member.dto.MemberModifyReqDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.FileInputStream;

import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentRequest;
import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentResponse;
import static com.unibond.unibond.member.domain.Gender.MALE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("자신의 프로필 조회 API Test")
    void getMyProfile() throws Exception {
        String page = "0";
        String loginId = "29";

        this.mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/members/{memberId}", loginId)
                                .param("page", page)
                                .header("Authorization", loginId)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("get_my_profile",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("memberId").description("조회할 멤버 ID").optional()
                        ),
                        queryParameters(
                                parameterWithName("page").description("조회할 페이지 [default: 0]").optional()
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Basic auth credentials")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(NUMBER).description("결과 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메세지"),
                                fieldWithPath("result").type(OBJECT).description("결과 데이터"),
                                fieldWithPath("result.profileImage").type(STRING).description("프로필 이미지"),
                                fieldWithPath("result.nickname").type(STRING).description("닉네임"),
                                fieldWithPath("result.gender").type(STRING).description("성별 : `MALE`, `FEMALE`, `NULL` 존재"),
                                fieldWithPath("result.diseaseName").type(STRING).description("질병명"),
                                fieldWithPath("result.diagnosisTiming").type(STRING).description("진단 시기"),
                                fieldWithPath("result.bio").type(STRING).description("한 줄 소개"),
                                fieldWithPath("result.interestList").type(ARRAY).description("관심사")
                        )
                ));
    }

    @Test
    @DisplayName("남의 프로필 조회 API Test")
    void getOtherProfile() throws Exception {
        String page = "0";
        String loginId = "28";
        String memberId = "29";

        this.mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/members/{memberId}", memberId)
                                .param("page", page)
                                .header("Authorization", loginId)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("get_other_profile",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("memberId").description("조회할 멤버 ID").optional()
                        ),
                        queryParameters(
                                parameterWithName("page").description("조회할 페이지 [default: 0]").optional()
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("Basic auth credentials")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(NUMBER).description("결과 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메세지"),
                                fieldWithPath("result").type(OBJECT).description("결과 데이터"),
                                fieldWithPath("result.profileImage").type(STRING).description("프로필 이미지"),
                                fieldWithPath("result.nickname").type(STRING).description("닉네임"),
                                fieldWithPath("result.gender").type(STRING).description("성별 : `MALE`, `FEMALE`, `NULL` 존재"),
                                fieldWithPath("result.diseaseName").type(STRING).description("질병명"),
                                fieldWithPath("result.diagnosisTiming").type(STRING).description("진단 시기"),
                                fieldWithPath("result.bio").type(STRING).description("한 줄 소개"),
                                fieldWithPath("result.interestList").type(ARRAY).description("관심사"),
                                fieldWithPath("result.pageInfo").type(OBJECT).description("페이징 정보"),
                                fieldWithPath("result.pageInfo.numberOfElements").type(NUMBER).description("페이징 정보: 현재 페이지의 원소 개수"),
                                fieldWithPath("result.pageInfo.lastPage").type(BOOLEAN).description("페이징 정보: 마지막 페이지인지의 여부"),
                                fieldWithPath("result.pageInfo.totalPages").type(NUMBER).description("페이징 정보: 총 페이지 수"),
                                fieldWithPath("result.pageInfo.totalElements").type(NUMBER).description("페이징 정보: 총 검색 결과 개수"),
                                fieldWithPath("result.pageInfo.size").type(NUMBER).description("페이징 정보: 현재 페이지의 크기 [default: 30] - size는 parameter를 통해 전송하지 않는 것을 추천드립니다."),
                                fieldWithPath("result.postPreviewList").type(ARRAY).description("게시물 미리보기 리스트"),
                                fieldWithPath("result.postPreviewList[].createdDate").type(STRING).description("게시물 생성 시간").optional(),
                                fieldWithPath("result.postPreviewList[].ownerId").type(NUMBER).description("게시물 작성자 아이디").optional(),
                                fieldWithPath("result.postPreviewList[].ownerProfileImg").type(STRING).description("게시물 작성자 프로필 이미지").optional(),
                                fieldWithPath("result.postPreviewList[].ownerNick").type(STRING).description("게시물 작성자 닉네임").optional(),
                                fieldWithPath("result.postPreviewList[].disease").type(STRING).description("게시물 작성자 질병").optional(),
                                fieldWithPath("result.postPreviewList[].postId").type(NUMBER).description("게시물 아이디").optional(),
                                fieldWithPath("result.postPreviewList[].postImg").type(STRING).description("게시물 이미지").optional(),
                                fieldWithPath("result.postPreviewList[].contentPreview").type(STRING).description("게시물 미리 보기 내용").optional(),
                                fieldWithPath("result.postPreviewList[].boardType").type(STRING).description("게시판 종류").optional(),
                                fieldWithPath("result.postPreviewList[].isEnd").type(BOOLEAN).description("게시물 미리 보기 뒤에 내용이 더 있는지 여부 : 45자 이상일 경우 false / 아닐 경우 true").optional()
                        )
                ));
    }

    @Test
    @Transactional
    @DisplayName("프로필 수정 API Test")
    public void modifyMemberInfo() throws Exception {

        Long modifyMemberId = 29L;

        MemberModifyReqDto reqDto = MemberModifyReqDto.builder()
                .gender(MALE)
                .diseaseId(79L)
                .bio("수정 테스트를 해봅시다")
                .build();

        String fileName = "test_profile_img";
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/static/" + fileName + ".png");
        MockMultipartFile testImg
                = new MockMultipartFile("profileImg", fileName + ".jpg", "multipart/form-data", fileInputStream);


        String content = objectMapper.writeValueAsString(reqDto);
        MockMultipartFile request
                = new MockMultipartFile("request", "request", "application/json", content.getBytes(UTF_8));


        MockMultipartHttpServletRequestBuilder builder = RestDocumentationRequestBuilders.multipart("/api/v1/members/{memberId}", modifyMemberId);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        this.mockMvc.perform(
                        builder
                                .file(testImg)
                                .file(request)
                                .header("Authorization", modifyMemberId)
                )
                .andExpect(status().isOk())
                .andDo(document("modify-member-info",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Basic auth credentials")
                        ),
                        pathParameters(
                                parameterWithName("memberId").description("수정할 memberId: 로그인한 아이디와 동일해야 수정이 가능합니다.").optional()
                        ),
                        requestParts(
                                partWithName("profileImg").description("수정할 프로필 사진 파일").optional(),
                                partWithName("request").description("게시물 업로드 요청").optional()
                        ),
                        requestPartFields(
                                "request",
                                fieldWithPath("nickname").type(STRING).description("[request.nickname] 새로운 닉네임 : 중복 체크에 유의해주세요! (닉네임 중복 체크 API와 함께 사용을 권장함.) ").optional(),
                                fieldWithPath("gender").type(STRING).description("[request.gender] 새로운 성별 : `MALE`, `FEMALE`, `NULL` 만 가능함. ").optional(),
                                fieldWithPath("diseaseId").type(NUMBER).description("[request.diseaseId] 새로운 질병 : 질병 아이디는 질병 검색 결과에서 아이디를 가져옵니다").optional(),
                                fieldWithPath("diagnosisTiming").type(STRING).description("[request.diagnosisTiming] 새로운 질병 시기 ex)`2002-06-27`").optional(),
                                fieldWithPath("bio").type(STRING).description("[request.bio] 새로운 한 줄 소개").optional(),
                                fieldWithPath("interestList").type(ARRAY).description("[request.interestList] 새로운 관심사 리스트").optional()
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(NUMBER).description("결과 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메세지"),
                                fieldWithPath("result").type(OBJECT).description("결과 데이터"),
                                fieldWithPath("result.profileImage").type(STRING).description("프로필 사진"),
                                fieldWithPath("result.nickname").type(STRING).description("닉네임"),
                                fieldWithPath("result.gender").type(STRING).description("성별"),
                                fieldWithPath("result.diseaseName").type(STRING).description("질병명"),
                                fieldWithPath("result.diagnosisTiming").type(STRING).description("진단 시기"),
                                fieldWithPath("result.bio").type(STRING).description("한 줄 소개"),
                                fieldWithPath("result.interestList").type(ARRAY).description("관심사")
                        )
                ));
    }
}