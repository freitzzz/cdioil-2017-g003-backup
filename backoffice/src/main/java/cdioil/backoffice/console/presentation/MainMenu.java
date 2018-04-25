package cdioil.backoffice.console.presentation;

import cdioil.application.authz.AuthenticationController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.domain.authz.Manager;

/**
 * BackOffice's User Interface.
 */
public class MainMenu {

    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();

    /* ==============
        COMMON
       ==============
     */
    private final String SEPARATOR = "=============================";

    private String INFO_SHUTDOWN = localizationHandler.getMessageValue("info_shutdown");

    private String ERROR_INVALID_OPTION = localizationHandler.getMessageValue("error_invalid_option");
    private String ERROR_NOT_IMPLEMENTED = localizationHandler.getMessageValue("error_not_implemented");

    private String REQUEST_SELECT_OPTION = localizationHandler.getMessageValue("request_select_option");

    private String OPTION_EXIT = localizationHandler.getMessageValue("option_exit");
    private String OPTION_CHANGE_LANGUAGE = localizationHandler.getMessageValue("option_change_language");

    /* ==============
        MANAGER
       ==============
     */
    private String INFO_MANAGER_HEADER = localizationHandler.getMessageValue("info_manager_header");

    private String OPTION_IMPORT_QUESTIONS_TEMPLATE = localizationHandler.getMessageValue("option_import_questions_template");
    private String OPTION_IMPORT_QUESTIONS_CATEGORY = localizationHandler.getMessageValue("option_import_questions_category");
    private String OPTION_EXPORT_SURVEY_ANSWERS = localizationHandler.getMessageValue("option_export_survey_answers");
    private String OPTION_CREATE_SURVEY = localizationHandler.getMessageValue("option_create_survey");
    private String OPTION_INSERT_QUESTION_CATEGORY = localizationHandler.getMessageValue("option_insert_question_category");
    private String OPTION_EXPORT_STATISTICS_SURVEY = localizationHandler.getMessageValue("option_export_stats_survey");
    private String OPTION_CREATE_TEMPLATE = localizationHandler.getMessageValue("option_create_template");


    /* ==============
        ADMIN
       ==============
     */
    private String INFO_ADMIN_HEADER = localizationHandler.getMessageValue("info_admin_header");

    private String OPTION_ASSIGN_MANAGER = localizationHandler.getMessageValue("option_assign_manager");
    private String OPTION_WHITELIST_DOMAIN = localizationHandler.getMessageValue("option_whitelist_domain");
    private String OPTION_DISPLAY_USERS = localizationHandler.getMessageValue("option_display_users");
    private String OPTION_IMPORT_USERS = localizationHandler.getMessageValue("option_import_users");
    private String OPTION_REGISTER_USER = localizationHandler.getMessageValue("option_register_user");
    private String OPTION_UPDATE_DATA = localizationHandler.getMessageValue("option_update_data");
    private String OPTION_IMPORT_CATEGORIES = localizationHandler.getMessageValue("option_import_categories");
    private String OPTION_USER_BY_EMAIL = localizationHandler.getMessageValue("option_search_user_by_email");
    private String OPTION_REMOVE_CATEGORIES_FROM_MANAGER = localizationHandler.getMessageValue("option_remove_categories_from_manager");
    private String OPTION_ASSOCIATE_CATEGORIES_TO_MANAGER = localizationHandler.getMessageValue("option_associate_categories_to_manager");
    private String OPTION_LIST_CATEGORIES_WITHOUT_MANAGERS = localizationHandler.getMessageValue("option_list_categories_without_managers");
    private String OPTION_ADD_USERS_QUESTIONNAIRE = localizationHandler.getMessageValue("option_add_users_questionnaire");
    private String OPTION_IMPORT_PRODUCTS = localizationHandler.getMessageValue("option_import_products");

