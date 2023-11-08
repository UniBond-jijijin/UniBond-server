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
public class GetParentCommentResDto {
    private String profileImgUrl;
    private Long commentUserId;
    private String commentUserName;
    private LocalDateTime createdDate;
    private String content;
    private Boolean hasChildComments;
    private List<ChildCommentsDto> childCommentResDtoList;

    public static List<GetParentCommentResDto> getParentCommentResDtoList(List<Comment> parentCommentList) {
        return parentCommentList.stream().map(
                GetParentCommentResDto::new
        ).collect(Collectors.toList());
    }

    @Builder
    public GetParentCommentResDto(Comment comment) {
        // no additional queries are issued because of the fetch join (with member).
        this.profileImgUrl = comment.getMember().getProfileImage();
        this.commentUserId = comment.getMember().getId();
        this.commentUserName = comment.getMember().getNickname();
        this.createdDate = comment.getCreatedDate();
        this.content = comment.getContent();

        // no additional queries are issued because of the batch size.
        this.hasChildComments = !comment.getChildCommentList().isEmpty();
    }
}
