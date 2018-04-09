package cdioil.backoffice.console.presentation;

import cdioil.application.utils.InvalidFileFormattingException;
import cdioil.backoffice.application.ImportQuestionsController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.backoffice.utils.Console;
import cdioil.domain.authz.Manager;

/**
 * User Interface for use cases US-205 (import independent questions from file)
 * and US-210 (import category questions from file).
 *
 * @author Ana Guerra (1161191)
 * @author António Sousa [1161371]
 */
public class ImportQuestionsUI {

    /**
     * Single instance of <code>BackOfficeLocalizationHandler</code>.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();
    /**
     * Represents the exit code for the User Interface.
     */
    private final String OPTION_EXIT = localizationHandler.getMessageValue("option_exit");
    /**
     * Represents a message that indicates the user to insert the path of the
     * file to import.
     */
    private final String REQUEST_FILE_PATH = localizationHandler.getMessageValue("request_file_path");
    /**
     * Represents a message that informs the user that the inserted filepath was
     * not found.
     */
    private final String ERROR_FILE_NOT_FOUND = localizationHandler.getMessageValue("error_file_not_found");
    /**
     * Represents a message that informs the user that the format of the file is
     * not valid.
     */
    private final String INVALID_FORMAT_MESSAGE = "Nenhuma questão válida foi importada!";
    /**
     * Represents a message that delimitates the imported questions.
     */
    private final String INFO_NUM_QUESTIONS_IMPORTED = localizationHandler.getMessageValue("info_num_questions_imported");
    /**
     * Represents a message that indicates the user to enter the exit code in
     * order to exit.
     */
    private final String INFO_EXIT_INPUT = localizationHandler.getMessageValue("info_exit_input");

    private final String ERROR_INVALID_FILE_FORMAT = localizationHandler.getMessageValue("error_invalid_file_format");

    private final String ERROR_NO_IMPORTED_QUESTIONS = localizationHandler.getMessageValue("error_no_imported_questions");

    /**
     * Instance of Controller that intermediates the interactions between the
     * manager and the system.
     */
    private final ImportQuestionsController controller;

    /**
     * Creates a new User Interface for use case US-205.
     */
    public ImportQuestionsUI() {
        controller = new ImportQuestionsController();
        //importIndependentQuestions();
    }

    /**
     * Creates a new User Interface for use case US-210.
     *
     * @param manager Manager
     */
    public ImportQuestionsUI(Manager manager) {
        controller = new ImportQuestionsController(manager);
        importCategoryQuestions();
    }

    /**
     * Method that intermediates the interactions with the manager (creates the
     * UI itself).
     */
    private void importCategoryQuestions() {

        while (true) {
            String fileName = Console.readLine(REQUEST_FILE_PATH + "\n" + INFO_EXIT_INPUT);

            if (fileName.equalsIgnoreCase(OPTION_EXIT)) {
                break;
            }

            try {
                int numImportedQuestions = controller.importCategoryQuestions(fileName);

                if (numImportedQuestions == 0) {
                    System.out.println(ERROR_NO_IMPORTED_QUESTIONS);
                } else {
                    System.out.println(INFO_NUM_QUESTIONS_IMPORTED + " " + numImportedQuestions);
                }
            } catch (InvalidFileFormattingException e) {
                System.out.println(ERROR_INVALID_FILE_FORMAT);
            }
        }

//        boolean catched = false;
//        while (!catched) {
//            String fileName = Console.readLine(REQUEST_FILE_PATH);
//            if (fileName.equalsIgnoreCase(OPTION_EXIT)) {
//                return;
//            }
//            try {
//                int cql = controller.importCategoryQuestions(fileName);
//            } catch (InvalidFileFormattingException e) {
//                System.out.println(INVALID_FORMAT_MESSAGE);
//            }
//
//            if (cql == 1) {
//                String choice = Console.readLine(ERROR_FILE_NOT_FOUND);
//                if (choice.equalsIgnoreCase(OPTION_EXIT)) {
//                    return;
//                }
//            }
//            if (cql == 2) {
//                System.out.println(INVALID_FORMAT_MESSAGE);
//            }
//            if (cql == 3) {
//                System.out.println(INFO_NUM_QUESTIONS_IMPORTED);
//                catched = true;
//            }
//            if (cql == 4) {
//                System.out.println(IMPORTED_SOME_QUESTIONS_MESSAGE);
//                catched = true;
//            }
//
//        }
    }
}
