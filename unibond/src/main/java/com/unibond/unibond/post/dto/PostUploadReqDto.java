package com.unibond.unibond.post.dto;

import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.BoardType;
import com.unibond.unibond.post.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.unibond.unibond.post.domain.BoardType.EXPERIENCE;
import static com.unibond.unibond.post.domain.BoardType.QNA;

@Data
@NoArgsConstructor
public class PostUploadReqDto {
    private String content;

    public Post toEntity(Member owner, BoardType boardType, String postImgUrl) {
        if (boardType.equals(QNA)) {
            return createQNAPost(owner);
        } else {
            return createExperiencePost(owner, postImgUrl);
        }
    }

    private Post createQNAPost(Member owner) {
        return Post.createQnAPost()
                .owner(owner)
                .boardType(QNA)
                .content(content)
                .build();
    }

    private Post createExperiencePost(Member owner, String postImgUrl) {
        return Post.createExperiencePost()
                .owner(owner)
                .boardType(EXPERIENCE)
                .content(content)
                .postImageUrl(postImgUrl)
                .build();
    }
}
