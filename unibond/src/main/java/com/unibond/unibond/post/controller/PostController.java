package com.unibond.unibond.post.controller;

import com.unibond.unibond.comment.service.CommentService;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public BaseResponse<?> getDetailCommunityPosts(@RequestHeader("Authorization") Long loginId,
                                                   @PathVariable("postId") Long postId,
                                                   @PageableDefault(size = 30) Pageable pageable) {
        try {
            return new BaseResponse<>(postService.getDetailCommunityContent(postId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
