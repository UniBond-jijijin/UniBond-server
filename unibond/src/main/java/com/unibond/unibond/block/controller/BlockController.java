package com.unibond.unibond.block.controller;

import com.unibond.unibond.block.dto.BlockMemberReqDto;
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
                                       @RequestBody BlockMemberReqDto reqDto) {
        try {
            return new BaseResponse<>(blockService.blockMember(reqDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
