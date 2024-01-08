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
import org.springframework.web.multipart.MultipartFile;

import static com.unibond.unibond.common.BaseResponseStatus.NOT_YOUR_PROFILE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public BaseResponse<?> signup(@RequestBody MemberRegisterReqDto request) {
        try {
            return new BaseResponse<>(memberService.signupWithNoProfileImg(request));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping(value = "/api/v2/members", consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<?> signup(@RequestPart MemberRegisterReqDto request,
                                  @RequestPart MultipartFile profileImg) {
        try {
            return new BaseResponse<>(memberService.signupMember(request, profileImg));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/api/v1/members/duplicate")
    public BaseResponse<?> checkNickDuplicate(@RequestParam("nickname") String nickname) {
        try {
            return new BaseResponse<>(memberService.checkNickNameDuplicate(nickname));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/api/v1/members/{memberId}")
    public BaseResponse<?> getMemberDetail(@PathVariable("memberId") Long memberId,
                                           @RequestHeader("Authorization") Long loginId,
                                           @PageableDefault(size = 30) Pageable pageable) {
        try {
            return new BaseResponse<>(memberService.getMemberInfo(memberId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @DeleteMapping("/api/v1/members")
    public BaseResponse<?> deleteMember(@RequestHeader("Authorization") Long loginId) {
        try {
            return new BaseResponse<>(memberService.deleteMember());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/api/v1/members/{memberId}")
    public BaseResponse<?> modifyMemberInfo(@PathVariable("memberId") Long memberId,
                                            @RequestBody(required = false) MemberModifyReqDto request,
                                            @RequestHeader("Authorization") Long loginId) {
        try {
            if (!memberId.equals(loginId)) {
                throw new BaseException(NOT_YOUR_PROFILE);
            }
            return new BaseResponse<>(memberService.modifyMemberInfoWithoutProfileImg(request));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping(value = "/api/v2/members/{memberId}", consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<?> modifyMemberInfo(@PathVariable("memberId") Long memberId,
                                            @RequestPart(required = false) MemberModifyReqDto request,
                                            @RequestPart(required = false) MultipartFile profileImg,
                                            @RequestHeader("Authorization") Long loginId) {
        try {
            if (!memberId.equals(loginId)) {
                throw new BaseException(NOT_YOUR_PROFILE);
            }
            return new BaseResponse<>(memberService.modifyMemberInfo(request, profileImg));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
