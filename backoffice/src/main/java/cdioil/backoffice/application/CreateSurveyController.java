package cdioil.backoffice.application;

import cdioil.domain.*;
import cdioil.domain.authz.UsersGroup;
import cdioil.persistence.impl.*;

import java.time.LocalDateTime;
import java.util.*;

public class CreateSurveyController {

    private ProductQuestionsLibrary productQuestionsLibrary;
    private CategoryTemplatesLibrary categoryTemplatesLibrary;
    private CategoryQuestionsLibrary categoryQuestionsLibrary;
    private IndependentQuestionsLibrary independentQuestionsLibrary;


    /**
     * Gets all the questions of a given Product
     *
     * @param product product to search the questions
     * @return List of questions for the product
     */
    public List<Question> questionForProducts(Product product) {
        ProductQuestionsLibraryRepositoryImpl repo = new ProductQuestionsLibraryRepositoryImpl();
        productQuestionsLibrary = repo.findProductQuestionLibrary();
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
        categoryQuestionsLibrary = questionsRepo.findCategoryQuestionsLibrary();

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
        categoryTemplatesLibrary = templatesRepo.findTemplatesForCategory();

        return new ArrayList<>(categoryTemplatesLibrary.categoryTemplateSet(category));
    }

    /**
     * Gets all the independent questions available
     *
     * @return all the independent questions
     */
    public List<Question> independantQuestions() {
        IndependentQuestionsLibraryRepositoryImpl independentRepo = new IndependentQuestionsLibraryRepositoryImpl();
        independentQuestionsLibrary = independentRepo.findLibrary();

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
     *
     * @param surveyItems list of survey items
     * @param map
     */
    public boolean createSurvey(List<SurveyItem> surveyItems, LocalDateTime dateBeginning, LocalDateTime dateEnding, HashMap<SurveyItem, List<Question>> map, UsersGroup targetAudience) {
        SurveyRepositoryImpl repo = new SurveyRepositoryImpl();
        Survey survey;

        if (targetAudience == null) {
            survey = new GlobalSurvey(surveyItems, dateBeginning, dateEnding);
        } else {
            survey = new TargetedSurvey(surveyItems, dateBeginning, dateEnding, targetAudience);
        }

        for (SurveyItem surveyItem : map.keySet()) {
            for (Question question : map.get(surveyItem)) {
               survey.addQuestion(question);
            }
        }
        return repo.merge(survey) != null;
    }
}
