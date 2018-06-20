package cdioil.application.bootstrap.domain;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.Category;
import cdioil.domain.CategoryQuestionsLibrary;
import cdioil.domain.GlobalSurvey;
import cdioil.domain.IndependentQuestionsLibrary;
import cdioil.domain.Product;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.SurveyItem;
import cdioil.domain.SurveyState;
import cdioil.domain.TargetedSurvey;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.UsersGroup;
import cdioil.persistence.impl.CategoryQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.IndependentQuestionsLibraryRepositoryImpl;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.ReviewRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;
import cdioil.time.TimePeriod;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SurveyBootstrap {

    /**
     * Survey repository.
     */
    private SurveyRepositoryImpl surveyRepository;
    /**
     * Market Structure Repository.
     */
    private MarketStructureRepositoryImpl marketStructureRepository;
    /**
     * CategoryQuestionsLibrary Repository.
     */
    private CategoryQuestionsLibraryRepositoryImpl categoryQuestionsLibraryRepository;

    /**
     * Survey bootstrap.
     */
    public SurveyBootstrap() {
        surveyRepository = new SurveyRepositoryImpl();
        marketStructureRepository = new MarketStructureRepositoryImpl();
        categoryQuestionsLibraryRepository = new CategoryQuestionsLibraryRepositoryImpl();
        prepareSurveys();
    }

    /**
     * Creates bootstrap surveys.
     */
    private void prepareSurveys() {
        List<SurveyItem> surveyItems = new LinkedList<>();
        Category bootstrapCategory = marketStructureRepository
                .findCategoriesByPathPattern("10938DC").get(0);
        surveyItems.add(bootstrapCategory.getProductSetIterator().next());
        surveyItems.add(bootstrapCategory);

        Survey survey = new GlobalSurvey(surveyItems, new TimePeriod(LocalDateTime.now(),
                LocalDateTime.of(2020, 1, 20, 12, 12)));

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
            for (Question question : persistedQuestions) {
                if (questionID.equalsIgnoreCase(question.getQuestionID())) {
                    fetchedQuestions.add(question);
                }
            }
        }

        Question firstQuestion = fetchedQuestions.get(0);
        Question secondQuestion = fetchedQuestions.get(1);
        Question thirdQuestion = fetchedQuestions.get(2);

        survey.addQuestion(firstQuestion);
        survey.addQuestion(secondQuestion);
        survey.addQuestion(thirdQuestion);

        survey.setNextQuestion(firstQuestion, secondQuestion,
                firstQuestion.getOptionList().get(0), 0); //true
        survey.setNextQuestion(firstQuestion, thirdQuestion,
                firstQuestion.getOptionList().get(1), 0); //false

        survey.changeState(SurveyState.ACTIVE);
        surveyRepository.add(survey);
        surveyRepository.add(createStressTestSurvey());
        surveyRepository.add(createDummySurveyForDummyProduct());

        RegisteredUser registeredUser = new RegisteredUserRepositoryImpl()
                .getUsersByDomain("email.com").get(0);
        Manager manager = new ManagerRepositoryImpl().findBySystemUser
        (new UserRepositoryImpl().findByEmail(new Email("bom.gestor@sonae.pt")));
        UsersGroup usersGroup = new UsersGroup(manager);
        usersGroup.addUser(registeredUser);
        survey = new TargetedSurvey(surveyItems,
                new TimePeriod(LocalDateTime.now(), LocalDateTime.of(2099,Month.JULY,31,15,55)), usersGroup);
        survey.changeState(SurveyState.ACTIVE);
        survey.addQuestion(firstQuestion);
        survey.addQuestion(secondQuestion);
        survey.addQuestion(thirdQuestion);

        survey.setNextQuestion(firstQuestion, secondQuestion,
                firstQuestion.getOptionList().get(0), 0); //true
        survey.setNextQuestion(firstQuestion, thirdQuestion,
                firstQuestion.getOptionList().get(1), 0); //false
        surveyRepository.add(survey);
        for(int i=0;i<50;i++){
            surveyRepository.add(createDummyTargetSurvey(registeredUser,manager));
        }
        Review r = new Review(survey);         
        new ReviewRepositoryImpl().merge(r);
    }

    /**
     * Creates the survey that is used for the stress test.
     *
     * @return stress test survey
     */
    private Survey createStressTestSurvey() {
        List<String> questionIDList;
        List<Question> fetchedQuestions;
        List<SurveyItem> surveyItems;
        Survey survey;
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
                        LocalDateTime.of(2020, 1, 12, 12, 12)));
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
        return survey;
    }
    /**
     * Creates a dummy target survey for a certain registered user made by a certain manager
     * @param registeredUser RegisteredUser with the user which the survey is targeted for
     * @param manager Manager with the manager that created the survey
     * @return Survey with the dummy target survey for a certain registered user made by a certain manager
     */
    private Survey createDummyTargetSurvey(RegisteredUser registeredUser,Manager manager){
        List<SurveyItem> surveyItems=new ArrayList<>();
        surveyItems.add(new MarketStructureRepositoryImpl().findCategoriesByPathPattern("10938DC").get(0));
        UsersGroup usersGroup=new UsersGroup(manager);
        usersGroup.addUser(registeredUser);
        Survey dummyTargetSurvey=new TargetedSurvey(surveyItems,new TimePeriod(LocalDateTime.now(),LocalDateTime.of(2020,Month.MARCH,22,22,22)),usersGroup);
        dummyTargetSurvey.changeState(SurveyState.ACTIVE);
        return dummyTargetSurvey;
    }
    /**
     * Method that creates a dummy survey for a dummy product (Agua Mineral Natural)
     * @return Survey with the survey for the dummy product
     */
    private Survey createDummySurveyForDummyProduct(){
        List<SurveyItem> surveyItems=new ArrayList<>();
        Product product=new MarketStructureRepositoryImpl().findProductByName("Agua Mineral Natural").get(0);
        surveyItems.add(product);
        GlobalSurvey globalSurvey=new GlobalSurvey(surveyItems,new TimePeriod(LocalDateTime.now()
                ,LocalDateTime.of(2099,Month.JULY,31,15,55)));
        BinaryQuestion question1 = new BinaryQuestion("Gosta da textura da Agua?", "AGUA1");
        BinaryQuestion question2 = new BinaryQuestion("Considera esta agua superior a Lean?", "AGUA2");
        BinaryQuestion question3 = new BinaryQuestion("Esta Agua e superior a qualquer liquido alguma vez ja ingerido?", "AGUA3");
        IndependentQuestionsLibraryRepositoryImpl independentQuestionsRepo = new IndependentQuestionsLibraryRepositoryImpl();

        IndependentQuestionsLibrary independentQuestionsLibrary = independentQuestionsRepo.findLibrary();

        independentQuestionsLibrary.addQuestion(question1);
        independentQuestionsLibrary.addQuestion(question2);
        independentQuestionsLibrary.addQuestion(question3);

        independentQuestionsRepo.merge(independentQuestionsLibrary);

        //Persisted questions need to be used rather than using the previous Question instances.
        List<String> questionIDList = new LinkedList<>(Arrays.asList("AGUA1", "AGUA2", "AGUA3"));
        List<Question> fetchedQuestions = new LinkedList<>();

        Set<Question> persistedQuestions = independentQuestionsLibrary.getID();

        for (String questionID : questionIDList) {
            for (Question question : persistedQuestions) {
                if (questionID.equalsIgnoreCase(question.getQuestionID())) {
                    fetchedQuestions.add(question);
                }
            }
        }

        Question firstQuestion = fetchedQuestions.get(0);
        Question secondQuestion = fetchedQuestions.get(1);
        Question thirdQuestion = fetchedQuestions.get(2);

        globalSurvey.addQuestion(firstQuestion);
        globalSurvey.addQuestion(secondQuestion);
        globalSurvey.addQuestion(thirdQuestion);

        globalSurvey.setNextQuestion(firstQuestion, secondQuestion,
                firstQuestion.getOptionList().get(0), 0); //true
        globalSurvey.setNextQuestion(firstQuestion, thirdQuestion,
                firstQuestion.getOptionList().get(1), 0); //false
        globalSurvey.setNextQuestion(secondQuestion,thirdQuestion,secondQuestion.getOptionList().get(0),0);

        globalSurvey.changeState(SurveyState.ACTIVE);
        return globalSurvey;
    }
}
