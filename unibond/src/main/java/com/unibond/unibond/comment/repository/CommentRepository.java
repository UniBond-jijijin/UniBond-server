package com.unibond.unibond.comment.repository;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.common.BaseEntityStatus;
import com.unibond.unibond.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentByIdAndStatus(Long id, BaseEntityStatus status);

    @Query("select c from Comment c " +
            "join fetch c.member " +
            "join fetch c.member.disease " +
            "where c.parentComment = :parentComment " +
            "and c.post.id = :postId " +
            "and c.status = 'ACTIVE'")
    Page<Comment> findCommentsByParentCommentFetchOwner(@Param("postId") Long postId,
                                                        @Param("parentComment") Comment parentComment,
                                                        Pageable pageable);

    @Query("select c from Comment c " +
            "join fetch c.member " +
            "where c.post = :post and c.parentComment = null and c.status = 'ACTIVE' " +
            "order by c.createdDate desc ")
    Page<Comment> findParentCommentsByPostFetchOwner(@Param("post") Post post, Pageable pageable);

    @Query("select COUNT(c) from Comment c " +
            "where c.post = :post and c.status = 'ACTIVE'")
    Integer getCommentCountByPost(@Param("post") Post post);

    @Query("select c from Comment c " +
            "join fetch c.member " +
            "where c.post.id = :postId and c.parentComment = :parentComment " +
            "and c.status = 'ACTIVE' " +
            "order by c.createdDate desc ")
    Page<Comment> findCommentsWithOwnerByPostAndParentComment(@Param("postId") Long postId,
                                                              @Param("parentComment") Comment parentComment,
                                                              Pageable pageable);
}
