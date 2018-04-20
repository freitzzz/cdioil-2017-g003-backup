package cdioil.backoffice.application;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.BinaryQuestionOption;
import cdioil.domain.Category;
import cdioil.domain.GlobalSurvey;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.MultipleChoiceQuestionOption;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.QuantitativeQuestionOption;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.SurveyItem;
import cdioil.persistence.impl.CategoryQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.ReviewRepositoryImpl;
import cdioil.time.TimePeriod;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Controller Class
 *
 * Stress Tests the application by loading hundreds of survey reviews on runtime
 */
public class LoadAnswersController {

    private ReviewRepositoryImpl reviewRepository;

    public LoadAnswersController() {
        reviewRepository = new ReviewRepositoryImpl();
    }

    /**
     * Loads hundreds of generated survey reviews into the application in
     * runtime
     *
     * @return time (in ms) that took to load
     */
    public long performStressTest() {
        final long startTime = System.currentTimeMillis();

        QuantitativeQuestionOption qo1 = new QuantitativeQuestionOption(new Double(1));
        QuantitativeQuestionOption qo2 = new QuantitativeQuestionOption(new Double(2));
        QuantitativeQuestionOption qo3 = new QuantitativeQuestionOption(new Double(3));
        QuantitativeQuestionOption qo4 = new QuantitativeQuestionOption(new Double(4));

        List<QuestionOption> questionOptionsQuantitative = Arrays.asList(qo1, qo2, qo3, qo4);

        MultipleChoiceQuestionOption mo1 = new MultipleChoiceQuestionOption("Na, na, na");
        MultipleChoiceQuestionOption mo2 = new MultipleChoiceQuestionOption("Ne, ne, ne");
        MultipleChoiceQuestionOption mo3 = new MultipleChoiceQuestionOption("Ni, ni, ni");

        List<QuestionOption> questionOptionsMultiple = Arrays.asList(mo1, mo2, mo3);

        // Criar Questoes
        BinaryQuestion bq12
                = new BinaryQuestion("Bla, bla?", "12");
        BinaryQuestion bq13
                = new BinaryQuestion("Bla, bla, bla?", "13");
        BinaryQuestion bq15
                = new BinaryQuestion("Bla, bla, bla?", "15");
        QuantitativeQuestion q16
                = new QuantitativeQuestion("Ble, ble", "16",
                        questionOptionsQuantitative);
        MultipleChoiceQuestion mc34
                = new MultipleChoiceQuestion("Bli, bli, bli", "34",
                        questionOptionsMultiple);

        List<SurveyItem> items = new LinkedList<>();
        items.add(new Category("c1", "10938DC"));
        Survey survey = new GlobalSurvey(items,
                new TimePeriod(LocalDateTime.now(),
                        LocalDateTime.MAX));
        survey.addQuestion(mc34);
        survey.addQuestion(bq12);
        survey.addQuestion(bq13);
        survey.addQuestion(bq15);
        survey.addQuestion(q16);

        survey.setNextQuestion(mc34, bq12, mo1, 0);
        survey.setNextQuestion(mc34, bq13, mo2, 0);
        survey.setNextQuestion(mc34, bq15, mo3, 0);
        survey.setNextQuestion(bq12, q16, new BinaryQuestionOption(true), 0);
        survey.setNextQuestion(bq12, q16, new BinaryQuestionOption(false), 0);

        // Gerar Respostas
        List<Review> allReviews = new LinkedList<>();

        Random randomNumber = new Random(100);
        Review currentReview;
        int generatedNumber;
        Question currentQuestion;
        for (int i = 0; i < 100000; i++) {
            currentReview = new Review(survey);
            while (!currentReview.isFinished()) {
                generatedNumber = randomNumber.nextInt();
                currentQuestion = currentReview.getCurrentQuestion();

                if (currentQuestion.equals(mc34)) {
                    if (generatedNumber <= 20) {
                        currentReview.answerQuestion(mo3);
                    } else if (generatedNumber > 20 && generatedNumber <= 50) {
                        currentReview.answerQuestion(mo1);
                    } else {
                        currentReview.answerQuestion(mo2);
                    }
                } else if (currentQuestion.equals(bq12)) {
                    if (generatedNumber <= 70) {
                        currentReview.answerQuestion(new BinaryQuestionOption(true));
                    } else {
                        currentReview.answerQuestion(new BinaryQuestionOption(false));
                    }
                } else if (currentQuestion.equals(bq13)) {
                    if (generatedNumber <= 50) {
                        currentReview.answerQuestion(new BinaryQuestionOption(true));
                    } else {
                        currentReview.answerQuestion(new BinaryQuestionOption(false));
                    }
                } else if (currentQuestion.equals(bq15)) {
                    if (generatedNumber <= 20) {
                        currentReview.answerQuestion(new BinaryQuestionOption(true));
                    } else {
                        currentReview.answerQuestion(new BinaryQuestionOption(false));
                    }
                } else if (currentQuestion.equals(q16)) {
                    if (generatedNumber <= 20) {
                        currentReview.answerQuestion(qo1);
                    } else if (generatedNumber > 20 && generatedNumber <= 40) {
                        currentReview.answerQuestion(qo2);
                    } else if (generatedNumber > 40 && generatedNumber <= 70) {
                        currentReview.answerQuestion(qo3);
                    } else {
                        currentReview.answerQuestion(qo4);
                    }
                }
            }

            allReviews.add(currentReview);
            reviewRepository.add(currentReview);
        }

        final long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
