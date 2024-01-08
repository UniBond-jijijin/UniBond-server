package com.unibond.unibond.block.repository;

import com.unibond.unibond.block.domain.CommentBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentBlockRepository extends JpaRepository<CommentBlock, Long> {
}
