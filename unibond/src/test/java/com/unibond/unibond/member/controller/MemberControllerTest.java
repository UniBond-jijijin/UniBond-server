package com.unibond.unibond.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibond.unibond.member.domain.Gender;
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
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.List;

import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentRequest;
import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentResponse;
import static com.unibond.unibond.member.domain.Gender.MALE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
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

        this.mockMvc.perform(
                        multipart(HttpMethod.PATCH, "/api/v1/members/{memberId}", modifyMemberId)
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