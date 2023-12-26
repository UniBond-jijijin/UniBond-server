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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diseaseId")
    private Disease disease;

    private LocalDate diagnosisTiming;

    @Column(length = 100)
    private String bio;

    @ElementCollection(fetch = LAZY)
    @CollectionTable(
            name = "Interest",
            joinColumns = @JoinColumn(name = "memberId")
    )
    @Column(name = "interest")
    private Set<String> interestSet = new HashSet<>();

    @Builder
    public Member(String profileImage, String nickname, Gender gender, Disease disease, LocalDate diagnosisTiming,
                  String bio, Set<String> interestSet) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.gender = gender;
        this.disease = disease;
        this.diagnosisTiming = diagnosisTiming;
        this.bio = bio;
        this.interestSet = interestSet;
    }

    public void modifyMember(MemberModifyReqDto reqDto, Disease disease, String profileImgUrl) {
        this.profileImage = propertyNullCheck(profileImgUrl, this.profileImage);
        this.nickname = propertyNullCheck(reqDto.getNickname(), this.nickname);
        this.gender = propertyNullCheck(reqDto.getGender(), this.gender);
        this.disease = propertyNullCheck(disease, this.disease);
        this.diagnosisTiming = propertyNullCheck(reqDto.getDiagnosisTiming(), this.diagnosisTiming);
        this.bio = propertyNullCheck(reqDto.getBio(), this.bio);
        modifyInterestSet(reqDto.getInterestList());
    }

    private <T> T propertyNullCheck(T property, T alternative) {
        if (property != null) {
            return property;
        }
        return alternative;
    }

    private void modifyInterestSet(List<String> interestList) {
        if (interestList != null) {
            this.interestSet = new HashSet<>(interestList);
        }
    }
}
