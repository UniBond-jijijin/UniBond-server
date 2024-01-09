package com.unibond.unibond.block.service;

import com.unibond.unibond.block.domain.*;
import com.unibond.unibond.block.dto.BlockReqDto;
import com.unibond.unibond.block.repository.*;
import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.comment.repository.CommentRepository;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.letter.repository.LetterRepository;
import com.unibond.unibond.letter_room.domain.LetterRoom;
import com.unibond.unibond.letter_room.repository.LetterRoomRepository;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.member.repository.MemberRepository;
import com.unibond.unibond.post.domain.Post;
import com.unibond.unibond.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.unibond.unibond.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final LoginInfoService loginInfoService;

    private final MemberBlockRepository memberBlockRepository;
    private final PostBlockRepository postBlockRepository;
    private final CommentBlockRepository commentBlockRepository;
    private final LetterBlockRepository letterBlockRepository;
    private final LetterRoomBlockRepository letterRoomBlockRepository;

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LetterRepository letterRepository;
    private final LetterRoomRepository letterRoomRepository;

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

    @Transactional
    public BaseResponseStatus blockLetter(BlockReqDto reqDto) throws BaseException {
        try {
            Member reporter = loginInfoService.getLoginMember();
            if (reqDto.getBlockedLetterId() == null) throw new BaseException(NULL_PROPERTY);
            Letter letter = findLetter(reqDto.getBlockedLetterId());
            LetterBlock letterBlock = reqDto.toEntity(reporter, letter);
            letterBlockRepository.save(letterBlock);
            return SUCCESS;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public BaseResponseStatus blockLetterRoom(BlockReqDto reqDto) throws BaseException {
        try {
            Member reporter = loginInfoService.getLoginMember();
            if (reqDto.getBlockedLetterRoomId() == null) throw new BaseException(NULL_PROPERTY);
            LetterRoom letterRoom = findLetterRoom(reqDto.getBlockedLetterRoomId());
            LetterRoomBlock letterRoomBlock = reqDto.toEntity(reporter, letterRoom);
            blockLetters(reporter, letterRoom);
            letterRoomBlockRepository.save(letterRoomBlock);
            return SUCCESS;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public BaseResponseStatus blockComment(BlockReqDto reqDto) throws BaseException {
        try {
            Member reporter = loginInfoService.getLoginMember();
            if (reqDto.getBlockedCommentId() == null) throw new BaseException(NULL_PROPERTY);
            Comment comment = findComment(reqDto.getBlockedCommentId());
            CommentBlock commentBlock = reqDto.toEntity(reporter, comment);
            blockChildComments(reporter, comment);
            commentBlockRepository.save(commentBlock);
            return SUCCESS;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private void blockChildComments(Member reporter, Comment comment) {
        List<Comment> childCommentList = comment.getChildCommentList();
        if (!childCommentList.isEmpty()) {
            List<CommentBlock> commentBlockList = childCommentList.stream().map(
                    childComment -> CommentBlock.builder()
                            .reportedComment(childComment)
                            .reporter(reporter)
                            .build()
            ).collect(Collectors.toList());

            commentBlockRepository.saveAll(commentBlockList);
        }
    }

    private void blockLetters(Member reporter, LetterRoom letterRoom) {
        List<Letter> letterList = letterRoom.getLetterList();
        if (!letterList.isEmpty()) {
            List<LetterBlock> letterBlockList = letterList.stream().map(
                    letter -> LetterBlock.builder()
                            .reporter(reporter)
                            .reportedLetter(letter)
                            .build()
            ).collect(Collectors.toList());

            letterBlockRepository.saveAll(letterBlockList);
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

    private Comment findComment(Long commentId) throws BaseException {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(INVALID_COMMENT_ID));
    }

    private Letter findLetter(Long letterId) throws BaseException {
        return letterRepository.findById(letterId)
                .orElseThrow(() -> new BaseException(INVALID_LETTER_ID));
    }

    private LetterRoom findLetterRoom(Long letterRoomId) throws BaseException {
        return letterRoomRepository.findById(letterRoomId)
                .orElseThrow(() -> new BaseException(INVALID_LETTER_ROOM_ID));
    }
}
