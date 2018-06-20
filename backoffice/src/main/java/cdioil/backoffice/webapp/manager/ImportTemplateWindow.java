package cdioil.backoffice.webapp.manager;

import cdioil.application.authz.AuthenticationController;
import cdioil.backoffice.application.ImportTemplateController;
import cdioil.domain.authz.Manager;
import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

public class ImportTemplateWindow extends Window {

    /**
     * Controller class
     */
    private transient ImportTemplateController controller;

    /**
     * Main Layout of the Window
     */
    private VerticalLayout mainLayout;

    /**
     * Temporary file that is created when the user uploads a file
     */
    private File tempFile;

    /**
     * Authentication Controller Class
     */
    private AuthenticationController authenticationController;

    /**
     * Creates an instance of the popup window
     */
    public ImportTemplateWindow(AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
        instantiateComponents();
        prepareComponents();
    }

    /**
     * Instantiates needed components
     */
    private void instantiateComponents() {
        controller = new ImportTemplateController((Manager) authenticationController.getUser());
        mainLayout = new VerticalLayout();
    }

    /**
     * Prepares all Window Components
     */
    private void prepareComponents() {
        setCaption("Importar Ficheiro Template");
        setDraggable(true);
        setModal(false);
        setResizable(false);
        center();

        prepareMainLayout();

        setContent(mainLayout);
    }

    /**
     * Prepares Main Layout
     */
    private void prepareMainLayout() {
        mainLayout.setSpacing(true);

        Upload uploadBtn = new Upload(null, (Upload.Receiver) (s, s1) -> {
            final String filename = s.substring(0, s.indexOf("."));
            final String fileExtension = s.substring(s.indexOf("."));
            try {
                tempFile = File.createTempFile(filename, fileExtension);
            } catch (IOException e) {
                ExceptionLogger.logException(LoggerFileNames.BACKOFFICE_LOGGER_FILE_NAME,
                        Level.SEVERE, e.getMessage());
            }

            try {
                return new FileOutputStream(tempFile);
            } catch (FileNotFoundException e) {
                ExceptionLogger.logException(LoggerFileNames.BACKOFFICE_LOGGER_FILE_NAME,
                        Level.SEVERE, e.getMessage());
                return null;
            }
        });

        uploadBtn.addFailedListener((Upload.FailedListener) failedEvent ->
                Notification.show("Could not upload file", Notification.Type.ERROR_MESSAGE));

        uploadBtn.addSucceededListener((Upload.SucceededListener) succeededEvent -> {
            try {
                controller.importTemplate(tempFile.getAbsolutePath());
            } catch (IOException e) {
                ExceptionLogger.logException(LoggerFileNames.BACKOFFICE_LOGGER_FILE_NAME,
                        Level.SEVERE, e.getMessage());
            }

            Notification.show("Success", Notification.Type.TRAY_NOTIFICATION);
            this.close();
        });

        uploadBtn.setImmediateMode(false);
        uploadBtn.setButtonCaption("Importar");

        mainLayout.addComponent(uploadBtn);
    }
}
