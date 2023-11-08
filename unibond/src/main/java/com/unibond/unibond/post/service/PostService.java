package com.unibond.unibond.post.service;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.comment.repository.CommentRepository;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.BoardType;
import com.unibond.unibond.post.domain.Post;
import com.unibond.unibond.post.dto.GetCommunityContentDetailResDto;
import com.unibond.unibond.post.dto.GetCommunityResDto;
import com.unibond.unibond.post.dto.PostPreviewDto;
import com.unibond.unibond.post.dto.PostUploadReqDto;
import com.unibond.unibond.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.unibond.unibond.common.BaseResponseStatus.DATABASE_ERROR;
import static com.unibond.unibond.common.BaseResponseStatus.INVALID_POST_ID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final LoginInfoService loginInfoService;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createPost(PostUploadReqDto reqDto) throws BaseException {
        try {
            Member loginMember = loginInfoService.getLoginMember();
            Post newPost = reqDto.toEntity(loginMember);
            postRepository.save(newPost);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetCommunityResDto getCommunityContent(BoardType boardType, Pageable pageable) throws BaseException {
        try {
            Page<PostPreviewDto> postPreviewList = postRepository.findPostsByBoardType(boardType, pageable);
            return new GetCommunityResDto(postPreviewList);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetCommunityContentDetailResDto getDetailCommunityContent(Long postId, Pageable pageable) throws BaseException {
        try {
            Member loginMember = loginInfoService.getLoginMember();
            Post post = postRepository.findPostByIdFetchMemberAndDisease(postId).orElseThrow(() -> new BaseException(INVALID_POST_ID));
            List<Comment> commentList = commentRepository.findParentCommentsByPostFetchOwner(post, pageable);
            int commentCount = commentRepository.getCommentCountByPost(post);

            return GetCommunityContentDetailResDto.builder()
                    .loginMember(loginMember)
                    .postOwner(post.getOwner())
                    .post(post)
                    .commentList(commentList)
                    .commentCount(commentCount)
                    .build();
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
