package com.unibond.unibond.comment.dto;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.common.PageInfo;
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
    private PageInfo pageInfo;
    List<ChildCommentsDto> childCommentList;

    public ChildCommentsPagingResDto(Page<Comment> commentPage) {
        this.pageInfo = new PageInfo(commentPage);
        this.childCommentList = commentPage.getContent().stream().map(
                ChildCommentsDto::new
        ).collect(Collectors.toList());
    }
}
