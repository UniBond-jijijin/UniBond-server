package com.unibond.unibond.letter_room.dto;

import com.unibond.unibond.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LetterPreviewResDto {
    private Long letterId;
    private Long senderId;
    private String senderName;
    private LocalDateTime sentDate;
    private String letterTitle;

    public LetterPreviewResDto(Letter letter) {
        this.letterId = letter.getId();
        this.senderId = letter.getSender().getId();
        this.senderName = letter.getSender().getNickname();
        this.sentDate = letter.getCreatedDate();
        this.letterTitle = letter.getTitle();
    }
}
