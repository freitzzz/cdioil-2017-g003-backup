/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ExportTemplateController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;
import cdioil.domain.Template;
import java.util.List;

/**
 * @author Pedro Portela (1150782)
 * @author Ana Guerra (1161191)
 */
public class ExportTemplateUI {

    /**
     * Localization handler to load messages in several langugaes.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();

    /**
     * Represents the exit code for the User Interface.
     */
    private final String exitCode = localizationHandler.getMessageValue("option_exit");

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String exitMessage = localizationHandler.getMessageValue("info_exit_message");
    /**
     * Represents a message that indicates the user to select a template.
     */
    private final String requestSelectTemplate = localizationHandler.getMessageValue("request_select_template");
    /**
     * Represents a message that indicates the user that the template was exported successfully.
     */
    private final String exportationSucceeded = localizationHandler.getMessageValue("info_success_exporting_template");
    /**
     * Represents a message that indicates the user to insert the path of the file.
     */
    private final String requestInsertPath = localizationHandler.getMessageValue("request_file_path");
    /**
     * Represents a message that indicates the user that there was an error exporting the file.
     */
    private final String errorExportationFailed = localizationHandler.getMessageValue("error_exporting_template");

    /**
     * Represents a message that indicates the user that there aren't any available templates.
     */
    private final String errorNoTemplates = localizationHandler.getMessageValue("error_no_templates");
    /**
     * Represents a message that informs the user that the template is not valid.
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
     * Instance of Controller that intermediates the interactions between the manager and the domain classes.
     */
    private final ExportTemplateController ctrl;

    /**
     * Creates the UI itself.
     */
    public ExportTemplateUI() {
        ctrl = new ExportTemplateController();
        doShow();
    }

    private void doShow() {
        System.out.println(separator);
        System.out.println(exitMessage);

        //1. List all templates
        List<Template> templates = ctrl.getAllTemplates();
        if (!listAllTemplates(templates)) {
            System.out.println(errorNoTemplates);
            System.out.println(separator);
            return;
        }
        System.out.println(separator);

        //2. User chooses the template
        boolean isTemplateIDValid = false;
        Template template;

        while (!isTemplateIDValid) {
            String templateID = Console.readLine(requestSelectTemplate);
            if (templateID != null && templateID.equalsIgnoreCase(exitCode)) {
                return;
            }

            template = ctrl.getChosenTemplate(templateID);
            if (template == null) {
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

            //3. Exports the file
            if (ctrl.exportTemplate(filePath)) {
                System.out.println(exportationSucceeded);
                isPathValid = true;
            } else {
                System.out.println(errorExportationFailed);
            }

        }
    }

    /**
     * Lists all templates.
     *
     * @param templates List with all templates
     * @return true, if there are template to list. Otherwise, returns false
     */
    private boolean listAllTemplates(List<Template> templates) {
        if (templates == null || templates.isEmpty()) {
            return false;
        }
        int cont = 1;
        for (Template t : templates) {
            System.out.println("\n" + listAllTemplates + " " + cont + ":\n");
            System.out.println(t.toString());
            cont++;
        }
        return true;
    }
}