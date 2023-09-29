package com.unibond.unibond.letter.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.letter_room.domain.LetterRoom;
import com.unibond.unibond.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Letter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "receiverId")
    private Member receiver;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "senderId")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "letterRoomId")
    private LetterRoom letterRoom;

    private Boolean isLiked;

    @Column(length = 200)
    private String content;
}
