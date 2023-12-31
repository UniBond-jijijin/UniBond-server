package com.unibond.unibond.letter.service;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.letter.dto.LetterDetailResDto;
import com.unibond.unibond.letter.dto.LetterLikeResDto;
import com.unibond.unibond.letter.dto.SendLetterReqDto;
import com.unibond.unibond.letter.dto.SendLetterResDto;
import com.unibond.unibond.letter.repository.LetterRepository;
import com.unibond.unibond.letter_room.domain.LetterRoom;
import com.unibond.unibond.letter_room.repository.LetterRoomRepository;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.unibond.unibond.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final MemberRepository memberRepository;
    private final LetterRepository letterRepository;
    private final LetterRoomRepository letterRoomRepository;

    private final LoginInfoService loginInfoService;

    @Transactional
    public SendLetterResDto sendLetter(SendLetterReqDto reqDto) throws BaseException {
        try {
            checkLetterLength(reqDto);
            checkSendingCapability(loginInfoService.getLoginMemberId(), reqDto.getReceiverId());

            Member sender = loginInfoService.getLoginMember();
            Member receiver = getReceiver(reqDto.getReceiverId());

            LetterRoom letterRoom = findLetterRoom(sender, receiver);
            Letter letter = reqDto.toEntity(letterRoom, sender, receiver);
            Letter savedLetter = letterRepository.save(letter);

            return new SendLetterResDto(savedLetter.getArrivalDate());
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public LetterLikeResDto likeLetter(Long letterId) throws BaseException {
        try {
            Member loginMember = loginInfoService.getLoginMember();
            Letter letter = findLetterByIdAndReceiver(loginMember, letterId);
            letter.checkIsArrived();
            letter.setLiked(!letter.getLiked());
            return new LetterLikeResDto(letter);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public LetterDetailResDto getLetterDetail(Long letterId) throws BaseException {
        try {
            Letter letter = letterRepository.findById(letterId).orElseThrow(() -> new BaseException(INVALID_LETTER_ID));
            Long loginMemberId = loginInfoService.getLoginMemberId();

            if (letter.isReceiver(loginMemberId)) {
                letter.checkIsArrived();
                return LetterDetailResDto.getReceivedLetter(letter);
            } else if (letter.isSender(loginMemberId)) {
                return LetterDetailResDto.getSentLetter(letter);
            }
            throw new BaseException(NOT_YOUR_LETTER);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private Member getReceiver(Long receiverId) throws BaseException {
        return memberRepository.findById(receiverId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));
    }

    private Letter findLetterByIdAndReceiver(Member receiver, Long id) throws BaseException {
        return letterRepository.findByReceiverAndLetterId(receiver, id)
                .orElseThrow(() -> new BaseException(INVALID_LETTER_ID));
    }

    private LetterRoom findLetterRoom(Member sender, Member receiver) {
        Optional<LetterRoom> letterRoom = letterRoomRepository.findLetterRoomBy2Member(sender, receiver);
        if (letterRoom.isPresent()) {
            return letterRoom.get();
        } else {
            LetterRoom newLetterRoom = new LetterRoom(sender, receiver);
            letterRoomRepository.save(newLetterRoom);
            return newLetterRoom;
        }
    }

    private void checkLetterLength(SendLetterReqDto reqDto) throws BaseException {
        if (reqDto.getContent().length() < 50) {
            throw new BaseException(BaseResponseStatus.NOT_ENOUGH_CHARS);
        }
    }

    private void checkSendingCapability(Long senderId, Long receiverId) throws BaseException {
        System.out.println(LocalDateTime.now().minusHours(1L));
        Boolean result = letterRepository.hasSentLetterToSamePersonWithinHour(senderId, receiverId, LocalDateTime.now().minusHours(1L));
        if (result) {
            throw new BaseException(CANT_SEND_LETTER);
        }
    }
}
