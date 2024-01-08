package com.unibond.unibond.comment.repository;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.common.BaseEntityStatus;
import com.unibond.unibond.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentByIdAndStatus(Long id, BaseEntityStatus status);

    @Query("select c from Comment c " +
            "left join MemberBlock mb on ( c.member = mb.respondent and mb.reporter.id = :loginId ) " +
            "left join CommentBlock cb on ( c = cb.reportedComment and cb.reporter.id = :loginId ) " +
            "join fetch c.member " +
            "join fetch c.member.disease " +
            "where c.parentComment = :parentComment " +
                "and c.post.id = :postId " +
                "and c.status = 'ACTIVE' " +
                "and mb.id IS NULL and cb.id IS NULL " +
            "order by c.createdDate desc ")
    Page<Comment> findCommentsByParentCommentFetchOwner(@Param("postId") Long postId,
                                                        @Param("parentComment") Comment parentComment,
                                                        @Param("loginId") Long loginId,
                                                        Pageable pageable);

    @Query("select c from Comment c " +
            "left join MemberBlock mb on ( c.member = mb.respondent and mb.reporter.id = :loginId ) " +
            "left join CommentBlock cb on ( c = cb.reportedComment and cb.reporter.id = :loginId ) " +
            "join fetch c.member " +
            "where c.post = :post and c.parentComment = null and c.status = 'ACTIVE' and mb.id IS NULL and cb.id IS NULL " +
            "order by c.createdDate desc ")
    Page<Comment> findParentCommentsByPostFetchOwner(@Param("post") Post post,
                                                     @Param("loginId") Long loginId,
                                                     Pageable pageable);

    @Query("select COUNT(c) from Comment c " +
            "left join MemberBlock mb on ( c.member = mb.respondent and mb.reporter.id = :loginId ) " +
            "left join CommentBlock cb on ( c = cb.reportedComment and cb.reporter.id = :loginId ) " +
            "where c.post = :post and c.status = 'ACTIVE' and mb.id IS NULL and cb.id IS NULL ")
    Integer getCommentCountByPost(@Param("post") Post post,
                                  @Param("loginId") Long loginId);

    @Modifying
    @Query("update Comment c set c.status = 'DELETED' " +
            "where c.member.id = :memberId ")
    void bulkDeleteByMember(@Param("memberId") Long memberId);
}
