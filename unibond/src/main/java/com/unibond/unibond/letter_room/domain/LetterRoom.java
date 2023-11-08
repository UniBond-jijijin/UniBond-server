package com.unibond.unibond.letter_room.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class LetterRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId1")
    private Member member1;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId2")
    private Member member2;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "letterRoom")
    private List<Letter> letterList = new ArrayList<>();

}
