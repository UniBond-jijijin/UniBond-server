package com.unibond.unibond.comment.repository;

import com.unibond.unibond.comment.domain.Comment;
import com.unibond.unibond.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "join fetch c.member " +
            "where c.post = :post and c.parentComment = null and c.status = 'ACTIVE' " +
            "order by c.createdDate desc ")
    List<Comment> findParentCommentsByPostFetchOwner(@Param("post") Post post, Pageable pageable);

    @Query("select COUNT(c) from Comment c " +
            "where c.post = :post and c.status = 'ACTIVE'")
    Integer getCommentCountByPost(@Param("post") Post post);

    @Query("select c from Comment c " +
            "join fetch c.member " +
            "where c.post.id = :postId and c.parentComment = :parentComment " +
            "and c.status = 'ACTIVE' " +
            "order by c.createdDate desc ")
    List<Comment> findCommentsWithOwnerByPostAndParentComment(@Param("postId") Long postId,
                                                     @Param("parentComment") Comment parentComment,
                                                     Pageable pageable);
}
