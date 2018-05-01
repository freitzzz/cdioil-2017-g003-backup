package cdioil.domain;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
import cdioil.domain.authz.UsersGroup;
import cdioil.time.TimePeriod;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit testing class for class Review.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @authoer João
 */
public class ReviewTest {

    Review instance;

    @Before
    public void setUp() {
        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
        List<SurveyItem> list = new LinkedList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
        BinaryQuestion firstQuestion = new BinaryQuestion("Question 1", "567");
        BinaryQuestion secondQuestion = new BinaryQuestion("Question 2", "456");
        BinaryQuestion thirdQuestion = new BinaryQuestion("Question 3", "345");
        globalSurvey.addQuestion(firstQuestion);
        globalSurvey.setNextQuestion(firstQuestion, secondQuestion, new BinaryQuestionOption(Boolean.FALSE), 0);
        globalSurvey.setNextQuestion(firstQuestion, thirdQuestion, new BinaryQuestionOption(Boolean.TRUE), 0);
        instance = createReview(globalSurvey);
    }

    /**
     * Constructor of class Review tests.
     */
    @Test
    public void testConstructor() {
        System.out.println("Constructor tests");
        assertNotNull("Empty constructor test", new Review());
        assertNull("The condition should succeed because the survey is "
                + "null", createReview(null));
        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
        List<SurveyItem> list = new LinkedList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
        assertNotNull("The condition should succeed because the global survey is "
                + "valid", createReview(globalSurvey));
        UsersGroup userGroup = new UsersGroup(new Manager(new SystemUser(new Email("quimBarreiros@gmail.com"), new Name("Quim",
                "Barreiros"), new Password("M3n1n4_C0M0_e_Qu3_V41"))));
        Survey targetedSurvey = new TargetedSurvey(list, timePeriod, userGroup);
        assertNotNull("The condition should succeed because the targeted survey "
                + "is valid", createReview(targetedSurvey));
    }

    /**
     * Test of getSurvey method, of class Review.
     */
    @Test
    public void testGetSurvey() {
        System.out.println("getSurvey");
        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
        List<SurveyItem> list = new LinkedList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
        BinaryQuestion firstQuestion = new BinaryQuestion("Question 1", "567");
        BinaryQuestion secondQuestion = new BinaryQuestion("Question 2", "456");
        BinaryQuestion thirdQuestion = new BinaryQuestion("Question 3", "345");
        globalSurvey.addQuestion(firstQuestion);
        globalSurvey.setNextQuestion(firstQuestion, secondQuestion, new BinaryQuestionOption(Boolean.FALSE), 0);
        globalSurvey.setNextQuestion(firstQuestion, thirdQuestion, new BinaryQuestionOption(Boolean.TRUE), 0);
        assertEquals(globalSurvey, instance.getSurvey());
    }

    /**
     * Test of getCurrentQuestion method, of class Review.
     */
    @Test
    public void testGetCurrentQuestion() {
        System.out.println("getCurrentQuestion");
        BinaryQuestion expQuestion = new BinaryQuestion("Question 1", "567");
        assertEquals(expQuestion, instance.getCurrentQuestion());
    }

    /**
     * Test of answerQuestion method, of class Review.
     */
    @Test
    public void testAnswerQuestion() {
        System.out.println("answerQuestion");
        instance.answerQuestion(new QuantitativeQuestionOption(4.0)); //Mutation test
        assertTrue(instance.answerQuestion(new BinaryQuestionOption(Boolean.FALSE)));
        assertNotNull("The condition should succeed because a question was "
                + "answered", instance.getReviewQuestionAnswers());
        assertFalse(instance.answerQuestion(new BinaryQuestionOption(Boolean.FALSE)));
    }

