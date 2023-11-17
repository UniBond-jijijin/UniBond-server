package com.unibond.unibond.letter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unibond.unibond.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class LetterDetailResDto {
    private LocalDateTime sendDate;
    private LocalDateTime arrivalDate; // only sent letter
    private Boolean liked; // only received letter
    private String content;

    public static LetterDetailResDto getReceivedLetter(Letter letter) {
        LetterDetailResDto resDto = new LetterDetailResDto();
        resDto.setSendDate(letter.getCreatedDate());
        resDto.setLiked(letter.getLiked());
        resDto.setContent(letter.getContent());

        return resDto;
    }

    public static LetterDetailResDto getSentLetter(Letter letter) {
        LetterDetailResDto resDto = new LetterDetailResDto();
        resDto.setSendDate(letter.getCreatedDate());
        resDto.setArrivalDate(letter.getArrivalDate());
        resDto.setContent(letter.getContent());

        return resDto;
    }
}
