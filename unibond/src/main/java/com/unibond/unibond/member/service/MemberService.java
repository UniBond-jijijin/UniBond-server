package com.unibond.unibond.member.service;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.disease.domain.Disease;
import com.unibond.unibond.disease.repository.DiseaseRepository;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.member.dto.MemberDetailResDto;
import com.unibond.unibond.member.dto.MemberModifyReqDto;
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
    public Long signupMember(MemberRegisterReqDto registerReqDto) throws BaseException {
        try {
            if (memberRepository.existsMemberByNickname(registerReqDto.getNickname())) {
                throw new BaseException(DUPLICATE_MEMBER_NICK);
            }

            Disease disease = diseaseRepository.findById(registerReqDto.getDiseaseId()).orElseThrow(
                    () -> new BaseException(INVALID_DISEASE_ID)
            );

            Member newMember = registerReqDto.toEntity(disease);
            Member savedMember = memberRepository.save(newMember);
            return savedMember.getId();
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
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

    @Transactional
    public BaseResponseStatus modifyMemberInfo(MemberModifyReqDto reqDto, Long loginId) throws BaseException {
        try {
            Member member = memberRepository.findById(loginId).orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));

            Disease disease = null;
            if (reqDto.getDiseaseId() != null) {
                disease = diseaseRepository.findById(reqDto.getDiseaseId())
                        .orElseThrow(() -> new BaseException(INVALID_DISEASE_ID));
            }
            member.modifyMember(reqDto, disease);
            return SUCCESS;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public MemberDetailResDto getMemberInfo(Long memberId) throws BaseException {
        try {
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));
            return new MemberDetailResDto(member);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}