package com.unibond.unibond.block.repository;

import com.unibond.unibond.block.domain.MemberBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberBlockRepository extends JpaRepository<MemberBlock, Long> {

    @Query("select count (mb) > 0 from MemberBlock mb " +
            "where mb.reporter.id = :reporterId and mb.respondent.id = :respondentId ")
    Boolean existsByReporterIdAndRespondentId(@Param("reporterId") Long reporterId,
                                              @Param("respondentId") Long respondentId);
}
