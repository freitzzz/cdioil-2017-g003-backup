package cdioil.application.utils;

import cdioil.files.FileReader;
import cdioil.files.FilesUtils;
import java.io.File;

/**
 * Interface that represents the XSL documents for the Survey Answers transformations
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */
public interface XSLSurveyAnswersDocuments {
    /**
     * Constant that represents the XSL document containing the transformation being applied to a XML file 
     * to transform it as a CSV document containing all reviews of a Survey
     */
    public static final String CSV_SURVEY_ANSWERS_XSLT
            =FilesUtils.listAsString(FileReader.readFile(
                    new File(XSLSurveyAnswersDocuments.class.getClassLoader().getResource("xsl/CSVSurveyReviewsXSLT.xsl").getPath())));
    /**
     * Constant that represents the XSL document containing the transformation being applied to a XML file 
     * to transform it as a JSON document containing all reviews of a Survey
     */
    public static final String JSON_SURVEY_ANSWERS_XSLT
            =FilesUtils.listAsString(FileReader.readFile(
                    new File(XSLSurveyAnswersDocuments.class.getClassLoader().getResource("xsl/JSONSurveyReviewsXSLT.xsl").getPath())));
}
