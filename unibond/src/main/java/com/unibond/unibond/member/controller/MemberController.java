package com.unibond.unibond.member.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.member.dto.MemberRegisterReqDto;
import com.unibond.unibond.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // TODO: 병명 검색

    // TODO: 닉네임 중복 체크
}
