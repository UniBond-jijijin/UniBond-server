package com.unibond.unibond.block.repository;

import com.unibond.unibond.block.domain.PostBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostBlockRepository extends JpaRepository<PostBlock, Long> {

    @Query("select count (pb) > 0 from PostBlock pb " +
            "where pb.reporter.id = :reporterId and pb.reportedPost.id = :reportedPostId ")
    Boolean existsByReporterIdAndReportedPostId(@Param("reporterId") Long reporterId,
                                                @Param("reportedPostId") Long reportedPostId);
}
