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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.HashMap;
import java.util.Map;

import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentRequest;
import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class ExperiencePostControllerTest {

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
    @DisplayName("경험 기록 게시판 조회 Test")
    void getExperienceCommunityPosts() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/community/experience")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("get-experience-community",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("isSuccess").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(NUMBER).description("결과 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메세지"),
                                fieldWithPath("result").type(OBJECT).description("결과 데이터"),
                                fieldWithPath("result.numberOfElements").type(NUMBER).description("반환된 게시글 수"),
                                fieldWithPath("result.postPreviewList").type(ARRAY).description("게시글 리스트"),
                                fieldWithPath("result.postPreviewList[].createdDate").type(STRING).description("게시글 작성 일자"),
                                fieldWithPath("result.postPreviewList[].ownerProfileImg").type(STRING).description("게시글 작성자 프로필 사진"),
                                fieldWithPath("result.postPreviewList[].ownerNick").type(STRING).description("게시글 작성자 닉네임"),
                                fieldWithPath("result.postPreviewList[].disease").type(STRING).description("게시글 작성자의 질병"),
                                fieldWithPath("result.postPreviewList[].contentPreview").type(STRING).description("게시글 미리보기"),
                                fieldWithPath("result.postPreviewList[].boardType").type(STRING).description("게시글 종류"),
                                fieldWithPath("result.postPreviewList[].isEnd").type(BOOLEAN).description("미리보기로 제공된 게시글의 내용이 마지막인지 여부"),
                                fieldWithPath("result.lastPage").type(BOOLEAN).description("현재 마지막 페이지인지 여부"),
                                fieldWithPath("result.totalPages").type(NUMBER).description("총 페이지 수"),
                                fieldWithPath("result.totalElements").type(NUMBER).description("총 원소 개수"),
                                fieldWithPath("result.size").type(NUMBER).description("현재 페이지 사이즈")
                        )
                ));
    }

    @Test
    @DisplayName("경험 기록 게시판 게시물 업로드 Test")
    void createPost() throws Exception {

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("content", "경험 기록 게시판 게시물 업로드 테스트");

        String content = objectMapper.writeValueAsString(requestMap);

        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/community/experience")
                                .contentType(APPLICATION_JSON)
                                .content(content)
                                .header("Authorization", 3)
                )
                .andExpect(status().isOk())
                .andDo(document("post-experience-community",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}