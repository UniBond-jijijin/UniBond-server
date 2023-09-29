package com.unibond.unibond.disease.controller;

import com.unibond.unibond.disease.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DiseaseController {
    private final DiseaseService diseaseService;
}
