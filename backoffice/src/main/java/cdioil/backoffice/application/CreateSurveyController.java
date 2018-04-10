package cdioil.backoffice.application;

import cdioil.domain.*;
import cdioil.persistence.impl.GlobalLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;

import java.time.LocalDateTime;
import java.util.*;

public class CreateSurveyController {

    /**
     * Gets all the questions of a given Product
     *
     * @param product product to search the questions
     * @returnnList of questions for the product
     */
    public List<Question> questionForProducts(Product product) {
        GlobalLibraryRepositoryImpl repo = new GlobalLibraryRepositoryImpl();
        HashSet<Question> hashSet = repo.findAll().iterator().next().getProdQuestionsLibrary().productQuestionSet(product);
        List<Question> list = new ArrayList<>();
        list.addAll(hashSet);

        return list;
    }

    /**
     * Gets all the questions of a givenCategory
     *
     * @param category category to search the questions
     * @return question for the category
     */
    public List<Question> questionsForCategory(Category category) {
        GlobalLibraryRepositoryImpl repository = new GlobalLibraryRepositoryImpl();
        GlobalLibrary globalLibrary = repository.findGlobalLibrary();

        return new ArrayList<>(globalLibrary.getCatQuestionsLibrary().categoryQuestionSet(category));
    }

    /**
     * Finds category through a path
     *
     * @param path path to the category
     * @return Category found or null
     */
    public Category findCategory(String path) {
        MarketStructureRepositoryImpl marketStructure = new MarketStructureRepositoryImpl();
        List<Category> temporary = marketStructure.findCategoriesByPathPattern(path.toUpperCase());

        if (temporary.size() > 0) {
            return temporary.get(0);
        }

        return null;
    }

    /**
     * Finds Products given a name
     *
     * @param productName Product name
     * @return List of products found or null
     */
    public List<Product> findProducts(String productName) {
        MarketStructureRepositoryImpl marketStructureRepository = new MarketStructureRepositoryImpl();
        return marketStructureRepository.findProductByName(".*" + productName + ".*");
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
        return repo.merge(survey) != null;
    }
}
