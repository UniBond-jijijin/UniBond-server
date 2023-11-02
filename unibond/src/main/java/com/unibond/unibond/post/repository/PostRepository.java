package com.unibond.unibond.post.repository;

import com.unibond.unibond.post.domain.BoardType;
import com.unibond.unibond.post.domain.Post;
import com.unibond.unibond.post.dto.PostPreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select new com.unibond.unibond.post.dto.PostPreviewDto(o, d, p) " +
            "from Post p " +
            "join fetch p.owner o " +
            "join fetch p.owner.disease d " +
            "where p.boardType = :boardType " +
            "order by p.createdDate desc ")
    Page<PostPreviewDto> findPostsByBoardType(@Param("boardType") BoardType boardType, Pageable pageable);
}
