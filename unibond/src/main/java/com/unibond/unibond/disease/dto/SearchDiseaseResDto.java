package com.unibond.unibond.disease.dto;

import com.unibond.unibond.common.PageInfo;
import com.unibond.unibond.disease.domain.Disease;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDiseaseResDto {
    private PageInfo pageInfo;
    private List<SearchedDisease> diseaseDataList = new ArrayList<>();

    public SearchDiseaseResDto(Page<Disease> diseasePage) {
        this.pageInfo = new PageInfo(diseasePage);
        this.diseaseDataList = diseasePage.getContent().stream().map(
                SearchedDisease::new
        ).collect(Collectors.toList());
    }

    @Getter
    @NoArgsConstructor
    public static class SearchedDisease {
        private Long diseaseId;
        private String diseaseNameKor;
        private String diseaseNameEng;

        @Builder
        public SearchedDisease(Disease disease) {
            this.diseaseId = disease.getId();
            this.diseaseNameKor = disease.getDiseaseNameKor();
            this.diseaseNameEng = disease.getDiseaseNameEng();
        }
    }
}
