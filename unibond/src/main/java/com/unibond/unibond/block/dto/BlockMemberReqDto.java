package com.unibond.unibond.block.dto;

import com.unibond.unibond.block.domain.MemberBlock;
import com.unibond.unibond.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockMemberReqDto {
    private Long blockedMemberId;

    public MemberBlock toEntity(Member reporter, Member respondent) {
        return MemberBlock.builder()
                .reporter(reporter)
                .respondent(respondent)
                .build();
    }
}
