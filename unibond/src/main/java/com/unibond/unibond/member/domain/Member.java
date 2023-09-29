package com.unibond.unibond.member.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.disease.domain.Disease;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String profileImage;

    @Column(length = 10)
    private String nickname;

    private String gender;

    @OneToOne
    @JoinColumn(name = "diseaseId")
    private Disease disease;

    private LocalDateTime diagnosisTiming;

    @Column(length = 100)
    private String bio;
}
