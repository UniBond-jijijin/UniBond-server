package com.unibond.unibond.member.dto;

import com.unibond.unibond.member.domain.Gender;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberModifyReqDto {
    private String nickname;
    private Gender gender;
    private Long diseaseId;
    private LocalDate diagnosisTiming;
    private String bio;
    private List<String> interestList;

    @Builder
    public MemberModifyReqDto(String nickname, Gender gender, Long diseaseId, LocalDate diagnosisTiming, String bio, List<String> interestList) {
        this.nickname = nickname;
        this.gender = gender;
        this.diseaseId = diseaseId;
        this.diagnosisTiming = diagnosisTiming;
        this.bio = bio;
        this.interestList = interestList;
    }
}
