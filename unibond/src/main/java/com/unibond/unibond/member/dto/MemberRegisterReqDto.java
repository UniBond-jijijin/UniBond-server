package com.unibond.unibond.member.dto;

import com.unibond.unibond.disease.domain.Disease;
import com.unibond.unibond.member.domain.Gender;
import com.unibond.unibond.member.domain.Member;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberRegisterReqDto {
    private String profileImage;
    private Long diseaseId;
    private LocalDate diseaseTiming;
    private Gender gender;
    private String nickname;
    private String bio;

    public Member toEntity(Disease disease) {
        return Member.builder()
                .profileImage(this.profileImage)
                .disease(disease)
                .diagnosisTiming(this.diseaseTiming)
                .gender(gender)
                .nickname(this.nickname)
                .bio(this.bio)
                .build();
    }
}
