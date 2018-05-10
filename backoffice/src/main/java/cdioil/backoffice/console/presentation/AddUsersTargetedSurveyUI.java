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

    private final String infoActiveSurveys = localizationHandler.getMessageValue("info_active_surveys");
    
    private final String infoExitInput = localizationHandler.getMessageValue("info_exit_input");
    
    private final String infoNumUsersAddedSurvey = localizationHandler.getMessageValue("info_num_users_added_survey");

    private final String requestSelectOption = localizationHandler.getMessageValue("request_select_option");
    
    private final String requestSurvey = localizationHandler.getMessageValue("request_survey");
    
    private final String requestDomain = localizationHandler.getMessageValue("request_domain");

    private final String requestUsername = localizationHandler.getMessageValue("request_username");

    private final String requestBirthYear = localizationHandler.getMessageValue("request_birth_year");

    private final String requestLocation = localizationHandler.getMessageValue("request_location");

    private final String requestContinue = localizationHandler.getMessageValue("request_continue");

    private final String errorInvalidOption = localizationHandler.getMessageValue("error_invalid_option");

    private final String errorInvalidValue = localizationHandler.getMessageValue("error_invalid_value");

    private final String errorNoSurveysAvailable = localizationHandler.getMessageValue("error_no_surveys_available");

    private final String optionExit = localizationHandler.getMessageValue("option_exit");

    private final String optionFilterDomain = localizationHandler.getMessageValue("option_filter_domain");

    private final String optionFilterUsername = localizationHandler.getMessageValue("option_filter_username");

    private final String optionFilterYear = localizationHandler.getMessageValue("option_filter_year");

    private final String optionLocation = localizationHandler.getMessageValue("option_filter_location");

    private final String optionConfirm = localizationHandler.getMessageValue("option_confirm");
    
    private final String optionYes = localizationHandler.getMessageValue("option_yes");
    
    private final String optionNo = localizationHandler.getMessageValue("option_no");

    private final String errorNoWhitelist = localizationHandler.getMessageValue("error_no_whitelist");
    
    private final String errorNoUsersAddedSurvey = localizationHandler.getMessageValue("error_no_users_added_survey");

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

            int option = Console.readInteger(requestSelectOption);

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
                    System.out.println(errorInvalidOption);
                    break;
            }
        }
    }

    private void showSurveysPrompt() {

        List<String> surveyList = ctrl.getActiveTargetedSurveys();

        if (surveyList == null || surveyList.isEmpty()) {
            System.out.println(errorNoSurveysAvailable);
            menuIsActive = false;
        } else {
            System.out.println(infoActiveSurveys);
            //print target surveys along with an int to later fetch the chosen one from the list
            int option = -1;
            int i = 1;
            for (String survey : surveyList) {
                System.out.println(i + ". " + survey);
                i++;
            }

            while (option < 0 || option > surveyList.size()) {
                option = Console.readInteger(requestSurvey + "\nEscreva '0' para cancelar a seleção de inquérito");
                if (option == 0) {
                    menuIsActive = false;
                    return;
                }
            }

            ctrl.selectSurvey(option - 1);
        }
    }

    private void showOptionMenu() {
        System.out.println("1. " + optionFilterDomain);
        System.out.println("2. " + optionFilterUsername);
        System.out.println("3. " + optionFilterYear);
        System.out.println("4. " + optionLocation);
        System.out.println("5. " + optionConfirm);
        System.out.println("0. " + optionExit);
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
            System.out.println("0. " + optionExit);
            while (option < 0 || option > whitelist.size()) {
                option = Console.readInteger(requestDomain);
                if (option == 0) {
                    return;
                }
            }
            ctrl.selectDomain(option - 1);
        } else {
            System.out.println(errorNoWhitelist);
        }
    }

    private void showUsernamePrompt() {
        String username = Console.readLine(requestUsername + "\n" +infoExitInput);
        if (username.equalsIgnoreCase(optionExit)) {
            return;
        }
        ctrl.selectUsername(username);
    }

    private void showBirthYearPrompt() {
        boolean isValid = false;
        String year = "";
        while (!isValid) {
            year = Console.readLine(requestBirthYear + "\n" + infoExitInput);
            if (year.equalsIgnoreCase(optionExit)) {
                return;
            }
            if (!year.matches(YEAR_PATTERN)) {
                System.out.println(errorInvalidValue);
            } else {
                isValid = true;
            }
        }
        ctrl.selectBirthYear(year);
    }

    private void showLocationPrompt() {
        String location = Console.readLine(requestLocation + "\n" + infoExitInput);
        if (location.equalsIgnoreCase(optionExit)) {
            return;
        }
        ctrl.selectLocation(location);
    }

    private void showConfirmPrompt() {
        String option = Console.readLine(requestContinue);
        while (!option.equalsIgnoreCase(optionYes) && !option.equalsIgnoreCase(optionNo)) {
            option = Console.readLine(errorInvalidOption);
        }
        if (option.equalsIgnoreCase(optionYes)) {
            int numberUsers = ctrl.addUsersTargetedSurvey();
            if (numberUsers == 0) {
                System.out.println(errorNoUsersAddedSurvey);
                menuIsActive = false;
            } else {
                System.out.println(infoNumUsersAddedSurvey + ": " + numberUsers);
                menuIsActive = false;
            }
        }
    }

}
