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
        private String senderImg;
        private LocalDateTime sentDate;
        private String letterTitle;
        private String senderNickname;

        public LikedLetterPreviewDto(Letter letter) {
            Member sender = letter.getSender();
            this.senderImg = sender.getProfileImage();
            this.sentDate = letter.getCreatedDate();
            this.letterTitle = letter.getTitle();
            this.senderNickname = sender.getNickname();
        }
    }
}
