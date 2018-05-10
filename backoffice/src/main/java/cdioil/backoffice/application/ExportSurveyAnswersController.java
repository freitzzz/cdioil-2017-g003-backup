package cdioil.backoffice.application;

import cdioil.application.utils.SurveyAnswersWriter;
import cdioil.application.utils.SurveyAnswersWriterFactory;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.persistence.impl.ReviewRepositoryImpl;
import java.io.Serializable;
import java.util.List;

/**
 * Controller for the <i>Exportar Respostas de um Inquerito</i> use case <b>(US-601)</b>
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class ExportSurveyAnswersController implements Serializable{
    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 5L;
    /**
     * Constant that represents the message that occures when a Survey hasn't any reviews
     */
    private static final String INVALID_SURVEY_MESSAGE="Não existem respostas para "
            + "este Inquérito!";
    /**
     * Survey with the survey which answers are going to be exported
     */
    private Survey survey;
    /**
     * List with all reviews from a certain survey
     */
    private List<Review> surveyReviews;
    /**
     * Hides default constructor
     */
    private ExportSurveyAnswersController(){}
    /**
     * Builds a new controller for the <i>Exportar Respostas de um Inquerito</i> use case <b>(US-601)</b> 
     * with the survey which answers are going to be exported
     * <br>Throws an IllegalArgumentExceptions if there are no reviews for the current survey
     * @param survey Survey with the survey which answers are going to be exported
     */
    public ExportSurveyAnswersController(Survey survey){
        this.survey=survey;
        getAllAnswersFromSurvey();
    }
    /**
     * Method that exports all answers from a survey into a certain file
     * @param filepath String with the filepath which is going to be written with 
     * all the survey answers
     * @return boolean true if the survey answers were exported with sucess, false if not
     */
    public boolean exportAnswersFromSurvey(String filepath){
        SurveyAnswersWriter surveyWriter=SurveyAnswersWriterFactory.create(filepath,surveyReviews);
        if(surveyWriter==null){
            return false;
        }
        return surveyWriter.write();
    }
    /**
     * Method that gets all survey answers
     */
    private void getAllAnswersFromSurvey(){
        surveyReviews=new ReviewRepositoryImpl().getReviewsBySurvey(survey);
        if(surveyReviews==null||surveyReviews.isEmpty()){
            throw new IllegalArgumentException(INVALID_SURVEY_MESSAGE);
        }
    }
}