    /**
     * Test of isFinished method, of class Review.
     */
    @Test
    public void testIsFinished() {
        System.out.println("isFinished");
        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
        List<SurveyItem> list = new LinkedList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
        BinaryQuestion firstQuestion = new BinaryQuestion("Question 1", "567");
        BinaryQuestion secondQuestion = new BinaryQuestion("Question 2", "456");
        BinaryQuestion thirdQuestion = new BinaryQuestion("Question 3", "345");
        globalSurvey.addQuestion(firstQuestion);
        globalSurvey.setNextQuestion(firstQuestion, secondQuestion, new BinaryQuestionOption(Boolean.FALSE), 0);
        globalSurvey.setNextQuestion(firstQuestion, thirdQuestion, new BinaryQuestionOption(Boolean.TRUE), 0);
        Review review = createReview(globalSurvey);
        review.answerQuestion(new BinaryQuestionOption(Boolean.TRUE));
        assertFalse("Review isn't finished yet", review.isFinished());
        review.answerQuestion(new BinaryQuestionOption(Boolean.FALSE));
        assertTrue("Review is finished", review.isFinished());
    }

    /**
     * Test of undoAnswer method, of class Review.
     */
    @Test
    public void testUndoAnswer() {
        System.out.println("undoAnswer");
        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
        List<SurveyItem> list = new LinkedList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
        BinaryQuestion firstQuestion = new BinaryQuestion("Question 1", "567");
        BinaryQuestion secondQuestion = new BinaryQuestion("Question 2", "456");
        BinaryQuestion thirdQuestion = new BinaryQuestion("Question 3", "345");
        globalSurvey.addQuestion(firstQuestion);
        globalSurvey.setNextQuestion(firstQuestion, secondQuestion, new BinaryQuestionOption(Boolean.FALSE), 0);
        globalSurvey.setNextQuestion(firstQuestion, thirdQuestion, new BinaryQuestionOption(Boolean.TRUE), 0);
        Review review = createReview(globalSurvey);
        assertFalse("The condition should succeed because the current question"
                + " is the first one", review.undoAnswer());
        review.answerQuestion(new BinaryQuestionOption(Boolean.TRUE));
        assertTrue("The condition should succeed because we can go back to"
                + " the first question", review.undoAnswer());
        assertTrue("The condition should succeed because the current question is the first "
                + "question", review.getCurrentQuestion().equals(firstQuestion));
        assertTrue("The condition should succeed because all of the questions have been undone",
                review.getReviewQuestionAnswers().isEmpty());
    }

    /**
     * Test of submitSuggestion method, of class Review.
     */
    @Test(expected = IllegalArgumentException.class)
    public void ensureSubmitSuggestionThrowsExceptionWithInvalidValues() {
        System.out.println("ensureSubmitSuggestionThrowsExceptionWithInvalidValues");
        instance.submitSuggestion(null);
        instance.submitSuggestion("");
    }

    /**
     * Test of submitSuggestion method, of class Review.
     */
    @Test
    public void ensureSubmitSuggestionWorks() {
        System.out.println("ensureSubmitSuggestionWorks");
        assertTrue(instance.submitSuggestion("Suggestion 1"));
    }

    /**
     * Test of getReviewQuestionAnswers method, of class Review.
     */
    @Test
    public void testGetReviewQuestionAnswers() {
        System.out.println("getReviewQuestionAnswers");
        assertEquals("The condition shuold succeed because no question "
                + "has been answered", instance.getReviewQuestionAnswers(), new LinkedHashMap<>());
        instance.answerQuestion(new BinaryQuestionOption(Boolean.FALSE));
        assertNotNull("The condition should succeed because a question "
                + "has been answered", instance.getReviewQuestionAnswers());
    }

