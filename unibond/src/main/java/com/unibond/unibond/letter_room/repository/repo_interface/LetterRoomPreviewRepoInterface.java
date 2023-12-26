package com.unibond.unibond.letter_room.repository.repo_interface;

import java.time.LocalDateTime;

public interface LetterRoomPreviewRepoInterface {
    Long getMember1Id();

    String getMember1ProfileImg();

    String getMember1Nickname();

    Long getMember2Id();

    String getMember2ProfileImg();

    String getMember2Nickname();

    LocalDateTime getRecentLetterCreatedDate();

    Long getLetterRoomId();
}
