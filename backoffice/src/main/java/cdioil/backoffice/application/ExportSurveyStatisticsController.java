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
    private final List<Survey> surveys;

    /**
     * Chosen survey by the user.
     */
    private Survey survey;

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
        iterableAllSurveys.forEach(validSurvey
                -> surveys.add(validSurvey)
        );
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
            return new ArrayList<>();
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

        reviews.stream().map(r -> r.getReviewQuestionAnswers()).
                forEachOrdered(answer
                        -> answer.keySet().forEach(q -> {
                    List<Answer> answerList = new ArrayList<>();
                    answerList.add(answer.get(q));

                    if (answers.containsKey(q)) {
                        answerList.addAll(answers.get(q));
                        answers.put(q, answerList);
                    } else {
                        answers.put(q, answerList);
                    }
                })
                );
        return answers;
    }

    /**
     * Calculates the statistics about the survey.
     */
    public void calculateStats() {
        List<Review> reviews = getAllReviews();
        Map<Question, List<Answer>> answers = getAllAnswers(reviews);

        if (answers == null || answers.isEmpty()) {
            return;
        }

        Map<Question, List<Answer>> binaryAnswers = new TreeMap<>(); //Will store all binary answers
        Map<Question, List<Answer>> quantitativeAnswers = new TreeMap<>(); //Will store all quantitative answers

        getAnswers(answers, binaryAnswers, quantitativeAnswers); //Fills the two maps with the questions its answers
        getBinaryStats(binaryAnswers);
        getQuantitativeStats(quantitativeAnswers);
    }

    /**
     * Calculates the statistics related to the binary ansers.
     *
     * @param binaryAnswers Map with all binary answers
     */
    public void getBinaryStats(Map<Question, List<Answer>> binaryAnswers) {
        if (binaryAnswers.isEmpty()) {
            return;
        }
        for (Map.Entry<Question, List<Answer>> entry : binaryAnswers.entrySet()) {
            Question q = entry.getKey();
            List<Double> values = new ArrayList<>();
            int total = 0;
            for (Answer a : entry.getValue()) {
                String answer = a.getContent();
                if ("true".equalsIgnoreCase(answer)) {
                    values.add((double) 1);
                    total++;
                } else if ("false".equalsIgnoreCase(answer)) {
                    values.add((double) 0);
                    total++;
                }
            }
            binaryTotal.put(q, total);
            binaryMean.put(q, MathUtils.calculateMean(values));
            binaryMeanDeviation.put(q, MathUtils.calculateMeanDeviation(values));
        }
    }

    /**
     * Calculates the statistics related to the quantitative ansers.
     *
     * @param quantitativeAnswers Map with all quantitative answers
     */
    public void getQuantitativeStats(Map<Question, List<Answer>> quantitativeAnswers) {
        if (!quantitativeAnswers.isEmpty()) {
            for (Map.Entry<Question, List<Answer>> entry : quantitativeAnswers.entrySet()) {
                Question q = entry.getKey();
                List<Double> values = new ArrayList<>();
                int total = 0;
                for (Answer a : entry.getValue()) {
                    values.add(Double.parseDouble(a.getContent()));
                    total++;
                }
                quantitativeTotal.put(q, total);
                quantitativeMean.put(q, MathUtils.calculateMean(values));
                quantitativeMeanDeviation.put(q, MathUtils.calculateMeanDeviation(values));
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
        answers.keySet().forEach(q -> {
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
     * Retrieves the survey's database ID.
     *
     * @return the ID of the chosen survey
     */
    private long getSurveyID() {
        return new SurveyRepositoryImpl().getSurveyID(survey);
    }

    /**
     * Exports the statistics about the survey to a file.
     *
     * @param filePath Path where the file will be stored
     * @return
     */
    public boolean exportStatsFromSurvey(String filePath) {
        calculateStats();
        SurveyStatsWriter statsWriter = SurveyStatsWriterFactory.create(filePath, getSurveyID(), binaryTotal,
                quantitativeTotal, binaryMean, quantitativeMean, binaryMeanDeviation, quantitativeMeanDeviation);
        if (statsWriter == null) {
            return false;

        }
        return statsWriter.writeStats();
    }
}
