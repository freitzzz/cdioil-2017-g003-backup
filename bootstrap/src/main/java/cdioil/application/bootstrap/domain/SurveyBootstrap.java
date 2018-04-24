package cdioil.application.bootstrap.domain;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.BinaryQuestionOption;
import cdioil.domain.GlobalSurvey;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.SurveyItem;
import cdioil.domain.SurveyState;
import cdioil.domain.TargetedSurvey;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.SystemUser;
import cdioil.domain.authz.UsersGroup;
import cdioil.persistence.impl.CategoryQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;
import cdioil.time.TimePeriod;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class SurveyBootstrap {

    private SurveyRepositoryImpl surveyRepository;
    private MarketStructureRepositoryImpl marketStructureRepository;
    private CategoryQuestionsLibraryRepositoryImpl categoryQuestionsLibraryRepository;
    private UserRepositoryImpl userRepository;
    private ManagerRepositoryImpl managerRepository;

    public SurveyBootstrap() {
        surveyRepository = new SurveyRepositoryImpl();
        marketStructureRepository = new MarketStructureRepositoryImpl();
        categoryQuestionsLibraryRepository = new CategoryQuestionsLibraryRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        managerRepository = new ManagerRepositoryImpl();
        prepareSurveys();
    }

    private void prepareSurveys() {
        List<SurveyItem> surveyItems = new LinkedList<>();
        surveyItems.add(marketStructureRepository
                .findCategoriesByPathPattern("10938DC").get(0));


//        SystemUser user = userRepository.findByEmail(new Email("tiago.almeida@sonae.pt"));
//        
//        Manager manager = managerRepository.findBySystemUser(user);
//        
//        UsersGroup group = new UsersGroup(manager);
//        
//        TargetedSurvey survey = new TargetedSurvey(surveyItems, new TimePeriod(LocalDateTime.now(),
//                LocalDateTime.of(2018, Month.MAY, 25, 14, 0)), group);
//        
//        survey.changeState(SurveyState.ACTIVE);
        
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
        //Review review = new Review(survey);
        surveyRepository.add(survey);
        //Review otherReview = new Review(surveyRepository.findAllActiveSurveys().get(0));
    }
}