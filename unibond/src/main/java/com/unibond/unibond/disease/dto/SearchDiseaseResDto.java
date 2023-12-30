package com.unibond.unibond.disease.dto;

import com.unibond.unibond.common.PageInfo;
import com.unibond.unibond.disease.domain.Disease;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDiseaseResDto {
    private PageInfo pageInfo;
    private List<SearchedDisease> diseaseDataList = new ArrayList<>();

    public SearchDiseaseResDto(Page<Disease> diseasePage) {
        this.pageInfo = new PageInfo(diseasePage);
        for (Disease disease : diseasePage.getContent()) {
            SearchedDisease searchedDisease = SearchedDisease.builder()
                    .diseaseNameKor(disease.getDiseaseNameKor())
                    .diseaseNameEng(disease.getDiseaseNameEng())
                    .build();
            diseaseDataList.add(searchedDisease);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SearchedDisease {
        private String diseaseNameKor;
        private String diseaseNameEng;

        @Builder
        public SearchedDisease(String diseaseNameKor, String diseaseNameEng) {
            this.diseaseNameKor = diseaseNameKor;
            this.diseaseNameEng = diseaseNameEng;
        }
    }
}
