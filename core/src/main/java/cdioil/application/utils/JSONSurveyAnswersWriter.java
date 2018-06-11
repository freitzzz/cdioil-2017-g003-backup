package cdioil.application.utils;

import cdioil.application.utils.services.xml.SurveyAnswersXMLService;
import cdioil.domain.Review;
import cdioil.files.FileWriter;
import cdioil.xsl.XSLTransformer;
import java.io.File;
import java.util.List;

/**
 * JSONSurveyAnswersWriter class that writes survey answers into a JSON file
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 6.0 of FeedbackMonkey
 */
public final class JSONSurveyAnswersWriter implements SurveyAnswersWriter{
    /**
     * File with the file that is going to be written all survey answers in a JSON format
     */
    private final File file;
    /**
     * List with all the survey answers being written into the JSON file
     */
    private final List<Review> surveyReviews;
    /**
     * Builds a new JSONSurveyAnswersWriter with the file which is going to 
     * written all survey answers in a JSON format
     * @param filename String with the file name which is going to be written all 
     * survey answers
     * @param surveyReviews List with the all the survey reviews that are going to be written 
     * in the file in a JSON format
     */
    public JSONSurveyAnswersWriter(String filename,List<Review> surveyReviews){
        this.file=new File(filename);
        this.surveyReviews=surveyReviews;
    }
    /**
     * Method that writes all survey answers into a JSON file
     * @return boolean true if the survey answers were written with success, false if not
     */
    @Override
    public boolean write() {
        return FileWriter.writeFile(file,XSLTransformer.create(XSLSurveyAnswersDocuments
                .JSON_SURVEY_ANSWERS_XSLT)
                .transform(new SurveyAnswersXMLService(surveyReviews)
                        .toXMLDocument()));
    }
}
