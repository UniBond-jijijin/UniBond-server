package com.unibond.unibond.alarm.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@Getter
public class Alarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member receiver;

    private String information;

    private Boolean isViewed;

    // prevent initiate
    protected Alarm() {
    }
}
