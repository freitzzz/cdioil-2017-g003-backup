/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.InsertQuestionController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.domain.QuestionOption;
import cdioil.domain.authz.Manager;
import com.vaadin.sass.internal.parser.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User Interface for the User Story 220 - Add question about Categories.
 *
 * @author Rita Gonçalves (1160912)
 */
public class InsertQuestionUI {

    //Constants common to all questions
    /**
     * Localization handler to load messages in several langugaes.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();

    /**
     * Represents the exit code for the User Interface.
     */
    private final String EXIT_CODE = localizationHandler.getMessageValue("option_exit");

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String EXIT_MESSAGE = localizationHandler.getMessageValue("info_exit_message");

    /**
     * Represents a message that indicates the user to select a type of question.
     */
    private final String TYPE_QUESTION_MESSAGE = localizationHandler.getMessageValue("request_select_question_type");

    /**
     * Represents a message that indicates the user to select a valid type of question.
     */
    private final String INVALID_TYPE_QUESTION_MESSAGE = localizationHandler.getMessageValue("error_select_a_valid_question_type");

    /**
     * Represents a message that indicates the user to insert the ID of the question.
     */
    private final String ID_QUESTION_MESSAGE = localizationHandler.getMessageValue("request_question_id");

    /**
     * Represents a message that indicates the user to insert the question itself.
     */
    private final String TXT_QUESTION_MESSAGE = localizationHandler.getMessageValue("request_question_txt");

    /**
     * Reprensents a message that indicates the user to insert the path of the category.
     */
    private final String CATEGORY_PATH = localizationHandler.getMessageValue("request_category_identifier_general");

    /**
     * Represents a message that indicates the user to insert a valid category.
     */
    private final String INVALID_CATEGORY_PATH = localizationHandler.getMessageValue("error_invalid_category");

    /**
     * Represents a message that indicates the user to stop or proceed.
     */
    private final String DO_YOU_WANT_TO_CONTINUE = localizationHandler.getMessageValue("request_confirmation_to_continue");

    /**
     * Represents a message that indicates the user that the question was not added to any category.
     */
    private final String QUESTION_NOT_ADDED = localizationHandler.getMessageValue("error_question_not_added_to_any_category" );

    /**
     * Represents a message that indicates the user how many categories the question was added to.
     */
    private final String QUESTION_ADDED = localizationHandler.getMessageValue("info_number_categories_question_was_added_to");

    /**
     * Separator used for clarity.
     */
    private final String SEPARATOR = localizationHandler.getMessageValue("separator");

    /**
     * Instance of Controller that intermediates the interactions between the administrator and the system.
     */
    private final InsertQuestionController ctrl;

    ///////////////////////////////////////////////////////////////////////////////////////////
    //Constants for multiple choice questions
    /**
     * Represents a message that indicates the user to insert the options for that question.
     */
    private final String OPTIONS_QUESTION_MESSAGE = localizationHandler.getMessageValue("please_insert_options");

    /**
     * Represents a message that indicates the user to insert an option.
     */
    private final String OPTION_MESSAGE = localizationHandler.getMessageValue("info_option_message");

    /**
     * Represents the exit code for inserting more options.
     */
    private final String NO_MORE_OPTIONS_CODE = "0";

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String NO_MORE_OPTIONS_MESSAGE = localizationHandler.getMessageValue("info_no_more_options")+ " " + NO_MORE_OPTIONS_CODE;

    /**
     * Represents a message that indicates the user to insert a valid option.
     */
    private final String INVALID_OPTION = localizationHandler.getMessageValue("error_invalid_option");

    /**
     * Represents a message that indicates that the question was not added because it was not valid.
     */
    private final String INVALID_QUESTION = localizationHandler.getMessageValue("error_invalid_question");

    ///////////////////////////////////////////////////////////////////////////////////////////
    //Constants for binary questions
    /**
     * Represents a message that indicates the user to insert the maximum and minimum values of the scale.
     */
    private final String INSERT_EXTREMES_MESSAGE = localizationHandler.getMessageValue("request_insert_extremes");

    /**
     * Represents a message that indicates the user to insert the maximum value of the scale.
     */
    private final String MAXIMUM_MESSAGE = localizationHandler.getMessageValue("request_insert_max");

    /**
     * Represents a message that indicates the user to insert the minimum value of the scale.
     */
    private final String MINIMUM_MESSAGE = localizationHandler.getMessageValue("request_insert_min");

    /**
     * Represents a message that indicates the user to insert a valid number.
     */
    private final String INVALID_VALUE = localizationHandler.getMessageValue("error_invalid_value");

    /**
     * Represents a message that indicates the user that the extreme values have been changed.
     */
    private final String CHANGED_EXTREME_VALUES = localizationHandler.getMessageValue("info_changed_extreme_values");

    /**
     * Creates a new User Interface.
     *
     * @param mng Current manager
     */
    public InsertQuestionUI(Manager mng) {
        ctrl = new InsertQuestionController(mng);
        insertQuestion();
    }

