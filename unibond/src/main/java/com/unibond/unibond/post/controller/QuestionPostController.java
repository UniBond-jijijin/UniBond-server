package com.unibond.unibond.post.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.post.dto.PostUploadReqDto;
import com.unibond.unibond.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static com.unibond.unibond.common.BaseResponseStatus.SUCCESS;
import static com.unibond.unibond.post.domain.BoardType.QNA;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community/question")
public class QuestionPostController {
    private final PostService postService;

    @PostMapping("")
    public BaseResponse<?> createPost(@RequestHeader("Authorization") Long loginId,
                                      @RequestBody PostUploadReqDto reqDto) {
        try {
            reqDto.setBoardType(QNA);
            postService.createPost(reqDto, null);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("")
    public BaseResponse<?> getQnACommunityPosts(@PageableDefault(size = 30) Pageable pageable) {
        try {
            return new BaseResponse<>(postService.getCommunityContent(QNA, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
