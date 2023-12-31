package com.unibond.unibond.letter_room.dto;

import com.unibond.unibond.common.PageInfo;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetAllLikedLetterResDto {
    private PageInfo pageInfo;
    private List<LikedLetterPreviewDto> likedLetterList;

    public GetAllLikedLetterResDto(Page<Letter> letterPage) {
        this.pageInfo = new PageInfo(letterPage);
        this.likedLetterList = letterPage.getContent().stream().map(
                LikedLetterPreviewDto::new
        ).collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class LikedLetterPreviewDto {
        private Long senderId;
        private String senderImg;
        private Long letterId;
        private Long letterRoomId;
        private LocalDateTime sentDate;
        private String letterTitle;
        private String senderName;

        public LikedLetterPreviewDto(Letter letter) {
            Member sender = letter.getSender();
            this.senderId = sender.getId();
            this.senderImg = sender.getProfileImage();
            this.letterId = letter.getId();
            this.letterRoomId = letter.getLetterRoom().getId();
            this.sentDate = letter.getCreatedDate();
            this.letterTitle = letter.getTitle();
            this.senderName = sender.getNickname();
        }
    }
}