    /**
     * Method that intermediates the interactions with the manager (creates the UI itself).
     */
    private void insertQuestion() {
        System.out.println(SEPARATOR);
        System.out.println(EXIT_MESSAGE);
        boolean catched = false;
        while (!catched) {

            //1. The user inserts the category
            String categoryPath = Console.readLine(CATEGORY_PATH);
            if (categoryPath != null && categoryPath.equalsIgnoreCase(EXIT_CODE)) return;

            while (ctrl.findCategories(categoryPath) == null || !ctrl.checkPath(categoryPath)) {
                categoryPath = Console.readLine(INVALID_CATEGORY_PATH);
                if (categoryPath.equalsIgnoreCase(EXIT_CODE)) {
                    return;
                }
            }

            //2. The system lists the question types
            System.out.println(SEPARATOR);
            List<String> questionTypes = ctrl.getQuestionTypes();
            listQuestionTypes(questionTypes);
            System.out.println(SEPARATOR);

            //3. The user chooses a question type
            String questionType = Console.readLine(TYPE_QUESTION_MESSAGE);
            if (questionType.equalsIgnoreCase(EXIT_CODE)) {
                return;
            }
            int option = ctrl.extractOption(questionType);

            while (option == -1) {
                questionType = Console.readLine(INVALID_TYPE_QUESTION_MESSAGE);
                if (questionType.equalsIgnoreCase(EXIT_CODE)) {
                    return;
                }
                option = ctrl.extractOption(questionType);
            }

            //4. The user inserts que ID of the quesion
            String questionID = Console.readLine(ID_QUESTION_MESSAGE);
            if (questionID.equalsIgnoreCase(EXIT_CODE)) {
                return;
            }

            //5. The user inserts the question itself
            String questionText = Console.readLine(TXT_QUESTION_MESSAGE);
            if (questionText.equalsIgnoreCase(EXIT_CODE)) {
                return;
            }

            switch (option) {
                case 1: //Binary
                    ctrl.createQuestion(questionType, questionText, questionID, new LinkedList<>());
                    break;
                case 2:  //Multiple choice
                    insertMultipleChoiceQuestion(questionType, questionText, questionID);
                    break;
                case 3: //Quantitative
                    insertQuantitativeQuestion(questionType, questionText, questionID);
                    break;
                default:
                    break;
            }

            //6. The system persists the question
            int numberCategories = ctrl.persistQuestion(categoryPath);

            System.out.println(SEPARATOR);
            if (numberCategories == 0) {
                System.out.println(QUESTION_NOT_ADDED);
            } else {
                System.out.println(QUESTION_ADDED + numberCategories + ".");
            }

            //7. The system asks the user to proceed or exit
            System.out.println(SEPARATOR);
            String choice = Console.readLine(DO_YOU_WANT_TO_CONTINUE + EXIT_MESSAGE);
            if (choice.equalsIgnoreCase(EXIT_CODE)) {
                catched = true;
            }
        }
    }

    /**
     * Lists all types of existing questions.
     *
     * @param typesList List with the types of questions
     */
    private void listQuestionTypes(List<String> typesList) {
        int cont = 1;
        for (String type : typesList) {
            System.out.println(cont + ". " + type + "\n");
            cont++;
        }
    }

    /**
     * Allows the user to insert a quantitative question.
     *
     * @param questionType Type of the question (quantitative)
     * @param questionText Text of the question
     * @param questionID ID of the question
     */
    private void insertQuantitativeQuestion(String questionType, String questionText, String questionID) {
        System.out.println(INSERT_EXTREMES_MESSAGE);

        boolean isMaxValid = false;
        double max = 0;
        while (!isMaxValid) {
            try {
                max = Double.parseDouble(Console.readLine(MAXIMUM_MESSAGE));
                isMaxValid = true;
            } catch (Exception ex) {
                System.out.println(INVALID_VALUE);
            }
        }

        boolean isMinValid = false;
        double min = 0;
        while (!isMinValid) {
            try {
                min = Double.parseDouble(Console.readLine(MINIMUM_MESSAGE));
                isMinValid = true;
            } catch (ParseException ex) {
                System.out.println(INVALID_VALUE);
            }
        }

        if (min > max) {
            double aux = max;
            max = min;
            min = aux;
            System.out.println(CHANGED_EXTREME_VALUES);
        }

        List<QuestionOption> values = new ArrayList<>();
        for (double i = min; i < max; i++) {
            values.add(ctrl.createNewQuantitativeQuestionOption(i));
        }
        if (ctrl.createQuestion(questionType, questionText, questionID, values) != 1) {
            System.out.println(INVALID_QUESTION);
        }
    }

    /**
     * Allows the user to insert a multiple choice question.
     *
     * @param questionType Type of the question (multiple choice)
     * @param questionText Text of the question
     * @param questionID ID of the question
     */
    private void insertMultipleChoiceQuestion(String questionType, String questionText, String questionID) {
        System.out.println(OPTIONS_QUESTION_MESSAGE);
        System.out.println(NO_MORE_OPTIONS_MESSAGE);

        boolean noMoreOptions = false;
        int optionNumber = 1;
        List<QuestionOption> options = new ArrayList<>();

        while (!noMoreOptions) {
            String content = Console.readLine(OPTION_MESSAGE + " " + optionNumber + ":");
            try {
                QuestionOption newOption = ctrl.createNewMultipleChoiceOption(content);
                options.add(newOption);
            } catch (IllegalArgumentException ex) {
                System.out.println(INVALID_OPTION);
                optionNumber--;
            }

            optionNumber++;

            if (content.equalsIgnoreCase(NO_MORE_OPTIONS_CODE)) {
                noMoreOptions = true;
                if (options.isEmpty()) {
                    System.out.println(INVALID_QUESTION);
                } else {
                    if (ctrl.createQuestion(questionType, questionText, questionID, options) != 1) {
                        System.out.println(INVALID_QUESTION);
                    }
                }
            }
        }
    }

}
