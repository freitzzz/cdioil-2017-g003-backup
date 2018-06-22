package cdioil.backoffice.webapp.manager;

import cdioil.application.authz.AuthenticationController;
import cdioil.backoffice.application.CreateSurveyController;
import cdioil.domain.authz.Manager;
import cdioil.framework.dto.QuestionDTO;
import cdioil.framework.dto.SurveyItemDTO;
import cdioil.framework.dto.SystemUserDTO;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.DropTargetExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create new Survey Window
 */
public class CreateSurveyWindow extends Window {

    /**
     * Success Message
     */
    private static final String SUCCESS_MESSAGE =
            "Inquérito criado com sucesso";
    /**
     * Tab Sheet
     */
    private TabSheet tabSheet = new TabSheet();

    /**
     * Main Window Layout
     */
    private VerticalLayout mainLayout = new VerticalLayout();

    /**
     * Twin Column Select Component
     */
    private TwinColSelect<SurveyItemDTO> surveyItemsTwinCol = new TwinColSelect<>();

    /**
     * Twin Column Select Items/Data
     */
    private transient List<SurveyItemDTO> surveyItemsData = new ArrayList<>();

    /**
     * Selected Questions List Layout
     */
    private VerticalLayout selectedQuestionsLayout = new VerticalLayout();

    /**
     * List of Selected Questions
     */
    private List<QuestionDTO> selectedQuestions = new ArrayList<>();

    /**
     * Twin Column Select Users
     */
    private TwinColSelect<SystemUserDTO> usersTwinCol = new TwinColSelect<>();

    /**
     * Twin Column Select Users Items/Data
     */
    private transient List<SystemUserDTO> usersData = new ArrayList<>();

    /**
     * CheckBox Indeterminate
     */
    private CheckBox indeterminateCheckBox = new CheckBox("Indeterminado");

    /**
     * Start Date Time Field
     */
    private DateTimeField startDateField = new DateTimeField();

    /**
     * End Date Time Field
     */
    private DateTimeField endDateField = new DateTimeField();

    /**
     * Controller Class
     */
    private transient CreateSurveyController controller = new CreateSurveyController();

    /**
     * Authentication Controller Class
     */
    private AuthenticationController authenticationController;

    /**
     * Constructor
     */
    public CreateSurveyWindow(AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;

        setProperties();
        prepareWindow();
    }

    /**
     * Sets Window Properties
     */
    private void setProperties() {
        //setModal(true);
        setResizable(false);
        setWindowMode(WindowMode.MAXIMIZED);
        setSizeFull();

        mainLayout.setSizeFull();
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        mainLayout.setSpacing(true);

        tabSheet.setSizeFull();

        selectedQuestionsLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        selectedQuestionsLayout.setHeightUndefined();
        selectedQuestionsLayout.setWidth(100, Unit.PERCENTAGE);
    }

    /**
     * Prepares Window
     */
    private void prepareWindow() {
        addSurveyItemsTab();
        addSurveyFlowTab();
        addUsersTab();
        addTimePeriodTab();

        Label header = new Label("<h1>Novo Inquérito</h2>", ContentMode.HTML);
        Button confirmBtn = new Button("Confirmar", VaadinIcons.CHECK);
        confirmBtn.addClickListener(confirmBtnClickListener());
        mainLayout.addComponents(header, tabSheet, confirmBtn);

        // Expand Ratios
        mainLayout.setExpandRatio(header, 0.1f);
        mainLayout.setExpandRatio(tabSheet, 0.8f);
        mainLayout.setExpandRatio(confirmBtn, 0.1f);

        setContent(mainLayout);
    }

    /**
     * Prepares Survey Items Tab
     */
    private void addSurveyItemsTab() {
        HorizontalLayout surveyItemsTab = new HorizontalLayout();
        surveyItemsTab.setCaption("Inquérito");
        surveyItemsTab.setSizeFull();
        surveyItemsTab.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // Get Current logged in manager
        Manager activeManager = (Manager) authenticationController.getUser();

        // Populate survey items data
        surveyItemsData.addAll(controller.findManagerSurveyItems(activeManager));

        surveyItemsTwinCol.setItems(surveyItemsData);
        surveyItemsTwinCol.setLeftColumnCaption("Itens de Inquérito");
        surveyItemsTwinCol.setRightColumnCaption("Selecionados");
        surveyItemsTwinCol.setRows(15);
        surveyItemsTwinCol.setWidth(100, Unit.PERCENTAGE);
        surveyItemsTwinCol.setHeightUndefined();

        surveyItemsTab.addComponent(surveyItemsTwinCol);

        tabSheet.addTab(surveyItemsTab);
    }

