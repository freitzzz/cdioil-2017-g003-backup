package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ImportTemplateController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.domain.authz.Manager;
import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.io.IOException;
import java.util.logging.Level;

/**
 * User Interface for US-236 (import template script from XML file).
 *
 * @author António Sousa [1161371]
 */
public class ImportTemplateUI {

    /**
     * Localization handler to load messages in several langugaes.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();

    /**
     * Represents the exit code for the User Interface.
     */
    private final String exitCode = localizationHandler.getMessageValue("option_exit");

    /**
     * Represents a message that indicates the user to enter the exit code in
     * order to exit.
     */
    private final String exitMessage = localizationHandler.getMessageValue("info_exit_message");

    /**
     * Represents a message that indicates the user to insert the path of the
     * file to import.
     */
    private final String filePathMessage = localizationHandler.getMessageValue("request_file_path");

    /**
     * Represents a message that indicates the user that the template was
     * successfully imported.
     */
    private final String successMessage = localizationHandler.getMessageValue("info_success_importing_template");

    /**
     * Represents a message that indicates the user that there was an error
     * while importing the template.
     */
    private final String failedMessage = localizationHandler.getMessageValue("error_importing_template_failed");

    /**
     * Separator used for clarity.
     */
    private final String separator = localizationHandler.getMessageValue("separator");

    /**
     * Instance of Controller that intermediates the interactions between the
     * manager and the system.
     */
    private final ImportTemplateController ctrl;

    /**
     * Creates a new User Interface.
     *
     * @param manager
     */
    public ImportTemplateUI(Manager manager) {
        ctrl = new ImportTemplateController(manager);
        importTemplate();
    }

    /**
     * Method that intermediates the interactions with the manager (creates the
     * UI itself).
     */
    private void importTemplate() {
        System.out.println(separator);
        System.out.println(exitMessage);

        boolean exit = false;
        while (!exit) {
            String filePath = Console.readLine(filePathMessage);
            if (filePath.equalsIgnoreCase(exitCode)) {
                return;
            }

            if (filePath.trim().isEmpty()) {
                continue;
            }

            boolean imported = false;

            try {
                imported = ctrl.importTemplate(filePath);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ExceptionLogger.logException(LoggerFileNames.BACKOFFICE_LOGGER_FILE_NAME, Level.SEVERE, ex.getMessage());
            }

            if (imported) {
                System.out.println(successMessage);
                exit = true;
            } else {
                System.out.println(failedMessage);
            }
        }
    }
}
