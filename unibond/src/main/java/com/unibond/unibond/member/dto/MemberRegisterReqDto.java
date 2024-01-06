package com.unibond.unibond.member.dto;

import com.unibond.unibond.disease.domain.Disease;
import com.unibond.unibond.member.domain.Gender;
import com.unibond.unibond.member.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberRegisterReqDto {
    private Long diseaseId;
    private LocalDate diseaseTiming;
    private Gender gender;
    private String nickname;
    private String bio;
    private List<String> interestList;

    public Member toEntity(Disease disease) {
        HashSet<String> interestSet = new HashSet<>(interestList);
        return Member.builder()
                .disease(disease)
                .diagnosisTiming(this.diseaseTiming)
                .gender(gender)
                .nickname(this.nickname)
                .bio(this.bio)
                .interestSet(interestSet)
                .build();
    }

    public Member toEntity(Disease disease, String imgUrl) {
        HashSet<String> interestSet = new HashSet<>(interestList);
        return Member.builder()
                .profileImage(imgUrl)
                .disease(disease)
                .diagnosisTiming(this.diseaseTiming)
                .gender(gender)
                .nickname(this.nickname)
                .bio(this.bio)
                .interestSet(interestSet)
                .build();
    }
}
