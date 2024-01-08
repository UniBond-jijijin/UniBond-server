package com.unibond.unibond.letter_room.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.letter_room.service.LetterRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/letter-rooms")
public class LetterRoomController {
    private final LetterRoomService letterRoomService;

    @GetMapping("")
    public BaseResponse<?> getAllLetterRooms(@RequestHeader("Authorization") Long loginId,
                                             @PageableDefault(size = 30) Pageable pageable) {
        try {
            return new BaseResponse<>(letterRoomService.getAllLetterRooms(pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/like")
    public BaseResponse<?> getAllLikedLetters(@RequestHeader("Authorization") Long loginId,
                                              @PageableDefault(size = 30) Pageable pageable) {
        try {
            return new BaseResponse<>(letterRoomService.getAllLikeLetters(pageable));
        } catch (BaseException e) {
            System.out.println(e);
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{letterRoomId}")
    public BaseResponse<?> getAllLetters(@PathVariable("letterRoomId") Long letterRoomId,
                                         @RequestHeader("Authorization") Long loginId,
                                         @PageableDefault(size = 30) Pageable pageable) {
        try {
            return new BaseResponse<>(letterRoomService.getAllLetters(letterRoomId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
