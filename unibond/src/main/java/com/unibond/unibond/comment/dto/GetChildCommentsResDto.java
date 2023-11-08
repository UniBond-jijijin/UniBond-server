package com.unibond.unibond.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetChildCommentsResDto {
    private int childCommentCount;
    private List<ChildCommentsDto> childCommentsDtoList;

    @Builder
    public GetChildCommentsResDto(List<ChildCommentsDto> childCommentsDtoList) {
        this.childCommentsDtoList = childCommentsDtoList;
        this.childCommentCount = childCommentsDtoList.size();
    }
}
