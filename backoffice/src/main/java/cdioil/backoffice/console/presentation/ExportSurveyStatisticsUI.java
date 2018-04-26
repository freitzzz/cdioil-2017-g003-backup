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
    private final String SURVEY_WITH_NO_REVIEWS_MESSAGE = localizationHandler.getMessageValue("error_survey_with_no_reviews");
    
    /**
     * Represents a message that indicates the user that there aren't any available surveys.
     */
    private final String NO_SURVEYS = localizationHandler.getMessageValue("error_no_surveys_available");

    /**
     * Represents the exit code for the User Interface.
     */
    private final String EXIT_CODE = localizationHandler.getMessageValue("option_exit");

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String EXIT_MESSAGE = localizationHandler.getMessageValue("info_exit_message");

    /**
     * Represents a message that indicates the user that there was an error exporting the file.
     */
    private final String EXPORTED_SURVEY_ANSWERS_FAILURE_MESSAGE = localizationHandler.getMessageValue("error_exporting_stats");

    /**
     * Represents a message that indicates the user that the stats were successfully exported.
     */
    private final String EXPORTED_SURVEY_ANSWERS_SUCCESS_MESSAGE = localizationHandler.getMessageValue("info_success_exporting_stats");

    /**
     * Represents a message that indicates the user to select a survey.
     */
    private final String SELECT_SURVEY_MESSAGE = localizationHandler.getMessageValue("request_select_survey");

    /**
     * Represents a message used to list the surveys.
     */
    private final String SURVEY_MESSAGE = localizationHandler.getMessageValue("info_survey_message");

    /**
     * Represents a message that informs the user that the survey is not valid.
     */
    private final String INVALID_SURVEY_MESSAGE = localizationHandler.getMessageValue("error_invalid_survey");

    /**
     * Represents a message that indicates the user to insert the path of the file.
     */
    private final String INSERT_PATH_MESSAGE = localizationHandler.getMessageValue("request_file_path");

    /**
     * Separator used for clarity.
     */
    private final String SEPARATOR = localizationHandler.getMessageValue("separator");

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
        System.out.println(SEPARATOR);
        System.out.println(EXIT_MESSAGE);

        //1. List all surveys
        List<Survey> surveys = ctrl.getAllSurveys();
        if (listAllSurveys(surveys) == false) {
            System.out.println(NO_SURVEYS);
            System.out.println(SEPARATOR);
            return;
        }
        System.out.println(SEPARATOR);

        //2. User chooses survey
        boolean isSurveyIDValid = false;
        Survey survey;

        while (!isSurveyIDValid) {
            String surveyID = Console.readLine(SELECT_SURVEY_MESSAGE);
            if (surveyID != null && surveyID.equalsIgnoreCase(EXIT_CODE)) return;
            
            survey = ctrl.getChosenSurvey(surveyID);
            if (survey == null) System.out.println(INVALID_SURVEY_MESSAGE);
            else if(ctrl.getAllReviews() == null) System.out.println(SURVEY_WITH_NO_REVIEWS_MESSAGE + " " + EXIT_MESSAGE);
            else isSurveyIDValid = true;
        }
            boolean isPathValid = false;
            while (!isPathValid) {
                //3. Inserts the path of the file
                String filePath = Console.readLine(INSERT_PATH_MESSAGE);
                if (filePath != null && filePath.equalsIgnoreCase(EXIT_CODE)) return;
       
                //3. Exports the file
                if (ctrl.exportStatsFromSurvey(filePath)) {
                    System.out.println(EXPORTED_SURVEY_ANSWERS_SUCCESS_MESSAGE);
                    isPathValid = true;
                } else {
                    System.out.println(EXPORTED_SURVEY_ANSWERS_FAILURE_MESSAGE);
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
        if (surveys == null || surveys.isEmpty()) return false;
        int cont = 1;
        for (Survey s : surveys) {
            System.out.println("\n" + SURVEY_MESSAGE + " " + cont + ":\n");
            System.out.println(s.toString());
            cont++;
        }
        return true;
    }
}
