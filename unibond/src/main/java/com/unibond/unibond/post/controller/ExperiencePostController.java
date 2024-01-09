package com.unibond.unibond.post.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.post.dto.PostUploadReqDto;
import com.unibond.unibond.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.unibond.unibond.common.BaseResponseStatus.SUCCESS;
import static com.unibond.unibond.post.domain.BoardType.EXPERIENCE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
public class ExperiencePostController {
    private final PostService postService;

    @PostMapping(value = "/api/v1/community/experience")
    public BaseResponse<?> createPost(@RequestHeader("Authorization") Long loginId,
                                      @RequestBody PostUploadReqDto request) {
        try {
            postService.createPost(request, EXPERIENCE);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping(value = "/api/v2/community/experience", consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<?> createPost(@RequestHeader("Authorization") Long loginId,
                                      @RequestPart MultipartFile postImg,
                                      @RequestPart PostUploadReqDto request) {
        try {
            postService.createPost(request, postImg);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/api/v1/community/experience")
    public BaseResponse<?> getExperienceCommunityPosts(@PageableDefault(size = 30) Pageable pageable) {
        try {
            return new BaseResponse<>(postService.getCommunityContent(EXPERIENCE, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
