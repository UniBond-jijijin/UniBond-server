package com.unibond.unibond.disease.repository;

import com.unibond.unibond.disease.domain.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    Page<Disease> findFirst5ByDiseaseNameKorContains(String query, Pageable pageable);
    Page<Disease> findFirst5ByDiseaseNameEngContains(String query, Pageable pageable);
}
