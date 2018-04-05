package cdioil.backoffice.console.presentation;

import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.backoffice.utils.Console;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Manager;

/**
 * BackOffice's User Interface.
 */
public class MainMenu {

    /* ==============
        COMMON
       ==============
     */
    private final String SEPARATOR = "=============================";

    private final String INFO_SHUTDOWN = BackOfficeLocalizationHandler.getInstance().getMessageValue("info_shutdown");

    private final String ERROR_INVALID_OPTION = BackOfficeLocalizationHandler.getInstance().getMessageValue("error_invalid_option");
    private final String ERROR_NOT_IMPLEMENTED = BackOfficeLocalizationHandler.getInstance().getMessageValue("error_not_implemented");

    private final String REQUEST_SELECT_OPTION = BackOfficeLocalizationHandler.getInstance().getMessageValue("request_select_option");

    private final String OPTION_EXIT = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_exit");
    private final String OPTION_CHANGE_LANGUAGE = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_change_language");

    /* ==============
        MANAGER
       ==============
     */
    private final String INFO_MANAGER_HEADER = BackOfficeLocalizationHandler.getInstance().getMessageValue("info_manager_header");

    private final String OPTION_IMPORT_QUESTIONS_TEMPLATE = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_import_questions_template");
    private static final String OPTION_IMPORT_QUESTIONS_CATEGORY = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_import_questions_category");
    private static final String OPTION_EXPORT_SURVEY_ANSWERS = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_export_survey_answers");
    public static final String OPTION_CREATE_SURVEY = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_create_survey");

    /* ==============
        ADMIN
       ==============
     */
    private final String INFO_ADMIN_HEADER = BackOfficeLocalizationHandler.getInstance().getMessageValue("info_admin_header");

    private final String OPTION_ASSIGN_MANAGER = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_assign_manager");
    private final String OPTION_WHITELIST_DOMAIN = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_whitelist_domain");
    private final String OPTION_DISPLAY_USERS = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_display_users");
    private final String OPTION_IMPORT_USERS = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_import_users");
    private final String OPTION_REGISTER_USER = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_register_user");
    private final String OPTION_UPDATE_DATA = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_update_data");
    private final String OPTION_IMPORT_CATEGORIES = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_import_categories");
    private final String OPTION_USER_BY_EMAIL = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_search_user_by_email");
    private final String OPTION_REMOVE_CATEGORIES_FROM_MANAGER = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_remove_categories_from_manager");
    private final String OPTION_ASSOCIATE_CATEGORIES_TO_MANAGER = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_associate_categories_to_manager");
    private final String OPTION_LIST_CATEGORIES_WITHOUT_MANAGERS = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_list_categories_without_managers");
    private final String OPTION_ADD_USERS_QUESTIONNAIRE = BackOfficeLocalizationHandler.getInstance().getMessageValue("option_add_users_questionnaire");

    public void mainLoopAdmin(Admin admin) {
        int opcao = 0;
        do {
            opcao = menuAdmin();

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
                    new ChangeLanguageUI();
                    break;
                default:
                    System.out.println(ERROR_INVALID_OPTION);
                    break;
            }
        } while (opcao != 0);
    }

    public void mainLoopGestor(Manager gestor) {
        int opcao = 0;
        do {
            opcao = menuGestor();

            switch (opcao) {
                case 0:
                    System.out.println(INFO_SHUTDOWN);
                    break;
                case 1:
                    new ImportQuestionsUI(gestor);
                    break;
                case 2:
                    new ChangeLanguageUI();
                    break;
                case 3:
                    new ExportSurveyAnswersUI();
                    break;
                case 4:
                    new AddUsersQuestionnaireUI().addUsersQuestionnaire();
                    break;
                case 5:
                    //new CreateSurveyUI();
                    break;
                default:
                    System.out.println(ERROR_INVALID_OPTION);
                    break;
            }
        } while (opcao != 0);
    }

    private int menuGestor() {
        int option = -1;
        System.out.println(SEPARATOR);
        System.out.println(INFO_MANAGER_HEADER);
        System.out.println(SEPARATOR);
        System.out.println("1. " + OPTION_IMPORT_QUESTIONS_TEMPLATE);
        System.out.println("2. " + OPTION_CHANGE_LANGUAGE);
        System.out.println("3. " + OPTION_EXPORT_SURVEY_ANSWERS);
        System.out.println("4. " + OPTION_ADD_USERS_QUESTIONNAIRE);
        System.out.println("5. " + OPTION_CREATE_SURVEY);
        System.out.println(SEPARATOR);
        System.out.println("0. " + OPTION_EXIT);
        option = Console.readInteger(REQUEST_SELECT_OPTION);
        return option;
    }

    private int menuAdmin() {
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
        System.out.println("12. " + OPTION_CHANGE_LANGUAGE);
        System.out.println(SEPARATOR);
        System.out.println("0. " + OPTION_EXIT);
        option = Console.readInteger(REQUEST_SELECT_OPTION);
        return option;
    }
}
