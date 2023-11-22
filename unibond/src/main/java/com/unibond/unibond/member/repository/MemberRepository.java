package com.unibond.unibond.member.repository;

import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsMemberByNickname(String nickname);

    Optional<Member> findMemberByIdAndStatus(Long memberId, BaseResponseStatus status);

    @Query("Select m from Member m " +
            "join fetch m.disease " +
            "where m.id = :memberId and m.status  = 'ACTIVE' ")
    Optional<Member> findMemberByIdFetchJoinDisease(@Param("memberId") Long memberId);
}
