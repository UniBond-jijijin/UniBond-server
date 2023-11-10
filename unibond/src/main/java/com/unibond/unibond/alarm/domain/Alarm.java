package com.unibond.unibond.alarm.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.SINGLE_TABLE;
import static lombok.AccessLevel.*;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Alarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId")
    private Member receiver;

    private String information;

    private Boolean isViewed;
}
