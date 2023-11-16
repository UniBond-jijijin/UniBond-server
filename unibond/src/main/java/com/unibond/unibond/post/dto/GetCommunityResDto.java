package com.unibond.unibond.post.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class GetCommunityResDto {
    private boolean isLastPage;
    private Long count;
    private Page<PostPreviewDto> postPreviewList;

    public GetCommunityResDto(Page<PostPreviewDto> postPreviewList) {
        this.postPreviewList = postPreviewList;
        this.count = postPreviewList.getTotalElements();
        this.isLastPage = !postPreviewList.hasNext();
    }
}


