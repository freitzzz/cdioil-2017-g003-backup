/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Question;
import cdioil.files.CommonFileExtensions;
import java.util.Map;

/**
 * Factory of SurveyStatsWriter.
 *
 * @author Rita Gonçalves (1160912)
 */
public final class SurveyStatsWriterFactory {

    /**
     * Creates an instance of SurveyStatsWriter.
     *
     * @param filename Path of the file
     * @param totalBinary Total of answers to binary questions
     * @param totalQuantitative Total of answers to quantitative questions
     * @param binaryMean Average value for binary answers
     * @param quantitativeMean Average value for quantitative answers
     * @param binaryMeanDeviation Mean deviation for binary answers
     * @param quantitativeMeanDeviation Mean deviation for quantitative answers
     *
     * @return the created SurveyStatsWriter. If the file is not valid, returns null.
     */
    public static SurveyStatsWriter create(String filename, Map<Question, Integer> totalBinary, Map<Question, Integer> totalQuantitative,
            Map<Question, Double> binaryMean, Map<Question, Double> quantitativeMean, Map<Question, Double> binaryMeanDeviation,
            Map<Question, Double> quantitativeMeanDeviation) {
        if (filename.endsWith(CommonFileExtensions.CSV_EXTENSION)) return new CSVSurveyStatsWriter(filename, totalBinary, 
                totalQuantitative, binaryMean, quantitativeMean, binaryMeanDeviation, quantitativeMeanDeviation);
        return null;
    }

    /**
     * Hides the default constructor.
     */
    private SurveyStatsWriterFactory() {
    }
}
