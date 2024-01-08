package com.unibond.unibond.block.service;

import com.unibond.unibond.block.domain.MemberBlock;
import com.unibond.unibond.block.domain.PostBlock;
import com.unibond.unibond.block.dto.BlockReqDto;
import com.unibond.unibond.block.repository.MemberBlockRepository;
import com.unibond.unibond.block.repository.PostBlockRepository;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.member.repository.MemberRepository;
import com.unibond.unibond.post.domain.Post;
import com.unibond.unibond.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.unibond.unibond.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final LoginInfoService loginInfoService;
    private final MemberBlockRepository memberBlockRepository;
    private final PostBlockRepository postBlockRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public BaseResponseStatus blockMember(BlockReqDto reqDto) throws BaseException {
        try {
            Member reporter = loginInfoService.getLoginMember();
            if (reqDto.getBlockedMemberId() == null) throw new BaseException(NULL_PROPERTY);
            Member respondent = findMember(reqDto.getBlockedMemberId());
            MemberBlock memberBlock = reqDto.toEntity(reporter, respondent);
            memberBlockRepository.save(memberBlock);
            return SUCCESS;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public BaseResponseStatus blockPost(BlockReqDto reqDto) throws BaseException {
        try {
            Member reporter = loginInfoService.getLoginMember();
            if (reqDto.getBlockedPostId() == null) throw new BaseException(NULL_PROPERTY);
            Post post = findPost(reqDto.getBlockedPostId());
            PostBlock postBlock = reqDto.toEntity(reporter, post);
            postBlockRepository.save(postBlock);
            return SUCCESS;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private Member findMember(Long memberId) throws BaseException {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));
    }

    private Post findPost(Long postId) throws BaseException {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(INVALID_POST_ID));
    }
}
