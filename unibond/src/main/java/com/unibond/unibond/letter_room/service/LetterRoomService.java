package com.unibond.unibond.letter_room.service;

import com.unibond.unibond.block.repository.LetterRoomBlockRepository;
import com.unibond.unibond.block.repository.MemberBlockRepository;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.letter.repository.LetterRepository;
import com.unibond.unibond.letter_room.dto.GetAllLetterRoomsResDto;
import com.unibond.unibond.letter_room.dto.GetAllLikedLetterResDto;
import com.unibond.unibond.letter_room.dto.GetLetterRoomDetailResDto;
import com.unibond.unibond.letter_room.repository.LetterRoomCustomRepository;
import com.unibond.unibond.letter_room.repository.LetterRoomRepository;
import com.unibond.unibond.letter_room.repository.repo_interface.LetterRoomPreviewRepoInterface;
import com.unibond.unibond.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.unibond.unibond.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class LetterRoomService {
    private final LoginInfoService loginInfoService;
    private final LetterRoomRepository letterRoomRepository;
    private final LetterRoomCustomRepository letterRoomCustomRepository;
    private final LetterRepository letterRepository;

    private final MemberBlockRepository memberBlockRepository;
    private final LetterRoomBlockRepository letterRoomBlockRepository;

    public GetLetterRoomDetailResDto getAllLetters(Long letterRoomId, Pageable pageable) throws BaseException {
        try {
            Long loginId = loginInfoService.getLoginMemberId();
            Page<Letter> letterPage = letterRepository
                    .findLettersByLetterRoomAndReceiverOrSender(letterRoomId, loginId, pageable);
            checkLetterRoomBlocked(loginId, letterRoomId);

            if (letterPage.getContent().isEmpty()) {
                throw new BaseException(INVALID_LETTER_ROOM_ID);
            }
            Member receiver = findAnotherParticipant(letterPage.getContent().get(0), loginId);

            checkMemberBlocked(loginId, receiver.getId());
            return new GetLetterRoomDetailResDto(loginId, receiver, letterPage);
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

    public GetAllLikedLetterResDto getAllLikeLetters(Pageable pageable) throws BaseException {
        try {
            Long loginMemberId = loginInfoService.getLoginMemberId();
            Page<Letter> letterPage = letterRepository.findLikedLetterByReceiver(loginMemberId, pageable);
            return new GetAllLikedLetterResDto(letterPage);
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

    private void checkMemberBlocked(Long reporterId, Long respondentId) throws BaseException {
        Boolean isBlocked = memberBlockRepository.existsByReporterIdAndRespondentId(reporterId, respondentId);
        if (isBlocked) {
            throw new BaseException(BLOCKED_LETTER);
        }
    }

    private void checkLetterRoomBlocked(Long reporterId, Long reportedLetterRoomId) throws BaseException {
        Boolean isBlocked = letterRoomBlockRepository.existsByReporterIdAndReportedLetterRoomId(reporterId, reportedLetterRoomId);
        if (isBlocked) {
            throw new BaseException(BLOCKED_LETTER_ROOM);
        }
    }
}
