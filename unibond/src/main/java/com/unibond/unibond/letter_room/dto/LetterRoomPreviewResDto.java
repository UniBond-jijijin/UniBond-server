package com.unibond.unibond.letter_room.dto;

import com.unibond.unibond.letter_room.repository.repo_interface.LetterRoomPreviewRepoInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LetterRoomPreviewResDto {
    private String senderProfileImg;
    private String senderNick;
    private Long senderId;
    private LocalDateTime recentLetterSentDate;
    private Long letterRoomId;

    public LetterRoomPreviewResDto(Long loginId, LetterRoomPreviewRepoInterface letterRoomPreview) {
        getSender(loginId, letterRoomPreview);
        this.recentLetterSentDate = letterRoomPreview.getRecentLetterCreatedDate();
        this.letterRoomId = letterRoomPreview.getLetterRoomId();
    }

    private void getSender(Long loginId, LetterRoomPreviewRepoInterface letterRoomPreview) {
        if (letterRoomPreview.getMember1Id().equals(loginId)) {
            this.senderId = letterRoomPreview.getMember2Id();
            this.senderNick = letterRoomPreview.getMember2Nickname();
            this.senderProfileImg = letterRoomPreview.getMember2ProfileImg();
        } else {
            this.senderId = letterRoomPreview.getMember1Id();
            this.senderNick = letterRoomPreview.getMember1Nickname();
            this.senderProfileImg = letterRoomPreview.getMember1ProfileImg();
        }
    }
}
