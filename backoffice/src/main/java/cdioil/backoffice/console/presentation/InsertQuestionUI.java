/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.InsertQuestionController;
import cdioil.backoffice.utils.Console;
import cdioil.domain.QuestionOption;
import cdioil.domain.QuestionTypes;
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
     * Represents the exit code for the User Interface.
     */
    private static final String EXIT_CODE = "Sair";

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private static final String EXIT_MESSAGE = "A qualquer momento digite \"" + EXIT_CODE + "\" para sair.";

    /**
     * Represents a message that indicates the user to select a type of question.
     */
    private static final String TYPE_QUESTION_MESSAGE = "Por favor selecione o tipo de questão:";

    /**
     * Represents a message that indicates the user to select a valid type of question.
     */
    private static final String INVALID_TYPE_QUESTION_MESSAGE = "Insira um tipo de questão válido:";

    /**
     * Represents a message that indicates the user to insert the ID of the question.
     */
    private static final String ID_QUESTION_MESSAGE = "Por favor insira o ID da questão:";

    /**
     * Represents a message that indicates the user to insert the question itself.
     */
    private static final String TXT_QUESTION_MESSAGE = "Por favor insira a questão:";

    /**
     * Reprensents a message that indicates the user to insert the path of the category.
     */
    private static final String CATEGORY_PATH = "Insira o caminho da categoria:";

    /**
     * Represents a message that indicates the user to insert a valida category.
     */
    private static final String INVALID_CATEGORY_PATH = "Insira uma categoria válida:";

    ///////////////////////////////////////////////////////////////////////////////////////////
    //Constants for multiple choice questions
    /**
     * Represents a message that indicates the user to insert the options for that question.
     */
    private static final String OPTIONS_QUESTION_MESSAGE = "Por favor insira as opções:";

    /**
     * Represents a message that indicates the user to insert an option.
     */
    private static final String OPTION_MESSAGE = "Opção ";

    /**
     * Represents the exit code for inserting more options.
     */
    private static final String NO_MORE_OPTIONS_CODE = "0";

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private static final String NO_MORE_OPTIONS_MESSAGE = "Para terminar a inserção de opções escreva \"" + NO_MORE_OPTIONS_CODE + ".\"";

    /**
     * Represents a message that indicates the user to insert a valid option.
     */
    private static final String INVALID_OPTION = "A opção não foi adicionada, porque era inválida.";

    /**
     * Represents a message that indicates that the question was not added because there were no options.
     */
    private static final String INVALID_QUESTION = "A questão não foi adicionada, porque não tinha opções.";

    ///////////////////////////////////////////////////////////////////////////////////////////
    //Constants for binary questions
    /**
     * Represents a message that indicates the user to insert the maximum and minimum values of the scale.
     */
    private static final String INSERT_EXTREMES_MESSAGE = "Insira os valores mínimo e máximo para a escala:";

    /**
     * Represents a message that indicates the user to insert the maximum value of the scale.
     */
    private static final String MAXIMUM_MESSAGE = "Insira o valor máximo:";

    /**
     * Represents a message that indicates the user to insert the minimum value of the scale.
     */
    private static final String MINIMUM_MESSAGE = "Insira o valor mínimo:";

    /**
     * Represents a message that indicates the user to insert a valid number.
     */
    private static final String INVALID_VALUE = "Valor inválido!";

    /**
     * Represents a message that indicates the user that the extreme values have been changed.
     */
    private static final String CHANGED_EXTREME_VALUES = "Os valores foram trocados, porque o mínimo era superior ao máximo.";

    /**
     * Instance of Controller that intermediates the interactions between the administrator and the system.
     */
    private final InsertQuestionController ctrl;

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
        System.out.println(EXIT_MESSAGE);
        boolean catched = false;
        while (!catched) {

            //1. The user inserts the category
            String categoryPath = Console.readLine(CATEGORY_PATH);
            while (ctrl.findCategories(categoryPath) == null) {
                categoryPath = Console.readLine(INVALID_CATEGORY_PATH);
                if(categoryPath.equalsIgnoreCase(EXIT_CODE)) return;
            }
            if(categoryPath.equalsIgnoreCase(EXIT_CODE)) return;

            //2. The system lists the question types
            List<String> questionTypes = ctrl.getQuestionTypes();
            listQuestionTypes(questionTypes);
            
            //3. The user chooses a question type
            String questionType = Console.readLine(TYPE_QUESTION_MESSAGE);
            int option = ctrl.extractOption(questionType);
            while (option == -1) {
                questionType = Console.readLine(INVALID_TYPE_QUESTION_MESSAGE);
                if (questionType.equalsIgnoreCase(EXIT_CODE)) return;
            }
            if (questionType.equalsIgnoreCase(EXIT_CODE)) return;

            //4. The user inserts que ID of the quesion
            String questionID = Console.readLine(ID_QUESTION_MESSAGE);
            if (questionID.equalsIgnoreCase(EXIT_CODE)) return;

            //5. The user inserts the question itself
            String questionText = Console.readLine(TXT_QUESTION_MESSAGE);
            if (questionText.equalsIgnoreCase(EXIT_CODE)) return;
            
            switch (option) {
                case 2:  //Multiple choice
                    insertMultipleChoiceQuestion(questionType, questionText, questionID);
                    break;
                case 1: //Binary
                    insertBinaryQuestion(questionType, questionText, questionID);
                    break;
                case 3: //Quantitative
                    insertQuantitativeQuestion(questionType, questionText, questionID);
                    break;
                default:
                    break;
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
     * Allows the user to insert a binary question.
     *
     * @param questionType Type of the question (binary)
     * @param questionText Text of the question
     * @param questionID ID of the question
     */
    private void insertBinaryQuestion(String questionType, String questionText, String questionID) {
        ctrl.createQuestion(questionType, questionText, questionID, new LinkedList<>());

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
            } catch (ParseException ex) {
                System.out.println(INVALID_VALUE);
            }
        }
        boolean isMinValid = false;
        double min = 0;
        while (!isMinValid) {
            try {
                min = Double.parseDouble(Console.readLine(MINIMUM_MESSAGE));
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
        ctrl.createQuestion(questionType, questionText, questionID, values);
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
            String content = Console.readLine(OPTION_MESSAGE + optionNumber + ":");
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
                    ctrl.createQuestion(questionType, questionText, questionID, options);
                }
            }
        }
    }

}
