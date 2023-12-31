package com.unibond.unibond.comment.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parentCommentId")
    private Comment parentComment;

    // default = lazy loading
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childCommentList = new ArrayList<>();

    private String content;

    @Builder
    public Comment(Member member, Post post, Comment parentComment, String content) {
        this.member = member;
        this.post = post;
        this.parentComment = parentComment;
        this.content = content;
    }

    public static Comment getNewParentComment(Member member, Post post, String content) {
        return Comment.builder()
                .member(member)
                .post(post)
                .content(content)
                .build();
    }

    public static Comment getNewChildComment(Member member, Comment parentComment, Post post, String content) {
        Comment newComment = Comment.builder()
                .member(member)
                .post(post)
                .content(content)
                .parentComment(parentComment)
                .build();
        parentComment.getChildCommentList().add(newComment);
        return newComment;
    }
}
