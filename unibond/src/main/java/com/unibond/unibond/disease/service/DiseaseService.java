package com.unibond.unibond.disease.service;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.disease.domain.Disease;
import com.unibond.unibond.disease.dto.SearchDiseaseResDto;
import com.unibond.unibond.disease.repository.DiseaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.unibond.unibond.common.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
public class DiseaseService {
    private final DiseaseRepository diseaseRepository;

    public SearchDiseaseResDto searchDisease(String language, String searchWord) throws BaseException {
        try {

            List<Disease> searchedDiseaseList;

            if (language.equals("kor")) {
                searchedDiseaseList = diseaseRepository.findFirst5ByDiseaseNameKorContaining(searchWord);
            } else if (language.equals("eng")) {
                searchedDiseaseList = diseaseRepository.findFirst5ByDiseaseNameEngContaining(searchWord);
            } else {
                throw new BaseException(NULL_SEARCH_LAN);
            }

            SearchDiseaseResDto searchDiseaseResDto = new SearchDiseaseResDto();
            searchDiseaseResDto.appendDiseaseDataToList(searchedDiseaseList);

            return searchDiseaseResDto;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
