/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ExportSurveyStatisticsController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.domain.Survey;
import java.util.List;

/**
 * User Interface for the User Story 610 - Export Survey Statistics for a CSV file.
 *
 * @author Rita Gonçalves (1160912)
 */
public class ExportSurveyStatisticsUI {

    /**
     * Localization handler to load messages in several langugaes.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();

    /**
     * Represents a message that indicates the user that the chosen survey doesn't have any reviews.
     */
    private final String errorNoReviews = localizationHandler.getMessageValue("error_survey_with_no_reviews");

    /**
     * Represents a message that indicates the user that there aren't any available surveys.
     */
    private final String errorNoSurveys = localizationHandler.getMessageValue("error_no_surveys_available");

    /**
     * Represents the exit code for the User Interface.
     */
    private final String exitCode = localizationHandler.getMessageValue("option_exit");

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String exitMessage = localizationHandler.getMessageValue("info_exit_message");

    /**
     * Represents a message that indicates the user that there was an error exporting the file.
     */
    private final String errorExportationFailed = localizationHandler.getMessageValue("error_exporting_stats");

    /**
     * Represents a message that indicates the user that the stats were successfully exported.
     */
    private final String exportationSucceeded = localizationHandler.getMessageValue("info_success_exporting_stats");

    /**
     * Represents a message that indicates the user to select a survey.
     */
    private final String requestSelectSurvey = localizationHandler.getMessageValue("request_select_survey");

    /**
     * Represents a message used to list the surveys.
     */
    private final String listAllSurveys = localizationHandler.getMessageValue("info_survey_message");

    /**
     * Represents a message that informs the user that the survey is not valid.
     */
    private final String errorInvalidSurvey = localizationHandler.getMessageValue("error_invalid_survey");

    /**
     * Represents a message that indicates the user to insert the path of the file.
     */
    private final String requestInsertPath = localizationHandler.getMessageValue("request_file_path");

    /**
     * Separator used for clarity.
     */
    private final String separator = localizationHandler.getMessageValue("separator");

    /**
     * Instance of Controller that intermediates the interactions between the administrator and the domain classes.
     */
    private final ExportSurveyStatisticsController ctrl;

    /**
     * Creates the UI itself.
     */
    public ExportSurveyStatisticsUI() {
        ctrl = new ExportSurveyStatisticsController();
        doShow();
    }

    private void doShow() {
        System.out.println(separator);
        System.out.println(exitMessage);

        //1. List all surveys
        List<Survey> surveys = ctrl.getAllSurveys();
        if (!listAllSurveys(surveys)) {
            System.out.println(errorNoSurveys);
            System.out.println(separator);
            return;
        }
        System.out.println(separator);

        //2. User chooses survey
        boolean isSurveyIDValid = false;
        Survey survey;

        while (!isSurveyIDValid) {
            String surveyID = Console.readLine(requestSelectSurvey);
            if (surveyID != null && surveyID.equalsIgnoreCase(exitCode)) {
                return;
            }

            survey = ctrl.getChosenSurvey(surveyID);
            if (survey == null) {
                System.out.println(errorInvalidSurvey);
            } else if (ctrl.getAllReviews().isEmpty()) {
                System.out.println(errorNoReviews + " " + exitMessage);
            } else {
                isSurveyIDValid = true;
            }
        }
        boolean isPathValid = false;
        while (!isPathValid) {
            //3. Inserts the path of the file
            String filePath = Console.readLine(requestInsertPath);
            if (filePath != null && filePath.equalsIgnoreCase(exitCode)) {
                return;
            }

            //3. Exports the file
            if (ctrl.exportStatsFromSurvey(filePath)) {
                System.out.println(exportationSucceeded);
                isPathValid = true;
            } else {
                System.out.println(errorExportationFailed);
            }

        }
    }

    /**
     * Lists all available surveys.
     *
     * @param surveys List with all surveys
     * @return true, if there are surveys to list. Otherwise, returns false
     */
    private boolean listAllSurveys(List<Survey> surveys) {
        if (surveys == null || surveys.isEmpty()) {
            return false;
        }
        int cont = 1;
        for (Survey s : surveys) {
            System.out.println("\n" + listAllSurveys + " " + cont + ":\n");
            System.out.println(s.toString());
            cont++;
        }
        return true;
    }
}