    /**
     * Prepares Survey Flow Tab
     */
    private void addSurveyFlowTab() {
        HorizontalLayout flowTab = new HorizontalLayout();
        flowTab.setCaption("Fluxo");
        flowTab.setSizeFull();
        flowTab.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        flowTab.addComponents(prepareQuestionListPanel(), prepareSelectedQuestionsPanel());

        tabSheet.addTab(flowTab, "Fluxo Decisão");
    }

    /**
     * Prepares Question List Panel
     *
     * @return Panel
     */
    private Panel prepareQuestionListPanel() {
        Panel questionListPanel = new Panel();
        questionListPanel.setSizeFull();
        questionListPanel.setCaption("Todas as Questões");

        VerticalLayout panelLayout = new VerticalLayout();
        panelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //Populate all questions
        for (QuestionDTO question : controller.getlAllQuestions()) {
            // Layout
            final Label questionText = new Label(question.getText());
            final Label questionType = new Label(question.getQuestionType());

            HorizontalLayout row = new HorizontalLayout();
            row.setWidth(100, Unit.PERCENTAGE);
            row.setMargin(new MarginInfo(true, false));
            row.setHeightUndefined();
            row.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

            row.addComponents(questionText, questionType);
            row.setExpandRatio(questionText, 0.7f);
            row.setExpandRatio(questionType, 0.6f);

            Panel rowPanel = new Panel(row);
            rowPanel.setCaption("Questão");

            // Handle drag
            DragSourceExtension<Panel> dragSource = new DragSourceExtension<>(rowPanel);
            dragSource.setEffectAllowed(EffectAllowed.MOVE);

            /**
             * To allow data transfer between the two panels,
             * the question data (text, type, options, etc) is
             * concatenated in a string and then splitted
             */
            dragSource.setDataTransferText(question.toString());
            dragSource.setDragData(question);

            panelLayout.addComponent(rowPanel);
        }

        questionListPanel.setContent(panelLayout);

        return questionListPanel;
    }

    /**
     * Prepares Selected Questions List Panel
     *
     * @return Panel
     */
    private Panel prepareSelectedQuestionsPanel() {
        Panel selectedQuestionsPanel = new Panel(selectedQuestionsLayout);
        selectedQuestionsPanel.setCaption("Questões Selecionadas");
        selectedQuestionsPanel.setSizeFull();

        // Add first question slot
        addNewQuestionSlotToLayout();

        return selectedQuestionsPanel;
    }

    /**
     * Confirm Button Click Listener
     *
     * @return ClickListener
     */
    private Button.ClickListener confirmBtnClickListener() {
        return onClick -> {
            // Populates each question option's map
            for (int i = 0; i < selectedQuestions.size(); i++) {
                SelectedQuestionRow currentRow =
                        (SelectedQuestionRow) selectedQuestionsLayout.getComponent(i);

                QuestionDTO currentQuestion = currentRow.assignedQuestion;

                List<String> textFields = currentRow.getTextFieldValues();
                for (int j = 0; j < textFields.size(); j++) {
                    currentQuestion.addNextQuestion(currentQuestion.getOptions().get(j),
                            textFields.get(j));
                }

            }

            // Populate User List
            List<SystemUserDTO> userList = new ArrayList<>();
            for (SystemUserDTO suDTO : usersTwinCol.getValue()) {
                userList.add(suDTO);
            }

            LocalDateTime startTime = startDateField.getValue();
            LocalDateTime endTime = endDateField.getValue();

            if (indeterminateCheckBox.getValue()) {
                endTime = LocalDateTime.MAX;
            }

            try {
                // Creates Survey
                controller.createSurvey(surveyItemsData, startTime, endTime,
                        userList, (Manager) authenticationController.getUser(),
                        selectedQuestions);
            } catch (Exception e) {
                Notification error = new Notification(e.getMessage(),
                        Notification.Type.ERROR_MESSAGE);
                error.setDelayMsec(300);
                error.show(UI.getCurrent().getPage());
                return;
            }

            Notification alert =
                    new Notification(SUCCESS_MESSAGE, Notification.Type.TRAY_NOTIFICATION);
            alert.setDelayMsec(300);
            alert.show(UI.getCurrent().getPage());

            this.close();
        };
    }

