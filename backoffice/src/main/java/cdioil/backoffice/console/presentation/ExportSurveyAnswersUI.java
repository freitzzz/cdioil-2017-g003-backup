package cdioil.backoffice.console.presentation;

import cdioil.application.SurveyController;
import cdioil.backoffice.application.ExportSurveyAnswersController;
import cdioil.console.Console;
import cdioil.domain.Survey;
import java.util.List;

/**
 * User Interface for the <i>Exportar Respostas de um Inquéritos</i> use case <b>(US-601)</b>
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class ExportSurveyAnswersUI {
    /**
     * Constant that represents the exit code from the User Interface
     */
    private static final String EXIT_CODE="Sair";
    /**
     * Constant that represents the exit message from the User Interface
     */
    private static final String EXIT_MESSAGE="A qualquer momento digite \""+EXIT_CODE+"\" para sair";
    /**
     * Constant that represents the alternative exit message from the User Interface
     */
    private static final String ALTERNATIVE_EXIT_MESSAGE=" ou digite \""+EXIT_CODE+"\" para sair";
    /**
     * Constant that represents the character separator between the representation 
     * of all surveys
     */
    private static final String CHARACTER_SEPARATOR=" - ";
    /**
     * Constant that represents the message that asks the user to choose a survey which 
     * answers are going to be exported
     */
    private static final String CHOOSE_SURVEY_MESSAGE="Escolha um Inquérito do qual queira "
                + "exportar as respostas "+ALTERNATIVE_EXIT_MESSAGE;
    /**
     * Constant that represents the message that asks the user to choose the file which 
     * is going to hold the exported survey answers
     */
    private static final String CHOOSE_FILE_PATH_MESSAGE="Escolha o caminho do ficheiro do qual queira  "
                + "exportar as respostas "+ALTERNATIVE_EXIT_MESSAGE;
    /**
     * Constant that represents the message that is shown to the user when an invalid 
     * survey is selected
     */
    private static final String INVALID_SELECTED_SURVEY="Escolha um Inquérito válido!";
    /**
     * Constant that represents the message that informs the user that there are 
     * currently no available surveys
     */
    private static final String NO_AVAILABLE_SURVEYS_MESSAGE="Não existe nenhum inquérito disponivel!";
    /**
     * Constant that represents the message that informs the user that there are 
     * currently no reviews on the current selected survey
     */
    private static final String NO_AVAILABLE_REVIEWS_MESSAGE="O Inquérito não tem avaliações!\nPor favor escolha outro inquérito "+ALTERNATIVE_EXIT_MESSAGE;
    /**
     * Constant that represents the message that informs the user that the selected survey 
     * answers were exported with success
     */
    private static final String EXPORTED_SURVEY_ANSWERS_SUCCESS_MESSAGE="As respostas do Inquérito foram exportadas "
            + "com sucesso!";
    /**
     * Constant that represents the message that informs the user that the selected survey 
     * answers weren't exported with success
     */
    private static final String EXPORTED_SURVEY_ANSWERS_FAILURE_MESSAGE="Ocorreu um erro ao exportar as respostas do Inquérito!"
            + "\nPor favor, verifique se o ficheiro é valido";
    /**
     * Constant that represents the console header messages used for listing all available surveys
     */
    private static final String[] AVAILABLE_SURVEYS_MESSAGE={"#####Inquéritos disponiveis#####"
            ,"#####                       #####"};
    /**
     * List with the all existing surveys
     */
    private List<Survey> allSurveys;
    /**
     * Survey with the choosen survey which answers are going to be exported
     */
    private Survey currentChoosenSurvey;
    /**
     * Controller for the Export Survey Answers use case
     */
    private ExportSurveyAnswersController exportSurveyAnswersController;
    /**
     * Builds a new ExportSurveyAnswersUI
     */
    public ExportSurveyAnswersUI(){
        showAllSurveys();
    }
    /**
     * Method that shows all surveys
     */
    private void showAllSurveys(){
        getAllSurveys();
        System.out.println(EXIT_MESSAGE);
        if(allSurveys.isEmpty()){System.out.println(NO_AVAILABLE_SURVEYS_MESSAGE);return;}
        System.out.println(AVAILABLE_SURVEYS_MESSAGE[0]);
        for(int i=0;i<allSurveys.size();i++){
            System.out.println((i+1)+CHARACTER_SEPARATOR+allSurveys.get(i));
        }
        System.out.println(AVAILABLE_SURVEYS_MESSAGE[1]);
        chooseSurvey();
    }
    /**
     * Method that asks and allows the user to choose a Survey
     * @return Survey with the choosen survey, or null if the user has choosen an 
     * unexisting survey
     */
    private void chooseSurvey(){
        String choosenSurveyInString=Console.readLine(CHOOSE_SURVEY_MESSAGE);
        if(checkForExitCode(choosenSurveyInString))return;
        if((this.currentChoosenSurvey=getChoosenSurvey(choosenSurveyInString))==null){
            System.out.println(INVALID_SELECTED_SURVEY);
            chooseSurvey();
        }else{
            if(!checkForSurveyReviews()){
                System.out.println(NO_AVAILABLE_REVIEWS_MESSAGE);
                chooseSurvey();
            }else{
                chooseExportedFile();
            }
        }
    }
    /**
     * Method that asks and allows the user to choose the file that is going to be 
     * exported with the survey answers
     */
    private void chooseExportedFile(){
        String filePath=Console.readLine(CHOOSE_FILE_PATH_MESSAGE);
        if(checkForExitCode(filePath))return;
        if(exportSurveyAnswersController.exportAnswersFromSurvey(filePath)){
            System.out.println(EXPORTED_SURVEY_ANSWERS_SUCCESS_MESSAGE);
        }else{
            System.out.println(EXPORTED_SURVEY_ANSWERS_FAILURE_MESSAGE);
            chooseExportedFile();
        }
    }
    /**
     * Method that gets all current surveys
     */
    private void getAllSurveys(){
        allSurveys=new SurveyController().getAllSurveys();
    }
    /**
     * Method that checks if the user typed the exit code
     * @param message String with the message that the user typed
     * @return boolean true if the user typed in the exit code, false if not
     */
    private boolean checkForExitCode(String message){return message.equalsIgnoreCase(EXIT_CODE);}
    /**
     * Method that checks if a user selected a correct survey or not
     * @param choosenSurvey String with the survey selected
     * @return Survey with the survey that the user selected, null if an invalid survey 
     * was selected
     */
    private Survey getChoosenSurvey(String choosenSurvey){
        try{
            return allSurveys.get(Integer.parseInt(choosenSurvey)-1);
        }catch(NumberFormatException | IndexOutOfBoundsException e){
            return null;
        }
    }
    /**
     * Method that checks if the current selected survey has reviews
     * @return boolean true if the survey has reviews, false if not
     */
    private boolean checkForSurveyReviews(){
        try{
            exportSurveyAnswersController=new ExportSurveyAnswersController(currentChoosenSurvey);
            return true;
        }catch(IllegalArgumentException e){
            return false;
        }
    }
}