    /**
     * Test of hashCode method, of class Review.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
        List<SurveyItem> list = new LinkedList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
        BinaryQuestion firstQuestion = new BinaryQuestion("Question 1", "567");
        BinaryQuestion secondQuestion = new BinaryQuestion("Question 2", "456");
        BinaryQuestion thirdQuestion = new BinaryQuestion("Question 3", "345");
        globalSurvey.addQuestion(firstQuestion);
        globalSurvey.setNextQuestion(firstQuestion, secondQuestion, new BinaryQuestionOption(Boolean.FALSE), 0);
        globalSurvey.setNextQuestion(firstQuestion, thirdQuestion, new BinaryQuestionOption(Boolean.TRUE), 0);
        Review other = createReview(globalSurvey);
        assertEquals(instance.hashCode(), other.hashCode());

        //Mutation tests
        assertNotEquals("".hashCode(), instance.hashCode());
        other.answerQuestion(new BinaryQuestionOption(Boolean.FALSE));
        other.submitSuggestion("Suggestion");
        int num = 19 * (19 * 7 + other.getSurvey().getGraphCopy().hashCode())
                + other.getReviewQuestionAnswers().hashCode();
        assertEquals(num, other.hashCode());
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
        List<SurveyItem> list = new LinkedList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
        BinaryQuestion firstQuestion = new BinaryQuestion("Question 1", "567");
        BinaryQuestion secondQuestion = new BinaryQuestion("Question 2", "456");
        BinaryQuestion thirdQuestion = new BinaryQuestion("Question 3", "345");
        globalSurvey.addQuestion(firstQuestion);
        globalSurvey.setNextQuestion(firstQuestion, secondQuestion, new BinaryQuestionOption(Boolean.FALSE), 0);
        globalSurvey.setNextQuestion(firstQuestion, thirdQuestion, new BinaryQuestionOption(Boolean.TRUE), 0);
        Review another = createReview(globalSurvey);
        assertNotEquals("The condition should succeed because we are comparing "
                + "a review with a null value", instance, null);
        assertNotEquals("The condition should succeed because we are comparing "
                + "instances of different classes", instance, "bananas");
        assertEquals("The condition should succeed because we are comparing "
                + "the same instance", instance, instance);
        Review other = createReview(globalSurvey);
        assertEquals("The condition should succeed because the reviews are "
                + "the same", another, other);
        other.answerQuestion(new BinaryQuestionOption(Boolean.TRUE));
        another.answerQuestion(new BinaryQuestionOption(Boolean.FALSE));
        assertNotEquals("The condition should succeed because the reviews "
                + "have different answers", instance, other);
        Survey anotherSurvey = new GlobalSurvey(list, timePeriod);
        anotherSurvey.addQuestion(firstQuestion);
        globalSurvey.setNextQuestion(firstQuestion, secondQuestion, new BinaryQuestionOption(Boolean.TRUE), 0);
        Review anotherReview = createReview(anotherSurvey);
        assertNotEquals("The condition should succeed because the reviews are "
                + "associated to different surveys", anotherReview, other);
    }

    /**
     * Test of toString method, of class Review.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        TimePeriod timePeriod = new TimePeriod(LocalDateTime.of(1, Month.MARCH, 1, 1, 1),
                LocalDateTime.of(2, Month.MARCH, 2, 2, 2));
        List<SurveyItem> list = new LinkedList<>();
        list.add(new Product("ProdutoTeste", new SKU("544231234"), "1 L", new QRCode("4324235")));
        Survey globalSurvey = new GlobalSurvey(list, timePeriod);
        globalSurvey.addQuestion(new BinaryQuestion("Question 1", "567"));
        globalSurvey.addQuestion(new BinaryQuestion("Question 2", "999"));
        globalSurvey.addQuestion(new BinaryQuestion("Question 3", "12314"));
        Review other = createReview(globalSurvey);
        String expResult = other.toString();
        String result = instance.toString();
        assertEquals(result, expResult);
        assertNotEquals(result, null);
    }

    /**
     * Builds a Review instance with a survey.
     *
     * @param survey survey the review is about
     * @return new Review instance
     */
    private Review createReview(Survey survey) {
        try {
            return new Review(survey);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
