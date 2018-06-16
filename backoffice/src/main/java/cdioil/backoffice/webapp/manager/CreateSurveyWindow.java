package cdioil.backoffice.webapp.manager;

import cdioil.backoffice.application.CreateSurveyController;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.DropTargetExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create new Survey Window
 */
public class CreateSurveyWindow extends Window {

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
    private TwinColSelect<String> surveyItemsTwinCol = new TwinColSelect<>();

    /**
     * Twin Column Select Items/Data
     */
    private List<String> surveyItemsData = new ArrayList<>();

    /**
     * Selected Questions List Layout
     */
    private VerticalLayout selectedQuestionsLayout = new VerticalLayout();

    /**
     * Controller Class
     */
    private CreateSurveyController controller = new CreateSurveyController();

    /**
     * Constructor
     */
    public CreateSurveyWindow() {
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

        Label header = new Label("<h1>Novo Inquérito</h2>", ContentMode.HTML);
        Button confirmBtn = new Button("Confirmar", VaadinIcons.CHECK);
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

        surveyItemsTwinCol.setItems(surveyItemsData);
        surveyItemsTwinCol.setLeftColumnCaption("Itens de Inquérito");
        surveyItemsTwinCol.setRightColumnCaption("Selecionados");
        surveyItemsTwinCol.setRows(15);
        surveyItemsTwinCol.setSizeUndefined();

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

        //TODO DEBUG-ONLY
        for (int i = 0; i < 10; i++) {
            Label lbl = new Label("Question " + i);
            DragSourceExtension<Label> dragSource = new DragSourceExtension<>(lbl);
            dragSource.setEffectAllowed(EffectAllowed.MOVE);
            dragSource.setDataTransferText(lbl.getValue());
            panelLayout.addComponent(lbl);
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
     * Inner Class
     * Holds Labels and options corresponding to a selected question
     */
    private class QuestionRow extends Panel {

        /**
         * Row Layout
         */
        private HorizontalLayout mainLayout = new HorizontalLayout();

        /**
         * Question Index Label
         */
        private Label indexLabel = new Label();

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
         * Constructor
         */
        private QuestionRow() {
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

            indexLabel.setSizeUndefined();
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
                    if (dragSource instanceof Label) {
                        updateNewQuestion(((Label) dragSource).getValue());

                        if (((HorizontalLayout) dropTarget.getParent().getContent())
                                .getComponentCount() == 1) {
                            addNewQuestionSlotToLayout();
                        }
                    }
                });
            });
        }

        /**
         * When a new question is dragged onto this panel,
         * updates components to show question information
         *
         * @param questionText dragged question text
         */
        private void updateNewQuestion(String questionText) {
            mainLayout.removeAllComponents();

            final int questionIndexInLayout = selectedQuestionsLayout.getComponentCount();

            indexLabel.setValue("<h3>" + questionIndexInLayout + "</h3>");
            indexLabel.setContentMode(ContentMode.HTML);

            questionLabel.setValue(questionText);

            typeQuestionLabel.setValue("Tipo");

            questionOptionPopupContent = new QuestionOptionPopupContent();

            PopupView popup = new PopupView(questionOptionPopupContent);
            popup.setHideOnMouseOut(true);

            QuestionRow current = this;
            removeQuestionBtn.addClickListener(clickAction ->
                    selectedQuestionsLayout.removeComponent(current));

            mainLayout.addComponents(indexLabel, questionLabel, typeQuestionLabel,
                    popup, removeQuestionBtn);

            mainLayout.setExpandRatio(indexLabel, 0.1f);
            mainLayout.setExpandRatio(questionLabel, 0.45f);
            mainLayout.setExpandRatio(typeQuestionLabel, 0.2f);
            mainLayout.setExpandRatio(popup, 0.1f);
            mainLayout.setExpandRatio(removeQuestionBtn, 0.1f);
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
             * Constructor
             */
            private QuestionOptionPopupContent() {
                layout = new FormLayout();
                setCaption("Opções Fluxo");
                prepareQuestionOptionPopupLayout();
            }

            /**
             * Prepares Layout
             */
            private void prepareQuestionOptionPopupLayout() {
                //TODO Get Question Options
                List<String> questionOptions = new ArrayList<>(Arrays.asList("Sim", "Não"));

                for (String questionOption :
                        questionOptions) {
                    TextField tf = new TextField();
                    tf.setCaption(questionOption);
                    tf.setMaxLength(3);
                    tf.setSizeUndefined();
                    layout.addComponent(tf);
                }
            }

            /**
             * Placeholder HTML Value
             * @return
             */
            @Override
            public String getMinimizedValueAsHTML() {
                return "Opções";
            }

            /**
             * PopupView Content
             * @return layout
             */
            @Override
            public Component getPopupComponent() {
                return layout;
            }

            /**
             * Gets a list of all text field values
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
     * Adds a new Drag Slot to the Selected Questions List
     */
    private void addNewQuestionSlotToLayout() {
        selectedQuestionsLayout.addComponent(new QuestionRow());
    }

    /**
     * Prepares Users Tab
     */
    private void addUsersTab() {
        VerticalLayout userTab = new VerticalLayout();
        userTab.setCaption("Utilizadores");
        userTab.setSizeFull();

        tabSheet.addTab(userTab);
    }
}
