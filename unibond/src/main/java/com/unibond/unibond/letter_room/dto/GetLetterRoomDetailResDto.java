package com.unibond.unibond.letter_room.dto;

import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLetterRoomDetailResDto {
    private String receiverProfileImg;
    private String receiverName;
    private String receiverDiseaseName;
    private LocalDate receiverDiagnosisTiming;

    private List<LetterPreviewResDto> letterList;

    public GetLetterRoomDetailResDto(Member receiver, List<Letter> letterList) {
        this.receiverProfileImg = receiver.getProfileImage();
        this.receiverName = receiver.getNickname();
        this.receiverDiseaseName = receiver.getDisease().getDiseaseNameKor();
        this.receiverDiagnosisTiming = receiver.getDiagnosisTiming();
        this.letterList = letterList.stream().map(
                LetterPreviewResDto::new
        ).collect(Collectors.toList());
    }
}
