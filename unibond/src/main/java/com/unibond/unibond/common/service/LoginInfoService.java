package com.unibond.unibond.common.service;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.unibond.unibond.common.BaseResponseStatus.INVALID_MEMBER_ID;

@Service
@RequiredArgsConstructor
public class LoginInfoService {
    private final MemberRepository memberRepository;

    public Member getLoginMember() throws BaseException {
        HttpServletRequest httpServletRequest
                = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Long loginId = Long.parseLong(httpServletRequest.getHeader("Authorization"));

        return memberRepository.findById(loginId).orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));
    }
}
