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
    private static final String LINE_SEPARATOR =
            "===================================";

    /**
     * Controller class
     */
    private AnswerSurveyController controller;

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
        String id = Console.readLine("Survey Number");

        try {
            controller.answerSurvey(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Can't find a survey with that number");
            return;
        }

        //TODO answer survey
    }
}
