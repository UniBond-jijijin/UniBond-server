package com.unibond.unibond.member.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.member.dto.MemberRegisterReqDto;
import com.unibond.unibond.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("")
    public BaseResponse signup(@RequestBody MemberRegisterReqDto registerReqDto) {
        try {
            return new BaseResponse(memberService.signupMember(registerReqDto));
        } catch (BaseException e) {
            return new BaseResponse(e.getStatus());
        }
    }

    // TODO: 닉네임 중복 체크
}
