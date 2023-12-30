package com.unibond.unibond.letter_room.dto;

import com.unibond.unibond.common.PageInfo;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private PageInfo pageInfo;
    private List<LetterPreviewResDto> letterList;

    public GetLetterRoomDetailResDto(Member receiver, Page<Letter> letterPage) {
        this.receiverProfileImg = receiver.getProfileImage();
        this.receiverName = receiver.getNickname();
        this.receiverDiseaseName = receiver.getDisease().getDiseaseNameKor();
        this.receiverDiagnosisTiming = receiver.getDiagnosisTiming();
        this.pageInfo = new PageInfo(letterPage);
        this.letterList = letterPage.getContent().stream().map(
                LetterPreviewResDto::new
        ).collect(Collectors.toList());
    }
}
