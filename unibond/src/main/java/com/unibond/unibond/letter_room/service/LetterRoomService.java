package com.unibond.unibond.letter_room.service;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.letter.repository.LetterRepository;
import com.unibond.unibond.letter_room.dto.GetLetterRoomDetailResDto;
import com.unibond.unibond.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.unibond.unibond.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class LetterRoomService {
    private final LetterRepository letterRepository;

    public GetLetterRoomDetailResDto getAllLetters(Long letterRoomId, Long loginId) throws BaseException {
        try {
            List<Letter> letterList = letterRepository
                    .findLettersByLetterRoomAndReceiverOrSender(letterRoomId, loginId);
            if (letterList.isEmpty()) {
                throw new BaseException(INVALID_LETTER_ROOM_ID);
            }
            Member receiver = findAnotherParticipant(letterList.get(0), loginId);
            return new GetLetterRoomDetailResDto(receiver, letterList);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private Member findAnotherParticipant(Letter letter, Long loginId) throws BaseException {
        if (letter.getSender().getId().equals(loginId)) {
            return letter.getReceiver();
        } else if (letter.getReceiver().getId().equals(loginId)) {
            return letter.getSender();
        }
        throw new BaseException(NOT_YOUR_LETTER_ROOM);
    }
}
