package com.unibond.unibond.report.domain;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.Post;
import com.unibond.unibond.report.controller.ReportType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Report extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reporterId")
    private Member reporter;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "letterId")
    private Letter letter;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "commentId")
    private Comment comment;

    @Enumerated(STRING)
    @Setter
    private ReportType reportType;

    private String content;

    @Builder
    public Report(Member reporter, Member member, Letter letter, Post post, Comment comment,
                  ReportType reportType, String content) {
        this.reporter = reporter;
        this.member = member;
        this.letter = letter;
        this.post = post;
        this.comment = comment;
        this.reportType = reportType;
        this.content = content;
    }
}
