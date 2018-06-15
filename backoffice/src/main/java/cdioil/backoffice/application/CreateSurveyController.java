package cdioil.backoffice.application;

import cdioil.domain.*;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.UsersGroup;
import cdioil.persistence.impl.*;
import cdioil.time.TimePeriod;

import java.time.LocalDateTime;
import java.util.*;

public class CreateSurveyController {

    /**
     * Gets all the questions of a given Product
     *
     * @param product product to search the questions
     * @return List of questions for the product
     */
    public List<Question> questionForProducts(Product product) {
        ProductQuestionsLibraryRepositoryImpl repo = new ProductQuestionsLibraryRepositoryImpl();
        ProductQuestionsLibrary productQuestionsLibrary = repo.findProductQuestionLibrary();
        List<Question> list = new ArrayList<>();
        list.addAll(productQuestionsLibrary.productQuestionSet(product));

        return list;
    }

    /**
     * Gets all the questions of a given Category
     *
     * @param category category to search the questions
     * @return question for the category
     */
    public List<Question> questionsForCategory(Category category) {
        CategoryQuestionsLibraryRepositoryImpl questionsRepo = new CategoryQuestionsLibraryRepositoryImpl();
        CategoryQuestionsLibrary categoryQuestionsLibrary = questionsRepo.findCategoryQuestionsLibrary();

        return new ArrayList<>(categoryQuestionsLibrary.categoryQuestionSet(category));
    }

    /**
     * Gets all templates of a given Category
     *
     * @param category category to search the templates
     * @return all templates for the category
     */
    public List<Template> templatesForCategory(Category category) {
        CategoryTemplatesLibraryRepositoryImpl templatesRepo = new CategoryTemplatesLibraryRepositoryImpl();
        CategoryTemplatesLibrary categoryTemplatesLibrary = templatesRepo.findTemplatesForCategory();

        return new ArrayList<>(categoryTemplatesLibrary.categoryTemplateSet(category));
    }

    /**
     * Gets all the independent questions available
     *
     * @return all the independent questions
     */
    public List<Question> independantQuestions() {
        IndependentQuestionsLibraryRepositoryImpl independentRepo = new IndependentQuestionsLibraryRepositoryImpl();
        IndependentQuestionsLibrary independentQuestionsLibrary = independentRepo.findLibrary();

        return new ArrayList<>(independentQuestionsLibrary.getID());

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

        if (!temporary.isEmpty()) {
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
     * Finds all users
     *
     * @return Iterable of registered users
     */
    public Iterable<RegisteredUser> findAll() {
        RegisteredUserRepositoryImpl registeredUserRepository = new RegisteredUserRepositoryImpl();
        return registeredUserRepository.findAll();
    }

    /**
     * Create a Survey for specific products or categories
     *
     * @param surveyItems list of survey items
     * @param map
     */
    public boolean createSurvey(List<SurveyItem> surveyItems, LocalDateTime dateBeginning, LocalDateTime dateEnding,
                                Map<SurveyItem, List<Question>> map, UsersGroup targetAudience) {
        SurveyRepositoryImpl repo = new SurveyRepositoryImpl();
        Survey survey;

        TimePeriod timePeriod = new TimePeriod(dateBeginning, dateEnding);

        if (targetAudience == null) {
            survey = new GlobalSurvey(surveyItems, timePeriod);
        } else {
            survey = new TargetedSurvey(surveyItems, timePeriod, targetAudience);
        }
        
        for(Map.Entry<SurveyItem, List<Question>> mapEntry : map.entrySet()){
            for(Question question : map.get(mapEntry.getKey())){
                survey.addQuestion(question);
            }
        }
        
        return repo.merge(survey) != null;
    }
}
