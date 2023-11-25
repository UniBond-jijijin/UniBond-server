package com.unibond.unibond.report.dto;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.report.controller.ReportType;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.unibond.unibond.common.BaseResponseStatus.EMPTY_REPORT_ID;
import static com.unibond.unibond.report.controller.ReportType.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportReqDto {
    @Nullable
    private Long commentId;

    @Nullable
    private Long postId;

    @Nullable
    private Long letterId;

    @Nullable
    private Long memberId;

    private String content;

    public ReportType getReportType() throws BaseException {
        if (commentId != null) {
            return COMMENT;
        } else if (postId != null) {
            return POST;
        } else if (letterId != null) {
            return LETTER;
        } else if (memberId != null) {
            return MEMBER;
        }
        throw new BaseException(EMPTY_REPORT_ID);
    }
}
