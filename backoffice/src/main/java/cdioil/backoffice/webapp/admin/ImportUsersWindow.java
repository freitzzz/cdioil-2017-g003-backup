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
import java.io.OutputStream;
import java.util.logging.Level;

/**
 * Window implementation of the ImportUsers US
 */
public class ImportUsersWindow extends Window {

    /**
     * Controller class
     */
    private ImportUsersFromFilesController controller;

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
        setCaption("Import Users");
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

        Upload uploadBtn = new Upload(null, new Upload.Receiver() {
            @Override
            public OutputStream receiveUpload(String s, String s1) {
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
            }
        });

        uploadBtn.addFailedListener(new Upload.FailedListener() {
            @Override
            public void uploadFailed(Upload.FailedEvent failedEvent) {
                Notification.show("Could not upload file", Notification.Type.ERROR_MESSAGE);
            }
        });

        uploadBtn.addSucceededListener(new Upload.SucceededListener() {
            @Override
            public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
                controller.readUsers(tempFile.getAbsolutePath());

                Notification.show("Success", Notification.Type.TRAY_NOTIFICATION);
            }
        });

        uploadBtn.setImmediateMode(false);
        uploadBtn.setButtonCaption("Import");

        mainLayout.addComponent(uploadBtn);
    }
}
