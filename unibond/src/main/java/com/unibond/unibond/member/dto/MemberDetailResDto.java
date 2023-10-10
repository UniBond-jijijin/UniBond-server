package com.unibond.unibond.member.dto;

import com.unibond.unibond.member.domain.Gender;
import com.unibond.unibond.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailResDto {

    private String profileImage;

    private String nickname;

    private Gender gender;

    private Long diseaseId;

    private String diseaseName;

    private LocalDate diagnosisTiming;

    private String bio;

    public MemberDetailResDto(Member member) {
        this.profileImage = member.getProfileImage();
        this.nickname = member.getNickname();
        this.gender = member.getGender();
        // TODO: Disease 조인 리팩토링
        this.diseaseId = member.getDisease().getId();
        this.diseaseName = member.getDisease().getDiseaseNameKor();
        this.diagnosisTiming = member.getDiagnosisTiming();
        this.bio = member.getBio();
    }
}
