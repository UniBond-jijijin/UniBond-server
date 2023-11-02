package com.unibond.unibond.post.dto;

import com.unibond.unibond.disease.domain.Disease;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostPreviewDto {
    // TODO: 이미지 URL 추후 업데이트 필요
    private LocalDateTime createdDate;
    private String ownerProfileImg;
    private String ownerNick;
    private String disease;
    private String contentPreview;
    private Boolean isEnd;

    @Builder
    public PostPreviewDto(Member member, Disease disease, Post post) {
        final int MAX_PREVIEW_LIMIT = 45;

        this.createdDate = post.getCreatedDate();
        this.ownerProfileImg = member.getProfileImage();
        this.ownerNick = member.getNickname();
        this.disease = disease.getDiseaseNameKor();
        this.contentPreview = post.getContent().substring(0, MAX_PREVIEW_LIMIT);
        this.isEnd = post.getContent().length() <= MAX_PREVIEW_LIMIT;
    }
}
