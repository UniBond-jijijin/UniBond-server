package com.unibond.unibond.member.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.member.dto.MemberModifyReqDto;
import com.unibond.unibond.member.dto.MemberRegisterReqDto;
import com.unibond.unibond.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static com.unibond.unibond.common.BaseResponseStatus.NOT_YOUR_PROFILE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping(value = "", consumes = {MULTIPART_FORM_DATA_VALUE})
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

    @GetMapping("/{memberId}")
    public BaseResponse<?> getMemberDetail(@PathVariable("memberId") Long memberId,
                                           @RequestHeader("Authorization") Long loginId,
                                           @PageableDefault(size = 30) Pageable pageable) {
        try {
            return new BaseResponse<>(memberService.getMemberInfo(memberId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping(value = "/{memberId}", consumes = {MULTIPART_FORM_DATA_VALUE})
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
