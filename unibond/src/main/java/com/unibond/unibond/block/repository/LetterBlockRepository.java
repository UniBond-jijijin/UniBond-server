package com.unibond.unibond.block.repository;

import com.unibond.unibond.block.domain.LetterBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterBlockRepository extends JpaRepository<LetterBlock, Long> {
}
