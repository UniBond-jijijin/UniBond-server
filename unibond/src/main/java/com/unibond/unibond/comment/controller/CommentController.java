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
@RequestMapping("/api/v1/community/{postId}")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments")
    public BaseResponse<?> uploadComments(@RequestHeader("Authorization") Long loginId,
                                          @PathVariable("postId") Long postId,
                                          @RequestBody UploadCommentReqDto reqDto) {
        try {
            return new BaseResponse<>(commentService.uploadComments(postId, reqDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{commentId}")
    public BaseResponse<?> getChildCommentsWithPaging(@RequestHeader("Authorization") Long loginId,
                                                      @PageableDefault(size = 30) Pageable pageable,
                                                      @PathVariable("postId") Long postId,
                                                      @PathVariable("commentId") Long commentId) {
        try {
            return new BaseResponse<>(commentService.getChildCommentsWithPaging(postId, commentId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
