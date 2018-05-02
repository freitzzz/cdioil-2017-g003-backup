/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import cdioil.domain.Question;
import cdioil.files.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Exports statistics of a survey to a .csv file.
 *
 * @author Rita Gonçalves (1160912)
 */
public class CSVSurveyStatsWriter implements SurveyStatsWriter {

    //Constants
    /**
     * Splitter of the columns of the file.
     */
    private static final String SPLITTER = ";";

    //Attributes
    /**
     * File to read.
     */
    private final File file;

    /**
     * Average value for answers of binary questions.
     */
    private final Map<Question, Double> binaryMean;

    /**
     * Average value for answers of quantitative questions.
     */
    private final Map<Question, Double> quantitativeMean;

    /**
     * Mean deviation for answers of binary questions.
     */
    private final Map<Question, Double> binaryMeanDeviation;

    /**
     * Mean deviation for answers of quantitative questions.
     */
    private final Map<Question, Double> quantitativeMeanDeviation;

    /**
     * Total of binary questions.
     */
    private final Map<Question, Integer> binaryTotal;

    /**
     * Total of quantitative questions.
     */
    private final Map<Question, Integer> quantitativeTotal;

    /**
     * Field that identifies the question by its ID.
     */
    private static final String QUESTION_ID = "Questão (ID)";

    /**
     * Field that identifies the type of the question.
     */
    private static final String QUESTION_TYPE = "Tipo";

    /**
     * Field that contains the number of answers to the question.
     */
    private static final String TOTAL = "Total";

    /**
     * Field that contains the average of the answers to the question.
     */
    private static final String AVG = "Média";

    /**
     * Field that contains the mean deviation of the answers to the question.
     */
    private static final String MEAN_DEVIATION = "Desvio Padrão";

    /**
     * Creates a new CSVSurveyStatsWriter.
     *
     * @param filename Path of the file
     * @param binaryTotal Total of answers to binary questions
     * @param quantitativeTotal Total of answers to quantitative questions
     * @param binaryMean Average value for binary answers
     * @param quantitativeMean Average value for quantitative answers
     * @param binaryMeanDeviation Mean deviation for binary answers
     * @param quantitativeMeanDeviation Mean deviation for quantitative answers
     *
     */
    public CSVSurveyStatsWriter(String filename, Map<Question, Integer> binaryTotal, Map<Question, Integer> quantitativeTotal, Map<Question, Double> binaryMean,
            Map<Question, Double> quantitativeMean, Map<Question, Double> binaryMeanDeviation, Map<Question, Double> quantitativeMeanDeviation) {
        this.file = new File(filename);
        this.binaryTotal = binaryTotal;
        this.quantitativeTotal = quantitativeTotal;
        this.quantitativeMeanDeviation = quantitativeMeanDeviation;
        this.binaryMeanDeviation = binaryMeanDeviation;
        this.quantitativeMean = quantitativeMean;
        this.binaryMean = binaryMean;
    }

    /**
     * Writes the statistics in a .csv file.
     *
     * @return true, if the statistics are successfully exported. Otherwise, returns false
     */
    @Override
    public boolean writeStats() {
        List<String> fileContent = new ArrayList<>();
        String header = QUESTION_ID + SPLITTER + QUESTION_TYPE + SPLITTER + TOTAL + SPLITTER + AVG + SPLITTER + MEAN_DEVIATION;
        fileContent.add(header);

        Set<Entry<Question, Double>> binaryAnswers = binaryMean.entrySet();
        if (binaryAnswers.isEmpty()) {
            fileContent.add("Não há questões binárias");
        }
        for (Map.Entry<Question, Double> entry : binaryAnswers) {
            Question q = entry.getKey();
            String info = q.getQuestionID() + SPLITTER + q.getType() + SPLITTER + binaryTotal.get(q) + SPLITTER + binaryMean.get(q) + SPLITTER + binaryMeanDeviation.get(q);
            fileContent.add(info);
        }
        
        Set<Entry<Question, Double>> quantitativeQuestions = quantitativeMean.entrySet();
        if (quantitativeQuestions.isEmpty()) {
            fileContent.add("Não há questões quantitativas");
        }
        for (Map.Entry<Question, Double> entry : quantitativeQuestions) {
            Question q = entry.getKey();
            String info = q.getQuestionID() + SPLITTER + q.getType() + SPLITTER + quantitativeTotal.get(q) + SPLITTER + quantitativeMean.get(q) + SPLITTER + quantitativeMeanDeviation.get(q);
            fileContent.add(info);
        }
        return FileWriter.writeFile(file, fileContent);
    }
}