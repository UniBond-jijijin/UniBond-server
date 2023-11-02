package com.unibond.unibond.post.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class GetCommunityResDto {
    private boolean isLastPage;
    private int count;
    private Page<PostPreviewDto> postPreviewList;

    public GetCommunityResDto(Page<PostPreviewDto> postPreviewList) {
        this.postPreviewList = postPreviewList;
        this.count = postPreviewList.getTotalPages();
        this.isLastPage = !postPreviewList.hasNext();
    }
}