    /**
     * Inner Class
     * Holds Labels and options corresponding to a selected question
     */
    private class SelectedQuestionRow extends Panel {

        /**
         * Row Layout
         */
        private HorizontalLayout mainLayout = new HorizontalLayout();

        /**
         * Question Text Label
         */
        private Label questionLabel = new Label();

        /**
         * Question Type Label
         */
        private Label typeQuestionLabel = new Label();

        /**
         * Question Option Popup View
         */
        private QuestionOptionPopupContent questionOptionPopupContent;

        /**
         * Remove Question from List Button
         */
        private Button removeQuestionBtn = new Button(VaadinIcons.MINUS_CIRCLE);

        /**
         * Question DTO that is assigned to this row
         */
        private QuestionDTO assignedQuestion;

        /**
         * Constructor
         */
        private SelectedQuestionRow() {
            setProperties();

            prepareRow();

            setContent(mainLayout);
        }

        /**
         * Set Row Properties
         */
        private void setProperties() {
            mainLayout.setWidth(100, Unit.PERCENTAGE);
            mainLayout.setHeightUndefined();
            mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        }

        /**
         * Prepares Row
         */
        private void prepareRow() {
            prepareDragTarget();

            Label tempLbl = new Label("<h4>Arraste questão para aqui</h4>");
            tempLbl.setContentMode(ContentMode.HTML);
            tempLbl.setSizeUndefined();

            mainLayout.addComponent(tempLbl);
        }

        /**
         * Sets Row as a Drop Target
         */
        private void prepareDragTarget() {
            DropTargetExtension<Panel> dropTarget = new DropTargetExtension<>(
                    this);

            dropTarget.addDropListener(dropEvent -> {
                dropEvent.getDragSourceComponent().ifPresent(dragSource -> {
                    if (dragSource instanceof Panel) {
                        if (((HorizontalLayout) dropTarget.getParent().getContent())
                                .getComponentCount() == 1) {
                            addNewQuestionSlotToLayout();
                        }

                        Optional<Object> optional = dropEvent.getDragData();
                        if (optional.isPresent()) {
                            QuestionDTO draggedQuestion = (QuestionDTO) optional.get();
                            this.assignedQuestion = draggedQuestion;
                            updateNewQuestion(draggedQuestion);
                        }
                    }
                });
            });
        }

        /**
         * When a new question is dragged onto this panel,
         * updates components to show question information
         *
         * @param questionDTO question that was transfered
         */
        private void updateNewQuestion(QuestionDTO questionDTO) {
            mainLayout.removeAllComponents();

            mainLayout.setMargin(new MarginInfo(true, false));

            final int questionIndexInLayout = selectedQuestionsLayout.getComponentCount();
            setCaption("Questão " + questionIndexInLayout);


            selectedQuestions.add(questionDTO);

            questionLabel.setValue(questionDTO.getText());
            typeQuestionLabel.setValue(questionDTO.getQuestionType());

            questionOptionPopupContent = new QuestionOptionPopupContent(questionDTO.getOptions());

            PopupView popup = new PopupView(questionOptionPopupContent);
            popup.setHideOnMouseOut(true);

            SelectedQuestionRow current = this;
            removeQuestionBtn.addClickListener(clickAction -> {
                selectedQuestionsLayout.removeComponent(current);
                selectedQuestions.remove(questionDTO);
                refreshPanelIndices();
            });

            mainLayout.addComponents(questionLabel, typeQuestionLabel,
                    popup, removeQuestionBtn);

            mainLayout.setExpandRatio(questionLabel, 0.5f);
            mainLayout.setExpandRatio(typeQuestionLabel, 0.2f);
            mainLayout.setExpandRatio(popup, 0.15f);
            mainLayout.setExpandRatio(removeQuestionBtn, 0.1f);

            refreshPanelIndices();
        }

