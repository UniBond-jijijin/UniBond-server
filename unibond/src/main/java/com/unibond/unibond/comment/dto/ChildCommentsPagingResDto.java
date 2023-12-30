package com.unibond.unibond.comment.dto;

import com.unibond.unibond.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildCommentsPagingResDto {

    List<ChildCommentsDto> childCommentList;

    private Boolean childCommentLastPage;
    private int totalChildCommentPages;
    private long totalChildCommentElements;
    private int childCommentPagingSize;

    public ChildCommentsPagingResDto(Page<Comment> commentPage) {

        this.childCommentList = commentPage.getContent().stream().map(
                ChildCommentsDto::new
        ).collect(Collectors.toList());

        this.childCommentLastPage = commentPage.isLast();
        this.totalChildCommentPages = commentPage.getTotalPages();
        this.totalChildCommentElements = commentPage.getTotalElements();
        this.childCommentPagingSize = commentPage.getSize();
    }
}
