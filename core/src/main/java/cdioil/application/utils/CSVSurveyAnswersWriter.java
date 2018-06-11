package cdioil.application.utils;

import cdioil.application.utils.services.xml.SurveyAnswersXMLService;
import cdioil.domain.Review;
import cdioil.files.FileWriter;
import cdioil.xsl.XSLTransformer;
import java.io.File;
import java.util.List;

/**
 * Utilitary class that writes survey answers into a CSV file
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class CSVSurveyAnswersWriter implements SurveyAnswersWriter{
    /**
     * Constant that represents the label used for the question identifier on the CSV file
     */
    private static final String QUESTION_LABEL="Questão";
    /**
     * Constant that represents the label used for the answer identifier on the CSV file
     */
    private static final String ANSWER_LABEL="Respostas";
    /**
     * Constant that represents the label used for suggestion identified on the CSV file
     */
    private static final String SUGGESTION_LABEL="Sugestões";
    /**
     * Constant that represents the label being used for the Survey Informations header
     */
    private static final String SURVEY_INFORMATIONS_HEADER="Informações Inquérito";
    /**
     * Constant that represents the label being used for the survey review count identified on the CSV file
     */
    private static final String SURVEY_REVIEW_COUNT="NºAvaliações:";
    /**
     * Constant that represents the number of lines used to space different information about the reviews
     */
    private static final int CSV_LINES_SPACING=3;
    /**
     * Constant that represents the delimiter use for the CSV file (UTF-8 semi-colon like)
     */
    private static final char CSV_DELIMITER=';';
    /**
     * File with the file that is going to be written with all survey answers
     */
    private final File file;
    /**
     * List with all survey reviews being exported
     */
    private final List<Review> surveyReviews;
    /**
     * Builds a new CSVSurveyAnswersWriter with the file that is going to be 
     * written all survey answers
     * @param filename String with the filename that is going to be written all survey answers
     * @param surveyReviews List with all survey reviews being exported
     */
    public CSVSurveyAnswersWriter(String filename,List<Review> surveyReviews){
        this.file=new File(filename);
        this.surveyReviews=surveyReviews;
    }
    /**
     * Method that writes all answers from a certain survey into a CSV file
     * @return boolean true if all answers were writed with success, false if not
     */
    @Override
    public boolean write() {
        return FileWriter.writeFile(file,XSLTransformer.create(XSLSurveyAnswersDocuments
                .CSV_SURVEY_ANSWERS_XSLT)
                .transform(new SurveyAnswersXMLService(surveyReviews)
                        .toXMLDocument()));
    }
}
