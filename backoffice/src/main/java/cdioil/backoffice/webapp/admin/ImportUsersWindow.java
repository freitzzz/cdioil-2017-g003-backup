package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.ImportUsersFromFilesController;
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

/**
 * Window implementation of the ImportUsers US
 */
public class ImportUsersWindow extends Window {
    /**
     * Constant that represents the message that occurres if an error ocures while uploading a file
     */
    private static final String ERROR_UPLOAD_FILE="Ocorreu um erro ao fazer upload do ficheiro";
    /**
     * Constant that represents the message that occurres if an error ocures while uploading a file
     */
    private static final String SUCCESS_MESSAGE="Successo!";
    /**
     * Constant that represents the message that occurres if an error ocures while uploading a file
     */
    private static final String IMPORT_USERS_CAPTION="Import Users";
    /**
     * Constant that represents the message that occurres if an error ocures while uploading a file
     */
    private static final String IMPORT_USERS_BUTTON_CAPTION="Import";
    /**
     * Controller class
     */
    private transient ImportUsersFromFilesController controller;

    /**
     * Main Layout of the Window
     */
    private VerticalLayout mainLayout;

    /**
     * Temporary file that is created when the user uploads a file
     */
    private File tempFile;

    /**
     * Creates an instance of the popup window
     */
    public ImportUsersWindow() {
        instantiateComponents();
        prepareComponents();
    }

    /**
     * Instantiates needed components
     */
    private void instantiateComponents() {
        controller = new ImportUsersFromFilesController();
        mainLayout = new VerticalLayout();
    }

    /**
     * Prepares all Window Components
     */
    private void prepareComponents() {
        setCaption(IMPORT_USERS_CAPTION);
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

        Upload uploadBtn = new Upload(null, (String s, String s1) -> {
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

        uploadBtn.addFailedListener((Upload.FailedEvent failedEvent) -> {
            Notification.show(ERROR_UPLOAD_FILE, Notification.Type.ERROR_MESSAGE);
        });

        uploadBtn.addSucceededListener((Upload.SucceededEvent succeededEvent) -> {
            controller.readUsers(tempFile.getAbsolutePath());
            
            Notification.show(SUCCESS_MESSAGE, Notification.Type.TRAY_NOTIFICATION);
        });

        uploadBtn.setImmediateMode(false);
        uploadBtn.setButtonCaption(IMPORT_USERS_BUTTON_CAPTION);

        mainLayout.addComponent(uploadBtn);
    }
}
