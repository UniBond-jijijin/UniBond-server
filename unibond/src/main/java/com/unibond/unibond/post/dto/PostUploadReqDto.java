package com.unibond.unibond.post.dto;

import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.BoardType;
import com.unibond.unibond.post.domain.Post;
import lombok.Data;

import static com.unibond.unibond.post.domain.BoardType.QNA;

@Data
public class PostUploadReqDto {
    private String content;

    // null
    private Member owner;
    private BoardType boardType;

    public Post toEntity(Member owner, String postImgUrl) {
        this.owner = owner;
        if (boardType.equals(QNA)) {
            return createQNAPost();
        } else {
            return createExperiencePost(postImgUrl);
        }
    }

    private Post createQNAPost() {
        return Post.createQnAPost()
                .owner(owner)
                .boardType(boardType)
                .content(content)
                .build();
    }

    private Post createExperiencePost(String postImgUrl) {
        return Post.createExperiencePost()
                .owner(owner)
                .boardType(boardType)
                .content(content)
                .postImageUrl(postImgUrl)
                .build();
    }
}
