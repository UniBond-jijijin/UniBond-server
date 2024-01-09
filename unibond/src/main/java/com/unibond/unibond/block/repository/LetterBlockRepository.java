package com.unibond.unibond.block.repository;

import com.unibond.unibond.block.domain.LetterBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterBlockRepository extends JpaRepository<LetterBlock, Long> {
    @Query("select count (lb) > 0 from LetterBlock lb " +
            "where lb.reporter.id = :reporterId and lb.reportedLetter.id = :reportedLetterId ")
    Boolean existsByReporterIdAndReportedLetterId(@Param("reporterId") Long reporterId,
                                                  @Param("reportedLetterId") Long reportedLetterId);
}
