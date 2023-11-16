package com.unibond.unibond.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.comment.dto.ParentCommentResDto;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class GetCommunityContentDetailResDto {
    private String loginMemberProfileImage;

    private String profileImage;
    private String postOwnerName;
    private Long postOwnerId;
    private LocalDateTime createdDate;
    private String diseaseName;

    private String postImg;
    private String content;

    private int commentCount;
    private List<ParentCommentResDto> parentCommentList;

    @Builder
    public GetCommunityContentDetailResDto(Member loginMember, Member postOwner, Post post, int commentCount, List<Comment> commentList) {
        this.loginMemberProfileImage = loginMember.getProfileImage();

        this.profileImage = postOwner.getProfileImage();
        this.postOwnerName = postOwner.getNickname();
        this.postOwnerId = postOwner.getId();
        this.createdDate = post.getCreatedDate();
        this.diseaseName = postOwner.getDisease().getDiseaseNameKor();

        this.postImg = post.getPostImageUrl();
        this.content = post.getContent();

        this.commentCount = commentCount;
        this.parentCommentList = ParentCommentResDto.getParentCommentResDtoList(commentList);
    }
}
