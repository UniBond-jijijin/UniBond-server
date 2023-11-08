package com.unibond.unibond.comment.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.Post;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
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

    public void addChildComment(Comment parentComment, Comment childComment) {
        parentComment.childCommentList.add(childComment);
        childComment.parentComment = parentComment;
    }
}
