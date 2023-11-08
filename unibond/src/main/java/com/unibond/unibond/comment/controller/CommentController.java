package com.unibond.unibond.comment.controller;

import com.unibond.unibond.comment.dto.UploadCommentReqDto;
import com.unibond.unibond.comment.service.CommentService;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("")
    public BaseResponse<?> getChildComments(@PathVariable("postId") Long postId,
                                            @RequestParam("parent-comment-id") Long parentCommentId,
                                            @PageableDefault(size = 15) Pageable pageable) {
        try {
            return new BaseResponse<>(commentService.getChildComments(postId, parentCommentId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("")
    public BaseResponse<?> uploadComments(@PathVariable("postId") Long postId,
                                          @RequestBody UploadCommentReqDto reqDto) {
        try {
            return new BaseResponse<>(commentService.uploadComments(reqDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
