package com.unibond.unibond.member.service;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.disease.domain.Disease;
import com.unibond.unibond.disease.repository.DiseaseRepository;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.member.dto.MemberRegisterReqDto;
import com.unibond.unibond.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.unibond.unibond.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final DiseaseRepository diseaseRepository;

    @Transactional
    public BaseResponseStatus signupMember(MemberRegisterReqDto registerReqDto) throws BaseException {
        try {
            if (memberRepository.existsMemberByNickname(registerReqDto.getNickname())) {
                throw new BaseException(DUPLICATE_MEMBER_NICK);
            }

            Disease disease = diseaseRepository.findById(registerReqDto.getDiseaseId()).orElseThrow(
                    () -> new BaseException(INVALID_DISEASE_ID)
            );

            Member newMember = registerReqDto.toEntity(disease);
            memberRepository.save(newMember);
            return SUCCESS;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public BaseResponseStatus checkNickNameDuplicate(String nickname) throws BaseException {
        try {
            boolean isPresent = memberRepository.existsMemberByNickname(nickname);
            if (!isPresent) {
                return USABLE_NICK;
            } else {
                return UNUSABLE_NICK;
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}