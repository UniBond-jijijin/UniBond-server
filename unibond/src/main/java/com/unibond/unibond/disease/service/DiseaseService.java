package com.unibond.unibond.disease.service;

import com.unibond.unibond.disease.repository.DiseaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DiseaseService {
    private final DiseaseRepository diseaseRepository;
}
