/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.application;

import static cdioil.application.utils.MathUtils.*;
import cdioil.application.utils.SurveyStatsWriter;
import cdioil.application.utils.SurveyStatsWriterFactory;
import cdioil.domain.Answer;
import cdioil.domain.Question;
import cdioil.domain.QuestionTypes;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.persistence.impl.ReviewRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Controller class for the User Story 610 - Export Survey Statistics for a CSV file.
 *
 * @author Rita Gonçalves (1160912)
 */
public class ExportSurveyStatisticsController {

    /**
     * List with all surveys.
     */
    private List<Survey> surveys;

    /**
     * List with all reviews from a survey.
     */
    private List<Review> reviews;

    /**
     * Chosen survey by the user.
     */
    private Survey survey;

    /**
     * Average value for answers of binary questions.
     */
    private double binaryMean;

    /**
     * Average value for answers of quantitative questions.
     */
    private double quantitativeMean;

    /**
     * Mean deviation for answers of binary questions.
     */
    private double binaryMeanDeviation;

    /**
     * Mean deviation for answers of quantitative questions.
     */
    private double quantitativeMeanDeviation;

    /**
     * Total of binary questions.
     */
    private int binaryTotal;

    /**
     * Total of quantitative questions.
     */
    private int quantitativeTotal;

    /**
     * Builds an instance of ExportSurveyAnswersController.
     *
     */
    public ExportSurveyStatisticsController() {
        surveys = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    /**
     * Returns a list with all surveys.
     *
     * @return all surveys
     */
    public List<Survey> getAllSurveys() {
        Iterable<Survey> iterableAllSurveys = new SurveyRepositoryImpl().findAll();
        iterableAllSurveys.forEach((s) -> {
            surveys.add(s);
        });
        return surveys;
    }

    /**
     * Returns the chosen survey.
     *
     * @param surveyID ID of the survey
     * @return the survey itself. Null if the chosen index is not valid
     */
    public Survey getChosenSurvey(String surveyID) {
        try {
            Survey chosenOne = surveys.get(Integer.parseInt(surveyID) - 1);
            survey = chosenOne;
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            return null;
        }
        return survey;
    }

    /**
     * Returns a list with all reviews of the survey.
     *
     * @return the reviews of the survey
     */
    public List<Review> getSurveyReviews() {
        reviews = new ReviewRepositoryImpl().getReviewsBySurvey(survey);
        if (reviews == null || reviews.isEmpty()) return null;
            for(Review r : reviews) System.out.println(r.toString());
        return reviews;
    }

    /**
     * Calculates the statistics about the survey.
     */
    public void calculateStats() {
        reviews = getSurveyReviews();

        List<Review> binaryReviews = new LinkedList<>();
        List<Review> quantitativeReviews = new LinkedList<>();

        getAnswers(binaryReviews, quantitativeReviews);

        List<Double> binaryAnswers = getBinaryEvaluations(binaryReviews);
        List<Double> quantitativeAnswers = getQuantitativeEvaluations(quantitativeReviews);
        
        binaryTotal = binaryAnswers.size();
        quantitativeTotal = quantitativeAnswers.size();
        
        binaryMean = calculateMean(binaryAnswers);
        quantitativeMean = calculateMean(quantitativeAnswers);

        binaryMeanDeviation = calculateMeanDeviation(binaryAnswers);
        quantitativeMeanDeviation = calculateMeanDeviation(quantitativeAnswers);
    }

    /**
     * Fills two lists, one with all the binary reviews and another with all the quantitative reviews. 
     * 
     * @param binaryReviews List that will store the binary reviews
     * @param quantitativeReviews List that will store all quantitative reviews
     */
    private void getAnswers(List<Review> binaryReviews, List<Review> quantitativeReviews) {
        for (Review r : reviews) {
            Map<Question, Answer> answers = r.getReviewQuestionAnswers();
            for (Question q : answers.keySet()) {
                QuestionTypes type = q.getType();
                if (type.equals(QuestionTypes.BINARY)) {
                    binaryReviews.add(r);
                }
                if (type.equals(QuestionTypes.QUANTITATIVE)) {
                    quantitativeReviews.add(r);
                }
            }
        }
    }

    /**
     * Fills a list with all the answers for binary questions. 
     * 
     * @param binaryReviews List with all binary reviews
     * @return a list with the answer values 
     */
    private List<Double> getBinaryEvaluations(List<Review> binaryReviews) {
        List<Double> evaluations = new ArrayList<>();
        for (Review r : binaryReviews) {
            Map<Question, Answer> answers = r.getReviewQuestionAnswers();
            for (Answer a : answers.values()) {
                String value = a.getContent();
                double content = -1;
                if (value.equalsIgnoreCase("Sim")) content = 1;
                if (value.equalsIgnoreCase("Não")) content = 0;
                if (content == -1) {
                    binaryReviews.remove(r);
                } else {
                    evaluations.add(content);
                }
            }
        }
        return evaluations;
    }
    
    /**
     * Fills a list with all the answers for quantitative questions. 
     * 
     * @param binaryReviews List with all quantitative reviews
     * @return a list with the answer values 
     */
    private List<Double> getQuantitativeEvaluations(List<Review> quantitativeReviews) {
        List<Double> evaluations = new ArrayList<>();
        for (Review r : quantitativeReviews) {
            Map<Question, Answer> answers = r.getReviewQuestionAnswers();
            for (Answer a : answers.values()) {
                try {
                    double content = Double.parseDouble(a.getContent());
                    evaluations.add(content);
                } catch (NumberFormatException ex) {
                    quantitativeReviews.remove(r);
                }
            }
        }
        return evaluations;
    }

    /**
     * Exports the statistics about the survey to a file.
     *
     * @param filePath Path where the file will be stored
     * @return 
     */
    public boolean exportStatsFromSurvey(String filePath) {
        SurveyStatsWriter statsWriter = SurveyStatsWriterFactory.create(filePath, binaryTotal, quantitativeTotal,
        binaryMean, quantitativeMean, binaryMeanDeviation, quantitativeMeanDeviation);
        if(statsWriter == null) return false;
        return statsWriter.writeStats();
    }
}
