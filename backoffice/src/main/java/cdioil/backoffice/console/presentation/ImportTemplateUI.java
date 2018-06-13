/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

//import cdioil.backoffice.application.ImportTemplateController;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;

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
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private final String exitMessage = localizationHandler.getMessageValue("info_exit_message");

    /**
     * Represents a message that indicates the user to insert the path of the file to import.
     */
    private final String filePathMessage = localizationHandler.getMessageValue("request_file_path");

    /**
     * Represents a message that indicates the user that the template was successfully imported.
     */
    private final String successMessage = localizationHandler.getMessageValue("info_success_importing_template");
    
    /**
     * Represents a message that indicates the user that there was an error while importing the template.
     */
    private final String failedMessage = localizationHandler.getMessageValue("error_importing_template_failed");

    /**
     * Separator used for clarity.
     */
    private final String separator = localizationHandler.getMessageValue("separator");

    /**
     * Instance of Controller that intermediates the interactions between the manager and the system.
     */
  //  private final ImportTemplateController ctrl;

    /**
     * Creates a new User Interface.
     */
    public ImportTemplateUI() {
      //  ctrl = new ImportTemplateController();
        importTemplate();
    }

    /**
     *  Method that intermediates the interactions with the manager (creates the UI itself).
     */
    private void importTemplate() {
        System.out.println(separator);
        System.out.println(exitMessage);

        boolean catched = false;
        while (!catched) {
            String filePath = Console.readLine(filePathMessage).toLowerCase().trim();
            if (filePath.equalsIgnoreCase(exitCode)) {
                return;
            }

          //  boolean imported = ctrl.importTemplate(filePath);
           // while (!imported) {
                System.out.println(failedMessage);
                System.out.println(exitMessage);
                filePath = Console.readLine(filePathMessage);
                if (filePath.equalsIgnoreCase(exitCode)) {
                    return;
                }
         //   }
            System.out.println(successMessage);
            catched = true;
        }
    }
}
