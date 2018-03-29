package cdioil.application;

import cdioil.application.utils.SurveyAnswersWriter;
import cdioil.application.utils.SurveyAnswersWriterFactory;
import cdioil.domain.Survey;

/**
 * Controller for the <i>Exportar Respostas de um Inquerito</i> use case <b>(US-601)</b>
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class ExportSurveyAnswersController {
    
    /**
     * Builds a new controller for the <i>Exportar Respostas de um Inquerito</i> use case <b>(US-601)</b> 
     * with the survey each answers are going to be exported
     * @param survey Survey with the survey each answers are going to be exported
     */
    public ExportSurveyAnswersController(Survey survey){}
    /**
     * Method that exports all answers from a survey into a certain file
     * @param filepath String with the filepath which is going to be written with 
     * all the survey answers
     * @return boolean true if the survey answers were exported with sucess, false if not
     */
    public boolean exportAnswersFromSurvey(String filepath){
        SurveyAnswersWriter surveyWriter=SurveyAnswersWriterFactory.create(filepath);
        if(surveyWriter==null)return false;
        return surveyWriter.write();
    }
    /**
     * Method that gets all survey answers
     */
    private void getAllAnswersFromSurvey(){}
    /**
     * Hides default constructor
     */
    private ExportSurveyAnswersController(){}
}
