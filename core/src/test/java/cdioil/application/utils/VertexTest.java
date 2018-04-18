//package cdioil.application.utils;
//
//import cdioil.domain.BinaryQuestion;
//import cdioil.domain.BinaryQuestionOption;
//import cdioil.domain.MultipleChoiceQuestion;
//import cdioil.domain.MultipleChoiceQuestionOption;
//import cdioil.domain.Question;
//import cdioil.domain.QuestionOption;
//import org.junit.Test;
//import java.util.LinkedHashSet;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.Assert.*;
//
///**
// *
// * @author António Sousa [1161371]
// */
//public class VertexTest {
//
//    public VertexTest() {
//    }
//
//    @Test
//    public void testJPAConstructor() {
//        Vertex v = new Vertex();
//
//        assertNotNull(v);
//    }
//
//    @Test
//    public void ensureAddAdjacentVertexWorks() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0.0, v1, v2);
//        v1.addAdjacentVertex(edge, q2);
//
//        assertEquals(1, v1.numAdjacentVertices());
//        assertEquals(1, v1.numOutgoingEdges());
//
//        Set<Question> expected = new LinkedHashSet<>();
//        expected.add(q2);
//        Iterable<Question> result = v1.getAllAdjacentVertexElements();
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void ensureAddAdjacentVertexDoesNotAddEqualEdges() {
//
//        //An edge is equal to another edge, if the element and origin vertex are equal
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0.0, v1, v2);
//
//        v1.addAdjacentVertex(edge, q2);
//
//        assertEquals(1, v1.numAdjacentVertices());
//        assertEquals(1, v1.numOutgoingEdges());
//
//        Question q3 = new BinaryQuestion("This is yet another question", "A51");
//        Vertex v3 = new Vertex(q3);
//        Edge edge2 = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0.0, v1, v3);
//
//        v1.addAdjacentVertex(edge2, q3);    //Since the edge is equal, the previous edge should be replaced by this one
//
//        Set<Question> expected = new LinkedHashSet<>();
//        expected.add(q3);
//
//        Iterable<Question> result = v1.getAllAdjacentVertexElements();
//
//        assertEquals(expected, result);
//
//        assertEquals("Number of outgoing edges should still be one", 1, v1.numOutgoingEdges());
//        assertEquals("Number of adjacent vertices should still be one", 1, v1.numAdjacentVertices());
//    }
//
//    @Test
//    public void ensureAddAdjacentVertexAllowsMultipleEdgesToConnectTheSameVertex() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        //The adjacent vertex is the same, but the edge connecting them is not
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0.0, v1, v2);
//        v1.addAdjacentVertex(edge, q2);
//
//        Edge edge2 = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0.0, v1, v2);
//        v1.addAdjacentVertex(edge2, q2);
//
//        assertEquals("Number of outgoing edges should be two", 2, v1.numOutgoingEdges());
//        assertEquals("Number of adjacent vertices should be one", 1, v1.numAdjacentVertices());
//    }
//
//    @Test
//    public void ensureGetAddjacentVertexReturnsNullIfNoAdjacentVertexHasBeenAdded() {
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0.0, v1, v2);
//
//        assertNull(v1.getAdjacentVertex(edge));
//    }
//
//    @Test
//    public void ensureGetAddjacentVertexReturnsNullIfNoEqualEdgeIsFound() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0.0, v1, v2);
//
//        v1.addAdjacentVertex(edge, q2);
//
//        Edge fakeEdge = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0.0, v1, v2);
//
//        assertNull(v1.getAdjacentVertex(fakeEdge));
//    }
//
//    @Test
//    public void ensureGetAddjacentVertexReturnsCorrectVertex() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0.0, v1, v2);
//
//        v1.addAdjacentVertex(edge, q2);
//
//        assertEquals(q2, v1.getAdjacentVertex(edge));
//
//        Question q3 = new BinaryQuestion("Yet another question", "A51");
//        Vertex v3 = new Vertex(q3);
//        edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0.0, v1, v3);
//
//        v1.addAdjacentVertex(edge, q3);
//
//        assertEquals(q3, v1.getAdjacentVertex(edge));
//    }
//
//    @Test
//    public void ensureRemoveAdjancentVertexRemovesAllEdgesConnectingBothVertices() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        Edge edge1 = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0.0, v1, v2);
//
//        v1.addAdjacentVertex(edge1, q2);
//
//        Edge edge2 = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0.0, v1, v2);
//
//        v1.addAdjacentVertex(edge2, q2);
//
//        assertEquals("Number of outgoing edges should currently be two", 2, v1.numOutgoingEdges());
//        assertEquals("Number of adjacent vertices should currently be one", 1, v1.numAdjacentVertices());
//
//        v1.removeAdjacentVertex(q2);
//
//        assertEquals("Number of outgoing edges should currently be zero", 0, v1.numOutgoingEdges());
//        assertEquals("Number of adjacent vertices should currently be zero", 0, v1.numAdjacentVertices());
//    }
//
//    @Test
//    public void ensureGetEdgesIsNullWhenNoAdjacentVertexHasBeenAdded() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//
//        Iterable<Edge> edges = v1.getEdges(q2);
//
//        assertNull(edges);
//    }
//
//    @Test
//    public void ensureGetEdgesIsNullIfVertexIsNotConnected() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        Edge edge1 = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v1, v2);
//        v1.addAdjacentVertex(edge1, q2);
//
//        Question q3 = new BinaryQuestion("This is yet another question", "A51");
//
//        Iterable<Edge> edges = v1.getEdges(q3);
//
//        assertNull(edges);
//    }
//
//    @Test
//    public void ensureGetEdgesReturnsAllEdgesConnectingVertices() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        Edge edge1 = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v1, v2);
//        v1.addAdjacentVertex(edge1, q2);
//
//        Edge edge2 = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0, v1, v2);
//        v1.addAdjacentVertex(edge2, q2);
//
//        Iterable<Edge> edges = v1.getEdges(q2);
//        Set<Edge> expected = new LinkedHashSet<>();
//        expected.add(edge1);
//        expected.add(edge2);
//
//        assertEquals(expected, edges);
//    }
//
//    @Test
//    public void ensureGetAllAdjacentVertexElementsWorks() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        Edge edge1 = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v1, v2);
//        v1.addAdjacentVertex(edge1, q2);
//
//        Edge edge2 = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0, v1, v2);
//        v1.addAdjacentVertex(edge2, q2);
//
//        Set<Question> expected = new LinkedHashSet<>();
//        expected.add(q2);
//
//        Iterable<Question> result = v1.getAllAdjacentVertexElements();
//
//        assertEquals(expected, result);
//
//        Question q3 = new BinaryQuestion("Another question", "A123");
//        Vertex v3 = new Vertex(q3);
//
//        edge2 = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0, v1, v3);
//        v1.addAdjacentVertex(edge2, q3);
//
//        expected.add(q3);
//        result = v1.getAllAdjacentVertexElements();
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void ensureGetAllOutgoingEdgesWorks() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        Edge edge1 = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v1, v2);
//        v1.addAdjacentVertex(edge1, q2);
//
//        Edge edge2 = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0, v1, v2);
//        v1.addAdjacentVertex(edge2, q2);
//
//        Iterable<Edge> result = v1.getAllOutgoingEdges();
//
//        Set<Edge> expected = new LinkedHashSet<>();
//        expected.add(edge1);
//        expected.add(edge2);
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void ensureSameReferenceMakesVerticesEqual() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        assertEquals(v1, v1);
//    }
//
//    @Test
//    public void ensureNullMakesObjectNotEqual() {
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        assertFalse(v1.equals(null));
//    }
//
//    @Test
//    public void ensureDifferentClassesMakeObjectsNotEqual() {
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        assertFalse(v1.equals(q1));
//    }
//
//    @Test
//    public void ensureVerticesWithDifferentElementsAreNotEqual() {
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question too", "A144");
//        Vertex v2 = new Vertex(q2);
//
//        assertNotEquals(v1, v2);
//    }
//
//    @Test
//    public void ensureVerticesWithDifferentNumberAdjacentverticesAreNotEqual() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v2 = new Vertex(q2);
//
//        Question q3 = new BinaryQuestion("This is another question", "A123");
//        Vertex v3 = new Vertex(q3);
//        v1.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v1, v3), q3);
//
//        assertNotEquals(v1, v2);
//    }
//
//    @Test
//    public void ensureVerticesWithDifferentAdjacentverticesAreNotEqual() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v2 = new Vertex(q2);
//
//        Question q3 = new BinaryQuestion("This is another question", "A123");
//        Vertex v3 = new Vertex(q3);
//        v1.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v1, v3), q3);
//
//        Question q4 = new BinaryQuestion("A different question, but a question nevertheless", "A112");
//        Vertex v4 = new Vertex(q4);
//        v2.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v2, v4), q4);
//
//        assertNotEquals(v1, v2);
//    }
//
//    @Test
//    public void ensureVerticesWithDifferentNumberOutgoingEdgesAreNotEqual() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v2 = new Vertex(q2);
//
//        Question q3 = new BinaryQuestion("This is another question", "A123");
//        Vertex v3 = new Vertex(q3);
//        v1.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v1, v3), q3);
//        v1.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.TRUE), 0, v1, v3), q3);
//
//        Question q4 = new BinaryQuestion("A different question, but a question nevertheless", "A112");
//        Vertex v4 = new Vertex(q4);
//        v2.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v2, v4), q4);
//
//        assertNotEquals(v1, v2);
//    }
//
//    @Test
//    public void ensureVerticesWithDifferentOutgoingEdgesAreNotEqual() {
//
//        QuestionOption optionA = new MultipleChoiceQuestionOption("Option A");
//        QuestionOption optionB = new MultipleChoiceQuestionOption("Option B");
//        QuestionOption optionC = new MultipleChoiceQuestionOption("Option C");
//        QuestionOption optionD = new MultipleChoiceQuestionOption("Option D");
//        QuestionOption optionOther = new MultipleChoiceQuestionOption("Other");
//
//        List<QuestionOption> options = new LinkedList<>();
//        options.add(optionA);
//        options.add(optionB);
//        options.add(optionC);
//        options.add(optionD);
//        options.add(optionOther);
//        Question q1 = new MultipleChoiceQuestion("This is a multiple choice question", "B149", options);
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a binary question", "A213");
//        Vertex v2 = new Vertex(q2);
//
//        v1.addAdjacentVertex(new Edge(optionA, 0, v1, v2), q2);
//        v1.addAdjacentVertex(new Edge(optionB, 0, v1, v2), q2);
//        v1.addAdjacentVertex(new Edge(optionC, 0, v1, v2), q2);
//        v1.addAdjacentVertex(new Edge(optionD, 0, v1, v2), q2);
//        v1.addAdjacentVertex(new Edge(optionOther, 0, v1, v2), q2);
//
//        options.remove(optionOther);
//
//        QuestionOption differentOption = new MultipleChoiceQuestionOption("Totally different");
//        options.add(differentOption);
//        Question q3 = new MultipleChoiceQuestion("This is a multiple choice question", "B149", options);
//        Vertex v3 = new Vertex(q3);
//
//        v3.addAdjacentVertex(new Edge(optionA, 0, v3, v2), q2);
//        v3.addAdjacentVertex(new Edge(optionB, 0, v3, v2), q2);
//        v3.addAdjacentVertex(new Edge(optionC, 0, v3, v2), q2);
//        v3.addAdjacentVertex(new Edge(optionD, 0, v3, v2), q2);
//        v3.addAdjacentVertex(new Edge(differentOption, 0, v3, v2), q2);
//
//        assertNotEquals(v1, v3);
//    }
//
//    @Test
//    public void ensureVerticesAreEqual() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v2 = new Vertex(q2);
//
//        v1.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v1, v2), q2);
//        v1.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.TRUE), 0, v1, v2), q2);
//
//        Question q1Copy = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1Copy = new Vertex(q1Copy);
//
//        Question q2Copy = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v2Copy = new Vertex(q2Copy);
//
//        //adjacent vertices were added in different order on purpose
//        v1Copy.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.TRUE), 0, v1Copy, v2Copy), q2Copy);
//        v1Copy.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v1Copy, v2Copy), q2Copy);
//
//        assertEquals(v1, v1Copy);
//    }
//
//    @Test
//    public void ensureVerticesAreDifferentIfElementIsDifferent() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a different question", "A145");
//        Vertex v2 = new Vertex(q2);
//
//        assertNotEquals(v1.hashCode(), v2.hashCode());
//    }
//
//    @Test
//    public void ensureDifferentIfAdjacencyMapIsDifferent() {
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a different question", "A145");
//        Vertex v2 = new Vertex(q2);
//
//        v1.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v1, v2), q2);
//
//        assertNotEquals(v1.hashCode(), v2.hashCode());
//    }
//
//    @Test
//    public void ensureHashCodeIsTheSameIfAttributesAreEqual() {
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v1 = new Vertex(q1);
//
//        Question q2 = new BinaryQuestion("This is a yes/no question", "A143");
//        Vertex v2 = new Vertex(q2);
//
//        Question q3 = new BinaryQuestion("This is the third question", "A243");
//        Vertex v3 = new Vertex(q3);
//
//        v1.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v1, v3), q3);
//
//        v2.addAdjacentVertex(new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v2, v3), q3);
//
//        assertEquals(v1.hashCode(), v2.hashCode());
//    }
//
//}
