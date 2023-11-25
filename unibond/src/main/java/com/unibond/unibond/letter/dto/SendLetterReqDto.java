package com.unibond.unibond.letter.dto;

import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.letter_room.domain.LetterRoom;
import com.unibond.unibond.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendLetterReqDto {

    @NotNull(message = "수신자 아이디에는 null이 들어올 수 없습니다.")
    private Long receiverId;

    @NotNull(message = "편지 제목에는 null이 들어올 수 없습니다.")
    private String title;

    @NotNull(message = "편지 내용에는 null이 들어올 수 없습니다.")
    @Size(min = 50, message = "편지는 50자 이상이어야 합니다.")
    private String content;

    public Letter toEntity(LetterRoom letterRoom, Member sender, Member receiver) {
        return Letter.builder()
                .content(this.content)
                .title(this.title)
                .letterRoom(letterRoom)
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
