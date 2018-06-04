package cdioil.application.utils;

import cdioil.files.CommonFileExtensions;
import cdioil.domain.Review;
import java.util.List;

/**
 * SurveyAnswersWriter Factory that creates a new writer depending on 
 * the file extension of the file that is going to be written
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class SurveyAnswersWriterFactory {
    /**
     * Hides default constructor
     */
    private SurveyAnswersWriterFactory(){}
    /**
     * Creates a new SurveyAnswersWriter with the filename that is going to 
     * be written all survey answers
     * @param filename String with the file name that is going to be written all survey answers
     * @param surveyReviews List with all survey reviews being exported
     * @return SurveyAnswersWriter with the writter for the respective file extension, 
     * or null if the file extension is not allowed
     */
    public static SurveyAnswersWriter create(String filename,List<Review> surveyReviews){
        if(filename.endsWith(CommonFileExtensions.CSV_EXTENSION)){
            return new CSVSurveyAnswersWriter(filename,surveyReviews);
        }else if(filename.endsWith(CommonFileExtensions.JSON_EXTENSION)){
            return new JSONSurveyAnswersWriter(filename,surveyReviews);
        }else if(filename.endsWith(CommonFileExtensions.XML_EXTENSION)){
            return new XMLSurveyAnswersWriter(filename,surveyReviews);
        }
        return null;
    }
}
