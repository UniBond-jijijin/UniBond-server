package com.unibond.unibond.letter_room.service;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.letter.repository.LetterRepository;
import com.unibond.unibond.letter_room.domain.LetterRoom;
import com.unibond.unibond.letter_room.dto.GetAllLetterRoomsResDto;
import com.unibond.unibond.letter_room.dto.GetLetterRoomDetailResDto;
import com.unibond.unibond.letter_room.repository.LetterRoomCustomRepository;
import com.unibond.unibond.letter_room.repository.LetterRoomRepository;
import com.unibond.unibond.letter_room.repository.repo_interface.LetterRoomPreviewRepoInterface;
import com.unibond.unibond.member.domain.Member;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.unibond.unibond.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class LetterRoomService {
    private final LoginInfoService loginInfoService;
    private final LetterRoomCustomRepository letterRoomCustomRepository;
    private final LetterRoomRepository letterRoomRepository;
    private final LetterRepository letterRepository;

    public GetLetterRoomDetailResDto getAllLetters(Long letterRoomId, Pageable pageable) throws BaseException {
        try {
            Long loginId = loginInfoService.getLoginMemberId();
            Page<Letter> letterPage = letterRepository
                    .findLettersByLetterRoomAndReceiverOrSender(letterRoomId, loginId, pageable);
            if (letterPage.getContent().isEmpty()) {
                throw new BaseException(INVALID_LETTER_ROOM_ID);
            }
            Member receiver = findAnotherParticipant(letterPage.getContent().get(0), loginId);
            return new GetLetterRoomDetailResDto(receiver, letterPage);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetAllLetterRoomsResDto getAllLetterRooms(Pageable pageable) throws BaseException {
        try {
            Long loginMemberId = loginInfoService.getLoginMemberId();
            Page<LetterRoomPreviewRepoInterface> letterRooms = letterRoomCustomRepository.findLetterRoomsByMember(loginMemberId, pageable);
            return new GetAllLetterRoomsResDto(loginMemberId, letterRooms);
        } catch (Exception e) {
            System.out.println(e);
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
