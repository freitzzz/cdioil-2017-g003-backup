package cdioil.frontoffice.presentation;

import cdioil.frontoffice.application.AnswerSurveyController;
import cdioil.frontoffice.utils.Console;

import java.util.List;

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

    /**
     * Constructor
     */
    public AnswerSurveyUI() {
        try {
            controller = new AnswerSurveyController();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Error fetching Surveys.");
            return;
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
            System.out.println("1 - Show available surveys");
            System.out.println("2 - Answer Survey");
            System.out.println("0 - Exit");

            option = Console.readInteger("Option: ");

            switch (option) {
                case 0:
                    return;
                case 1:
                    showSurveys();
                    break;
                case 2:
                    answerSurvey();
                    break;
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
        List<String> surveys = controller.getActiveSurveyIDs();

        if (surveys.size() == 0) {
            System.out.println("There are no available surveys!");
            return;
        }

        System.out.println(LINE_SEPARATOR);
        System.out.println("     Surveys");
        for (int i = 0; i < surveys.size(); i++) {
            String surveyID = surveys.get(i);
            System.out.println(LINE_SEPARATOR);
            System.out.println("Survey #" + i);
            System.out.println(surveyID);
        }
    }

    private void answerSurvey() {
        int idx = Console.readInteger("Survey Number");

        try {
            controller.selectSurvey(idx);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Error: Can't find a survey with that number");
            return;
        }

        while (true) {
            System.out.println(controller.getQuestion());
            List<String> options = controller.getCurrentQuestionOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println(i + options.get(i));
            }

            idx = Console.readInteger("Select an option:\n");
            boolean canContinue = controller.answerQuestion(idx);

            if (!canContinue) {
                if (Console.readLine("Would you like to leave a suggestion about the items?").equals(YES)) {
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