        /**
         * Returns list of values of all text fields inside the popup view
         *
         * @return
         */
        public List<String> getTextFieldValues() {
            return questionOptionPopupContent.getTextFieldValues();
        }

        public QuestionDTO getQuestionDTO() {
            return assignedQuestion;
        }

        /**
         * Popup View Content Class
         */
        private class QuestionOptionPopupContent implements PopupView.Content {

            /**
             * View Layout
             */
            private FormLayout layout;

            /**
             * List of Text Fields
             * Each one assigned to a question option
             */
            private List<TextField> questionOptionsTextFields;

            /**
             * Constructor
             */
            private QuestionOptionPopupContent(List<String> questionOptions) {
                layout = new FormLayout();
                questionOptionsTextFields = new ArrayList<>();

                setCaption("Fluxo");

                prepareTextFields(questionOptions);
            }

            /**
             * Prepares All TextFields
             *
             * @param questionOptions all question options
             */
            private void prepareTextFields(List<String> questionOptions) {
                for (String option :
                        questionOptions) {
                    TextField tf = new TextField();
                    tf.setCaption(option);
                    tf.setWidth(50, Unit.PIXELS);

                    questionOptionsTextFields.add(tf);
                    layout.addComponent(tf);
                }
            }

            /**
             * Placeholder HTML Value
             *
             * @return
             */
            @Override
            public String getMinimizedValueAsHTML() {
                return "Opções";
            }

            /**
             * PopupView Content
             *
             * @return layout
             */
            @Override
            public Component getPopupComponent() {
                return layout;
            }

            /**
             * Gets a list of all text field values
             *
             * @return List
             */
            public List<String> getTextFieldValues() {
                List<String> tfValues = new ArrayList<>();

                for (int i = 0; i < layout.getComponentCount(); i++) {
                    tfValues.add(((TextField) layout.getComponent(i)).getValue().trim());
                }

                return tfValues;
            }

        }
    }

    /**
     * Refreshes all Panel Indices
     */
    private void refreshPanelIndices() {
        for (int i = 0; i < selectedQuestionsLayout.getComponentCount() - 1; i++) {
            selectedQuestionsLayout.getComponent(i).setCaption("Questão " + i);
        }
    }

    /**
     * Adds a new Drag Slot to the Selected Questions List
     */
    private void addNewQuestionSlotToLayout() {
        selectedQuestionsLayout.addComponent(new SelectedQuestionRow());
    }

    /**
     * Prepares Users Tab
     */
    private void addUsersTab() {
        VerticalLayout userTab = new VerticalLayout();
        userTab.setCaption("Utilizadores");
        userTab.setSizeFull();

        usersTwinCol.setLeftColumnCaption("Utilizadores Registados");
        usersTwinCol.setRightColumnCaption("Utilizadores Selecionados");

        usersData.addAll(controller.findAllRegisteredUsers());

        usersTwinCol.setItems(usersData);
        usersTwinCol.setSizeFull();

        userTab.addComponent(usersTwinCol);

        tabSheet.addTab(userTab);
    }

    /**
     * Add Time Period Tab
     */
    private void addTimePeriodTab() {
        VerticalLayout timeTab = new VerticalLayout();
        timeTab.setCaption("Periodo de Tempo");
        timeTab.setSizeFull();
        timeTab.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        startDateField.setValue(LocalDateTime.now());
        endDateField.setValue(LocalDateTime.now());

        startDateField.setPlaceholder("Inicio");
        endDateField.setPlaceholder("Fim");

        indeterminateCheckBox.addValueChangeListener(valueChangeEvent -> {
            startDateField.setEnabled(!valueChangeEvent.getValue());
            endDateField.setEnabled(!valueChangeEvent.getValue());
        });

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);
        hl.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        hl.setSizeFull();
        hl.addComponents(startDateField, endDateField);

        timeTab.addComponents(indeterminateCheckBox, hl);

        timeTab.setExpandRatio(indeterminateCheckBox, 0.2f);
        timeTab.setExpandRatio(hl, 0.8f);

        tabSheet.addTab(timeTab);
    }
}
