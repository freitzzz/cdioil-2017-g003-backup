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
    private static final String SEPARATOR = "=============================";

    private String infoShutdown = localizationHandler.getMessageValue("info_shutdown");

    private String errorInvalidOption = localizationHandler.getMessageValue("error_invalid_option");
    private String errorNotImplemented = localizationHandler.getMessageValue("error_not_implemented");

    private String requestSelectOption = localizationHandler.getMessageValue("request_select_option");

    private String optionExit = localizationHandler.getMessageValue("option_exit");
    private String optionChangeLanguage = localizationHandler.getMessageValue("option_change_language");

    /* ==============
        MANAGER
       ==============
     */
    private String infoManagerHeader = localizationHandler.getMessageValue("info_manager_header");

    private String optionImportQuestionsTemplate = localizationHandler.getMessageValue("option_import_questions_template");
    private String optionImportCategoryQuestions = localizationHandler.getMessageValue("option_import_questions_category");
    private String optionExportSurveyAnswers = localizationHandler.getMessageValue("option_export_survey_answers");
    private String optionCreateSurvey = localizationHandler.getMessageValue("option_create_survey");
    private String optionInsertCategoryQuestion = localizationHandler.getMessageValue("option_insert_question_category");
    private String optionExportSurveyStatistics = localizationHandler.getMessageValue("option_export_stats_survey");
    private String optionCreateTemplate = localizationHandler.getMessageValue("option_create_template");
    private String optionExportTemplate = localizationHandler.getMessageValue("option_export_template");
    private String optionImportTemplate = localizationHandler.getMessageValue("option_import_template");


    /* ==============
        ADMIN
       ==============
     */
    private String infoAdminHeader = localizationHandler.getMessageValue("info_admin_header");

    private String optionAssignManager = localizationHandler.getMessageValue("option_assign_manager");
    private String optionWhitelistDomain = localizationHandler.getMessageValue("option_whitelist_domain");
    private String optionDisplayUsers = localizationHandler.getMessageValue("option_display_users");
    private String optionImportUsers = localizationHandler.getMessageValue("option_import_users");
    private String optionRegisterUser = localizationHandler.getMessageValue("option_register_user");
    private String optionUpdateData = localizationHandler.getMessageValue("option_update_data");
    private String optionImportCategories = localizationHandler.getMessageValue("option_import_categories");
    private String optionUsersByEmail = localizationHandler.getMessageValue("option_search_user_by_email");
    private String optionRemoveCategoriesFromManager = localizationHandler.getMessageValue("option_remove_categories_from_manager");
    private String optionAssociateCategoriesToManager = localizationHandler.getMessageValue("option_associate_categories_to_manager");
    private String optionListCategoriesWithoutManager = localizationHandler.getMessageValue("option_list_categories_without_managers");
    private String optionAddUsersTargetedSurvey = localizationHandler.getMessageValue("option_add_users_questionnaire");
    private String optionImportProducts = localizationHandler.getMessageValue("option_import_products");

    public void mainLoopAdmin(AuthenticationController authenticationController) {
        int opcao = 0;
        do {
            opcao = adminMenu();

            switch (opcao) {
                case 0:
                    System.out.println(authenticationController.logout());
                    Console.log(infoShutdown, Console.ConsoleColors.PURPLE);
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
                    new CategoryManagementUI().addCategories();
                    break;
                case 10:
                    new CategoryManagementUI().removeCategories();
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
                    System.out.println(errorInvalidOption);
                    break;
            }
        } while (opcao != 0);
    }

    public void mainLoopManager(AuthenticationController authenticationController) {
        int opcao = 0;
        do {
            opcao = managerMenu();

            switch (opcao) {
                case 0:
                    authenticationController.logout();
                    Console.log(infoShutdown, Console.ConsoleColors.PURPLE);
                    break;
                case 1:
                    new ImportQuestionsUI((Manager) authenticationController.getUser());
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
                    System.out.println(authenticationController.getUser());
                    new CreateSurveyUI((Manager) authenticationController.getUser());
                    break;
                case 6:
                    new InsertQuestionUI((Manager) authenticationController.getUser());
                    break;
                case 7:
                    new ExportSurveyStatisticsUI();
                    break;
                case 8:
                    new CreateTemplateUI((Manager) authenticationController.getUser());
                    break;
                case 9:
                    new ExportTemplateUI((Manager)authenticationController.getUser());
                case 10:
                    new ImportTemplateUI((Manager) authenticationController.getUser());
                    break;
                default:
                    System.out.println(errorInvalidOption);
                    break;
            }
        } while (opcao != 0);
    }

    private int managerMenu() {
        int option = -1;
        System.out.println(SEPARATOR);
        System.out.println(infoManagerHeader);
        System.out.println(SEPARATOR);
        System.out.println("1. " + optionImportCategoryQuestions);
        System.out.println("2. " + optionChangeLanguage);
        System.out.println("3. " + optionExportSurveyAnswers);
        System.out.println("4. " + optionAddUsersTargetedSurvey);
        System.out.println("5. " + optionCreateSurvey);
        System.out.println("6. " + optionInsertCategoryQuestion);
        System.out.println("7. " + optionExportSurveyStatistics);
        System.out.println("8. " + optionCreateTemplate);
        System.out.println("9. " + optionExportTemplate);
        System.out.println("10. " + optionImportTemplate);
        System.out.println(SEPARATOR);
        System.out.println("0. " + optionExit);
        option = Console.readInteger(requestSelectOption);
        return option;
    }

    private int adminMenu() {
        int option = -1;
        System.out.println(SEPARATOR);
        System.out.println(infoAdminHeader);
        System.out.println(SEPARATOR);
        System.out.println("1. " + optionAssignManager);
        System.out.println("2. " + optionWhitelistDomain);
        System.out.println("3. " + optionImportUsers);
        System.out.println("4. " + optionDisplayUsers);
        System.out.println("5. " + optionRegisterUser);
        System.out.println("6. " + optionUpdateData);
        System.out.println("7. " + optionImportCategories);
        System.out.println("8. " + optionUsersByEmail);
        System.out.println("9. " + optionAssociateCategoriesToManager);
        System.out.println("10. " + optionRemoveCategoriesFromManager);
        System.out.println("11. " + optionListCategoriesWithoutManager);
        System.out.println("12. " + optionImportQuestionsTemplate);
        System.out.println("13. " + optionImportProducts);
        System.out.println("14. " + optionChangeLanguage);
        System.out.println(SEPARATOR);
        System.out.println("0. " + optionExit);
        option = Console.readInteger(requestSelectOption);
        return option;
    }

    /**
     * Updates UI messages.
     */
    private void refreshLocalizedMessages() {

        infoShutdown = localizationHandler.getMessageValue("info_shutdown");

        errorInvalidOption = localizationHandler.getMessageValue("error_invalid_option");
        errorNotImplemented = localizationHandler.getMessageValue("error_not_implemented");

        requestSelectOption = localizationHandler.getMessageValue("request_select_option");

        optionExit = localizationHandler.getMessageValue("option_exit");
        optionChangeLanguage = localizationHandler.getMessageValue("option_change_language");

        infoManagerHeader = localizationHandler.getMessageValue("info_manager_header");

        optionImportQuestionsTemplate = localizationHandler.getMessageValue("option_import_questions_template");
        optionImportCategoryQuestions = localizationHandler.getMessageValue("option_import_questions_category");
        optionExportSurveyAnswers = localizationHandler.getMessageValue("option_export_survey_answers");
        optionCreateSurvey = localizationHandler.getMessageValue("option_create_survey");
        optionInsertCategoryQuestion = localizationHandler.getMessageValue("option_insert_question_category");

        infoAdminHeader = localizationHandler.getMessageValue("info_admin_header");

        optionAssignManager = localizationHandler.getMessageValue("option_assign_manager");
        optionWhitelistDomain = localizationHandler.getMessageValue("option_whitelist_domain");
        optionDisplayUsers = localizationHandler.getMessageValue("option_display_users");
        optionImportUsers = localizationHandler.getMessageValue("option_import_users");
        optionRegisterUser = localizationHandler.getMessageValue("option_register_user");
        optionUpdateData = localizationHandler.getMessageValue("option_update_data");
        optionImportCategories = localizationHandler.getMessageValue("option_import_categories");
        optionUsersByEmail = localizationHandler.getMessageValue("option_search_user_by_email");
        optionRemoveCategoriesFromManager = localizationHandler.getMessageValue("option_remove_categories_from_manager");
        optionAssociateCategoriesToManager = localizationHandler.getMessageValue("option_associate_categories_to_manager");
        optionListCategoriesWithoutManager = localizationHandler.getMessageValue("option_list_categories_without_managers");
        optionAddUsersTargetedSurvey = localizationHandler.getMessageValue("option_add_users_questionnaire");
        optionExportSurveyStatistics = localizationHandler.getMessageValue("option_export_stats_survey");
        optionCreateTemplate = localizationHandler.getMessageValue("option_create_template");
    }

}
