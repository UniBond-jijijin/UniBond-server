package com.unibond.unibond.comment.service;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.comment.dto.ChildCommentsDto;
import com.unibond.unibond.comment.dto.GetChildCommentsResDto;
import com.unibond.unibond.comment.repository.CommentRepository;
import com.unibond.unibond.common.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.unibond.unibond.common.BaseResponseStatus.INVALID_COMMENT_ID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public GetChildCommentsResDto getChildComments(Long postId, Long parentCommentId, Pageable pageable)
            throws BaseException {
        Comment parentComment
                = commentRepository.findById(parentCommentId).orElseThrow(() -> new BaseException(INVALID_COMMENT_ID));
        List<Comment> childComments
                = commentRepository.findCommentsWithOwnerByPostAndParentComment(postId, parentComment, pageable);

        List<ChildCommentsDto> childCommentDtoList = ChildCommentsDto.getChildCommentDtoList(childComments);

        return GetChildCommentsResDto.builder()
                .childCommentsDtoList(childCommentDtoList)
                .build();
    }
}
