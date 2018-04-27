package cdioil.frontoffice.presentation;

import cdioil.frontoffice.application.AnswerSurveyController;
import cdioil.console.Console;
import cdioil.domain.Review;
import cdioil.domain.authz.RegisteredUser;
import java.util.LinkedList;

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
     * User that's logged in.
     */
    private RegisteredUser loggedUser;

    /**
     * String with the text Yes.
     */
    private static final String YES = "Yes";

    /**
     * Constructor
     */
    public AnswerSurveyUI(RegisteredUser loggedUser) {
        try {
            this.loggedUser = loggedUser;
            controller = new AnswerSurveyController(this.loggedUser);
        } catch (NullPointerException e) {
            e.printStackTrace();
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
            System.out.println("1 - Show available surveys");
            System.out.println("2 - Show pending reviews");
            System.out.println("3 - Answer Survey");
            System.out.println("4 - Continue a Review");
            System.out.println("0 - Exit");

            option = Console.readInteger("Option: ");

            switch (option) {
                case 0:
                    return;
                case 1:
                    showSurveys();
                    break;
                case 2:
                    showPendingReviews();
                    break;
                case 3:
                    answerSurvey();
                    break;
                case 4:
                    answerPendingReview();
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

        if (surveys.isEmpty()) {
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

    /**
     * Shows a list of the user's pending reviews
     */
    private void showPendingReviews() {
        List<String> pendingReviews = new LinkedList<>();
        try {
            pendingReviews = controller.getPendingReviewIDs();
            if (pendingReviews.isEmpty()) {
                System.out.println("You dont have any pending reviews!");
                return;
            }
            System.out.println(LINE_SEPARATOR);
            System.out.println("     Pending Reviews");
            for (int i = 0; i < pendingReviews.size(); i++) {
                String reviewID = pendingReviews.get(i);
                System.out.println(LINE_SEPARATOR);
                System.out.println("Review #" + i);
                System.out.println(reviewID);
            }
        } catch (NullPointerException e) {
            System.out.println("You dont have any pending reviews");
        }
    }

    private void answerSurvey() {
        int idx = Console.readInteger("Survey Number");
        String option;
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
            System.out.println("Type EXIT to leave at any time\n");
            option = Console.readLine("Select an option:\n");
            if (option.equalsIgnoreCase("EXIT")) {
                if (controller.saveReview(true)) {
                    System.out.println("A sua avaliacao foi gravada com sucesso. "
                            + "Podera continuar a responder ao inquerito em qualquer"
                            + " altura");
                    break;
                }
            } else {
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

                    if (controller.saveReview(true)) {
                        System.out.println("Avaliação submetida com sucesso!");
                    }
                    break;
                }
            }

        }
    }
    
    private void answerPendingReview() {
        int idx = Console.readInteger("Review Number");
        String option;
        try {
            controller.selectPendingReview(idx);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Error: Can't find a review with that number");
            return;
        }

        while (true) {
            System.out.println(controller.getQuestion());
            List<String> options = controller.getCurrentQuestionOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println(i + options.get(i));
            }
            System.out.println("Type EXIT to leave at any time\n");
            option = Console.readLine("Select an option:\n");
            if (option.equalsIgnoreCase("EXIT")) {
                if (controller.saveReview(false)) {
                    System.out.println("A sua avaliacao foi gravada com sucesso. "
                            + "Podera continuar a responder ao inquerito em qualquer"
                            + " altura");
                    break;
                }
            } else {
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

                    if (controller.saveReview(false)) {
                        System.out.println("Avaliação submetida com sucesso!");
                    }
                    break;
                }
            }

        }
    }
}
