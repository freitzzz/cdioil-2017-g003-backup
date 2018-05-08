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
    private final String exitCode = localizationHandler.getMessageValue("option_exit");

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String exitMessage = localizationHandler.getMessageValue("info_exit_message");

    /**
     * Represents a message that indicates the user to select a type of question.
     */
    private final String requestQuestionTypeMessage = localizationHandler.getMessageValue("request_select_question_type");

    /**
     * Represents a message that indicates the user to select a valid type of question.
     */
    private final String invalidQuestionTypeMessage = localizationHandler.getMessageValue("error_select_a_valid_question_type");

    /**
     * Represents a message that indicates the user to insert the ID of the question.
     */
    private final String idQuestionMessage = localizationHandler.getMessageValue("request_question_id");

    /**
     * Represents a message that indicates the user to insert the question itself.
     */
    private final String txtQuestionMessage = localizationHandler.getMessageValue("request_question_txt");

    /**
     * Reprensents a message that indicates the user to insert the path of the category.
     */
    private final String requestCategoryPath = localizationHandler.getMessageValue("request_category_identifier_general");

    /**
     * Represents a message that indicates the user to insert a valid category.
     */
    private final String invalidCategoryPath = localizationHandler.getMessageValue("error_invalid_category");

    /**
     * Represents a message that indicates the user to stop or proceed.
     */
    private final String requestConfirmationToContinue = localizationHandler.getMessageValue("request_confirmation_to_continue");

    /**
     * Represents a message that indicates the user that the question was not added to any category.
     */
    private final String questionNotAddedMessage = localizationHandler.getMessageValue("error_question_not_added_to_any_category");

    /**
     * Represents a message that indicates the user how many categories the question was added to.
     */
    private final String questionAddedMessage = localizationHandler.getMessageValue("info_number_categories_question_was_added_to");

    /**
     * Separator used for clarity.
     */
    private final String separator = localizationHandler.getMessageValue("separator");

    /**
     * Instance of Controller that intermediates the interactions between the administrator and the system.
     */
    private final InsertQuestionController ctrl;

    ///////////////////////////////////////////////////////////////////////////////////////////
    //Constants for multiple choice questions
    /**
     * Represents a message that indicates the user to insert the options for that question.
     */
    private final String requestOptions = localizationHandler.getMessageValue("request_insert_options");

    /**
     * Represents a message that indicates the user to insert an option.
     */
    private final String optionMessage = localizationHandler.getMessageValue("info_option_message");

    /**
     * Represents the exit code for inserting more options.
     */
    private static final String NO_MORE_OPTIONS_CODE = "0";

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String noMoreOptionsMessage = localizationHandler.getMessageValue("info_no_more_options") + " " + NO_MORE_OPTIONS_CODE;

    /**
     * Represents a message that indicates the user to insert a valid option.
     */
    private final String invalidOptionMessage = localizationHandler.getMessageValue("error_invalid_option");

    /**
     * Represents a message that indicates that the question was not added because it was not valid.
     */
    private final String invalidQuestionMessage = localizationHandler.getMessageValue("error_invalid_question");

    ///////////////////////////////////////////////////////////////////////////////////////////
    //Constants for binary questions
    /**
     * Represents a message that indicates the user to insert the maximum and minimum values of the scale.
     */
    private final String insertExtremesMessage = localizationHandler.getMessageValue("request_insert_extremes");

    /**
     * Represents a message that indicates the user to insert the maximum value of the scale.
     */
    private final String insertMaximumValueMessage = localizationHandler.getMessageValue("request_insert_max");

    /**
     * Represents a message that indicates the user to insert the minimum value of the scale.
     */
    private final String insertMinimumValueMessage = localizationHandler.getMessageValue("request_insert_min");

    /**
     * Represents a message that indicates the user to insert a valid number.
     */
    private final String invalidValueMessage = localizationHandler.getMessageValue("error_invalid_value");

    /**
     * Represents a message that indicates the user that the extreme values have been changed.
     */
    private final String changedExtremeValuesMessage = localizationHandler.getMessageValue("info_changed_extreme_values");

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
        System.out.println(separator);
        System.out.println(exitMessage);
        boolean catched = false;
        while (!catched) {

            //1. The user inserts the category
            String categoryPath = Console.readLine(requestCategoryPath);
            if (categoryPath != null && categoryPath.equalsIgnoreCase(exitCode)) {
                return;
            }

            while (ctrl.findCategories(categoryPath).isEmpty() || !ctrl.checkPath(categoryPath)) {
                categoryPath = Console.readLine(invalidCategoryPath);
                if (categoryPath.equalsIgnoreCase(exitCode)) {
                    return;
                }
            }

            //2. The system lists the question types
            System.out.println(separator);
            List<String> questionTypes = ctrl.getQuestionTypes();
            listQuestionTypes(questionTypes);
            System.out.println(separator);

            //3. The user chooses a question type
            String questionType = Console.readLine(requestQuestionTypeMessage);
            if (questionType.equalsIgnoreCase(exitCode)) {
                return;
            }
            int option = ctrl.extractOption(questionType);

            while (option == -1) {
                questionType = Console.readLine(invalidQuestionTypeMessage);
                if (questionType.equalsIgnoreCase(exitCode)) {
                    return;
                }
                option = ctrl.extractOption(questionType);
            }

            //4. The user inserts que ID of the quesion
            String questionID = Console.readLine(idQuestionMessage);
            if (questionID.equalsIgnoreCase(exitCode)) {
                return;
            }

            //5. The user inserts the question itself
            String questionText = Console.readLine(txtQuestionMessage);
            if (questionText.equalsIgnoreCase(exitCode)) {
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

            System.out.println(separator);
            if (numberCategories == 0) {
                System.out.println(questionNotAddedMessage);
            } else {
                System.out.println(questionAddedMessage + numberCategories + ".");
            }

            //7. The system asks the user to proceed or exit
            System.out.println(separator);
            String choice = Console.readLine(requestConfirmationToContinue + exitMessage);
            if (choice.equalsIgnoreCase(exitCode)) {
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
        System.out.println(insertExtremesMessage);

        boolean isMaxValid = false;
        double max = 0;
        while (!isMaxValid) {
            try {
                max = Double.parseDouble(Console.readLine(insertMaximumValueMessage));
                isMaxValid = true;
            } catch (Exception ex) {
                System.out.println(invalidValueMessage);
            }
        }

        boolean isMinValid = false;
        double min = 0;
        while (!isMinValid) {
            try {
                min = Double.parseDouble(Console.readLine(insertMinimumValueMessage));
                isMinValid = true;
            } catch (ParseException ex) {
                System.out.println(invalidValueMessage);
            }
        }

        if (min > max) {
            double aux = max;
            max = min;
            min = aux;
            System.out.println(changedExtremeValuesMessage);
        }

        List<QuestionOption> values = new ArrayList<>();
        for (double i = min; i < max; i++) {
            values.add(ctrl.createNewQuantitativeQuestionOption(i));
        }
        if (ctrl.createQuestion(questionType, questionText, questionID, values) != 1) {
            System.out.println(invalidQuestionMessage);
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
        System.out.println(requestOptions);
        System.out.println(noMoreOptionsMessage);

        boolean noMoreOptions = false;
        int optionNumber = 1;
        List<QuestionOption> options = new ArrayList<>();

        while (!noMoreOptions) {
            String content = Console.readLine(optionMessage + " " + optionNumber + ":");
            try {
                QuestionOption newOption = ctrl.createNewMultipleChoiceOption(content);
                options.add(newOption);
            } catch (IllegalArgumentException ex) {
                System.out.println(invalidOptionMessage);
                optionNumber--;
            }

            optionNumber++;

            if (content.equalsIgnoreCase(NO_MORE_OPTIONS_CODE)) {
                noMoreOptions = true;
                if (options.isEmpty()) {
                    System.out.println(invalidQuestionMessage);
                } else {
                    if (ctrl.createQuestion(questionType, questionText, questionID, options) != 1) {
                        System.out.println(invalidQuestionMessage);
                    }
                }
            }
        }
    }

}
