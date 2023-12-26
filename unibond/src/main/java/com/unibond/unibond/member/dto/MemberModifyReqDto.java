package com.unibond.unibond.member.dto;

import com.unibond.unibond.member.domain.Gender;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class MemberModifyReqDto {
    private String nickname;
    private Gender gender;
    private Long diseaseId;
    private LocalDate diagnosisTiming;
    private String bio;
    private List<String> interestList;
}
