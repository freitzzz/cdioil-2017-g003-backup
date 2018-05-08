package cdioil.backoffice.console.presentation;

import cdioil.files.InvalidFileFormattingException;
import cdioil.backoffice.application.ImportQuestionsController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.domain.authz.Manager;

/**
 * User Interface for use cases US-205 (import independent questions from file) and US-210 (import category questions from file).
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
    private final String optionExit = localizationHandler.getMessageValue("option_exit");
    /**
     * Represents a message that indicates the user to insert the path of the file to import.
     */
    private final String requestFilePath = localizationHandler.getMessageValue("request_file_path");
    /**
     * Represents a message that informs the user that the inserted filepath was not found.
     */
    private final String errorFileNotFound = localizationHandler.getMessageValue("error_file_not_found");
    /**
     * Represents a message that delimitates the imported questions.
     */
    private final String infoNumQuestionsImported = localizationHandler.getMessageValue("info_num_questions_imported");
    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String infoExitInput = localizationHandler.getMessageValue("info_exit_input");

    /**
     * Represents a message that indicate the invalid file format.
     */
    private final String errorInvalidFileFormat = localizationHandler.getMessageValue("error_invalid_file_format");

    /**
     * Represents a message that indicate that no imported questions
     */
    private final String errorNoImportedQuestions = localizationHandler.getMessageValue("error_no_imported_questions");

    /**
     * Option for import the category questions
     */
    private static final int CATEGORY_QUESTIONS_FILE_TYPE = 0;
    /**
     * Option for import the independet questions
     */
    private static final int INDEPENDENT_QUESTIONS_FILE_TYPE = 1;

    /**
     * Instance of Controller that intermediates the interactions between the manager and the system.
     */
    private final ImportQuestionsController controller;

    /**
     * Creates a new User Interface for use case US-205.
     */
    public ImportQuestionsUI() {
        controller = new ImportQuestionsController();
        importCategories(INDEPENDENT_QUESTIONS_FILE_TYPE);
    }

    /**
     * Creates a new User Interface for use case US-210.
     *
     * @param manager Manager
     */
    public ImportQuestionsUI(Manager manager) {
        controller = new ImportQuestionsController(manager);
        importCategories(CATEGORY_QUESTIONS_FILE_TYPE);
    }

    /**
     * Method for showing the UI itself.
     */
    private void importCategories(int fileType) {

        while (true) {

            String fileName = Console.readLine(requestFilePath + "\n" + infoExitInput);

            if (fileName.equalsIgnoreCase(optionExit)) {
                break;
            }
            try {
                Integer numImportedQuestions = 0;

                if (fileType == CATEGORY_QUESTIONS_FILE_TYPE) {
                    numImportedQuestions = controller.importCategoryQuestions(fileName);

                } else if (fileType == INDEPENDENT_QUESTIONS_FILE_TYPE) {
                    numImportedQuestions = controller.importIndependentQuestions(fileName);
                }

                if (numImportedQuestions == null) {
                    System.out.println(errorFileNotFound);
                } else if (numImportedQuestions == 0) {
                    System.out.println(errorNoImportedQuestions);
                } else if (numImportedQuestions > 0) {
                    System.out.println(infoNumQuestionsImported + " " + numImportedQuestions);
                }
            } catch (InvalidFileFormattingException e) {
                System.out.println(errorInvalidFileFormat);
            }
        }

    }
}
