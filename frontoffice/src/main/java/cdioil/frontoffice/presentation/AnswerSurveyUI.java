package cdioil.frontoffice.presentation;

import cdioil.frontoffice.application.AnswerSurveyController;
import cdioil.console.Console;
import cdioil.domain.authz.RegisteredUser;
import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.util.LinkedList;

import java.util.List;
import java.util.logging.Level;

/**
 * User interface for AnswerSurvey User Story
 */
public class AnswerSurveyUI {

    /**
     * Line separator
     */
    private static final String LINE_SEPARATOR
            = "===================================";

    /**
     * Controller class
     */
    private AnswerSurveyController controller;

    /**
     * String with the text Yes.
     */
    private static final String YES = "Yes";

    private static final String NUMERIC_REGEX = "[0-9]+";

    /**
     * Constructor
     * @param loggedUser
     */
    public AnswerSurveyUI(RegisteredUser loggedUser) {
        try {
            controller = new AnswerSurveyController(loggedUser);
        } catch (NullPointerException e) {
            ExceptionLogger.logException(LoggerFileNames.FRONTOFFICE_LOGGER_FILE_NAME,
                    Level.SEVERE, e.getMessage());
            System.out.println("ERROR: Error fetching Surveys.");
        }

        showMenu();
    }

    /**
     * Shows US menu
     */
    private void showMenu() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("             Answer Survey");

        int option;

        while (true) {
            System.out.println(LINE_SEPARATOR);
            System.out.println("1 - Show surveys");
//            System.out.println("2 - Show pending reviews");
//            System.out.println("3 - Answer Survey");
//            System.out.println("4 - Continue a Review");
            System.out.println("0 - Exit");

            option = Console.readInteger("Option: ");

            switch (option) {
                case 0:
                    return;
                case 1:
                    showSurveys();
                    break;
//                case 2:
//                    showPendingReviews();
//                    break;
//                case 3:
//                    answerSurvey(true);
//                    break;
//                case 4:
//                    answerSurvey(false);
//                    break;
                default:
                    System.out.println("ERROR: Invalid option");
                    continue;
            }
        }
    }

    /**
     * Shows a list of all survey IDs
     */
    private void showSurveys() {
        //Refresh surveys and their review status
        controller.findActiveSurveys();
        
        List<String> surveys = controller.getSurveyDescriptions();

        if (surveys.isEmpty()) {
            System.out.println("There are no available surveys!");
            return;
        }

        System.out.println(LINE_SEPARATOR);
        System.out.println("     Surveys");
        for (int i = 0; i < surveys.size(); i++) {
            System.out.println(LINE_SEPARATOR);
            System.out.println("Survey #" + i);
            System.out.println(surveys.get(i));
        }
        
        answerSurvey();
    }

    private void answerSurvey() {

        int idx;
        String option;

        idx = Console.readInteger("Select a survey: ");

        controller.selectSurvey(idx);
        
        while (true) {
            boolean isValidOption = false;

            while (!isValidOption) {

                System.out.println(controller.getQuestion());
                List<String> options = controller.getCurrentQuestionOptions();
                for (int i = 0; i < options.size(); i++) {
                    System.out.println(i + options.get(i));
                }
                System.out.println("Type EXIT to leave at any time\n");
                option = Console.readLine("Select an option or UNDO previous answer:\n");

                if ("EXIT".equalsIgnoreCase(option)) {
                    if (controller.saveReview()) {
                        System.out.println("A sua avaliacao foi gravada com sucesso. "
                                + "Podera continuar a responder ao inquerito em qualquer"
                                + " altura");
                        return;
                    }
                } else if (option.matches(NUMERIC_REGEX)) {
                    idx = Integer.parseInt(option);
                    if (idx < options.size()) {
                        isValidOption = true;
                    }
                } else if ("UNDO".equalsIgnoreCase(option) && controller.undoAnswer()) {
                        break;
                }
            }

            if (!isValidOption) {
                continue;
            }

            boolean canContinue = controller.answerQuestionByIndex(idx);

            if (!canContinue) {
                if (Console.readLine("Would you like to leave a suggestion about the items?").equalsIgnoreCase(YES)) {
                    boolean suggestionFlag = true;
                    while (suggestionFlag) {
                        try {
                            if (controller.submitSuggestion(Console.readLine("Insert your suggestion:\n"))) {
                                System.out.println("Your suggestion has been saved!\n");
                                suggestionFlag = false;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("There was an error with your suggestion, please try again.\n");
                        }
                    }
                }

                if (controller.saveReview()) {
                    System.out.println("Avaliação submetida com sucesso!");
                }
                break;
            }
        }
    }
}
