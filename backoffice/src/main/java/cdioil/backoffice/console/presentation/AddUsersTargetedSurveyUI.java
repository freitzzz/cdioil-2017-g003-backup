package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.AddUsersTargetedSurveyController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import java.util.List;

/**
 * UI of US Add Users to TargetedSurvey
 *
 * @author João
 * @author António Sousa [1161371]
 * @author Pedro Portela
 */
public class AddUsersTargetedSurveyUI {

    private AddUsersTargetedSurveyController ctrl;

    private BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();

    private final String INFO_ACTIVE_SURVEYS = localizationHandler.getMessageValue("info_active_surveys");
    
    private final String INFO_EXIT_INPUT = localizationHandler.getMessageValue("info_exit_input");
    
    private final String INFO_NUM_USERS_ADDED_SURVEY = localizationHandler.getMessageValue("info_num_users_added_survey");

    private final String REQUEST_SELECT_OPTION = localizationHandler.getMessageValue("request_select_option");
    
    private final String REQUEST_SURVEY = localizationHandler.getMessageValue("request_survey");
    
    private final String REQUEST_DOMAIN = localizationHandler.getMessageValue("request_domain");

    private final String REQUEST_USERNAME = localizationHandler.getMessageValue("request_username");

    private final String REQUEST_BIRTH_YEAR = localizationHandler.getMessageValue("request_birth_year");

    private final String REQUEST_LOCATION = localizationHandler.getMessageValue("request_location");

    private final String REQUEST_CONTINUE = localizationHandler.getMessageValue("request_continue");

    private final String ERROR_INVALID_OPTION = localizationHandler.getMessageValue("error_invalid_option");

    private final String ERROR_INVALID_VALUE = localizationHandler.getMessageValue("invalid_value");

    private final String ERROR_NO_SURVEYS_AVAILABLE = localizationHandler.getMessageValue("error_no_surveys_available");

    private final String OPTION_EXIT = localizationHandler.getMessageValue("option_exit");

    private final String OPTION_FILTER_DOMAIN = localizationHandler.getMessageValue("option_filter_domain");

    private final String OPTION_FILTER_USERNAME = localizationHandler.getMessageValue("option_filter_username");

    private final String OPTION_FILTER_YEAR = localizationHandler.getMessageValue("option_filter_year");

    private final String OPTION_LOCATION = localizationHandler.getMessageValue("option_filter_location");

    private final String OPTION_CONFIRM = localizationHandler.getMessageValue("option_confirm");
    
    private final String OPTION_YES = localizationHandler.getMessageValue("option_yes");
    
    private final String OPTION_NO = localizationHandler.getMessageValue("option_no");

    private final String ERROR_NO_WHITELIST = localizationHandler.getMessageValue("error_no_whitelist");
    
    private final String ERROR_NO_USERS_ADDED_SURVEY = localizationHandler.getMessageValue("error_no_users_added_survey");

    private static final String YEAR_PATTERN = "[0-9]{4}";

    private boolean menuIsActive = true;

    /**
     * Constructs a new instance of AddUsersTargetedSurveyUI.
     */
    public AddUsersTargetedSurveyUI() {
        ctrl = new AddUsersTargetedSurveyController();
        addUsersTargetedSurvey();
    }

    /**
     * Displays the user interface for adding registered users to targeted
     * surveys.
     */
    private void addUsersTargetedSurvey() {

        showSurveysPrompt();

        while (menuIsActive) {

            showOptionMenu();

            int option = Console.readInteger(REQUEST_SELECT_OPTION);

            switch (option) {

                case 1:
                    showWhitelistedDomainMenu();
                    break;

                case 2:
                    showUsernamePrompt();
                    break;

                case 3:
                    showBirthYearPrompt();
                    break;

                case 4:
                    showLocationPrompt();
                    break;

                case 5:
                    showConfirmPrompt();
                    break;

                case 0:
                    menuIsActive = false;
                    break;

                default:
                    System.out.println(ERROR_INVALID_OPTION);
                    break;
            }
        }
    }

