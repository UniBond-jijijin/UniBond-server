package com.unibond.unibond.common.service;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.unibond.unibond.common.BaseEntityStatus.DELETED;
import static com.unibond.unibond.common.BaseResponseStatus.DELETED_MEMBER;
import static com.unibond.unibond.common.BaseResponseStatus.INVALID_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class LoginInfoService {
    private final MemberRepository memberRepository;

    public Long getLoginMemberId() {
        HttpServletRequest httpServletRequest
                = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return Long.parseLong(httpServletRequest.getHeader("Authorization"));
    }

    public Member getLoginMember() throws BaseException {
        HttpServletRequest httpServletRequest
                = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Long loginId = Long.parseLong(httpServletRequest.getHeader("Authorization"));

        Member member
                = memberRepository.findById(loginId).orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));
        if (member.getStatus().equals(DELETED)) {
            throw new BaseException(DELETED_MEMBER);
        }
        return member;
    }

    public Member getLoginMemberFetchJoinDisease() throws BaseException {
        HttpServletRequest httpServletRequest
                = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Long loginId = Long.parseLong(httpServletRequest.getHeader("Authorization"));

        return memberRepository.findMemberByIdFetchJoinDisease(loginId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));
    }
}
