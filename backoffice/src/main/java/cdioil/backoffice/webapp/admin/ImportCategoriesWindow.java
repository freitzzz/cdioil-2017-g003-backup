package cdioil.backoffice.webapp.admin;

import cdioil.backoffice.application.ImportCategoriesController;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImportCategoriesWindow extends Window {

    private ImportCategoriesController controller;

    private VerticalLayout mainLayout;

    /**
     * Temporary file that is created when the user uploads a file
     */
    private File tempFile;

    public ImportCategoriesWindow() {
        //instantiateComponents();
        //prepareComponents();
    }

    private void instantiateComponents() {
        controller = new ImportCategoriesController();
        mainLayout = new VerticalLayout();
    }

    private void prepareComponents() {
        setCaption("Import Categories");
        setDraggable(true);
        setModal(false);
        setResizable(false);
        center();

        prepareMainLayout();

        setContent(mainLayout);
    }

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
                    e.printStackTrace();
                }

                try {
                    return new FileOutputStream(tempFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
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
                Notification.show("Success", Notification.Type.HUMANIZED_MESSAGE);
            }
        });

        uploadBtn.setImmediateMode(false);
        uploadBtn.setButtonCaption("Import");

        mainLayout.addComponent(uploadBtn);
    }
}
