package cdioil.backoffice.application;

import cdioil.domain.Category;
import cdioil.domain.CategoryQuestionsLibrary;
import cdioil.domain.CategoryTemplatesLibrary;
import cdioil.domain.GlobalSurvey;
import cdioil.domain.IndependentQuestionsLibrary;
import cdioil.domain.Product;
import cdioil.domain.ProductQuestionsLibrary;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.Survey;
import cdioil.domain.SurveyItem;
import cdioil.domain.TargetedSurvey;
import cdioil.domain.Template;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.UsersGroup;
import cdioil.framework.dto.QuestionDTO;
import cdioil.framework.dto.SurveyItemDTO;
import cdioil.framework.dto.SystemUserDTO;
import cdioil.persistence.impl.CategoryQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.CategoryTemplatesLibraryRepositoryImpl;
import cdioil.persistence.impl.IndependentQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.ProductQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.ProductRepositoryImpl;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.time.TimePeriod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    public List<Question> independentQuestions() {
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

        for (Map.Entry<SurveyItem, List<Question>> mapEntry : map.entrySet()) {
            for (Question question : map.get(mapEntry.getKey())) {
                survey.addQuestion(question);
            }
        }

        return repo.merge(survey) != null;
    }

    /**
     * Gets the names of all SurveyItems assigned to the Manager
     *
     * @param manager active user/manager
     * @return list of survey items names
     */
    public List<SurveyItemDTO> findManagerSurveyItems(Manager manager) {
        List<SurveyItemDTO> result = new ArrayList<>();

        List<Category> managerCategories = manager.categoriesFromManager();

        for (Category cat :
                managerCategories) {

            SurveyItemDTO catDTO = new SurveyItemDTO("category",
                    cat.categoryName(), cat.categoryPath());
            result.add(catDTO);

            // Add current category's products
            Iterator<Product> catProducts = cat.getProductSetIterator();
            while (catProducts.hasNext()) {
                Product currentCatProduct = catProducts.next();

                SurveyItemDTO catProdDTO = new SurveyItemDTO("product",
                        currentCatProduct.productName(), currentCatProduct.getID().toString());

                result.add(catProdDTO);
            }
        }

        return result;
    }

    public List<QuestionDTO> getlAllQuestions() {
        List<QuestionDTO> result = new ArrayList<>();

        // All Repositories
        CategoryQuestionsLibraryRepositoryImpl catQuestionsRepo =
                new CategoryQuestionsLibraryRepositoryImpl();
        ProductQuestionsLibraryRepositoryImpl productQuestionsRepo =
                new ProductQuestionsLibraryRepositoryImpl();
        IndependentQuestionsLibraryRepositoryImpl independentQuestionsRepo =
                new IndependentQuestionsLibraryRepositoryImpl();

        ProductRepositoryImpl productRepo = new ProductRepositoryImpl();
        MarketStructureRepositoryImpl mkRepo = new MarketStructureRepositoryImpl();

        // Add All Cat Questions
        List<Category> allCats = mkRepo.findMarketStructure().getAllCategories();
        for (Category cat :
                allCats) {
            for (Question catQuestion :
                    catQuestionsRepo.findLibrary().categoryQuestionSet(cat)) {

                List<String> catQuestionOptions = new ArrayList<>();

                for (QuestionOption currentCatQuestionOption :
                        catQuestion.getOptionList()) {
                    catQuestionOptions.add(currentCatQuestionOption.toString());
                }

                QuestionDTO questionDTO = new QuestionDTO("catQuestion",
                        catQuestion.getQuestionText(), catQuestion.getType().toString(), catQuestionOptions);

                result.add(questionDTO);
            }
        }

        // Add All Product Questions
        for (Product product :
                productRepo.findAll()) {
            Iterable<Question> productQuestions =
                    productQuestionsRepo.findProductQuestionLibrary().productQuestionSet(product);

            if (productQuestions == null) {
                continue;
            }

            for (Question prodQuestion :
                    productQuestions) {
                List<String> options = new ArrayList<>();
                for (QuestionOption questionOption :
                        prodQuestion.getOptionList()) {
                    options.add(questionOption.toString());
                }

                QuestionDTO questionDTO = new QuestionDTO("prodQuestion",
                        prodQuestion.getQuestionText(), prodQuestion.getType().toString(), options);

                result.add(questionDTO);
            }
        }

        // TODO Add All Independent Questions

        return result;
    }

    /**
     * Finds All Registered Users
     *
     * @return list of all registered users, in the form of DTOs
     */
    public List<SystemUserDTO> findAllRegisteredUsers() {
        RegisteredUserRepositoryImpl registeredUserRepository = new RegisteredUserRepositoryImpl();

        List<SystemUserDTO> usersDTOList = new ArrayList<>();

        for (RegisteredUser user :
                registeredUserRepository.findAll()) {
            usersDTOList.add(user.toDTO());
        }

        return usersDTOList;
    }
}
