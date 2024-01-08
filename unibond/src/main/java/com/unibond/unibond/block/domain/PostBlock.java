package com.unibond.unibond.block.domain;

import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class PostBlock {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reporterId")
    private Member reporter;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reportedPostId")
    private Post reportedPost;

    @Builder
    public PostBlock(Member reporter, Post reportedPost) {
        this.reporter = reporter;
        this.reportedPost = reportedPost;
    }
}
