package com.unibond.unibond.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.common.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentCommentResDto {
    private Long commentUserId;
    private String profileImgUrl;
    private String commentUserName;
    private Long commentId;
    private LocalDateTime createdDate;
    private String content;

    private PageInfo childCommentPageInfo;
    private List<ChildCommentsDto> childCommentList;

    public static List<ParentCommentResDto> getParentCommentResDtoList(List<Comment> parentCommentList) {
        return parentCommentList.stream().map(
                ParentCommentResDto::new
        ).collect(Collectors.toList());
    }

    @Builder
    public ParentCommentResDto(Comment comment) {
        this.profileImgUrl = comment.getMember().getProfileImage();
        this.commentUserId = comment.getMember().getId();
        this.commentUserName = comment.getMember().getNickname();
        this.commentId = comment.getId();
        this.createdDate = comment.getCreatedDate();
        this.content = comment.getContent();

        if (!comment.getChildCommentList().isEmpty()) {
            Page<ChildCommentsDto> childCommentDtoPage =
                    ChildCommentsDto.getChildCommentDtoList(
                            comment.getChildCommentList(),
                            PageRequest.of(0, 30));
            this.childCommentPageInfo = new PageInfo(childCommentDtoPage);
            this.childCommentList = childCommentDtoPage.getContent();
        }
    }
}
