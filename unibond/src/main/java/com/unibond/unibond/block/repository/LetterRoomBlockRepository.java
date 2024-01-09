package com.unibond.unibond.block.repository;

import com.unibond.unibond.block.domain.LetterRoomBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRoomBlockRepository extends JpaRepository<LetterRoomBlock, Long> {
    @Query("select count (lb) > 0 from LetterRoomBlock lb " +
            "where lb.reporter.id = :reporterId and lb.reportedLetterRoom.id = :reportedLetterRoomId ")
    Boolean existsByReporterIdAndReportedLetterRoomId(@Param("reporterId") Long reporterId,
                                                      @Param("reportedLetterRoomId") Long reportedLetterRoomId);
}
