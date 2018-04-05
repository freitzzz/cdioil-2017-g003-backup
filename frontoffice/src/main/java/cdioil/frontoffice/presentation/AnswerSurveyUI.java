package cdioil.frontoffice.presentation;

import cdioil.frontoffice.application.AnswerSurveyController;
import cdioil.frontoffice.utils.Console;

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
        controller = new AnswerSurveyController();

        showMenu();
    }

    /**
     * Shows US menu
     */
    private void showMenu() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("             Answer Survey");
        System.out.println(LINE_SEPARATOR);

        int option;

        while (true) {
            System.out.println("0 - Exit");

            option = Console.readInteger("Option: ");

            switch (option) {
                case 0:
                    break;
                default:
                    System.out.println("ERROR: Invalid option");
                    continue;
            }
        }
    }
}
