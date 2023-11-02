package com.unibond.unibond.post.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ownerId")
    private Member owner;

    @Enumerated(STRING)
    private BoardType boardType;

    private String postImageUrl;

    private String content;

    @Builder(builderMethodName = "createExperiencePost")
    public Post(Member owner, BoardType boardType, String content) {
        this.owner = owner;
        this.boardType = boardType;
        this.content = content;
    }

    @Builder(builderMethodName = "createQnAPost")
    public Post(Member owner, BoardType boardType, String content, String postImageUrl) {
        this.owner = owner;
        this.boardType = boardType;
        this.content = content;
        this.postImageUrl = postImageUrl;
    }
}
