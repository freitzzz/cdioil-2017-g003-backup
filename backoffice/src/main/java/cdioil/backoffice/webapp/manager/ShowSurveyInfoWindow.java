package cdioil.backoffice.webapp.manager;

import cdioil.framework.SurveyDTO;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Show Survey Information Info
 */
public class ShowSurveyInfoWindow extends Window {

    /**
     * Selected Survey
     */
    private SurveyDTO selectedSurvey;

    /**
     * Main Window Layout
     */
    private VerticalLayout mainLayout;

    /**
     * Constructor
     * @param selectedSurvey selected survey
     */
    public ShowSurveyInfoWindow(SurveyDTO selectedSurvey) {
        this.selectedSurvey = selectedSurvey;

        mainLayout = new VerticalLayout();

        setProperties();
        prepareWindow();
    }

    /**
     * Set Window Properties
     */
    private void setProperties() {
        setCaption(selectedSurvey.getName());
        setModal(true);
        setDraggable(true);
    }

    /**
     * Prepares Window
     */
    private void prepareWindow() {
        setContent(mainLayout);
    }
}
