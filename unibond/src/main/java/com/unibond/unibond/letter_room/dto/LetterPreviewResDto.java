package com.unibond.unibond.letter_room.dto;

import com.unibond.unibond.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LetterPreviewResDto {
    public String senderName;
    public LocalDate sentDate;

    public LetterPreviewResDto(Letter letter) {
        this.senderName = letter.getSender().getNickname();
        this.sentDate = letter.getCreatedDate().toLocalDate();
    }
}
