package com.unibond.unibond.disease.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentRequest;
import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
class DiseaseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    @DisplayName("질병 검색 API - 한글로 검색")
    public void searchDiseaseTestByKor() throws Exception {

        String language = "kor";
        String searchWord = "8번 염색체";
        String page = "0";

        this.mockMvc.perform(
                        get("/api/v1/diseases/search")
                                .param("lan", language)
                                .param("query", searchWord)
                                .param("page", page)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("search-disease",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("lan").description("검색할 언어: 언어는 한국어, 영어 즉 `kor`, `eng` 문자열만 전송 가능"),
                                parameterWithName("query").description("검색할 내용"),
                                parameterWithName("page").description("조회할 페이지 [default: 0]").optional()
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(NUMBER).description("결과 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메세지"),
                                fieldWithPath("result").type(OBJECT).description("결과 데이터"),
                                fieldWithPath("result.pageInfo").type(OBJECT).description("페이징 정보"),
                                fieldWithPath("result.pageInfo.lastPage").type(BOOLEAN).description("페이징 정보: 마지막 페이지인지의 여부"),
                                fieldWithPath("result.pageInfo.totalPages").type(NUMBER).description("페이징 정보: 총 페이지 수"),
                                fieldWithPath("result.pageInfo.totalElements").type(NUMBER).description("페이징 정보: 총 검색 결과 개수"),
                                fieldWithPath("result.pageInfo.size").type(NUMBER).description("페이징 정보: 현재 페이지의 크기"),
                                fieldWithPath("result.diseaseDataList").type(ARRAY).description("검색된 질병 리스트"),
                                fieldWithPath("result.diseaseDataList[].diseaseNameKor").type(STRING).description("검색된 질병 리스트: 한글 질병 이름").optional(),
                                fieldWithPath("result.diseaseDataList[].diseaseNameEng").type(STRING).description("검색된 질병 리스트: 영어 질병 이름").optional()
                        )
                ));
    }

}