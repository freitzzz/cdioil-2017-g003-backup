package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ExportTemplateController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.domain.Template;
import cdioil.domain.authz.Manager;
import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.util.List;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;

/**
 * User Interface for US-238 (Export template to a XML, CSV or JSON file).
 *
 * @author <a href="1150782@isep.ipp.pt">Pedro Portela</a>
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 * @author <a href="1161371@isep.ipp.pt">António Sousa</a>
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 */
public class ExportTemplateUI {

    /**
     * Localization handler to load messages in several languages.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();
    /**
     * Represents the exit code for the UI.
     */
    private final String exitCode = localizationHandler.getMessageValue("option_exit");
    /**
     * Represents a message that indicates the user to enter the exit code in
     * order to exit the UI.
     */
    private final String exitMessage = localizationHandler.getMessageValue("info_exit_message");
    /**
     * Represents a message that indicates the user to select a template from
     * those listed.
     */
    private final String requestSelectTemplate = localizationHandler.getMessageValue("request_select_template");
    /**
     * Represents a message that indicates the user that the template was
     * successfully exported.
     */
    private final String exportationSucceeded = localizationHandler.getMessageValue("info_success_exporting_template");
    /**
     * Represents a message that indicates the user to insert the path of the
     * file.
     */
    private final String requestInsertPath = localizationHandler.getMessageValue("request_file_path");
    /**
     * Represents a message that indicates the user that there was an error
     * exporting the file.
     */
    private final String errorExportationFailed = localizationHandler.getMessageValue("error_exporting_template");
    /**
     * Represents a message that indicates the user that there aren't any
     * available templates.
     */
    private final String errorNoTemplates = localizationHandler.getMessageValue("error_no_templates");
    /**
     * Represents a message that informs the user that the template is not
     * valid.
     */
    private final String errorInvalidTemplate = localizationHandler.getMessageValue("error_invalid_template");
    /**
     * Represents a message used to list the templates.
     */
    private final String listAllTemplates = localizationHandler.getMessageValue("info_template_message");
    /**
     * Separator used for clarity.
     */
    private final String separator = localizationHandler.getMessageValue("separator");
    /**
     * Instance of Controller that intermediates the interactions between the
     * manager and the domain classes.
     */
    private final ExportTemplateController ctrl;

    /**
     * Creates the UI itself.
     *
     * @param manager
     */
    public ExportTemplateUI(Manager manager) {
        ctrl = new ExportTemplateController(manager);
        doShow();
    }

    /**
     * Shows the UI components.
     */
    private void doShow() {
        System.out.println(separator);
        System.out.println(exitMessage);

        //1. List all templates
        List<String> templates = ctrl.getAllTemplates();
        if (!listAllTemplates(templates)) {
            System.out.println(errorNoTemplates);
            System.out.println(separator);
            return;
        }

        System.out.println(separator);

        //2. User chooses the template
        boolean isTemplateIDValid = false;

        while (!isTemplateIDValid) {
            String templateID = Console.readLine(requestSelectTemplate);
            if (templateID != null && templateID.equalsIgnoreCase(exitCode)) {
                return;
            }

            boolean isValid = ctrl.getChosenTemplate(templateID);
            if (!isValid) {
                System.out.println(errorInvalidTemplate);
            } else {
                isTemplateIDValid = true;
            }
        }

        boolean isPathValid = false;
        while (!isPathValid) {
            //3. Inserts the path of the file
            String filePath = Console.readLine(requestInsertPath);
            if (filePath != null && filePath.equalsIgnoreCase(exitCode)) {
                return;
            }

            //4. Exports the file
            try {
                if (!ctrl.exportTemplate(filePath)) {
                    System.out.println(errorExportationFailed);
                } else {
                    System.out.println(exportationSucceeded);
                    isPathValid = true;
                }

            } catch (JAXBException e) {
                System.out.println(errorExportationFailed);
                ExceptionLogger.logException(LoggerFileNames.BACKOFFICE_LOGGER_FILE_NAME, Level.SEVERE, e.getMessage());
            }
        }
    }

    /**
     * Lists all templates.
     *
     * @param templates List with all templates
     * @return true, if there are template to list. Otherwise, returns false
     */
    private boolean listAllTemplates(List<String> templates) {
        if (templates == null || templates.isEmpty()) {
            return false;
        }
        int cont = 1;
        for (String t : templates) {
            System.out.println("\n" + listAllTemplates + " " + cont + ":");
            System.out.println(t);
            cont++;
        }
        return true;
    }
}
