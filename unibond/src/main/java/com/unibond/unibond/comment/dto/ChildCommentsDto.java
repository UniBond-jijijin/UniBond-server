package com.unibond.unibond.comment.dto;

import com.unibond.unibond.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildCommentsDto {
    private Long commentUserId;
    private String profileImgUrl;
    private String commentUserName;
    private Long commentId;
    private LocalDateTime createdDate;
    private String content;

    public static Page<ChildCommentsDto> getChildCommentDtoList(List<Comment> childCommentList, PageRequest pageRequest) {
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), childCommentList.size());

        List<ChildCommentsDto> childCommentsDtoList = childCommentList.stream().map(
                ChildCommentsDto::new
        ).collect(Collectors.toList());

        return new PageImpl<>(childCommentsDtoList.subList(start, end), pageRequest, childCommentsDtoList.size());
    }

    @Builder
    public ChildCommentsDto(Comment comment) {
        this.profileImgUrl = comment.getMember().getProfileImage();
        this.commentUserId = comment.getMember().getId();
        this.commentUserName = comment.getMember().getNickname();
        this.commentId = comment.getId();
        this.createdDate = comment.getCreatedDate();
        this.content = comment.getContent();
    }
}
