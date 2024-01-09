package com.unibond.unibond.common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /**
     * 1000: 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    // alarm (1200 ~ 1299)

    // comment (1300 ~ 1399)

    // disease (1400 ~ 1499)

    // letter (1500 ~ 1599)

    // letter_room (1600 ~ 1699)

    // member (1700 ~ 1799)
    UNUSABLE_NICK(true, 1700, "이미 존재하는 닉네임입니다."),
    USABLE_NICK(true, 1701, "사용 가능한 닉네임입니다."),

    // post (1800 ~ 1899)

    /**
     * 2000: Request Error
     */
    // alarm (2000 ~ 2099)

    // comment (2100 ~ 2199)
    INVALID_COMMENT_ID(false, 2100, "유효하지 않은 댓글 id 입니다."),

    // disease (2200 ~ 2299)
    INVALID_DISEASE_ID(false, 2200, "유효하지 않은 질병 id 입니다."),
    NULL_SEARCH_LAN(false, 2201, "검색할 단어의 언어를 설정하지 않았습니다."),

    // letter (2300 ~ 2399)
    INVALID_LETTER_ID(false, 2300, "유효하지 않은 편지 id 입니다."),

    // letter_room (2400 ~ 2499)
    INVALID_LETTER_ROOM_ID(false, 2400, "유효하지 않은 편지방 id 입니다."),

    // member (2500 ~ 2599)
    INVALID_MEMBER_ID(false, 2500, "유효하지 않은 멤버 id 입니다."),
    TOO_LONG_NICKNAME(false, 2501, "설정한 닉네임이 너무 깁니다"),
    DUPLICATE_MEMBER_NICK(false, 2502, "중복되는 닉네임입니다."),
    NOT_YOUR_PROFILE(false, 2503, "자신의 프로필이 아니므로 수정할 수 없습니다."),

    // post (2600 ~ 2699)
    INVALID_POST_ID(false, 2600, "유효하지 않은 게시글 id 입니다"),

    // report (2700 ~ 2799)
    EMPTY_REPORT_ID(false, 2700, "신고 대상의 ID가 null값 입니다."),
    INVALID_REPORT_ID(false, 2701, "신고 대상의 ID가 유효하지 않습니다."),

    // common
    NULL_PROPERTY(false, 2800, "주어져야 할 값이 null 값입니다. api 문서를 다시 확인해주세요."),

    /**
     * 3000: Response Error
     */
    // alarm (3000 ~ 3099)

    // comment (3100 ~ 3199)
    NOT_YOUR_COMMENT(false, 3100, "댓글 삭제 권한이 없습니다."),

    // disease (3200 ~ 3299)

    // letter (3300 ~ 3399)
    NOT_ENOUGH_CHARS(false, 3300, "편지는 최소 50자 이상이어야 합니다."),
    NOT_YOUR_LETTER(false, 3301, "해당 편지를 접근할 권한이 없습니다."),
    CANT_SEND_LETTER(false, 3302, "아직 한 시간이 지나지 않았으므로 편지를 또 다시 보낼 수 없습니다."),
    NOT_YET_ARRIVED(false, 3303, "아직 도착하지 않은 편지입니다."),
    BLOCKED_LETTER(false, 3304, "차단한 사용자이므로 편지를 주고 받거나 조회할 수 없습니다."),

    // letter_room (3400 ~ 3499)
    NOT_YOUR_LETTER_ROOM(false, 3400, "해당 편지방에 접근할 권한이 없습니다."),

    // member (3500 ~ 3599)
    BLOCKED_MEMBER(false, 3500, "차단한 사용자입니다."),
    DELETED_MEMBER(false, 3501, "탈퇴한 회원입니다."),

    // post (3600 ~ 3699)
    INVALID_POST_STATUS(false, 3600, "접근 불가능한 게시물 입니다."),
    BLOCKED_POST(false, 3601, "차단한 게시글입니다."),

    /**
     * 4000: DB Error
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
