package com.unibond.unibond.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unibond.unibond.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class ParentCommentResDto {
    private String profileImgUrl;
    private Long commentUserId;
    private String commentUserName;

    private Long commentId;
    private LocalDateTime createdDate;
    private String content;

    private List<ChildCommentsDto> childCommentResDtoList;
    private Boolean childCommentLastPage;
    private int totalChildCommentPages;
    private long totalChildCommentElements;
    private int childCommentPagingSize;

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
            Page<ChildCommentsDto> childCommentDtoPage = ChildCommentsDto.getChildCommentDtoList(comment.getChildCommentList(), null);
            this.childCommentResDtoList = childCommentDtoPage.getContent();
            this.childCommentLastPage = childCommentDtoPage.isLast();
            this.totalChildCommentPages = childCommentDtoPage.getTotalPages();
            this.totalChildCommentElements = childCommentDtoPage.getTotalElements();
            this.childCommentPagingSize = childCommentDtoPage.getSize();
        }
    }
}
