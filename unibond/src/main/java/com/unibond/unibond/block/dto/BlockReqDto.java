package com.unibond.unibond.block.dto;

import com.unibond.unibond.block.domain.*;
import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.letter_room.domain.LetterRoom;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.unibond.unibond.common.BaseResponseStatus.NULL_PROPERTY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockReqDto {
    private Long blockedMemberId;
    private Long blockedPostId;
    private Long blockedCommentId;
    private Long blockedLetterId;
    private Long blockedLetterRoomId;

    public MemberBlock toEntity(Member reporter, Member respondent) {
        return MemberBlock.builder()
                .reporter(reporter)
                .respondent(respondent)
                .build();
    }

    public PostBlock toEntity(Member reporter, Post reportedPost) {
        return PostBlock.builder()
                .reporter(reporter)
                .reportedPost(reportedPost)
                .build();
    }

    public CommentBlock toEntity(Member reporter, Comment reportedComment) {
        return CommentBlock.builder()
                .reporter(reporter)
                .reportedComment(reportedComment)
                .build();
    }

    public LetterBlock toEntity(Member reporter, Letter reportedLetter) {
        return LetterBlock.builder()
                .reporter(reporter)
                .reportedLetter(reportedLetter)
                .build();
    }

    public LetterRoomBlock toEntity(Member reporter, LetterRoom reportedLetterRoom) {
        return LetterRoomBlock.builder()
                .reporter(reporter)
                .reportedLetterRoom(reportedLetterRoom)
                .build();
    }
}
