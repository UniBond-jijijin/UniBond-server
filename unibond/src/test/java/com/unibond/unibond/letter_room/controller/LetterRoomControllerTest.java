package com.unibond.unibond.letter_room.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentRequest;
import static com.unibond.unibond.common.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class LetterRoomControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("현재 참여 중인 편지방들 조회")
    public void getAllLetterRoomList() throws Exception {
        String page = "0";

        this.mockMvc.perform(
                        get("/api/v1/letter-rooms")
                                .param("page", page)
                                .header("Authorization", "3")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("get_all_letter-rooms",
                        getDocumentRequest(),
                        getDocumentResponse(),
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
                                fieldWithPath("result.pageInfo").type(OBJECT).description("페이징 정보"),
                                fieldWithPath("result.pageInfo.lastPage").type(BOOLEAN).description("페이징 정보: 마지막 페이지인지의 여부"),
                                fieldWithPath("result.pageInfo.totalPages").type(NUMBER).description("페이징 정보: 총 페이지 수"),
                                fieldWithPath("result.pageInfo.totalElements").type(NUMBER).description("페이징 정보: 총 검색 결과 개수"),
                                fieldWithPath("result.pageInfo.size").type(NUMBER).description("페이징 정보: 현재 페이지의 크기 [default: 30] - size는 parameter를 통해 전송하지 않는 것을 추천드립니다."),
                                fieldWithPath("result.letterRoomList").type(ARRAY).description("편지방 리스트"),
                                fieldWithPath("result.letterRoomList[].senderProfileImg").type(STRING).description("편지방 리스트: 전송자 프로필 이미지").optional(),
                                fieldWithPath("result.letterRoomList[].senderNick").type(STRING).description("편지방 리스트: 전송자의 닉네임").optional(),
                                fieldWithPath("result.letterRoomList[].senderId").type(NUMBER).description("편지방 리스트: 전송자 아이디").optional(),
                                fieldWithPath("result.letterRoomList[].recentLetterSentDate").type(STRING).description("편지방 리스트: 해당 편지방에서 가장 최근의 편지가 도착한 시각").optional(),
                                fieldWithPath("result.letterRoomList[].letterRoomId").type(NUMBER).description("편지방 리스트: 편지방 아이디").optional()
                        )
                ));
    }

    @Test
    @DisplayName("편지방 내부의 편지들 조회")
    public void getAllLettersInLetterRoom() throws Exception {
        String page = "0";
        String letterRoomId = "9";
        String loginId = "2";

        this.mockMvc.perform(
                        get("/api/v1/letter-rooms/{letterRoomId}", letterRoomId)
                                .param("page", page)
                                .header("Authorization", loginId)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("get_all_letters_in_letter-rooms",
                        getDocumentRequest(),
                        getDocumentResponse(),
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
                                fieldWithPath("result.receiverProfileImg").type(STRING).description("수신자 프로필 이미지"),
                                fieldWithPath("result.receiverName").type(STRING).description("수신자 이름"),
                                fieldWithPath("result.receiverDiseaseName").type(STRING).description("수신자 질병 이름"),
                                fieldWithPath("result.receiverDiagnosisTiming").type(STRING).description("수신자 진단 시기"),
                                fieldWithPath("result.pageInfo").type(OBJECT).description("페이징 정보"),
                                fieldWithPath("result.pageInfo.lastPage").type(BOOLEAN).description("페이징 정보: 마지막 페이지인지의 여부"),
                                fieldWithPath("result.pageInfo.totalPages").type(NUMBER).description("페이징 정보: 총 페이지 수"),
                                fieldWithPath("result.pageInfo.totalElements").type(NUMBER).description("페이징 정보: 총 검색 결과 개수"),
                                fieldWithPath("result.pageInfo.size").type(NUMBER).description("페이징 정보: 현재 페이지의 크기 [default: 30] - size는 parameter를 통해 전송하지 않는 것을 추천드립니다."),
                                fieldWithPath("result.letterList").type(ARRAY).description("편지 리스트"),
                                fieldWithPath("result.letterList[].senderName").type(STRING).description("편지 리스트: 송신자 이름"),
                                fieldWithPath("result.letterList[].sentDate").type(STRING).description("편지 리스트: 편지 송신 시각"),
                                fieldWithPath("result.letterList[].letterTitle").type(STRING).description("편지 리스트: 편지 제목")
                        )
                ));
    }

}