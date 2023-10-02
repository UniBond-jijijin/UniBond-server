package com.unibond.unibond.common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /**
     * 1000: 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000: Request Error
     */
    // alarm (2000 ~ 2099)

    // comment (2100 ~ 2199)

    // disease (2200 ~ 2299)
    INVALID_DISEASE_ID(false, 2200, "유효하지 않은 disease id 입니다."),
    NULL_SEARCH_LAN(false, 2201, "검색할 단어의 언어를 설정하지 않았습니다."),

    // letter (2300 ~ 2399)

    // letter_room (2400 ~ 2499)

    // member (2500 ~ 2599)
    INVALID_MEMBER_ID(false, 2500, "유효하지 않은 member id 입니다."),
    DUPLICATE_MEMBER_NICK(false, 2501, "중복되는 닉네임입니다."),

    // post (2600 ~ 2699)

    /**
     * 3000: Response Error
     */
    // alarm (3000 ~ 3099)

    // comment (3100 ~ 3199)

    // disease (3200 ~ 3299)

    // letter (3300 ~ 3399)

    // letter_room (3400 ~ 3499)

    // member (3500 ~ 3599)

    // post (3600 ~ 3699)

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
