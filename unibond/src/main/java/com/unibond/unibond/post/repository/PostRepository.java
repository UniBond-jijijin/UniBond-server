package com.unibond.unibond.post.repository;

import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.BoardType;
import com.unibond.unibond.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p " +
            "left join MemberBlock mb on (p.owner = mb.respondent and mb.reporter = :loginId ) " +
            "join fetch p.owner o " +
            "join fetch p.owner.disease d " +
            "where p.boardType = :boardType and p.status = 'ACTIVE' and mb.id IS NULL " +
            "order by p.createdDate desc ")
    Page<Post> findPostsByBoardType(@Param("boardType") BoardType boardType,
                                    @Param("loginId") Long loginId,
                                    Pageable pageable);

    @Query("select p from Post p " +
            "join fetch p.owner m " +
            "join fetch m.disease d " +
            "where p.id = :postId and p.status = 'ACTIVE' ")
    Optional<Post> findPostByIdFetchMemberAndDisease(@Param("postId") Long postId);

    @Query("select p from Post p " +
            "join fetch p.owner m " +
            "join fetch m.disease d " +
            "where p.owner = :member and p.status = 'ACTIVE' " +
            "order by p.createdDate desc ")
    Page<Post> findPostsByMember(@Param("member") Member member, Pageable pageable);
}
