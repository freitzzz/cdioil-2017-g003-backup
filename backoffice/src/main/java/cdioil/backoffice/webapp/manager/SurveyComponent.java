package cdioil.backoffice.webapp.manager;

import cdioil.backoffice.application.ListSurveysController;
import cdioil.backoffice.webapp.DefaultPanelView;
import cdioil.framework.SurveyDTO;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Survey Component for Manager Dashboard
 */
public class SurveyComponent extends DefaultPanelView {

    /**
     * Controller class
     */
    private transient ListSurveysController controller;

    /**
     * SurveyGrid
     */
    private Grid<SurveyDTO> surveyGrid;

    /**
     * SurveyGrid's Data Handler
     */
    private transient List<SurveyDTO> surveyGridData;

    /**
     * Creates a new instance
     */
    public SurveyComponent() {
        super("Inquéritos");
        createComponents();
        prepareComponents();
    }

    /**
     * Instantiate Components
     */
    private void createComponents() {
        controller = new ListSurveysController();
        surveyGrid = new Grid<>();
        surveyGridData = new ArrayList<>();
    }

    /**
     * Prepares Components
     */
    private void prepareComponents() {
        prepareHeader();
        prepareGrid();

        setExpandRatio(headerLayout, 0.10f);
        setExpandRatio(surveyGrid, 0.90f);
    }

    /**
     * Prepare Header
     */
    private void prepareHeader() {
        Responsive.makeResponsive(headerLayout);

        HorizontalLayout topBarLayout = new HorizontalLayout();
        topBarLayout.addStyleName("toolbar");

        TextField searchTextField = new TextField();
        searchTextField.setPlaceholder("Pesquisar");
        searchTextField.setIcon(VaadinIcons.SEARCH);
        searchTextField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        searchTextField.addValueChangeListener(onValueChange -> {
            String input = onValueChange.getValue().toLowerCase().trim();

            surveyGridData.clear();

            if (input.isEmpty()) {
                surveyGridData.addAll(controller.getListOfSurveys());
                surveyGrid.getDataProvider().refreshAll();
                return;
            }

            List<SurveyDTO> filteredSurveys = controller.getFilteredSurveys(input);

            surveyGridData.addAll(filteredSurveys);
            surveyGrid.getDataProvider().refreshAll();
        });

        // Create search/filter
        topBarLayout.addComponents(searchTextField, createOptionsDropDown());

        headerLayout.addComponent(topBarLayout);
        headerLayout.setComponentAlignment(topBarLayout, Alignment.MIDDLE_RIGHT);
    }

    /**
     * Creates dropdown menu for
     * @return dropdown component
     */
    private Component createOptionsDropDown() {
        MenuBar settingsMenuBar = new MenuBar();
        MenuBar.MenuItem menuItem = settingsMenuBar.addItem("", null);

        menuItem.addItem("Criar Inquérito", VaadinIcons.PLUS_CIRCLE, command -> {
            UI.getCurrent().addWindow(new CreateSurveyWindow());
        });

        menuItem.addItem("Templates", VaadinIcons.BOOKMARK, command -> {
            //TODO
        });

        return settingsMenuBar;
    }

    /**
     * Prepares surveyGrid
     */
    private void prepareGrid() {
        surveyGridData = controller.getListOfSurveys();

        surveyGrid.setItems(surveyGridData);
        surveyGrid.addColumn(SurveyDTO::getName).setCaption("Nome").setResizable(false);
        surveyGrid.addColumn(SurveyDTO::getEndDate).setCaption("Data Fim").setResizable(false);
        surveyGrid.addColumn(SurveyDTO::getState).setCaption("Estado").setResizable(false);

        surveyGrid.addItemClickListener(onClick -> {
            //TODO Implement survey information window
            //UI.getCurrent().addWindow(new ShowSurveyInfoWindow(onClick.getItem()));
        });

        surveyGrid.setSizeFull();
        addComponent(surveyGrid);
    }
}
