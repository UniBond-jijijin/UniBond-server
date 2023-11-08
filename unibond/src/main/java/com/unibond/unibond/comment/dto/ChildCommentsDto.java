package com.unibond.unibond.comment.dto;

import com.unibond.unibond.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildCommentsDto {
    private String profileImgUrl;
    private Long commentUserId;
    private String commentUserName;
    private LocalDateTime createdDate;
    private String content;

    public static List<ChildCommentsDto> getChildCommentDtoList(List<Comment> childCommentList) {
        return childCommentList.stream().map(
                ChildCommentsDto::new
        ).collect(Collectors.toList());
    }

    @Builder
    public ChildCommentsDto(Comment comment) {
        this.profileImgUrl = comment.getMember().getProfileImage();
        this.commentUserId = comment.getMember().getId();
        this.commentUserName = comment.getMember().getNickname();
        this.createdDate = comment.getCreatedDate();
        this.content = comment.getContent();
    }
}
