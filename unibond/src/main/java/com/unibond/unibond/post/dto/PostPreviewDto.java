package com.unibond.unibond.post.dto;

import com.unibond.unibond.disease.domain.Disease;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.BoardType;
import com.unibond.unibond.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPreviewDto {
    private LocalDateTime createdDate;
    private Long ownerId;
    private String ownerProfileImg;
    private String ownerNick;
    private String disease;
    private Long postId;
    private String postImg;
    private String contentPreview;
    private BoardType boardType;
    private Boolean isEnd;

    @Builder
    public PostPreviewDto(Member member, Disease disease, Post post) {
        final int MAX_PREVIEW_LIMIT = 45;
        this.createdDate = post.getCreatedDate();
        this.ownerId = member.getId();
        this.ownerProfileImg = member.getProfileImage();
        this.ownerNick = member.getNickname();
        this.disease = disease.getDiseaseNameKor();
        this.postId = post.getId();
        this.postImg = post.getPostImageUrl();
        this.contentPreview = post.getContent();
        this.isEnd = true;
        this.boardType = post.getBoardType();
        if (post.getContent().length() >= 45) {
            this.contentPreview = post.getContent().substring(0, MAX_PREVIEW_LIMIT);
            this.isEnd = false;
        }
    }
}
