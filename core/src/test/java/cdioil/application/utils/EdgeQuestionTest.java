package cdioil.application.utils;

import cdioil.domain.BinaryQuestion;
import cdioil.domain.Question;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author António Sousa [1161371]
 */
public class EdgeQuestionTest {

    public EdgeQuestionTest() {
    }

    @Test
    public void ensureEmptyConstructorWorks() {
        EdgeQuestion edgeQuestion = new EdgeQuestion();

        assertNotNull(edgeQuestion);
    }

    @Test
    public void ensureConstructorWorks() {

        Question question1 = new BinaryQuestion("Question 1", "Q1");
        Question question2 = new BinaryQuestion("Question 2", "Q2");

        Vertex origin = new Vertex(question1);
        Vertex destination = new Vertex(question2);

        Edge edge = new Edge(question1.getOptionList().get(0), 0, origin, destination);

        EdgeQuestion edgeQuestion = new EdgeQuestion(edge, question2);

        assertNotNull(edgeQuestion);
    }

    @Test
    public void ensureGetEdgeWorks() {

        Question question1 = new BinaryQuestion("Question 1", "Q1");
        Question question2 = new BinaryQuestion("Question 2", "Q2");

        Vertex origin = new Vertex(question1);
        Vertex destination = new Vertex(question2);

        Edge edge = new Edge(question1.getOptionList().get(0), 0, origin, destination);

        EdgeQuestion edgeQuestion = new EdgeQuestion(edge, question2);

        assertEquals(edgeQuestion.getEdge(), edge);
    }

    @Test
    public void ensureGetOutgoingQuestionWorks() {

        Question question1 = new BinaryQuestion("Question 1", "Q1");
        Question question2 = new BinaryQuestion("Question 2", "Q2");

        Vertex origin = new Vertex(question1);
        Vertex destination = new Vertex(question2);

        Edge edge = new Edge(question1.getOptionList().get(0), 0, origin, destination);

        EdgeQuestion edgeQuestion = new EdgeQuestion(edge, question2);

        assertEquals(edgeQuestion.getOutgoingQuestion(), question2);
    }

    @Test
    public void ensureEdgeQuestionsWithEqualEdgesHaveSameHashCode() {

        Question question1 = new BinaryQuestion("Question 1", "Q1");
        Question question2 = new BinaryQuestion("Question 2", "Q2");

        Vertex origin = new Vertex(question1);
        Vertex destination = new Vertex(question2);

        Edge edge = new Edge(question1.getOptionList().get(0), 0, origin, destination);

        EdgeQuestion edgeQuestion = new EdgeQuestion(edge, question2);

        Edge edge2 = new Edge(question1.getOptionList().get(0), 0, origin, destination);

        EdgeQuestion edgeQuestion2 = new EdgeQuestion(edge2, question2);

        assertEquals(edgeQuestion.hashCode(), edgeQuestion2.hashCode());
    }

    @Test
    public void ensureEdgeQuestionWithDifferentEdgesHaveDifferentHashCode() {

        Question question1 = new BinaryQuestion("Question 1", "Q1");
        Question question2 = new BinaryQuestion("Question 2", "Q2");

        Vertex origin = new Vertex(question1);
        Vertex destination = new Vertex(question2);

        Edge edge = new Edge(question1.getOptionList().get(0), 0, origin, destination);

        EdgeQuestion edgeQuestion = new EdgeQuestion(edge, question2);

        Edge edge2 = new Edge(question1.getOptionList().get(1), 0, origin, destination);

        EdgeQuestion edgeQuestion2 = new EdgeQuestion(edge2, question2);

        assertNotEquals(edgeQuestion.hashCode(), edgeQuestion2.hashCode());
    }

    @Test
    public void ensureSameReferenceEdgeQuestionIsEqual() {

        Question question1 = new BinaryQuestion("Question 1", "Q1");
        Question question2 = new BinaryQuestion("Question 2", "Q2");

        Vertex origin = new Vertex(question1);
        Vertex destination = new Vertex(question2);

        Edge edge = new Edge(question1.getOptionList().get(0), 0, origin, destination);

        EdgeQuestion edgeQuestion = new EdgeQuestion(edge, question2);

        assertEquals(edgeQuestion, edgeQuestion);
    }

    @Test
    public void ensureNullEdgeQuestionIsNotEqual() {

        Question question1 = new BinaryQuestion("Question 1", "Q1");
        Question question2 = new BinaryQuestion("Question 2", "Q2");

        Vertex origin = new Vertex(question1);
        Vertex destination = new Vertex(question2);

        Edge edge = new Edge(question1.getOptionList().get(0), 0, origin, destination);

        EdgeQuestion edgeQuestion = new EdgeQuestion(edge, question2);

        EdgeQuestion nullEdgeQuestion = null;

        assertNotEquals(edgeQuestion, nullEdgeQuestion);
    }

    @Test
    public void ensureDifferentClassObjectIsNotEqual() {

        Question question1 = new BinaryQuestion("Question 1", "Q1");
        Question question2 = new BinaryQuestion("Question 2", "Q2");

        Vertex origin = new Vertex(question1);
        Vertex destination = new Vertex(question2);

        Edge edge = new Edge(question1.getOptionList().get(0), 0, origin, destination);

        EdgeQuestion edgeQuestion = new EdgeQuestion(edge, question2);

        assertFalse(edgeQuestion.equals(edge)); //in order to explicitly call EdgeQuestion's equals method
    }

    @Test
    public void ensureToStringIsEqualIfObjectsHaveEqualAttributes() {

        Question question1 = new BinaryQuestion("Question 1", "Q1");
        Question question2 = new BinaryQuestion("Question 2", "Q2");

        Vertex origin = new Vertex(question1);
        Vertex destination = new Vertex(question2);

        Edge edge = new Edge(question1.getOptionList().get(0), 0, origin, destination);

        EdgeQuestion edgeQuestion = new EdgeQuestion(edge, question2);

        EdgeQuestion edgeQuestionCopy = new EdgeQuestion(edge, question2);

        assertEquals(edgeQuestion.toString(), edgeQuestionCopy.toString());
    }
}
