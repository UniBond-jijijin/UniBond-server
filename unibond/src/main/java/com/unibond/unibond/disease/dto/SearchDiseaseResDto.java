package com.unibond.unibond.disease.dto;

import com.unibond.unibond.disease.domain.Disease;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDiseaseResDto {
    private List<SearchedDisease> diseaseDataList = new ArrayList<>();

    public void appendDiseaseDataToList(List<Disease> diseaseList) {
        for (Disease disease : diseaseList) {
            SearchedDisease searchedDisease = SearchedDisease.builder()
                    .diseaseNameKor(disease.getDiseaseNameKor())
                    .diseaseNameEng(disease.getDiseaseNameEng())
                    .build();
            diseaseDataList.add(searchedDisease);
        }
    }

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
