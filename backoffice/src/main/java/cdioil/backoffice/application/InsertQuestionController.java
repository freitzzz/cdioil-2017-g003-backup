/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.application;

import cdioil.domain.*;
import cdioil.domain.authz.Manager;
import cdioil.persistence.impl.CategoryQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller class for the User Story 220 - Add question about Categories.
 *
 * @author Rita Gonçalves (1160912)
 */
public class InsertQuestionController {

    /**
     * The Question to add.
     */
    private Question question;

    /**
     * Current manager.
     */
    private final Manager manager;

    /**
     * Scale of the Category's path in the Market Structure.
     */
    private static final String SCALE = "[0-9]+";

    /**
     * Regular expression to validate the path of the Category in the Market Structure.
     */
    private static final String PATH_REGEX = SCALE + Category.Sufixes.SUFIX_DC + "((-" + SCALE + Category.Sufixes.SUFIX_UN + "(-" + SCALE
            + Category.Sufixes.SUFIX_CAT + "(-" + SCALE + Category.Sufixes.SUFIX_SCAT + "(-" + SCALE + Category.Sufixes.SUFIX_UB + ")?)?)?)?)";

    /**
     * Sufix of the regular expression used to search categories by its identifier.
     */
    private static final String REGEX_PREFIX = ".*";

    /**
     * Prefix of the regular expression used to search categories by its identifier.
     */
    private static final String REGEX_SUFIX = ".*";

    /**
     * Builds an instance of InsertQuestionController.
     *
     * @param mng Manager in question
     */
    public InsertQuestionController(Manager mng) {
        this.manager = mng;
    }

    /**
     * Returns all types of questions.
     *
     * @return a typesList with the types of questions
     */
    public List<String> getQuestionTypes() {
        QuestionTypes[] questionTypes = QuestionTypes.values();
        List<String> typesList = new ArrayList<>();
        for (int i = 0; i < questionTypes.length; i++) {
            typesList.add(questionTypes[i].toString());
        }
        return typesList;
    }

    /**
     * Creates a new question of a certain type.
     *
     * @param questionType Type of question to add
     * @param questionText Text of the question to add
     * @param questionID ID of the question to add
     * @param optionList List with the options
     * @return 1 if the question is successfully created. 0, if the data is not valid. -1 if the type of question is not valid.
     */
    public int createQuestion(String questionType, String questionText, String questionID, List<QuestionOption> optionList) {
        int option = extractOption(questionType);

        switch (option) {
            case 1: //Binary question
                return createBinaryQuestion(questionText, questionID) ? 1 : 0;
            case 2: //Multiple choice question
                return createMultipleChoiceQuestion(questionText, questionID, optionList) ? 1 : 0;
            case 3: //Quantitative question
                return createQuantitativeQuestion(questionText, questionID, optionList) ? 1 : 0;
            default: //Invalid
                return -1;
        }
    }

    /**
     * Creates a binary question.
     *
     * @param questionText Text of the question to create
     * @param questionID ID of the question to create
     * @return true if the question is successfully created. Otherwise, returns false
     */
    private boolean createBinaryQuestion(String questionText, String questionID) {
        try {
            this.question = new BinaryQuestion(questionText, questionID);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Creates a multiple choice question.
     *
     * @param questionTextText of the question to create
     * @param questionID ID of the question to create
     * @param optionList List with all options
     * @return true if the question is successfully created. Otherwise, returns false
     */
    private boolean createMultipleChoiceQuestion(String questionText, String questionID, List<QuestionOption> optionList) {
        try {
            this.question = new MultipleChoiceQuestion(questionText, questionID, optionList);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Creates a quantitative choice question.
     *
     * @param questionTextText of the question to create
     * @param questionID ID of the question to create
     * @param optionList List with all options
     * @return true if the question is successfully created. Otherwise, returns false
     */
    private boolean createQuantitativeQuestion(String questionText, String questionID, List<QuestionOption> optionList) {
        try {
            this.question = new QuantitativeQuestion(questionText, questionID, optionList);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Finds a list of Categories in the Market Structure according to the path.
     *
     * @param catPath Path of the Category/Categories in the Market Structure
     * @return the list with Categories, or an empty list if there are no categories with that path or if none is associated with the manager
     */
    public List<Category> findCategories(String catPath) {
        List<Category> categoryList = new MarketStructureRepositoryImpl().
                findCategoriesByIdentifierPattern(REGEX_PREFIX + catPath.toUpperCase() + REGEX_SUFIX);

        if (categoryList == null || categoryList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Category> managerCategories = new LinkedList<>();

        for (Category category : categoryList) {
            if (manager.isAssociatedWithCategory(category)) {
                managerCategories.add(category);
            }
        }

        if (managerCategories.isEmpty()) {
            return new ArrayList<>();
        }
        return managerCategories;
    }

    /**
     * Adds a new Question to que CategoryQuestionsLibrary.
     *
     * @param catPath Path of the category
     * @return the size (number of questions added) of the list
     */
    public int persistQuestion(String catPath) {
        CategoryQuestionsLibraryRepositoryImpl categoryQuestionsLibraryRepository = new CategoryQuestionsLibraryRepositoryImpl();

        CategoryQuestionsLibrary categoryQuestionsLibrary = categoryQuestionsLibraryRepository.findLibrary();

        List<Category> categoryList = findCategories(catPath);
        for (Category c : categoryList) {
            categoryQuestionsLibrary.addCategory(c);
            categoryQuestionsLibrary.addQuestion(question, c);
        }

        categoryQuestionsLibraryRepository.merge(categoryQuestionsLibrary);
        return categoryList.size();
    }

    /**
     * Validates the question type (user input).
     *
     * @param questionType Question type inserted by the user
     * @return 1, if the question is binary. 2 if it is a multiple choice question. 3 if the question is quantitative. -1 if the type is not valid.
     */
    public int extractOption(String questionType) {
        String type = questionType.toLowerCase();
        if (type.equalsIgnoreCase(QuestionTypes.BINARY.toString()) || "1".equals(type)) {
            return 1; //Binary Question
        } else if (type.equalsIgnoreCase(QuestionTypes.MULTIPLE_CHOICE.toString()) || "2".equals(type)) {
            return 2; //Multiple Choice Question
        } else if (type.equalsIgnoreCase(QuestionTypes.QUANTITATIVE.toString()) || "3".equals(type)) {
            return 3; //Quantitative Question
        }
        return -1; //Invalid type
    }

    /**
     * Creates a new option for a multiple choice question.
     *
     * @param content Content of the option
     * @return the option
     */
    public MultipleChoiceQuestionOption createNewMultipleChoiceOption(String content) {
        return new MultipleChoiceQuestionOption(content);
    }

    /**
     * Creates a new option for a quantitative question.
     *
     * @param content Content of the option
     * @return the option
     */
    public QuantitativeQuestionOption createNewQuantitativeQuestionOption(double content) {
        return new QuantitativeQuestionOption(content);
    }

    /**
     * Checks if the inserted path is valid.
     *
     * @param identifier identifier of the categories
     * @return true, if the categories are valid. Otherwise, returns false
     */
    public boolean checkPath(String identifier) {
        return identifier.toUpperCase().matches(PATH_REGEX);
    }
}
