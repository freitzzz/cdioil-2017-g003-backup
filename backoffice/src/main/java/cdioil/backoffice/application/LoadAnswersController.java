package cdioil.backoffice.application;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.BinaryQuestionOption;
import cdioil.domain.Category;
import cdioil.domain.GlobalSurvey;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.MultipleChoiceQuestionOption;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.Question;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.SurveyItem;
import cdioil.time.TimePeriod;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Controller Class
 *
 * Stress Tests the application by loading
 * hundreds of survey reviews on runtime
 */
public class LoadAnswersController {

    /**
     * Loads hundreds of generated survey reviews
     * into the application in runtime
     *
     * @return time (in ms) that took to load
     */
    public long performStressTest() {
        final long startTime = System.currentTimeMillis();
        
        //TODO load
        // Criar Questoes
        BinaryQuestion bq12
                = new BinaryQuestion("Bla, bla?", "12");
        BinaryQuestion bq13
                = new BinaryQuestion("Bla, bla, bla?", "13");
        BinaryQuestion bq15
                = new BinaryQuestion("Bla, bla, bla?", "15");
        QuantitativeQuestion q16
                = new QuantitativeQuestion("Ble, ble", "16",
                null);
        MultipleChoiceQuestion mc34
                = new MultipleChoiceQuestion("Bli, bli, bli", "34",
                null);

        List<SurveyItem> items = new LinkedList<>();
        items.add(new Category("c1", "c1path"));
        Survey survey = new GlobalSurvey(items,
                new TimePeriod(LocalDateTime.now(),
                        LocalDateTime.MAX));
        survey.addQuestion(bq12);
        survey.addQuestion(bq13);
        survey.addQuestion(bq15);
        survey.addQuestion(q16);
        survey.addQuestion(mc34);

        survey.setNextQuestion(mc34, bq12, new MultipleChoiceQuestionOption("Na, na, na"), 0);
        survey.setNextQuestion(mc34, bq13, new MultipleChoiceQuestionOption("Ne, ne, ne,"), 0);
        survey.setNextQuestion(mc34, bq15, new MultipleChoiceQuestionOption("Ni, ni, ni"), 0);
        survey.setNextQuestion(bq12, q16, new BinaryQuestionOption(true), 0);
        survey.setNextQuestion(bq12, q16, new BinaryQuestionOption(false), 0);

        // Distribution intervals

        // Gerar Respostas
        List<Review> allReviews = new LinkedList<>();
        Review r;
        Question currentQuestion;

        Random randomNumber = new Random(100);
        for (int i = 0; i < 100000; i++) {
            r = new Review(survey);
            currentQuestion = r.getCurrentQuestion();
            int generatedValue = randomNumber.nextInt();

            if (currentQuestion instanceof BinaryQuestion) {

            } else if(currentQuestion instanceof MultipleChoiceQuestion) {

            }
        }

        // Carregar Grafo com respostas

        final long endTime = System.currentTimeMillis();


        return endTime - startTime;
    }
}