    public void mainLoopAdmin(AuthenticationController authenticationController) {
        int opcao = 0;
        do {
            opcao = adminMenu();

            switch (opcao) {
                case 0:
                    System.out.println(INFO_SHUTDOWN);
                    break;
                case 1:
                    new AssignManagerUI();
                    break;
                case 2:
                    new AddWhitelistUI();
                    break;
                case 3:
                    new ImportUsersFromFilesUI();
                    break;
                case 4:
                    new ListUsersUI().listAllUsers();
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    new ImportCategoriesUI();
                    break;
                case 8:
                    new SearchUserUI();
                    break;
                case 9:
                    new AssociateCategoriesUI();
                    break;
                case 10:
                    new RemoveCategoriesUI().doShow();
                    break;
                case 11:
                    new ListCategoriesUI().listCategoriesWithoutManagers();
                    break;
                case 12:
                    new ImportQuestionsUI();
                    break;
                case 13:
                    new ImportProductsUI();
                    break;
                case 14:
                    new ChangeLanguageUI();
                    refreshLocalizedMessages();
                    break;
                default:
                    System.out.println(ERROR_INVALID_OPTION);
                    break;
            }
        } while (opcao != 0);
    }

    public void mainLoopManager(AuthenticationController controller) {
        int opcao = 0;
        do {
            opcao = managerMenu();

            switch (opcao) {
                case 0:
                    System.out.println(INFO_SHUTDOWN);
                    break;
                case 1:
                    new ImportQuestionsUI((Manager)controller.getUser());
                    break;
                case 2:
                    new ChangeLanguageUI();
                    refreshLocalizedMessages();
                    break;
                case 3:
                    new ExportSurveyAnswersUI();
                    break;
                case 4:
                    new AddUsersTargetedSurveyUI();
                    break;
                case 5:
                    System.out.println(controller.getUser());
                    new CreateSurveyUI((Manager)controller.getUser());
                    break;
                case 6:
                    new InsertQuestionUI((Manager)controller.getUser());
                    break;
                case 7:
                    new ExportSurveyStatisticsUI();
                    break;
                case 8:
                    new CreateTemplateUI((Manager)controller.getUser());
                    break;
                default:
                    System.out.println(ERROR_INVALID_OPTION);
                    break;
            }
        } while (opcao != 0);
    }

    private int managerMenu() {
        int option = -1;
        System.out.println(SEPARATOR);
        System.out.println(INFO_MANAGER_HEADER);
        System.out.println(SEPARATOR);
        System.out.println("1. " + OPTION_IMPORT_QUESTIONS_CATEGORY);
        System.out.println("2. " + OPTION_CHANGE_LANGUAGE);
        System.out.println("3. " + OPTION_EXPORT_SURVEY_ANSWERS);
        System.out.println("4. " + OPTION_ADD_USERS_QUESTIONNAIRE);
        System.out.println("5. " + OPTION_CREATE_SURVEY);
        System.out.println("6. " + OPTION_INSERT_QUESTION_CATEGORY);
        System.out.println("7. " + OPTION_EXPORT_STATISTICS_SURVEY);
        System.out.println("8. " + OPTION_CREATE_TEMPLATE);
        System.out.println(SEPARATOR);
        System.out.println("0. " + OPTION_EXIT);
        option = Console.readInteger(REQUEST_SELECT_OPTION);
        return option;
    }

