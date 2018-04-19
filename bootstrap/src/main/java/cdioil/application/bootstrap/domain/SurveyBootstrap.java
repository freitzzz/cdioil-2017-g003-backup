package cdioil.application.bootstrap.domain;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.BinaryQuestionOption;
import cdioil.domain.GlobalSurvey;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.SurveyItem;

import cdioil.domain.SurveyState;
import cdioil.persistence.impl.CategoryQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.time.TimePeriod;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class SurveyBootstrap {

    private SurveyRepositoryImpl surveyRepository;
    private MarketStructureRepositoryImpl marketStructureRepository;
    private CategoryQuestionsLibraryRepositoryImpl categoryQuestionsLibraryRepository;

    public SurveyBootstrap() {
        surveyRepository = new SurveyRepositoryImpl();
        marketStructureRepository = new MarketStructureRepositoryImpl();
        categoryQuestionsLibraryRepository = new CategoryQuestionsLibraryRepositoryImpl();

        prepareSurveys();
    }

    private void prepareSurveys() {
        List<SurveyItem> surveyItems = new LinkedList<>();
        surveyItems.add(marketStructureRepository
                .findCategoriesByPathPattern("10938DC").get(0));

        Survey survey = new GlobalSurvey(surveyItems, new TimePeriod(LocalDateTime.now(),
                LocalDateTime.MAX));

        BinaryQuestion bq1 = new BinaryQuestion("Bin. Question 1", "bq1");
        BinaryQuestion bq2 = new BinaryQuestion("Bin. Question 2", "bq2");
        BinaryQuestion bq3 = new BinaryQuestion("Bin. Question 3", "bq3");

        survey.addQuestion(bq1);
        survey.addQuestion(bq2);
        survey.addQuestion(bq3);

        BinaryQuestionOption bqOption1 = new BinaryQuestionOption(true);
        BinaryQuestionOption bqOption2 = new BinaryQuestionOption(false);

        survey.setNextQuestion(bq1, bq2, bqOption1, 0);
        survey.setNextQuestion(bq1, bq3, bqOption2, 0);

        survey.changeState(SurveyState.ACTIVE);
        Review review = new Review(survey);
        surveyRepository.add(survey);
        Review otherReview = new Review(surveyRepository.findAllActiveSurveys().get(0));
    }
}