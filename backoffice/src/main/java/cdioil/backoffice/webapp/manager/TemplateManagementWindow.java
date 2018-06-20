package cdioil.backoffice.webapp.manager;


import cdioil.application.authz.AuthenticationController;
import cdioil.backoffice.application.TemplateManagementController;
import cdioil.framework.dto.QuestionDTO;
import cdioil.framework.dto.TemplateDTO;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Template Management Window
 */
public class TemplateManagementWindow extends Window {

    /**
     * Controller Class
     */
    private TemplateManagementController controller = new TemplateManagementController();

    /**
     * Main Window Layout
     */
    private VerticalLayout mainLayout = new VerticalLayout();

    /**
     * List that shows all templates
     */
    private ListSelect<TemplateDTO> allTemplatesList = new ListSelect<>();

    /**
     * All Templates List Data
     */
    private List<TemplateDTO> allTemplatesData = new ArrayList<>();

    /**
     * List that shows each selected template's questions
     */
    private ListSelect<String> selectedTemplateList = new ListSelect<>();

    /**
     * Selected template List Data
     */
    private List<String> selectedTemplateData = new ArrayList<>();

    /**
     * Authentication Controller
     */
    private AuthenticationController authenticationController;

    /**
     * Constructor
     * @param authenticationController authentication controller
     */
    public TemplateManagementWindow(AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
        setProperties();
        prepareWindow();

        setContent(mainLayout);
    }

    /**
     * Sets Window Properties
     */
    private void setProperties() {
        setCaption("Templates");
        center();

        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        mainLayout.setSpacing(true);
    }

    /**
     * Prepares Window
     */
    private void prepareWindow() {
        // Header
        Label header = new Label("<h3>Templates</h3>");
        header.setContentMode(ContentMode.HTML);
        mainLayout.addComponent(header);

        prepareAllTemplatesList();
        prepareSelectedTemplateList();

        HorizontalLayout listLayout = new HorizontalLayout();
        listLayout.setSpacing(true);
        listLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        listLayout.addComponents(allTemplatesList, selectedTemplateList);
        listLayout.setExpandRatio(allTemplatesList, 0.5f);
        listLayout.setExpandRatio(selectedTemplateList, 0.5f);
        mainLayout.addComponent(listLayout);

        // Import Button
        Button importTemplateBtn = new Button("Importar");
        importTemplateBtn.addClickListener(onClick -> {
            UI.getCurrent().addWindow(new ImportTemplateWindow(authenticationController));
            updateTemplateList();
        });
        mainLayout.addComponent(importTemplateBtn);
    }

    private void prepareAllTemplatesList() {
        allTemplatesList.setSizeFull();
        allTemplatesData.addAll(controller.getAllTemplates());

        allTemplatesList.setItems(allTemplatesData);
        allTemplatesList.getDataProvider().refreshAll();

        allTemplatesList.addSelectionListener(multiSelectionEvent -> {
            Optional<TemplateDTO> selection = multiSelectionEvent.getFirstSelectedItem();
            if (!selection.isPresent()) {
                return;
            }

            List<String> questionNames = new ArrayList<>();
            for (QuestionDTO dto :
                    selection.get().getQuestions()) {
                questionNames.add(dto.getText());
            }

            selectedTemplateData.clear();
            selectedTemplateData.addAll(questionNames);
            selectedTemplateList.getDataProvider().refreshAll();
        });

    }

    /**
     * Refreshes both list
     */
    private void updateTemplateList() {
        selectedTemplateData.clear();
        selectedTemplateList.getDataProvider().refreshAll();

        allTemplatesData.clear();
        allTemplatesData.addAll(controller.getAllTemplates());
        allTemplatesList.getDataProvider().refreshAll();
    }

    /**
     * Prepares Selected Template List
     */
    private void prepareSelectedTemplateList() {
        selectedTemplateList.setItems(selectedTemplateData);
        selectedTemplateList.setSizeFull();
    }
}
