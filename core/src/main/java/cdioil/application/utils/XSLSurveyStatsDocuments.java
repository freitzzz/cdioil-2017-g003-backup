package cdioil.application.utils;

import cdioil.files.FileReader;
import cdioil.files.FilesUtils;
import java.io.File;

/**
 * Interface that represents the XSL documents for the Survey Stats
 * transformation
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @since Version 7.0 of FeedbackMonkey
 */
public interface XSLSurveyStatsDocuments {
    /**
     * Constant that represents the XSL document containing the transformation
     * being applied to a XML file to transform it to a CSV document containing
     * the stats of a Survey
     */
    public static final String CSV_SURVEY_STATS_XSLT
            = FilesUtils.listAsString(FileReader.readFile(
                    new File(XSLSurveyAnswersDocuments.class.getClassLoader().
                            getResource("xsl/CSVSurveyStatsXSLT.xsl").getFile())));
    /**
     * Constant that represents the XSL document containing the transformation
     * being applied to a XML file to transform it to a JSON document containing
     * the stats of a Survey
     */
    public static final String JSON_SURVEY_STATS_XSLT
            = FilesUtils.listAsString(FileReader.readFile(
                    new File(XSLSurveyAnswersDocuments.class.getClassLoader().
                            getResource("xsl/JSONSurveyStatsXSLT.xsl").getFile())));
}
