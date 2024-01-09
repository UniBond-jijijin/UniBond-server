package com.unibond.unibond.block.repository;

import com.unibond.unibond.block.domain.LetterRoomBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRoomBlockRepository extends JpaRepository<LetterRoomBlock, Long> {
}
