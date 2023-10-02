package com.unibond.unibond.disease.repository;

import com.unibond.unibond.disease.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    List<Disease> findFirst5ByDiseaseNameKorContaining(String query);
    List<Disease> findFirst5ByDiseaseNameEngContaining(String query);
}
