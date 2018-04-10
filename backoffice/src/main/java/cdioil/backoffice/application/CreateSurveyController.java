package cdioil.backoffice.application;

import cdioil.domain.*;
import cdioil.persistence.impl.GlobalLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
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

    public List<Question> questionsForCategory(Category category) {
        GlobalLibraryRepositoryImpl repository = new GlobalLibraryRepositoryImpl();
        GlobalLibrary globalLibrary = repository.findGlobalLibrary();

        return new ArrayList<>(globalLibrary.getCatQuestionsLibrary().categoryQuestionSet(category));
    }

    public Category findCategory(String path) {
        MarketStructureRepositoryImpl marketStructure = new MarketStructureRepositoryImpl();
        List<Category> temporary = marketStructure.findCategoriesByPathPattern(path.toUpperCase());

        if (temporary.size() > 0) {
            return temporary.get(0);
        }

        return null;
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
        System.out.println(survey);
        return repo.add(survey) == null;
    }
}
