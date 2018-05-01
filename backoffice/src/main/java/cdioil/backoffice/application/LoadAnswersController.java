package cdioil.backoffice.application;

import cdioil.application.utils.AnswerProbabilityReader;
import cdioil.application.utils.AnswerProbabilityReaderFactory;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.persistence.impl.ReviewRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import java.util.List;
import java.util.Map;
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
     * Loads generated survey reviews into the application in runtime.
     *
     * @param filename
     * @param numAnswers
     * @return time (in ms) that took to load
     */
    public long performStressTest(String filename, int numAnswers) {

        AnswerProbabilityReader reader = AnswerProbabilityReaderFactory.create(filename);

        Map<String, List<Double>> distributions = reader.readProbabilities();

        SurveyRepositoryImpl surveyRepositoryImpl = new SurveyRepositoryImpl();

        Survey survey = surveyRepositoryImpl.findAllActiveSurveys().get(1);

        final long startTime = System.currentTimeMillis();

        /*Note: For the time being, the Questions stored within the Review are 
        still referencing those that have already been persisted. Ideally these should be copies of the Questions.*/
        Random randomNumber = new Random();
        Review currentReview;
        double generatedNumber;
        Question currentQuestion;
        for (int i = 0; i < numAnswers; i++) {
            currentReview = new Review(survey);
            while (!currentReview.isFinished()) {
                generatedNumber = randomNumber.nextDouble();
                currentQuestion = currentReview.getCurrentQuestion();
                List<QuestionOption> currentQuestionOptions = currentQuestion.getOptionList();

                List<Double> probabilities = distributions.get(currentQuestion.getQuestionID());

                double infLimit = 0;
                for (int j = 0; j < currentQuestionOptions.size(); j++) {
                    double supLimit = probabilities.get(j) + infLimit;

                    if (generatedNumber > infLimit && generatedNumber <= supLimit) {
                        QuestionOption option = currentQuestionOptions.get(j);
                        currentReview.answerQuestion(option);
                        break;
                    }

                    infLimit = supLimit;
                }
            }
            reviewRepository.add(currentReview);
        }
        final long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
