/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.application;

import cdioil.application.utils.MathUtils;
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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
     * Chosen survey by the user.
     */
    private Survey survey;

    /**
     * Average value for answers of binary questions.
     */
    private Map<Question, Double> binaryMean;

    /**
     * Average value for answers of quantitative questions.
     */
    private Map<Question, Double> quantitativeMean;

    /**
     * Mean deviation for answers of binary questions.
     */
    private Map<Question, Double> binaryMeanDeviation;

    /**
     * Mean deviation for answers of quantitative questions.
     */
    private Map<Question, Double> quantitativeMeanDeviation;

    /**
     * Total of binary questions.
     */
    private Map<Question, Integer> binaryTotal;

    /**
     * Total of quantitative questions.
     */
    private Map<Question, Integer> quantitativeTotal;

    /**
     * Builds an instance of ExportSurveyAnswersController.
     *
     */
    public ExportSurveyStatisticsController() {
        surveys = new ArrayList<>();
        binaryMean = new TreeMap<>();
        quantitativeMean = new TreeMap<>();
        binaryMeanDeviation = new TreeMap<>();
        quantitativeMeanDeviation = new TreeMap<>();
        binaryTotal = new TreeMap<>();
        quantitativeTotal = new TreeMap<>();
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
    public List<Review> getAllReviews() {
        List<Review> reviews = new ReviewRepositoryImpl().getReviewsBySurvey(survey);
        if (reviews == null || reviews.isEmpty()) {
            return null;
        }
        return reviews;
    }

    /**
     * Returns a map where the keys are the questions and the values are its answers.
     * 
     * @param reviews All reviews of the survey
     * @return the map with all question-answers pairs
     */
    private Map<Question, List<Answer>> getAllAnswers(List<Review> reviews) {
        Map<Question, List<Answer>> answers = new TreeMap<>();

        reviews.stream().map((Review r) -> r.getReviewQuestionAnswers()).forEachOrdered((answer) -> {
            answer.keySet().forEach((Question q) -> {
                List<Answer> answerList = new ArrayList<>();
                answerList.add(answer.get(q));

                if (answers.containsKey(q)) {
                    answerList.addAll(answers.get(q));
                    answers.put(q, answerList);
                } else {
                    answers.put(q, answerList);
                }
            });
        });
        return answers;
    }

    /**
     * Calculates the statistics about the survey.
     */
    public void calculateStats() {
        List<Review> reviews = getAllReviews();
        Map<Question, List<Answer>> answers = getAllAnswers(reviews);

        if (answers != null) {
            Map<Question, List<Answer>> binaryAnswers = new TreeMap<>(); //Will store all binary answers
            Map<Question, List<Answer>> quantitativeAnswers = new TreeMap<>(); //Will store all quantitative answers

            getAnswers(answers, binaryAnswers, quantitativeAnswers); //Fills the two maps with the questions its answers

            if (!binaryAnswers.isEmpty() || !quantitativeAnswers.isEmpty()) {
                for(Question q : binaryAnswers.keySet()){
                    List<Double> values = new ArrayList<>();
                    int total = 0;
                   for(Answer a : binaryAnswers.get(q)){
                       String answer = a.getContent();
                        total++;
                       if(answer.equalsIgnoreCase("Sim")) values.add((double) 1);
                       else if(answer.equalsIgnoreCase("Não")) values.add((double) 0);
                   }
                   binaryTotal.put(q, total);
                   binaryMean.put(q, MathUtils.calculateMean(values));
                   binaryMeanDeviation.put(q, MathUtils.calculateMeanDeviation(values));
                }
                
                for(Question q : quantitativeAnswers.keySet()){
                    List<Double> values = new ArrayList<>();
                    int total = 0;
                    for(Answer a : quantitativeAnswers.get(q)){
                        values.add(Double.parseDouble(a.getContent()));
                        total++;
                    }
                    quantitativeTotal.put(q, total);
                    quantitativeMean.put(q, MathUtils.calculateMean(values));
                    quantitativeMeanDeviation.put(q, MathUtils.calculateMeanDeviation(values));
                }
            }
        }
    }

    /**
     * Fills two maps, one with all the binary answers and another with all the quantitative answers.
     *
     * @param answers Map that contains all answers
     * @param binaryAnswers Map that will store the binary answers
     * @param quantitativeAnswers List that will store all quantitative answers
     */
    private void getAnswers(Map<Question, List<Answer>> answers, Map<Question, List<Answer>> binaryAnswers, Map<Question, List<Answer>> quantitativeAnswers) {
        answers.keySet().forEach((Question q) -> {
            QuestionTypes type = q.getType();
            if (type.equals(QuestionTypes.BINARY)) {
                binaryAnswers.put(q, answers.get(q));
            }
            if (type.equals(QuestionTypes.QUANTITATIVE)) {
                quantitativeAnswers.put(q, answers.get(q));
            }
        });
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
                if (value.equalsIgnoreCase("Sim")) {
                    content = 1;
                }
                if (value.equalsIgnoreCase("Não")) {
                    content = 0;
                }
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
        if (statsWriter == null) {
            return false;
        }
        return statsWriter.writeStats();
    }
}
