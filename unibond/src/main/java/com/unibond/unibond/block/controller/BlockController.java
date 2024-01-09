package com.unibond.unibond.block.controller;

import com.unibond.unibond.block.dto.BlockReqDto;
import com.unibond.unibond.block.service.BlockService;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blocks")
public class BlockController {
    private final BlockService blockService;

    @PostMapping("/member")
    public BaseResponse<?> blockMember(@RequestHeader("Authorization") Long loginId,
                                       @RequestBody BlockReqDto reqDto) {
        try {
            return new BaseResponse<>(blockService.blockMember(reqDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/post")
    public BaseResponse<?> blockPost(@RequestHeader("Authorization") Long loginId,
                                       @RequestBody BlockReqDto reqDto) {
        try {
            return new BaseResponse<>(blockService.blockPost(reqDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/comment")
    public BaseResponse<?> blockComment(@RequestHeader("Authorization") Long loginId,
                                     @RequestBody BlockReqDto reqDto) {
        try {
            return new BaseResponse<>(blockService.blockComment(reqDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/letter")
    public BaseResponse<?> blockLetter(@RequestHeader("Authorization") Long loginId,
                                        @RequestBody BlockReqDto reqDto) {
        try {
            return new BaseResponse<>(blockService.blockLetter(reqDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/comment")
    public BaseResponse<?> blockLetterRoom(@RequestHeader("Authorization") Long loginId,
                                        @RequestBody BlockReqDto reqDto) {
        try {
            return new BaseResponse<>(blockService.blockLetterRoom(reqDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
