package com.unibond.unibond.letter.service;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.letter.domain.Letter;
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

import static com.unibond.unibond.common.BaseResponseStatus.DATABASE_ERROR;
import static com.unibond.unibond.common.BaseResponseStatus.INVALID_MEMBER_ID;

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
            Member sender = loginInfoService.getLoginMember();
            Member receiver = getReceiver(reqDto.receiverId);

            LetterRoom letterRoom = findLetterRoom(sender, receiver);

            Letter letter = reqDto.toEntity(letterRoom, sender, receiver);
            Letter savedLetter = letterRepository.save(letter);

            return new SendLetterResDto(savedLetter.getArrivalDate());
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private Member getReceiver(Long receiverId) throws BaseException {
        return memberRepository.findById(receiverId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));
    }

    private LetterRoom findLetterRoom(Member sender, Member receiver) {
        return letterRoomRepository.findLetterRoomBy2Member(sender, receiver).orElseGet(
                () -> new LetterRoom(sender, receiver)
        );
    }
}
