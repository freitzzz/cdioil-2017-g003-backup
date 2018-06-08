package cdioil.backoffice.webapp.manager;

import cdioil.backoffice.application.QuestionsPerCategoryController;
import cdioil.framework.dto.QuestionDTO;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Question Per Category Window
 */
public class QuestionsPerCatWindow extends Window {

    /**
     * Controler Class
     */
    private transient QuestionsPerCategoryController controller = new QuestionsPerCategoryController();

    /**
     * Window Layout
     */
    private VerticalLayout mainLayout = new VerticalLayout();

    /**
     * TextField
     */
    private TextField catPathTextField = new TextField();

    /**
     * Category Name
     */
    private Label catNameLbl = new Label("Selecione uma categoria");

    /**
     * Question Grid
     */
    private Grid<QuestionDTO> questionGrid = new Grid<>();

    /**
     * Grid Items/Data Provider
     */
    private transient List<QuestionDTO> gridItems = new ArrayList<>();

    /**
     * Instantiates a new Window
     */
    public QuestionsPerCatWindow() {
        setProperties();
        prepareComponents();
    }

    /**
     * Sets Window Properties
     */
    private void setProperties() {
        center();
        setModal(true);
        setDraggable(true);
        setResizable(false);
        setCaption("Perguntas por categoria");
        setWidth(800, Unit.PIXELS);
        setHeightUndefined();

        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
    }

    /**
     * Prepares window components
     */
    private void prepareComponents() {
        prepareHeader();
        mainLayout.addComponent(catNameLbl);
        preparePanel();

        setContent(mainLayout);
    }

    /**
     * Prepares Header
     */
    private void prepareHeader() {
        catPathTextField.setPlaceholder("Caminho");

        Button showQuestionsBtn = new Button("Mostrar Questões");
        showQuestionsBtn.addClickListener(onClick -> {
            List<QuestionDTO> questionList = controller
                    .getCategoryQuestions(catPathTextField.getValue().trim());

            if (questionList == null || questionList.isEmpty()) {
                Notification.show("Não há questões definidas para esta categoria.",
                        Notification.Type.ERROR_MESSAGE);
                return;
            }

            gridItems.clear();

            for (QuestionDTO dto :
                    questionList) {
                gridItems.add(dto);
            }

            questionGrid.getDataProvider().refreshAll();
        });

        HorizontalLayout layout = new HorizontalLayout(catPathTextField, showQuestionsBtn);
        mainLayout.addComponent(layout);
    }

    /**
     * Prepare Question Panel
     */
    private void preparePanel() {
        ListDataProvider<QuestionDTO> dataProvider = new ListDataProvider<>(gridItems);
        questionGrid.setDataProvider(dataProvider);
        questionGrid.setHeaderVisible(true);
        questionGrid.setWidth(100, Unit.PERCENTAGE);
        questionGrid.setHeight(300, Unit.PIXELS);
        questionGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        questionGrid.addColumn(QuestionDTO::getText).setCaption("Texto").setExpandRatio(1);
        questionGrid.addColumn(QuestionDTO::getQuestionType).setCaption("Tipo").setExpandRatio(1);
        questionGrid.addColumn(QuestionDTO::getOptions).setCaption("Opções").setExpandRatio(1);

        mainLayout.addComponent(questionGrid);
    }
}
