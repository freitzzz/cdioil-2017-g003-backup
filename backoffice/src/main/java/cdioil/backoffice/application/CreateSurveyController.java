package cdioil.application.authz;

import cdioil.domain.*;
import cdioil.persistence.impl.GlobalLibraryRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;

import java.time.LocalDateTime;
import java.util.*;

public class CreateSurveyController {

    public List<Question> questionForProducts(Product product) {
        GlobalLibraryRepositoryImpl repo = new GlobalLibraryRepositoryImpl();
        HashSet<Question> hashSet = repo.findAll().iterator().next().getProdQuestionsLibrary().productQuestionSet(product);
        List<Question> list = new ArrayList<>();
        list.addAll(hashSet);

        return list;
    }

    /**
     * Create a Survey for specific products or categories
     *  @param surveyItems list of survey items
     * @param map
     */
    public boolean createSurvey(List<SurveyItem> surveyItems, LocalDateTime dateEnding, HashMap<SurveyItem, List<Question>> map) {
        SurveyRepositoryImpl repo = new SurveyRepositoryImpl();
        Survey survey = new Survey(surveyItems, LocalDateTime.now(), dateEnding);

        for (SurveyItem surveyItem : map.keySet()) {
            for (Question question : map.get(surveyItem)) {
                survey.addQuestion(question);
            }
        }
        return repo.add(survey) == null;
    }
}
