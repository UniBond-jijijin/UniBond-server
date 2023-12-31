package com.unibond.unibond.post.dto;

import com.unibond.unibond.common.PageInfo;
import com.unibond.unibond.post.domain.Post;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetCommunityResDto {
    private PageInfo pageInfo;
    private List<PostPreviewDto> postPreviewList;

    public GetCommunityResDto(Page<Post> postList) {
        this.postPreviewList = postList.getContent().stream().map(
                post -> new PostPreviewDto(post.getOwner(), post.getOwner().getDisease(), post)
        ).collect(Collectors.toList());
        this.pageInfo = new PageInfo(postList);
    }
}


