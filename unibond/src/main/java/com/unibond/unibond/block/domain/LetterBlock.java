package com.unibond.unibond.block.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class LetterBlock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reporterId")
    private Member reporter;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reportedLetterId")
    private Letter reportedLetter;

    @Builder
    public LetterBlock(Member reporter, Letter reportedLetter) {
        this.reporter = reporter;
        this.reportedLetter = reportedLetter;
    }
}
