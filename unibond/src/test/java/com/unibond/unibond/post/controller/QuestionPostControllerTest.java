package com.unibond.unibond.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentRequest;
import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class QuestionPostControllerTest {

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
    @DisplayName("질문 게시판 조회 Test")
    void getQuestionCommunityPosts() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/community/question")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("get-qna-community",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("isSuccess").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(NUMBER).description("결과 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메세지"),
                                fieldWithPath("result").type(OBJECT).description("결과 데이터"),
                                fieldWithPath("result.pageInfo").type(OBJECT).description("페이징 정보"),
                                fieldWithPath("result.pageInfo.numberOfElements").type(NUMBER).description("페이징 정보: 현재 페이지의 원소 개수"),
                                fieldWithPath("result.pageInfo.lastPage").type(BOOLEAN).description("페이징 정보: 마지막 페이지인지의 여부"),
                                fieldWithPath("result.pageInfo.totalPages").type(NUMBER).description("페이징 정보: 총 페이지 수"),
                                fieldWithPath("result.pageInfo.totalElements").type(NUMBER).description("페이징 정보: 총 검색 결과 개수"),
                                fieldWithPath("result.pageInfo.size").type(NUMBER).description("페이징 정보: 현재 페이지의 크기 [default: 30] - size는 parameter를 통해 전송하지 않는 것을 추천드립니다."),
                                fieldWithPath("result.postPreviewList").type(ARRAY).description("게시글 리스트"),
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
}