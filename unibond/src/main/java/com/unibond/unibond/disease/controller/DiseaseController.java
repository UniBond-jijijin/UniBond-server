package com.unibond.unibond.disease.controller;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponse;
import com.unibond.unibond.disease.dto.SearchDiseaseResDto;
import com.unibond.unibond.disease.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/disease")
public class DiseaseController {
    private final DiseaseService diseaseService;

    // TODO: 페이징 추가
    @GetMapping("/search")
    public BaseResponse<SearchDiseaseResDto> searchDisease(@RequestParam("lan") String language,
                                                           @RequestParam("query") String searchWord,
                                                           @PageableDefault(size = 5) Pageable pageable) {
        try {
            return new BaseResponse<>(diseaseService.searchDisease(language, searchWord, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
