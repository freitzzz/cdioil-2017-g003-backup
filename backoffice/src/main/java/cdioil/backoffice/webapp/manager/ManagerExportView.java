package cdioil.backoffice.webapp.manager;

import cdioil.backoffice.application.ExportSurveyAnswersController;
import cdioil.backoffice.webapp.utils.PopupNotification;
import cdioil.domain.Survey;
import cdioil.files.CommonFileExtensions;
import cdioil.files.FilesUtils;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

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
     * Constant that represents the title of the notification title that pops up if the manager
     * tries to export answers from a survey that does not contain any reviews
     */
    private static final String NO_CURRENT_REVIEWS_FOR_SURVEY_TITLE="O Inquérito não contém avaliações!";
    /**
     * Constant that represents the title of the notification message that pops up if the manager
     * tries to export answers from a survey that does not contain any reviews
     */
    private static final String NO_CURRENT_REVIEWS_FOR_SURVEY_MESSAGE="Não existem avaliações de momento "
            + "para o Inquérito selecionado";
    /**
     * Constant that represents the title of the notification title that pops up if the manager
     * tries to export a file which name is invalid
     */
    private static final String INVALID_FILE_NAME_TITLE="Nome de ficheiro inválido!";
    /**
     * Constant that represents the title of the notification message that pops up if the manager
     * tries to export answers from a survey that does not contain any reviews
     */
    private static final String INVALID_FILE_NAME_MESSAGE="O nome do ficheiro é invalido! Verifique "
            + "se este não contem caratéres especiais não autorizados";
    /**
     * Constant that represents the title of the notification title that pops up if an error ocures
     * while generating the file with the Survey reviews
     */
    private static final String INVALID_FILE_GENERATION_TITLE="Erro";
    /**
     * Constant that represents the title of the notification message that pops up if an error ocures
     * while generating the file with the Survey reviews
     */
    private static final String INVALID_FILE_GENERATION_MESSAGE="Ocorreu um erro ao gerar o ficheiro com "
            + "as respostas!";
    /**
     * Constant that represents the title of the dialog that asks the user for the file name being generated
     */
    private static final String ASK_FOR_FILE_NAME_DIALOG_TITLE ="Insira o nome do ficheiro a ser gerado";
    /**
     * Constant that represents the confirm button description used on dialogs
     */
    private static final String CONFIRM_BUTTON="Confirmar";
    /**
     * Constant that represents the cancel button description used on dialogs
     */
    private static final String CANCEL_BUTTON="Cancel";
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
     * Survey with the current selected survey which answers are going to be exported
     */
    private Survey currentSelectedSurvey;
    /**
     * String with the file name being generated with the exported reviews
     */
    private String fileName;
    /**
     * Button with the button that is used on the input dialog that asks the user to confirm the
     * file name that is being generated with the Survey Reviews
     */
    private Button btnConfirm;
    /**
     * Button with the button that is used on the input dialog that asks the user to cancel the
     * file name that is being generated with the Survey Reviews
     */
    private Button btnCancel;
    /**
     * Textfield with the textfield used on the input dialog
     */
    private TextField txtInputDialog;
    /**
     * PopupView with the asynchronous input dialog that asks the user to insert the file name being
     * generated with the Survey Reviews
     */
    private PopupView popupInputDialog;

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
        configureButtonExportSurveyAnswers();
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
            currentSelectedSurvey=getCurrentSelectedSurvey();
            if(currentSelectedSurvey==null){
                showNotificationForNoSelectedSurvey();
            }else{
                exportReviews();
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
    /**
     * Exports the current selected Survey reviews
     */
    private void exportReviews(){
        try{
            this.currentController=new ExportSurveyAnswersController(currentSelectedSurvey);
            askForFileName();
        }catch(IllegalArgumentException e){
            showNotificationForNoReviewsOnSelectedSurvey();
        }
    }

    /**
     * Asks for the file name being generated with the Survey reviews on a asynchronous input dialog
     */
    private void askForFileName(){
        if(popupInputDialog==null){
            createInputDialog();
            popupInputDialog.setHideOnMouseOut(false);
        }
        popupInputDialog.setPopupVisible(true);
    }

    /**
     * Creates a Popupview that serves as a Popup dialog
     * <br>Since we are working on a asynchronous environment, we cant really
     * return the input written on dialog, so a PopupView is returned
     * @param title String with the popup dialog
     * @return PopupView with the
     */
    private void createInputDialog(){
        createInputTextField();
        createConfirmButton();
        createCancelButton();
        HorizontalLayout layoutButtons=new HorizontalLayout(btnConfirm,btnCancel);
        VerticalLayout layoutInputDialog=new VerticalLayout(txtInputDialog,layoutButtons);
        //TO-DO: ADD RADIO BUTTON FOR DIFFERENT FILE EXTENSIONS (ALLTHOUGH FOR NOW IT IS ONLY REQUIRED CSV FILES)
        this.popupInputDialog=new PopupView("",layoutInputDialog);
    }

    /**
     * Creates the Textfield used in the input dialog
     */
    private void createInputTextField(){
        txtInputDialog=new TextField(ASK_FOR_FILE_NAME_DIALOG_TITLE);
    }

    /**
     * Creates a new Button for the confirm option used on dialogs
     * @return Button with the confirm button used on dialogs
     */
    private void createConfirmButton(){
        this.btnConfirm=new Button(CONFIRM_BUTTON);
        btnConfirm.addClickListener(clickEvent -> {
            clearInputTextField();
            this.fileName=txtInputDialog.getValue()+CommonFileExtensions.CSV_EXTENSION; //SEE createInputDialog()
            if(FilesUtils.isFileNameValid(this.fileName)){
		if(currentController.exportAnswersFromSurvey(fileName)){
                    //TO-DO: ADD POPUP DIALOG THAT TELLS THE MANAGER THAT FILE HAS BEEN GENERATED 
                    //SUCCESSFULY, AND SHOW A BUTTON THAT LETS HIM DOWNLOAD THE FILE
                }else{
                    showNotificationForInvalidFileGeneration();
                }
                closeInputDialog();
            }else{
                showNotificationForInvalidFileName();
            }
            clearInputTextField();
        });
    }
    /**
     * Creates a new Button for the cancel option used on dialogs
     * @return Button with the cancel button used on dialogs
     */
    private void createCancelButton(){
       btnCancel=new Button(CANCEL_BUTTON);
       btnCancel.addClickListener(clickEvent -> {closeInputDialog();clearInputTextField();});
    }

    /**
     * Clears the textfield input that is on the input dialog
     */
    private void clearInputTextField(){this.txtInputDialog.setValue("");}

    /**
     * Closes the current open input dialog
     */
    private void closeInputDialog(){this.popupInputDialog.setPopupVisible(false);};

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

    /**
     * Shows up a notification that pops up to the manager if he attemps to export the
     * answers of a Survey in a file which name is invalid
     */
    private void showNotificationForInvalidFileName(){
        PopupNotification.show(INVALID_FILE_NAME_TITLE,INVALID_FILE_NAME_MESSAGE
                , Notification.Type.ERROR_MESSAGE, Position.TOP_RIGHT);
    }

    /**
     * Shows up a notification that pops up if an error occures while generating
     * the file that contains the Survey reviews
     */
    private void showNotificationForInvalidFileGeneration(){
        PopupNotification.show(INVALID_FILE_GENERATION_TITLE
                ,INVALID_FILE_GENERATION_MESSAGE
                ,Notification.Type.ERROR_MESSAGE,Position.TOP_RIGHT);
    }
}
