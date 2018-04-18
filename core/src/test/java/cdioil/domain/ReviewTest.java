//package cdioil.domain;
//
//import cdioil.domain.authz.Email;
//import cdioil.domain.authz.Manager;
//import cdioil.domain.authz.Name;
//import cdioil.domain.authz.Password;
//import cdioil.domain.authz.SystemUser;
//import cdioil.domain.authz.UsersGroup;
//import cdioil.time.TimePeriod;
//import java.time.LocalDateTime;
//import java.time.Month;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.TreeMap;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Unit testing class for class Review.
// *
// * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
// * @authoer João
// */
//public class ReviewTest {
//
//    Review instance;
//
//    @Before
//    public void setUp() {
//        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
//                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
//        List<SurveyItem> list = new LinkedList<>();
//        list.add(new Product("ProdutoTeste", new EAN("544231234"), new QRCode("4324235")));
//        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
//        BinaryQuestion firstQuestion = new BinaryQuestion("Question 1", "567");
//        BinaryQuestion secondQuestion = new BinaryQuestion("Question 2", "456");
//        BinaryQuestion thirdQuestion = new BinaryQuestion("Question 3", "345");
//        globalSurvey.addQuestion(firstQuestion);
//        globalSurvey.setNextQuestion(firstQuestion, secondQuestion, new BinaryQuestionOption(Boolean.FALSE), 0);
//        globalSurvey.setNextQuestion(firstQuestion, thirdQuestion, new BinaryQuestionOption(Boolean.TRUE), 0);
//        instance = createReview(globalSurvey);
//    }
//
//    /**
//     * Constructor of class Review tests.
//     */
//    @Test
//    public void testConstructor() {
//        System.out.println("Constructor tests");
//        assertNotNull("Empty constructor test", new Review());
//        assertNull("The condition should succeed because the survey is "
//                + "null", createReview(null));
//        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
//                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
//        List<SurveyItem> list = new LinkedList<>();
//        list.add(new Product("ProdutoTeste", new EAN("544231234"), new QRCode("4324235")));
//        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
//        assertNotNull("The condition should succeed because the global survey is "
//                + "valid", createReview(globalSurvey));
//        UsersGroup userGroup = new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
//                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
//        Survey targetedSurvey = new TargetedSurvey(list, timePeriod, userGroup);
//        assertNotNull("The condition should succeed because the targeted survey "
//                + "is valid", createReview(targetedSurvey));
//    }
//
//    /**
//     * Test of answerQuestion method, of class Review.
//     */
//    @Test
//    public void testAnswerQuestion() {
//        System.out.println("answerQuestion");
//        instance.answerQuestion(new BinaryQuestionOption(Boolean.FALSE));
//        assertNotNull("The condition should succeed because a question was "
//                + "answered", instance.getReviewQuestionAnswers());
//    }
//
//    /**
//     * Test of undoAnswer method, of class Review.
//     */
//    @Test
//    public void testUndoAnswer() {
//        System.out.println("undoAnswer");
//        instance.undoAnswer();
//        assertEquals("The condition should succeed because the answer to "
//                + "the first question was removed", instance.getReviewQuestionAnswers(), new TreeMap<>());
//        assertNotNull("The condition should succeed because no answer has been given"
//                + "yet", instance.getReviewQuestionAnswers());
//    }
//
//    /**
//     * Test of submitSuggestion method, of class Review.
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void ensureSubmitSuggestionThrowsExceptionWithInvalidValues() {
//        System.out.println("ensureSubmitSuggestionThrowsExceptionWithInvalidValues");
//        instance.submitSuggestion(null);
//        instance.submitSuggestion("");
//    }
//
//    /**
//     * Test of submitSuggestion method, of class Review.
//     */
//    @Test
//    public void ensureSubmitSuggestionWorks() {
//        System.out.println("ensureSubmitSuggestionWorks");
//        instance.submitSuggestion("Suggestion 1");
//    }
//
//    /**
//     * Test of getReviewQuestionAnswers method, of class Review.
//     */
//    @Test
//    public void testGetReviewQuestionAnswers() {
//        System.out.println("getReviewQuestionAnswers");
//        assertEquals("The condition shuold succeed because no question "
//                + "has been answered", instance.getReviewQuestionAnswers(), new TreeMap<>());
//        instance.answerQuestion(new BinaryQuestionOption(Boolean.FALSE));
//        assertNotNull("The condition should succeed because a question "
//                + "has been answered", instance.getReviewQuestionAnswers());
//    }
//
//    /**
//     * Test of hashCode method, of class Review.
//     */
//    @Test
//    public void testHashCode() {
//        System.out.println("hashCode");
//        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
//                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
//        List<SurveyItem> list = new LinkedList<>();
//        list.add(new Product("ProdutoTeste", new EAN("544231234"), new QRCode("4324235")));
//        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
//        BinaryQuestion firstQuestion = new BinaryQuestion("Question 1", "567");
//        BinaryQuestion secondQuestion = new BinaryQuestion("Question 2", "456");
//        BinaryQuestion thirdQuestion = new BinaryQuestion("Question 3", "345");
//        globalSurvey.addQuestion(firstQuestion);
//        globalSurvey.setNextQuestion(firstQuestion, secondQuestion, new BinaryQuestionOption(Boolean.FALSE), 0);
//        globalSurvey.setNextQuestion(firstQuestion, thirdQuestion, new BinaryQuestionOption(Boolean.TRUE), 0);
//        Review other = createReview(globalSurvey);
//        assertEquals(instance.hashCode(), other.hashCode());
//    }
//
//    @Test
//    public void testEquals() {
//        System.out.println("equals");
//        assertNotEquals("The condition should succeed because we are comparing "
//                + "a review with a null value", instance, null);
//        assertNotEquals("The condition should succeed because we are comparing "
//                + "instances of different classes", instance, "bananas");
//        assertEquals("The condition should succeed because we are comparing "
//                + "the same instance", instance, instance);
//        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
//                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
//        List<SurveyItem> list = new LinkedList<>();
//        list.add(new Product("ProdutoTeste", new EAN("544231234"), new QRCode("4324235")));
//        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
//        BinaryQuestion firstQuestion = new BinaryQuestion("Question 1", "567");
//        BinaryQuestion secondQuestion = new BinaryQuestion("Question 2", "456");
//        BinaryQuestion thirdQuestion = new BinaryQuestion("Question 3", "345");
//        globalSurvey.addQuestion(firstQuestion);
//        globalSurvey.setNextQuestion(firstQuestion, secondQuestion, new BinaryQuestionOption(Boolean.FALSE), 0);
//        globalSurvey.setNextQuestion(firstQuestion, thirdQuestion, new BinaryQuestionOption(Boolean.TRUE), 0);
//        Review other = createReview(globalSurvey);
//        assertEquals("The condition should succeed because the reviews are "
//                + "the same", instance, other);
//        other.answerQuestion(new BinaryQuestionOption(Boolean.TRUE));
//        instance.answerQuestion(new BinaryQuestionOption(Boolean.FALSE));
//        assertNotEquals("The condition should succeed because the reviews "
//                + "have different answers", instance, other);
//        instance.undoAnswer();
//        other.undoAnswer();
//        instance.submitSuggestion("Suggestion");
//        other.submitSuggestion("Other Suggestion");
//        assertNotEquals("The condition should succeed because the reviews "
//                + "have different suggestions", instance, other);
//        list.add(new Product("Other Product", new EAN("554231234"), new QRCode("4524235")));
//        globalSurvey = new GlobalSurvey(list, timePeriod);
//        other = createReview(globalSurvey);
//        assertNotEquals("The condition should succeed because the reviews are "
//                + "about a different list of items", instance, other);
//    }
//
//    /**
//     * Test of toString method, of class Review.
//     */
//    @Test
//    public void testToString() {
//        System.out.println("toString");
//        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
//                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
//        List<SurveyItem> list = new LinkedList<>();
//        list.add(new Product("ProdutoTeste", new EAN("544231234"), new QRCode("4324235")));
//        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
//        globalSurvey.addQuestion(new BinaryQuestion("Question 1", "567"));
//        globalSurvey.addQuestion(new BinaryQuestion("Question 2", "999"));
//        globalSurvey.addQuestion(new BinaryQuestion("Question 3", "12314"));
//        Review other = createReview(globalSurvey);
//        String expResult = other.toString();
//        String result = instance.toString();
//        assertEquals(result, expResult);
//    }
//
//    /**
//     * Builds a Review instance with a survey.
//     *
//     * @param survey survey the review is about
//     * @return new Review instance
//     */
//    private Review createReview(Survey survey) {
//        try {
//            return new Review(survey);
//        } catch (IllegalArgumentException e) {
//            return null;
//        }
//    }
//}
