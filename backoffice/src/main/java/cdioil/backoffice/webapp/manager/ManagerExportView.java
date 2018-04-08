package cdioil.backoffice.webapp.manager;

import cdioil.backoffice.application.ExportSurveyAnswersController;
import cdioil.backoffice.webapp.utils.PopupNotification;
import cdioil.domain.Survey;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.shared.Position;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="https://github.com/freitzzz">freitzzz</a>
 */
public class ManagerExportView extends ManagerExportDesign implements View {
    /**
     * Constant that represents the name of the current view
     */
    public static final String VIEW_NAME="Exportar Respostas de Inquérito";
    /**
     * Constant that represents the id of the grid column that contains all surveys
     */
    private static final String GRID_COLUMN_SURVEYS_ID="SURVEY_COLUMN";
    /**
     * Constant that represents the name of the grid column that contains all surveys
     */
    private static final String GRID_COLUMN_SURVEYS_NAME="Inquérito";
    /**
     * Constant that represents the name of the button that allows the manager to export the current
     * selected Survey answers
     */
    private static final String BUTTON_EXPORT_SURVEY_ANSWERS_NAME="Exportar Respostas";
    /**
     * Constant that represents the message that represents the message that occures if there
     * are currently no surveys
     */
    private static final String NO_SURVEYS_MESSAGE="Não existem inquéritos de momento!";
    /**
     * Constant that represents the title of the message that appears on if the manager attempts
     * to export the answers of a Survey without selecting it
     */
    private static final String NO_SELECTED_SURVEY_TITLE="Nenhum Inquérito Selecionado";
    /**
     * Constant that represents the title of the message that appears on if the manager attempts
     * to export the answers of a Survey without selecting it
     */
    private static final String NO_SELECTED_SURVEY_MESSAGE="Por favor selecione um Inquérito!";
    /**
     * Constant that represents the title of the notification title that popsup if the manager 
     * tries to export answers from a survey that does not contain any reviews
     */
    private static final String NO_CURRENT_REVIEWS_FOR_SURVEY_TITLE="O Inquérito não contém avaliações!";
    /**
     * Constant that represents the title of the notification message that popsup if the manager 
     * tries to export answers from a survey that does not contain any reviews
     */
    private static final String NO_CURRENT_REVIEWS_FOR_SURVEY_MESSAGE="Não existem avaliações de momento "
            + "para o Inquérito selecionado";
    /**
     * Constant that represents the initial lazy load index of the surveys retrieval
     */
    private static final int INITIAL_LAZYLOAD_INDEX=0;

    /**
     * Current Navigator
     */
    private final Navigator navigator;
    /**
     * Current Surveys
     */
    private List<Survey> currentSurveys;
    /**
     * Current ExportSurveyAnswers controller
     */
    private ExportSurveyAnswersController currentController;

    /**
     * Builds a new ManagerExportView
     */
    public ManagerExportView(){
        this.navigator= UI.getCurrent().getNavigator();
        configuration();
    }

    /**
     * Configures the current view
     */
    private void configuration(){
        checkForSurveys();
        configureGridSurveys();
    }

    /**
     * Configures the grid that contains the surveys
     */
    private void configureGridSurveys(){
        gridSurveys.setItems(currentSurveys);
        gridSurveys.addColumn(Survey::toString).setCaption(GRID_COLUMN_SURVEYS_NAME).setId(GRID_COLUMN_SURVEYS_ID);
        gridSurveys.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridSurveys.getDataProvider().refreshAll();
    }

    /**
     * Configures the button that allows the manager to export the current selected survey answers
     */
    private void configureButtonExportSurveyAnswers(){
        btnExportSurveyAnswers.setCaption(BUTTON_EXPORT_SURVEY_ANSWERS_NAME);
        btnExportSurveyAnswers.addClickListener(clickEvent -> {
            Survey currentSelectedSurvey=getCurrentSelectedSurvey();
            if(currentSelectedSurvey==null){
                showNotificationForNoSelectedSurvey();
            }else{
                
            }
        });
    }

    /**
     * Checks for the existence of surveys
     */
    private void checkForSurveys(){
        currentSurveys=new ArrayList<>();
        currentSurveys.addAll(new SurveyRepositoryImpl().getSurveysByLazyLoadingIndex(INITIAL_LAZYLOAD_INDEX));
        if(currentSurveys.isEmpty())throw new IllegalStateException(NO_SURVEYS_MESSAGE);
    }
    
    /**
     * Gets the current selected Survey from the Grid
     * @return Survey with the current selected survey from the Grid, null if no Survey is currently
     * selected
     */
    private Survey getCurrentSelectedSurvey(){
        Set<Survey> currentSelectedItem=gridSurveys.getSelectedItems();
        return !currentSelectedItem.isEmpty() ?
                currentSelectedItem.iterator().next() : null;
    }
    
    private void exportReviews(Survey survey){
        try{
            this.currentController=new ExportSurveyAnswersController(survey);
        }catch(IllegalArgumentException e){
            showNotificationForNoReviewsOnSelectedSurvey();
        }
    }
    
    /**
     * Shows up a notification that pops up to the manager if he attemps to export the
     * answers of a Survey without selecting one
     */
    private void showNotificationForNoSelectedSurvey(){
        PopupNotification.show(NO_SELECTED_SURVEY_TITLE,NO_SELECTED_SURVEY_MESSAGE
                , Notification.Type.ASSISTIVE_NOTIFICATION, Position.TOP_RIGHT);
    }
    
    /**
     * Shows up a notification that pops up if the manager tries to export reviews 
     * from a Survey that does not contain any reviews
     */
    private void showNotificationForNoReviewsOnSelectedSurvey(){
        PopupNotification.show(NO_CURRENT_REVIEWS_FOR_SURVEY_TITLE
                    ,NO_CURRENT_REVIEWS_FOR_SURVEY_MESSAGE
                    ,Notification.Type.ASSISTIVE_NOTIFICATION,Position.TOP_RIGHT);
    }

}
