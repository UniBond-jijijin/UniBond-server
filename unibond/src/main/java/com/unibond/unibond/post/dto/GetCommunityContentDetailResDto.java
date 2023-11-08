package com.unibond.unibond.post.dto;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.comment.dto.GetParentCommentResDto;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCommunityContentDetailResDto {
    private String profileImage;
    private String postOwnerName;
    private Long postOwnerId;
    private LocalDateTime createdDate;
    private String diseaseName;

    private String postImg;
    private String content;

    private int commentCount;
    private List<GetParentCommentResDto> parentCommentList;

    @Builder
    public GetCommunityContentDetailResDto(Member postOwner, Post post, int commentCount, List<Comment> commentList) {
        // no additional queries are issued because of the fetch join (with member, disease).
        this.profileImage = postOwner.getProfileImage();
        this.postOwnerName = postOwner.getNickname();
        this.postOwnerId = postOwner.getId();
        this.createdDate = post.getCreatedDate();
        this.diseaseName = postOwner.getDisease().getDiseaseNameKor();

        this.postImg = post.getPostImageUrl();
        this.content = post.getContent();

        this.commentCount = commentCount;
        this.parentCommentList = GetParentCommentResDto.getParentCommentResDtoList(commentList);
    }
}
