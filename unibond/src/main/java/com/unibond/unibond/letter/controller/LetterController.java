package com.unibond.unibond.letter.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.letter.dto.SendLetterReqDto;
import com.unibond.unibond.letter.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/letter")
public class LetterController {
    private final LetterService letterService;

    @PostMapping("")
    public BaseResponse<?> sendLetter(@RequestHeader("Authorization") Long loginId,
                                      @RequestBody SendLetterReqDto reqDto) {
        try {
            return new BaseResponse<>(letterService.sendLetter(reqDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
