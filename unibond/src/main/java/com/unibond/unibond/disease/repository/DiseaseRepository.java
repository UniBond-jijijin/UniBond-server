package com.unibond.unibond.disease.repository;

import com.unibond.unibond.common.BaseEntityStatus;
import com.unibond.unibond.disease.domain.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    Page<Disease> findByDiseaseNameKorContainsAndStatus(String query, BaseEntityStatus status, Pageable pageable);
    Page<Disease> findByDiseaseNameEngContainsAndStatus(String query, BaseEntityStatus status, Pageable pageable);
}
