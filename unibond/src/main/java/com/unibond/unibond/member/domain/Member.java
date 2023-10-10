package com.unibond.unibond.member.domain;

import com.unibond.unibond.common.BaseEntity;
import com.unibond.unibond.disease.domain.Disease;
import com.unibond.unibond.member.dto.MemberModifyReqDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String profileImage;

    @Column(length = 10)
    private String nickname;

    @Enumerated(STRING)
    @ColumnDefault("NULL")
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "diseaseId")
    private Disease disease;

    private LocalDate diagnosisTiming;

    @Column(length = 100)
    private String bio;

    @Builder
    public Member(String profileImage, String nickname, Gender gender, Disease disease, LocalDate diagnosisTiming, String bio) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.gender = gender;
        this.disease = disease;
        this.diagnosisTiming = diagnosisTiming;
        this.bio = bio;
    }

    public void modifyMember(MemberModifyReqDto reqDto, Disease disease) {
        this.profileImage = reqDto.getProfileImage();
        this.nickname = reqDto.getNickname();
        this.gender = reqDto.getGender();
        this.disease = disease;
        this.diagnosisTiming = reqDto.getDiagnosisTiming();
        this.bio = reqDto.getBio();
    }
}
