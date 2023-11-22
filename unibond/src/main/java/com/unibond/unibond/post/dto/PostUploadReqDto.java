package com.unibond.unibond.post.dto;

import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.BoardType;
import com.unibond.unibond.post.domain.Post;
import lombok.Data;

import static com.unibond.unibond.post.domain.BoardType.*;

@Data
public class PostUploadReqDto {
    // TODO: IMAGE UPLOAD
    private String content;

    // null
    private Member owner;
    private BoardType boardType;

    public Post toEntity(Member owner) {
        this.owner = owner;
        if (boardType.equals(QNA)) {
            return createQNAPost();
        } else {
            return createExperiencePost();
        }
    }

    private Post createQNAPost() {
        return Post.createQnAPost()
                .owner(owner)
                .boardType(boardType)
                .content(content)
                .build();
    }

    private Post createExperiencePost() {
        // TODO: 이미지 업로드 구현 뒤 호출 메소드 변경 필요
        return Post.createQnAPost()
                .owner(owner)
                .boardType(boardType)
                .content(content)
                .build();
    }
}
