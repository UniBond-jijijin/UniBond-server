package com.unibond.unibond.post.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.post.dto.PostUploadReqDto;
import com.unibond.unibond.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.unibond.unibond.common.BaseResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community/experience")
public class ExperiencePostController {
    private final PostService postService;

    @PostMapping("")
    public BaseResponse<?> createPost(@RequestHeader("Authorization") Long loginId,
                                      @RequestBody PostUploadReqDto reqDto) {
        try {
            postService.createPost(reqDto);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
