package com.unibond.unibond.letter.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.letter_room.domain.LetterRoom;
import com.unibond.unibond.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
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

    @Builder
    public Letter(Member receiver, Member sender, LetterRoom letterRoom, String content) {
        this.receiver = receiver;
        this.sender = sender;
        this.letterRoom = letterRoom;
        this.letterRoom.addLetter(this);
        this.isLiked = false;
        this.content = content;
    }

    public LocalDateTime getArrivalDate() {
        return this.getCreatedDate().plusHours(1);
    }
}
