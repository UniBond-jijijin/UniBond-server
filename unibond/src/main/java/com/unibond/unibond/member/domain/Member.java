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
import java.util.Optional;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
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

    @OneToOne(fetch = FetchType.LAZY)
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
        this.profileImage = propertyNullCheck(reqDto.getProfileImage(), this.profileImage);
        this.nickname = propertyNullCheck(reqDto.getNickname(), this.nickname);
        this.gender = propertyNullCheck(reqDto.getGender(), this.gender);
        this.disease = propertyNullCheck(disease, this.disease);
        this.diagnosisTiming = propertyNullCheck(reqDto.getDiagnosisTiming(), this.diagnosisTiming);
        this.bio = propertyNullCheck(reqDto.getBio(), this.bio);
    }

    private <T> T propertyNullCheck(T property, T alternative) {
        if (property != null) {
            return property;
        }
        return alternative;
    }
}
