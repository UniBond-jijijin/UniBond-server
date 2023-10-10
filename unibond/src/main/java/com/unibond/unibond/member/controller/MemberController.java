package com.unibond.unibond.member.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.member.dto.MemberModifyReqDto;
import com.unibond.unibond.member.dto.MemberRegisterReqDto;
import com.unibond.unibond.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.unibond.unibond.common.BaseResponseStatus.NOT_YOUR_PROFILE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("")
    public BaseResponse<?> signup(@RequestBody MemberRegisterReqDto registerReqDto) {
        try {
            return new BaseResponse<>(memberService.signupMember(registerReqDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/duplicate")
    public BaseResponse<?> checkNickDuplicate(@RequestParam("nickname") String nickname) {
        try {
            return new BaseResponse<>(memberService.checkNickNameDuplicate(nickname));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/{memberId}")
    public BaseResponse<?> modifyMemberInfo(@PathVariable("memberId") Long memberId,
                                            @RequestBody MemberModifyReqDto reqDto,
                                            @RequestHeader("Authorization") Long loginId) {
        try {
            if (!memberId.equals(loginId)) {
                throw new BaseException(NOT_YOUR_PROFILE);
            }
            return new BaseResponse<>(memberService.modifyMemberInfo(reqDto, loginId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
