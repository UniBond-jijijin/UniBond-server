package com.unibond.unibond.comment.service;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.comment.dto.ChildCommentsPagingResDto;
import com.unibond.unibond.comment.dto.UploadCommentReqDto;
import com.unibond.unibond.comment.repository.CommentRepository;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.Post;
import com.unibond.unibond.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.unibond.unibond.common.BaseEntityStatus.ACTIVE;
import static com.unibond.unibond.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final LoginInfoService loginInfoService;

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public BaseResponseStatus uploadComments(Long postId, UploadCommentReqDto reqDto) throws BaseException {
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> new BaseException(INVALID_POST_ID));
            Member loginMember = loginInfoService.getLoginMember();

            Comment comment;
            if (reqDto.getParentCommentId() != null) {
                comment = buildChildComment(post, loginMember, reqDto);
            } else {
                comment = buildParentComment(post, loginMember, reqDto);
            }
            commentRepository.save(comment);
            return SUCCESS;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private Comment buildChildComment(Post post, Member loginMember, UploadCommentReqDto reqDto) throws BaseException {
        Comment parentComment = commentRepository.findCommentByIdAndStatus(reqDto.getParentCommentId(), ACTIVE)
                .orElseThrow(() -> new BaseException(INVALID_COMMENT_ID));
        return reqDto.buildChildComment(loginMember, post, parentComment);
    }

    private Comment buildParentComment(Post post, Member loginMember, UploadCommentReqDto reqDto) throws BaseException {
        return reqDto.buildParentComment(loginMember, post);
    }

    @Transactional
    public ChildCommentsPagingResDto getChildCommentsWithPaging(Long postId, Long commentId, Pageable pageable) throws BaseException {
        try {
            Comment parentComment = findCommentById(commentId);
            Page<Comment> comments = commentRepository.findCommentsByParentCommentFetchOwner(postId, parentComment, pageable);
            return new ChildCommentsPagingResDto(comments);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private Comment findCommentById(Long id) throws BaseException {
        return commentRepository.findCommentByIdAndStatus(id, ACTIVE)
                .orElseThrow(() -> new BaseException(INVALID_COMMENT_ID));
    }
}