    private void showSurveysPrompt() {

        List<String> surveyList = ctrl.getActiveTargetedSurveys();

        if (surveyList == null) {
            System.out.println(ERROR_NO_SURVEYS_AVAILABLE);
            menuIsActive = false;
        } else {
            System.out.println(INFO_ACTIVE_SURVEYS);
            //print target surveys along with an int to later fetch the chosen one from the list
            int option = -1;
            int i = 1;
            for (String survey : surveyList) {
                System.out.println(i + ". " + survey);
                i++;
            }

            while (option < 0 || option > surveyList.size()) {
                option = Console.readInteger(REQUEST_SURVEY + "\nEscreva '0' para cancelar a seleção de inquérito");
                if (option == 0) {
                    menuIsActive = false;
                    return;
                }
            }

            ctrl.selectSurvey(option - 1);
        }
    }

    private void showOptionMenu() {
        System.out.println("1. " + OPTION_FILTER_DOMAIN);
        System.out.println("2. " + OPTION_FILTER_USERNAME);
        System.out.println("3. " + OPTION_FILTER_YEAR);
        System.out.println("4. " + OPTION_LOCATION);
        System.out.println("5. " + OPTION_CONFIRM);
        System.out.println("0. " + OPTION_EXIT);
    }

    private void showWhitelistedDomainMenu() {
        List<String> whitelist = ctrl.getWhitelistedDomains();
        //check if there are whitelisted domains
        if (whitelist != null) {
            int option = -1;
            int i = 1;
            for (String domain : whitelist) {
                System.out.println(i + ". " + domain);
                i++;
            }
            System.out.println("0. " + OPTION_EXIT);
            while (option < 0 || option > whitelist.size()) {
                option = Console.readInteger(REQUEST_DOMAIN);
                if (option == 0) {
                    return;
                }
            }
            ctrl.selectDomain(option - 1);
        } else {
            System.out.println(ERROR_NO_WHITELIST);
        }
    }

    private void showUsernamePrompt() {
        String username = Console.readLine(REQUEST_USERNAME + "\n" +INFO_EXIT_INPUT);
        if (username.equalsIgnoreCase(OPTION_EXIT)) {
            return;
        }
        ctrl.selectUsername(username);
    }

    private void showBirthYearPrompt() {
        boolean isValid = false;
        String year = "";
        while (!isValid) {
            year = Console.readLine(REQUEST_BIRTH_YEAR + "\n" + INFO_EXIT_INPUT);
            if (year.equalsIgnoreCase(OPTION_EXIT)) {
                return;
            }
            if (!year.matches(YEAR_PATTERN)) {
                System.out.println(ERROR_INVALID_VALUE);
            } else {
                isValid = true;
            }
        }
        ctrl.selectBirthYear(year);
    }

    private void showLocationPrompt() {
        String location = Console.readLine(REQUEST_LOCATION + "\n" + INFO_EXIT_INPUT);
        if (location.equalsIgnoreCase(OPTION_EXIT)) {
            return;
        }
        ctrl.selectLocation(location);
    }

    private void showConfirmPrompt() {
        String option = Console.readLine(REQUEST_CONTINUE);
        while (!option.equalsIgnoreCase(OPTION_YES) && !option.equalsIgnoreCase(OPTION_NO)) {
            option = Console.readLine(ERROR_INVALID_OPTION);
        }
        if (option.equalsIgnoreCase(OPTION_YES)) {
            int numberUsers = ctrl.addUsersTargetedSurvey();
            if (numberUsers == 0) {
                System.out.println(ERROR_NO_USERS_ADDED_SURVEY);
                menuIsActive = false;
            } else {
                System.out.println(INFO_NUM_USERS_ADDED_SURVEY + ": " + numberUsers);
                menuIsActive = false;
            }
        }
    }

}
