package com.unibond.unibond.block.service;

import com.unibond.unibond.block.domain.MemberBlock;
import com.unibond.unibond.block.dto.BlockMemberReqDto;
import com.unibond.unibond.block.repository.MemberBlockRepository;
import com.unibond.unibond.block.repository.PostBlockRepository;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.member.repository.MemberRepository;
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
    public BaseResponseStatus blockMember(BlockMemberReqDto reqDto) throws BaseException {
        try {
            Member reporter = loginInfoService.getLoginMember();
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

    private Member findMember(Long memberId) throws BaseException {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));
    }
}
