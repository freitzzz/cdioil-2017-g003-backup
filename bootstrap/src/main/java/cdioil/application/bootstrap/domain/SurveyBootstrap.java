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

        BinaryQuestion question1 = new BinaryQuestion("Questao Binaria 1 Survey Bootstrap", "BQS1");
        BinaryQuestion question2 = new BinaryQuestion("Questao Binaria 2 Survey Bootstrap", "BQS2");
        BinaryQuestion question3 = new BinaryQuestion("Questao Binaria 3 Survey Bootstrap", "BQS3");

        survey.addQuestion(question1);
        survey.addQuestion(question2);
        survey.addQuestion(question3);

        BinaryQuestionOption option1 = new BinaryQuestionOption(true);
        BinaryQuestionOption option2 = new BinaryQuestionOption(false);

        survey.setNextQuestion(question1, question2, option1, 0);
        survey.setNextQuestion(question1, question3, option2, 0);

        survey.changeState(SurveyState.ACTIVE);
        surveyRepository.add(survey);
    }
}