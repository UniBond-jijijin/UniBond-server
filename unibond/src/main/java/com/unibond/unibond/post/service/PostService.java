package com.unibond.unibond.post.service;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.comment.repository.CommentRepository;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.common.service.S3Uploader;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.BoardType;
import com.unibond.unibond.post.domain.Post;
import com.unibond.unibond.post.dto.GetCommunityContentDetailResDto;
import com.unibond.unibond.post.dto.GetCommunityResDto;
import com.unibond.unibond.post.dto.PostUploadReqDto;
import com.unibond.unibond.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.unibond.unibond.common.BaseResponseStatus.DATABASE_ERROR;
import static com.unibond.unibond.common.BaseResponseStatus.INVALID_POST_ID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final LoginInfoService loginInfoService;
    private final S3Uploader s3Uploader;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createPost(PostUploadReqDto reqDto, MultipartFile multipartFile) throws BaseException {
        try {
            Member loginMember = loginInfoService.getLoginMember();
            String imgUrl = null;
            if (multipartFile != null) {
                imgUrl = s3Uploader.upload(multipartFile, "post");
            }
            Post newPost = reqDto.toEntity(loginMember, imgUrl);
            postRepository.save(newPost);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetCommunityResDto getCommunityContent(BoardType boardType, Pageable pageable) throws BaseException {
        try {
            Page<Post> postPage = postRepository.findPostsByBoardType(boardType, pageable);

            return new GetCommunityResDto(postPage);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public GetCommunityContentDetailResDto getDetailCommunityContent(Long postId, Pageable pageable) throws BaseException {
        try {
            Member loginMember = loginInfoService.getLoginMember();
            Post post = postRepository.findPostByIdFetchMemberAndDisease(postId).orElseThrow(() -> new BaseException(INVALID_POST_ID));
            Page<Comment> commentList = commentRepository.findParentCommentsByPostFetchOwner(post, pageable);
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
            System.out.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
