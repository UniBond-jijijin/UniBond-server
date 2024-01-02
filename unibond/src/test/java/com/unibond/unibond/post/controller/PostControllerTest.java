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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentRequest;
import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class PostControllerTest {
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
    @DisplayName("게시판 상세 조회 Test")
    void getExperienceCommunityPosts() throws Exception {
        String postId = "59";
        String page = "0";
        String loginId = "29";

        this.mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/community/{postId}", postId)
                                .param("page", page)
                                .header("Authorization", loginId)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("get-detail-post",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("postId").description("조회할 게시물 ID").optional()
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
                                fieldWithPath("result.loginMemberProfileImage").type(STRING).description("로그인 된 유저의 프로필 사진"),
                                fieldWithPath("result.postOwnerId").type(NUMBER).description("게시물 작성자의 아이디"),
                                fieldWithPath("result.profileImage").type(STRING).description("게시물 작성자의 프로필 사진"),
                                fieldWithPath("result.postOwnerName").type(STRING).description("게시물 작성자 이름"),
                                fieldWithPath("result.createdDate").type(STRING).description("게시물 생성 시간"),
                                fieldWithPath("result.diseaseName").type(STRING).description("게시물 작성자의 질병"),
                                fieldWithPath("result.postImg").type(STRING).description("게시물 사진 url").optional(),
                                fieldWithPath("result.content").type(STRING).description("게시물 내용"),
                                fieldWithPath("result.commentCount").type(NUMBER).description("게시물의 총 댓글 수"),
                                fieldWithPath("result.parentCommentPageInfo").type(OBJECT).description("부모 댓글 페이징 정보"),
                                fieldWithPath("result.parentCommentPageInfo.numberOfElements").type(NUMBER).description("부모 댓글 페이징 정보: 현재 페이지의 원소 개수"),
                                fieldWithPath("result.parentCommentPageInfo.lastPage").type(BOOLEAN).description("부모 댓글 페이징 정보: 마지막 페이지인지의 여부"),
                                fieldWithPath("result.parentCommentPageInfo.totalPages").type(NUMBER).description("부모 댓글 페이징 정보: 총 페이지 수"),
                                fieldWithPath("result.parentCommentPageInfo.totalElements").type(NUMBER).description("부모 댓글 페이징 정보: 총 검색 결과 개수"),
                                fieldWithPath("result.parentCommentPageInfo.size").type(NUMBER).description("부모 댓글 페이징 정보: 현재 페이지의 크기 [default: 30] - size는 parameter를 통해 전송하지 않는 것을 추천드립니다."),
                                fieldWithPath("result.parentCommentList").type(ARRAY).description("부모 댓글 리스트").optional(),
                                fieldWithPath("result.parentCommentList[].commentUserId").type(NUMBER).description("부모 댓글 리스트: 작성자의 아이디").optional(),
                                fieldWithPath("result.parentCommentList[].profileImgUrl").type(STRING).description("부모 댓글 리스트: 작성자의 프로필 이미지").optional(),
                                fieldWithPath("result.parentCommentList[].commentUserName").type(STRING).description("부모 댓글 리스트: 작성자의 닉네임").optional(),
                                fieldWithPath("result.parentCommentList[].commentId").type(NUMBER).description("부모 댓글 리스트: 부모 댓글의 아이디").optional(),
                                fieldWithPath("result.parentCommentList[].createdDate").type(STRING).description("부모 댓글 리스트: 댓글 작성 일자").optional(),
                                fieldWithPath("result.parentCommentList[].content").type(STRING).description("부모 댓글 리스트: 댓글 내용").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentPageInfo").type(OBJECT).description("부모 댓글 리스트: 자식 댓글 페이징 정보").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentPageInfo.numberOfElements").type(NUMBER).description("자식 댓글 페이징 정보: 현재 페이지의 원소 개수").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentPageInfo.lastPage").type(BOOLEAN).description("자식 댓글 페이징 정보: 마지막 페이지인지의 여부").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentPageInfo.totalPages").type(NUMBER).description("자식 댓글 페이징 정보: 총 페이지 수").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentPageInfo.totalElements").type(NUMBER).description("자식 댓글 페이징 정보: 총 검색 결과 개수").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentPageInfo.size").type(NUMBER).description("자식 댓글 페이징 정보: 현재 페이지의 크기 [default: 30] - size는 parameter를 통해 전송하지 않는 것을 추천드립니다.").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentList").type(ARRAY).description("부모 댓글 리스트: 자식 댓글 리스트").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentList[].commentUserId").type(NUMBER).description("자식 댓글 리스트: 작성자 아이디").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentList[].profileImgUrl").type(STRING).description("자식 댓글 리스트: 프로필 이미지 URL").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentList[].commentUserName").type(STRING).description("자식 댓글 리스트: 작성자 닉네임").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentList[].commentId").type(NUMBER).description("자식 댓글 리스트: 댓글 아이디").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentList[].createdDate").type(STRING).description("자식 댓글 리스트: 댓글 작성 일자").optional(),
                                fieldWithPath("result.parentCommentList[].childCommentList[].content").type(STRING).description("자식 댓글 리스트: 댓글 내용").optional()
                        )
                ));
    }
}
