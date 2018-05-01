package cdioil.application.bootstrap.domain;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.Category;
import cdioil.domain.CategoryQuestionsLibrary;
import cdioil.domain.GlobalSurvey;
import cdioil.domain.IndependentQuestionsLibrary;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.Survey;
import cdioil.domain.SurveyItem;
import cdioil.domain.SurveyState;
import cdioil.persistence.impl.CategoryQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.IndependentQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;
import cdioil.time.TimePeriod;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SurveyBootstrap {

    private SurveyRepositoryImpl surveyRepository;
    private MarketStructureRepositoryImpl marketStructureRepository;
    private CategoryQuestionsLibraryRepositoryImpl categoryQuestionsLibraryRepository;
    private IndependentQuestionsLibraryRepositoryImpl independentQuestionsRepo;
    private UserRepositoryImpl userRepository;
    private ManagerRepositoryImpl managerRepository;

    public SurveyBootstrap() {
        surveyRepository = new SurveyRepositoryImpl();
        marketStructureRepository = new MarketStructureRepositoryImpl();
        categoryQuestionsLibraryRepository = new CategoryQuestionsLibraryRepositoryImpl();
        independentQuestionsRepo = new IndependentQuestionsLibraryRepositoryImpl();
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

        IndependentQuestionsLibraryRepositoryImpl independentQuestionsRepo = new IndependentQuestionsLibraryRepositoryImpl();

        IndependentQuestionsLibrary independentQuestionsLibrary = independentQuestionsRepo.findLibrary();

        independentQuestionsLibrary.addQuestion(question1);
        independentQuestionsLibrary.addQuestion(question2);
        independentQuestionsLibrary.addQuestion(question3);

        independentQuestionsRepo.merge(independentQuestionsLibrary);

        //Persisted questions need to be used rather than using the previous Question instances.
        List<String> questionIDList = new LinkedList<>(Arrays.asList("BQS1", "BQS2", "BQS3"));
        List<Question> fetchedQuestions = new LinkedList<>();

        Set<Question> persistedQuestions = independentQuestionsLibrary.getID();

        for (String questionID : questionIDList) {
            for (Question q : persistedQuestions) {
                if (questionID.equalsIgnoreCase(q.getQuestionID())) {
                    fetchedQuestions.add(q);
                }
            }
        }

        Question fetchedq1 = fetchedQuestions.get(0);
        Question fetchedq2 = fetchedQuestions.get(1);
        Question fetchedq3 = fetchedQuestions.get(2);

        survey.addQuestion(fetchedq1);
        survey.addQuestion(fetchedq2);
        survey.addQuestion(fetchedq3);

        survey.setNextQuestion(fetchedq1, fetchedq2, fetchedq1.getOptionList().get(0), 0); //true
        survey.setNextQuestion(fetchedq1, fetchedq3, fetchedq1.getOptionList().get(1), 0); //false

        survey.changeState(SurveyState.ACTIVE);
        surveyRepository.add(survey);

        /*==================================
        Bootstrap Survey for Stress Test
        ====================================*/
        Category category = marketStructureRepository.findCategoriesByIdentifierPattern("10DC-10UN-1002CAT-4SCAT-2UB").get(0);
        CategoryQuestionsLibrary categoryQuestionsLibrary = categoryQuestionsLibraryRepository.findLibrary();
        Set<Question> categoryQuestionsSet = categoryQuestionsLibrary.categoryQuestionSet(category);

        questionIDList = new LinkedList<>(Arrays.asList("12", "13", "16", "34", "15"));
        fetchedQuestions = new LinkedList<>();

        for (String questionID : questionIDList) {
            for (Question q : categoryQuestionsSet) {
                if (questionID.equalsIgnoreCase(q.getQuestionID())) {
                    fetchedQuestions.add(q);
                }
            }
        }

        surveyItems = new LinkedList<>();
        surveyItems.add(category);
        survey = new GlobalSurvey(surveyItems,
                new TimePeriod(LocalDateTime.now(),
                        LocalDateTime.MAX));

        Question fetchedQuestion12 = fetchedQuestions.get(0);
        Question fetchedQuestion13 = fetchedQuestions.get(1);
        Question fetchedQuestion16 = fetchedQuestions.get(2);
        Question fetchedQuestion34 = fetchedQuestions.get(3);
        Question fetchedQuestion15 = fetchedQuestions.get(4);

        survey.addQuestion(fetchedQuestion12);
        survey.addQuestion(fetchedQuestion13);
        survey.addQuestion(fetchedQuestion16);
        survey.addQuestion(fetchedQuestion34);
        survey.addQuestion(fetchedQuestion15);

        //All options lead to the next question
        for (QuestionOption optionQ12 : fetchedQuestion12.getOptionList()) {
            survey.setNextQuestion(fetchedQuestion12, fetchedQuestion13, optionQ12, 0);
        }
        for (QuestionOption optionQ13 : fetchedQuestion13.getOptionList()) {
            survey.setNextQuestion(fetchedQuestion13, fetchedQuestion16, optionQ13, 0);
        }
        for (QuestionOption optionQ16 : fetchedQuestion16.getOptionList()) {
            survey.setNextQuestion(fetchedQuestion16, fetchedQuestion34, optionQ16, 0);
        }
        for (QuestionOption optionQ34 : fetchedQuestion34.getOptionList()) {
            survey.setNextQuestion(fetchedQuestion34, fetchedQuestion15, optionQ34, 0);
        }

        survey.changeState(SurveyState.ACTIVE);
        surveyRepository.add(survey);

    }
}
