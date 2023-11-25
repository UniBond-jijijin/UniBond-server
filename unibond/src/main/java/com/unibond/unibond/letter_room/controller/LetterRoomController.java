package com.unibond.unibond.letter_room.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.letter_room.service.LetterRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/letter-rooms")
public class LetterRoomController {
    private final LetterRoomService letterRoomService;

    @GetMapping("/{letterRoomId}")
    public BaseResponse<?> getAllLetters(@PathVariable("letterRoomId") Long letterRoomId,
                                         @RequestHeader("Authorization") Long loginId) {
        try {
            return new BaseResponse<>(letterRoomService.getAllLetters(letterRoomId, loginId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
