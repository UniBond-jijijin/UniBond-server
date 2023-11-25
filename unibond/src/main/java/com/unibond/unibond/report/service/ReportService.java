package com.unibond.unibond.report.service;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.comment.repository.CommentRepository;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.letter.repository.LetterRepository;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.member.repository.MemberRepository;
import com.unibond.unibond.post.domain.Post;
import com.unibond.unibond.post.repository.PostRepository;
import com.unibond.unibond.report.controller.ReportType;
import com.unibond.unibond.report.domain.Report;
import com.unibond.unibond.report.dto.ReportReqDto;
import com.unibond.unibond.report.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.unibond.unibond.common.BaseResponseStatus.*;
import static com.unibond.unibond.report.controller.ReportType.*;
import static com.unibond.unibond.report.domain.Report.ReportBuilder;
import static com.unibond.unibond.report.domain.Report.builder;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final LetterRepository letterRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final LoginInfoService loginInfoService;

    @Transactional
    public BaseResponseStatus makeReport(ReportReqDto reqDto) throws BaseException {
        try {
            Report report = getReportByReportType(reqDto);
            reportRepository.save(report);
            return SUCCESS;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private Report getReportByReportType(ReportReqDto reqDto) throws BaseException {
        ReportType reportType = reqDto.getReportType();
        Member loginMember = loginInfoService.getLoginMember();
        ReportBuilder result = builder().reporter(loginMember).reportType(reportType).content(reqDto.getContent());
        if (reportType.equals(LETTER)) {
            Letter letter = letterRepository.findById(reqDto.getLetterId())
                    .orElseThrow(() -> new BaseException(INVALID_COMMENT_ID));
            return result.letter(letter).build();
        } else if (reportType.equals(MEMBER)) {
            Member member = memberRepository.findById(reqDto.getMemberId())
                    .orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));
            return result.member(member).build();
        } else if (reportType.equals(POST)) {
            Post post = postRepository.findById(reqDto.getPostId())
                    .orElseThrow(() -> new BaseException(INVALID_POST_ID));
            return result.post(post).build();
        } else {
            Comment comment = commentRepository.findById(reqDto.getCommentId())
                    .orElseThrow(() -> new BaseException(INVALID_COMMENT_ID));
            return result.comment(comment).build();
        }
    }
}
