package com.unibond.unibond.letter.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.letter_room.domain.LetterRoom;
import com.unibond.unibond.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "letterRoomId")
    private LetterRoom letterRoom;

    @Setter
    private Boolean liked;

    private String title;

    @Column(length = 200)
    private String content;

    @Enumerated(STRING)
    @Setter
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'SENDING'")
    private LetterStatus letterStatus;

    @Builder
    public Letter(Member receiver, Member sender, LetterRoom letterRoom, String content, String title) {
        this.receiver = receiver;
        this.sender = sender;
        this.setLiked(false);

        // 양방향
        this.letterRoom = letterRoom;
        this.letterRoom.addLetter(this);

        this.title = title;
        this.content = content;
    }

    public LocalDateTime getArrivalDate() {
        return this.getCreatedDate().plusHours(1);
    }
}
