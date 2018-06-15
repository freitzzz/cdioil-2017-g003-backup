package cdioil.backoffice.application;

import cdioil.domain.Survey;
import cdioil.framework.SurveyDTO;
import cdioil.persistence.impl.SurveyRepositoryImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListSurveysController {

    private SurveyRepositoryImpl surveyRepo = new SurveyRepositoryImpl();

    /**
     * Gets all Surveys in a form of a DTO (Data Transfer Object)
     * @return list of all surveys
     */
    public List<SurveyDTO> getListOfSurveys() {
        List<SurveyDTO> dtos = new ArrayList<>();

        Iterator<Survey> allSurveys = surveyRepo.findAll().iterator();

        while (allSurveys.hasNext()) {
            Survey currentSurvey = allSurveys.next();

            dtos.add(currentSurvey.toDTO());
        }

        return dtos;
    }

    public List<SurveyDTO> getFilteredSurveys(String query) {
        List<SurveyDTO> filteredResult = new ArrayList<>();

        for (SurveyDTO dto :
                getListOfSurveys()) {
            if (dto.getName().contains(query)) {
                filteredResult.add(dto);
            }
        }

        return filteredResult;
    }
}