    private int adminMenu() {
        int option = -1;
        System.out.println(SEPARATOR);
        System.out.println(INFO_ADMIN_HEADER);
        System.out.println(SEPARATOR);
        System.out.println("1. " + OPTION_ASSIGN_MANAGER);
        System.out.println("2. " + OPTION_WHITELIST_DOMAIN);
        System.out.println("3. " + OPTION_IMPORT_USERS);
        System.out.println("4. " + OPTION_DISPLAY_USERS);
        System.out.println("5. " + OPTION_REGISTER_USER);
        System.out.println("6. " + OPTION_UPDATE_DATA);
        System.out.println("7. " + OPTION_IMPORT_CATEGORIES);
        System.out.println("8. " + OPTION_USER_BY_EMAIL);
        System.out.println("9. " + OPTION_ASSOCIATE_CATEGORIES_TO_MANAGER);
        System.out.println("10. " + OPTION_REMOVE_CATEGORIES_FROM_MANAGER);
        System.out.println("11. " + OPTION_LIST_CATEGORIES_WITHOUT_MANAGERS);
        System.out.println("12. " + OPTION_IMPORT_QUESTIONS_TEMPLATE);
        System.out.println("13. " + OPTION_IMPORT_PRODUCTS);
        System.out.println("14. " + OPTION_CHANGE_LANGUAGE);
        System.out.println(SEPARATOR);
        System.out.println("0. " + OPTION_EXIT);
        option = Console.readInteger(REQUEST_SELECT_OPTION);
        return option;
    }

    /**
     * Updates UI messages.
     */
    private void refreshLocalizedMessages() {

        INFO_SHUTDOWN = localizationHandler.getMessageValue("info_shutdown");

        ERROR_INVALID_OPTION = localizationHandler.getMessageValue("error_invalid_option");
        ERROR_NOT_IMPLEMENTED = localizationHandler.getMessageValue("error_not_implemented");

        REQUEST_SELECT_OPTION = localizationHandler.getMessageValue("request_select_option");

        OPTION_EXIT = localizationHandler.getMessageValue("option_exit");
        OPTION_CHANGE_LANGUAGE = localizationHandler.getMessageValue("option_change_language");

        INFO_MANAGER_HEADER = localizationHandler.getMessageValue("info_manager_header");

        OPTION_IMPORT_QUESTIONS_TEMPLATE = localizationHandler.getMessageValue("option_import_questions_template");
        OPTION_IMPORT_QUESTIONS_CATEGORY = localizationHandler.getMessageValue("option_import_questions_category");
        OPTION_EXPORT_SURVEY_ANSWERS = localizationHandler.getMessageValue("option_export_survey_answers");
        OPTION_CREATE_SURVEY = localizationHandler.getMessageValue("option_create_survey");
        OPTION_INSERT_QUESTION_CATEGORY = localizationHandler.getMessageValue("option_insert_question_category");

        INFO_ADMIN_HEADER = localizationHandler.getMessageValue("info_admin_header");

        OPTION_ASSIGN_MANAGER = localizationHandler.getMessageValue("option_assign_manager");
        OPTION_WHITELIST_DOMAIN = localizationHandler.getMessageValue("option_whitelist_domain");
        OPTION_DISPLAY_USERS = localizationHandler.getMessageValue("option_display_users");
        OPTION_IMPORT_USERS = localizationHandler.getMessageValue("option_import_users");
        OPTION_REGISTER_USER = localizationHandler.getMessageValue("option_register_user");
        OPTION_UPDATE_DATA = localizationHandler.getMessageValue("option_update_data");
        OPTION_IMPORT_CATEGORIES = localizationHandler.getMessageValue("option_import_categories");
        OPTION_USER_BY_EMAIL = localizationHandler.getMessageValue("option_search_user_by_email");
        OPTION_REMOVE_CATEGORIES_FROM_MANAGER = localizationHandler.getMessageValue("option_remove_categories_from_manager");
        OPTION_ASSOCIATE_CATEGORIES_TO_MANAGER = localizationHandler.getMessageValue("option_associate_categories_to_manager");
        OPTION_LIST_CATEGORIES_WITHOUT_MANAGERS = localizationHandler.getMessageValue("option_list_categories_without_managers");
        OPTION_ADD_USERS_QUESTIONNAIRE = localizationHandler.getMessageValue("option_add_users_questionnaire");
        OPTION_EXPORT_STATISTICS_SURVEY = localizationHandler.getMessageValue("option_export_stats_survey");
        OPTION_CREATE_TEMPLATE = localizationHandler.getMessageValue("option_create_template");
    }

}
