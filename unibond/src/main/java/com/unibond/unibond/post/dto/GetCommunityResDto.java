package com.unibond.unibond.post.dto;

import com.unibond.unibond.post.domain.Post;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetCommunityResDto {
    private int numberOfElements;
    private List<PostPreviewDto> postPreviewList;

    private Boolean lastPage;
    private int totalPages;
    private long totalElements;
    private int size;

    public GetCommunityResDto(Page<Post> postList) {
        this.postPreviewList = postList.getContent().stream().map(
                post -> new PostPreviewDto(post.getOwner(), post.getOwner().getDisease(), post)
        ).collect(Collectors.toList());
        this.numberOfElements = postList.getNumberOfElements();
        this.lastPage = postList.isLast();
        this.totalPages = postList.getTotalPages();
        this.totalElements = postList.getTotalElements();
        this.size = postList.getSize();
    }
}


