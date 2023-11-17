package com.unibond.unibond.letter.dto;

import com.unibond.unibond.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LetterLikeResDto {
    private Long letterId;
    private Boolean liked;

    public LetterLikeResDto(Letter letter) {
        letterId = letter.getId();
        liked = letter.getLiked();
    }
}
