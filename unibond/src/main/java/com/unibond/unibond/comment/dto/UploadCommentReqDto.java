package com.unibond.unibond.comment.dto;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadCommentReqDto {
    private Long parentCommentId;
    private String content;

    public Comment buildChildComment(Member member, Post post, Comment parentComment) {
        return Comment.getNewChildComment(member, parentComment, post, content);

    }

    public Comment buildParentComment(Member member, Post post) {
        return Comment.getNewParentComment(member, post, content);
    }
}
