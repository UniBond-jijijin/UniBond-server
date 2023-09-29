package com.unibond.unibond.disease.repository;

import com.unibond.unibond.disease.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
}
