//package cdioil.application.utils;
//
//import cdioil.domain.BinaryQuestion;
//import cdioil.domain.BinaryQuestionOption;
//import cdioil.domain.MultipleChoiceQuestion;
//import cdioil.domain.Question;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.LinkedHashSet;
//import java.util.LinkedList;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author António Sousa [1161371]
// */
//public class GraphTest {
//
//    public GraphTest() {
//    }
//
//    @Test
//    public void ensureVertexExistsReturnsFalseIfElementHasNotBeenInserted() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        assertFalse(graph.vertexExists(q1));
//    }
//
//    @Test
//    public void ensureVertexExistsReturnsTrueIfElementHasBeenInserted() {
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        graph.insertVertex(q1);
//
//        assertTrue(graph.vertexExists(q1));
//
//        assertEquals("The graph should have one vertex", 1, graph.numVertices());
//    }
//
//    @Test
//    public void ensureInsertVertexReturnsTrueIfVertexHasNotYetBeenInserted() {
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        assertTrue(graph.insertVertex(q1));
//
//        assertEquals("The graph should have one vertex", 1, graph.numVertices());
//    }
//
//    @Test
//    public void ensureInsertVertexReturnsFalseIfVertexHasAlreadyBeenInserted() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        graph.insertVertex(q1);
//
//        assertFalse(graph.insertVertex(q1));
//    }
//
//    @Test
//    public void ensureInsertEdgeDoesNotAllowLoops() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        graph.insertVertex(q1);
//
//        assertFalse(graph.insertEdge(q1, q1, new BinaryQuestionOption(Boolean.FALSE), 0));
//
//        assertEquals("The graph should have no edges", 0, graph.numEdges());
//    }
//
//    @Test
//    public void ensureInsertEdgeInsertsVerticesIfTheyHaveNotBeenInserted() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        assertTrue(graph.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.FALSE), 0));
//
//        assertEquals("The graph should have one edge", 1, graph.numEdges());
//
//        assertEquals("The graph should have two vertices", 2, graph.numVertices());
//    }
//
//    @Test
//    public void ensureInsertEdgeOnlyInsertsEdgeAndNotVerticesIfTheyAlreadyExist() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        graph.insertVertex(q1);
//        graph.insertVertex(q2);
//
//        assertTrue(graph.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.FALSE), 0));
//
//        assertEquals("The graph should have one edge", 1, graph.numEdges());
//
//        assertEquals("The graph should have two vertices", 2, graph.numVertices());
//    }
//
//    @Test
//    public void ensureInsertEdgeDoesNotAddEqualEdge() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        graph.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//
//        assertFalse(graph.insertEdge(q1, q3, new BinaryQuestionOption(Boolean.FALSE), 0));
//    }
//
//    @Test
//    public void ensureAdjacentQuestionsReturnsNullIfVertexHasNotYetBeenInserted() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        assertNull(graph.adjacentQuestions(q1));
//    }
//
//    @Test
//    public void ensureAdjacentQuestionsReturnsAnEmptyIterableIfQuestionHasBeenInsertedButHasNoEdges() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        graph.insertVertex(q1);
//
//        Iterable<Question> result = graph.adjacentQuestions(q1);
//
//        Iterable<Question> expected = new LinkedHashSet<>();
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void ensureAdjacentQuestionsWorks() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//
//        graph.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.TRUE), 0);
//        graph.insertEdge(q1, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        Iterable<Question> result = graph.adjacentQuestions(q1);
//
//        LinkedHashSet<Question> expected = new LinkedHashSet<>();
//
//        expected.add(q2);
//        expected.add(q3);
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void ensureAllEdgesWorks() {
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//
//        graph.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.TRUE), 0);
//        graph.insertEdge(q1, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//        graph.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.TRUE), 0);
//        graph.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        Iterator<Edge> iterator = graph.allEdges().iterator();
//
//        Edge edge = iterator.next();
//
//        assertEquals(new BinaryQuestionOption(Boolean.TRUE), edge.getElement());
//        assertEquals(q1, edge.getOriginVertexElement());
//        assertEquals(q2, edge.getDestinationVertexElement());
//
//        edge = iterator.next();
//
//        assertEquals(new BinaryQuestionOption(Boolean.FALSE), edge.getElement());
//        assertEquals(q1, edge.getOriginVertexElement());
//        assertEquals(q3, edge.getDestinationVertexElement());
//
//        edge = iterator.next();
//
//        assertEquals(new BinaryQuestionOption(Boolean.TRUE), edge.getElement());
//        assertEquals(q2, edge.getOriginVertexElement());
//        assertEquals(q3, edge.getDestinationVertexElement());
//
//        edge = iterator.next();
//
//        assertEquals(new BinaryQuestionOption(Boolean.FALSE), edge.getElement());
//        assertEquals(q2, edge.getOriginVertexElement());
//        assertEquals(q3, edge.getDestinationVertexElement());
//    }
//
//    @Test
//    public void ensureEdgesConnectingQuestionsReturnsNullIfElementsHaveNotBeenAdded() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        assertNull(graph.edgesConnectingQuestions(q1, q2));
//    }
//
//    @Test
//    public void ensureEdgesConnectingQuestionsReturnsNullIfOriginElementHasNotBeenAdded() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        graph.insertVertex(q2);
//
//        assertNull(graph.edgesConnectingQuestions(q1, q2));
//
//    }
//
//    @Test
//    public void ensureEdgesConnectingQuestionsReturnsNullIfDestinationElementHasNotBeenAdded() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        graph.insertVertex(q1);
//
//        assertNull(graph.edgesConnectingQuestions(q1, q2));
//
//    }
//
//    @Test
//    public void ensureEdgesConnectingQuestionsWorks() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        graph.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.TRUE), 0);
//        graph.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        Iterator<Edge> edgeIterator = graph.edgesConnectingQuestions(q1, q2).iterator();
//
//        Edge edge = edgeIterator.next();
//
//        assertEquals(new BinaryQuestionOption(Boolean.TRUE), edge.getElement());
//        assertEquals(q1, edge.getOriginVertexElement());
//        assertEquals(q2, edge.getDestinationVertexElement());
//
//        edge = edgeIterator.next();
//
//        assertEquals(new BinaryQuestionOption(Boolean.FALSE), edge.getElement());
//        assertEquals(q1, edge.getOriginVertexElement());
//        assertEquals(q2, edge.getDestinationVertexElement());
//    }
//
//    @Test
//    public void ensureSameReferenceMakesObjectsEqual() {
//
//        Graph g = new Graph();
//
//        assertEquals(g, g);
//    }
//
//    @Test
//    public void ensureNullObjectMakesObjectsNotEqual() {
//
//        Graph g = new Graph();
//
//        assertFalse(g.equals(null));
//    }
//
//    @Test
//    public void ensureDifferentClassObjectsAreNotEqual() {
//
//        Graph g = new Graph();
//
//        Question q = new BinaryQuestion("This is a question", "A312");
//
//        assertFalse(g.equals(q));
//    }
//
//    @Test
//    public void ensureDifferentNumberOfVerticesMakesObjectsNotEqual() {
//
//        Graph g1 = new Graph();
//
//        Graph g2 = new Graph();
//
//        Question q = new BinaryQuestion("This is a question", "A312");
//
//        g1.insertVertex(q);
//
//        assertNotEquals(g1, g2);
//    }
//
//    @Test
//    public void ensureDifferentNumberOfEdgesMakesObjectsNotEqual() {
//
//        Graph g1 = new Graph();
//
//        Graph g2 = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a question", "A312");
//
//        Question q2 = new BinaryQuestion("This is another question", "A321");
//
//        g1.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        g2.insertVertex(q1);
//        g2.insertVertex(q2);
//
//        assertNotEquals(g1, g2);
//    }
//
//    @Test
//    public void ensureDifferentConnectionsMakeObjectsNotEqual() {
//
//        Graph g1 = new Graph();
//
//        Graph g2 = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a question", "A312");
//
//        Question q2 = new BinaryQuestion("This is another question", "A321");
//
//        Question q3 = new BinaryQuestion("Yet another question", "A432");
//
//        g1.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.TRUE), 0);
//        g1.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.FALSE), 0);
//        g1.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        g2.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.TRUE), 0);
//        g2.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.FALSE), 0);
//        g2.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.TRUE), 0);
//
//        assertNotEquals(g1, g2);
//    }
//
//    @Test
//    public void ensureCopyConstructorWorks() {
//
//        Graph graph = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//
//        graph.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.TRUE), 0);
//        graph.insertEdge(q1, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//        graph.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.TRUE), 0);
//        graph.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        Graph copy = new Graph(graph);
//
//        assertEquals(copy, graph);
//        assertEquals(copy.hashCode(), graph.hashCode());
//    }
//
//    @Test
//    public void ensureIncomingEdgesReturnsNullIfElementHasNotBeenInserted() {
//
//        Question q = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Graph g = new Graph();
//
//        assertNull(g.incomingEdges(q));
//    }
//
//    @Test
//    public void ensureIncomingEdgesRetunsEmptyIterableIfElementHasBeenInsertedButHasNoEdges() {
//        Question q = new BinaryQuestion("This is a yes/no question", "A424");
//
//        Graph g = new Graph();
//
//        g.insertVertex(q);
//
//        Iterable<Edge> result = g.incomingEdges(q);
//
//        LinkedList<Edge> expected = new LinkedList<>();
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void ensureOutgoingEdgesAreNotIncludedInIncomingEdges() {
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//
//        Graph graph = new Graph();
//
//        graph.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.TRUE), 0);
//        graph.insertEdge(q1, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        Iterable<Edge> result = graph.incomingEdges(q1);
//
//        LinkedList<Edge> expected = new LinkedList<>();
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void ensureIncomingEdgesWorks() {
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//
//        Graph graph = new Graph();
//
//        graph.insertEdge(q1, q3, new BinaryQuestionOption(Boolean.TRUE), 0);
//        graph.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        Iterable<Edge> result = graph.incomingEdges(q3);
//
//        LinkedList<Edge> expected = new LinkedList<>();
//
//        Vertex v1 = new Vertex(q1);
//        Vertex v2 = new Vertex(q2);
//        Vertex v3 = new Vertex(q3);
//        expected.add(new Edge(new BinaryQuestionOption(Boolean.TRUE), 0, v1, v3));
//        expected.add(new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, v2, v3));
//
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void ensureEndVerticesReturnsNullIfEdgeIsNull() {
//
//        Graph g = new Graph();
//
//        assertNull(g.endVertices(null));
//    }
//
//    @Test
//    public void ensureEndVerticesReturnsNullIfBothOfItsVerticesAreNull() {
//        Graph g = new Graph();
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, null, null);
//
//        assertNull(g.endVertices(edge));
//    }
//
//    @Test
//    public void ensureEndVerticesReturnsNullIfOriginVertexIsNull() {
//
//        Graph g = new Graph();
//
//        Vertex origin = new Vertex(new BinaryQuestion("This is a yes/no question", "A424"));
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, origin, null);
//
//        assertNull(g.endVertices(edge));
//    }
//
//    @Test
//    public void ensureEndVerticesReturnsNullIfDestinationVertexIsNull() {
//
//        Graph g = new Graph();
//
//        Vertex destination = new Vertex(new BinaryQuestion("This is a yes/no question", "A424"));
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, null, destination);
//
//        assertNull(g.endVertices(edge));
//    }
//
//    @Test
//    public void ensureEndVerticesReturnsNullIfVerticesHaveNotBeenInserted() {
//
//        Graph g = new Graph();
//
//        Vertex origin = new Vertex(new BinaryQuestion("This is a yes/no question", "A424"));
//        Vertex destination = new Vertex(new BinaryQuestion("This is yet another yes/no question", "A123"));
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, origin, destination);
//
//        assertNull(g.endVertices(edge));
//    }
//
//    @Test
//    public void ensureEndVerticesReturnsNullIfOriginVertexHasNotBeenInserted() {
//        Graph g = new Graph();
//
//        Vertex origin = new Vertex(new BinaryQuestion("This is a yes/no question", "A424"));
//
//        Question destinationElement = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Vertex destinationVertex = new Vertex(destinationElement);
//        g.insertVertex(destinationElement);
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, origin, destinationVertex);
//
//        assertNull(g.endVertices(edge));
//    }
//
//    @Test
//    public void ensureEndVerticesReturnsNullIfDestinationVertexHasNotBeenInserted() {
//        Graph g = new Graph();
//
//        Question originElement = new BinaryQuestion("This is a yes/no question", "A424");
//        Vertex originVertex = new Vertex(originElement);
//        g.insertVertex(originElement);
//
//        Question destinationElement = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Vertex destinationVertex = new Vertex(destinationElement);
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, originVertex, destinationVertex);
//
//        assertNull(g.endVertices(edge));
//    }
//
//    @Test
//    public void ensureEndVerticesReturnsNullIfEdgeHasNotBeenInserted() {
//
//        Graph g = new Graph();
//
//        Question originElement = new BinaryQuestion("This is a yes/no question", "A424");
//        Vertex originVertex = new Vertex(originElement);
//        g.insertVertex(originElement);
//
//        Question destinationElement = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Vertex destinationVertex = new Vertex(destinationElement);
//        g.insertVertex(destinationElement);
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, originVertex, destinationVertex);
//
//        assertNull(g.endVertices(edge));
//    }
//
//    @Test
//    public void ensureEndVerticesReturnsNullIfNoEqualEdgeHasBeenInserted() {
//
//        Graph g = new Graph();
//
//        Question originElement = new BinaryQuestion("This is a yes/no question", "A424");
//        Vertex originVertex = new Vertex(originElement);
//        g.insertVertex(originElement);
//
//        Question destinationElement = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Vertex destinationVertex = new Vertex(destinationElement);
//
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, originVertex, destinationVertex);
//
//        g.insertEdge(originElement, destinationElement, new BinaryQuestionOption(Boolean.TRUE), 0);
//
//        assertNull(g.endVertices(edge));
//    }
//
//    @Test
//    public void ensureEndVerticesWorks() {
//
//        Graph g = new Graph();
//
//        Question originElement = new BinaryQuestion("This is a yes/no question", "A424");
//        Question destinationElement = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        g.insertEdge(originElement, destinationElement, new BinaryQuestionOption(Boolean.TRUE), 0);
//        g.insertEdge(originElement, destinationElement, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        Vertex originVertex = new Vertex(originElement);
//        Vertex destinationVertex = new Vertex(destinationElement);
//        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, originVertex, destinationVertex);
//
//        Question[] result = g.endVertices(edge);
//
//        Question[] expected = {originElement, destinationElement};
//
//        assertArrayEquals(expected, result);
//    }
//
//    @Test
//    public void ensureRemoveEdgesReturnsFalseIfNeitherOfTheElementsHaveBeenInserted() {
//
//        Graph g = new Graph();
//
//        Question originElement = new BinaryQuestion("This is a yes/no question", "A424");
//        Question destinationElement = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        assertFalse(g.removeEdges(originElement, destinationElement));
//    }
//
//    @Test
//    public void ensureRemoveEdgesReturnsFalseIfOriginHasNotBeenInserted() {
//        Graph g = new Graph();
//
//        Question originElement = new BinaryQuestion("This is a yes/no question", "A424");
//        Question destinationElement = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        g.insertVertex(destinationElement);
//
//        assertFalse(g.removeEdges(originElement, destinationElement));
//    }
//
//    @Test
//    public void ensureRemoveEdgesReturnsFalseIfDestinationHasNotBeenInserted() {
//        Graph g = new Graph();
//
//        Question originElement = new BinaryQuestion("This is a yes/no question", "A424");
//        Question destinationElement = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        g.insertVertex(originElement);
//
//        assertFalse(g.removeEdges(originElement, destinationElement));
//    }
//
//    @Test
//    public void ensureRemoveEdgesReturnsFalseIfNoEdgesConnectingVerticesExist() {
//
//        Graph g = new Graph();
//
//        Question originElement = new BinaryQuestion("This is a yes/no question", "A424");
//        Question destinationElement = new BinaryQuestion("This is yet another yes/no question", "A123");
//
//        g.insertVertex(originElement);
//        g.insertVertex(destinationElement);
//
//        assertFalse(g.removeEdges(originElement, destinationElement));
//    }
//
//    @Test
//    public void ensureRemoveEdgesWorks() {
//
//        Graph g = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//
//        g.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.FALSE), 0);
//        g.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.TRUE), 0);
//        g.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.TRUE), 0);
//        g.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        assertEquals("The graph should have four edges", 4, g.numEdges());
//        assertTrue(g.removeEdges(q1, q2));
//        assertEquals("The graph should now have two edges", 2, g.numEdges());
//        assertNull("There should be no edges connecting q1 and q2", g.edgesConnectingQuestions(q1, q2));
//    }
//
//    @Test
//    public void ensureRemoveVertexReturnsFalseIfVertexHasNotBeenInserted() {
//
//        Graph g = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//
//        assertFalse(g.removeVertex(q1));
//    }
//
//    @Test
//    public void ensureRemoveVertexAlsoRemovesAllIncomingEdges() {
//
//        Graph g = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//
//        g.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.FALSE), 0);
//        g.insertEdge(q1, q3, new BinaryQuestionOption(Boolean.TRUE), 0);
//        g.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.TRUE), 0);
//        g.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        assertTrue(g.removeVertex(q3));
//        assertEquals("The graph should now only have 2 vertices", 2, g.numVertices());
//        assertEquals("The graph should now only have 1 edge", 1, g.numEdges());
//    }
//
//    @Test
//    public void ensureGetFirstQuestionReturnsNullIfNoVerticesHaveBeenInserted() {
//
//        Graph g = new Graph();
//
//        assertNull(g.getFirstQuestion());
//    }
//
//    @Test
//    public void ensureGetFirstQuestionReturnsFirstQuestion() {
//
//        Graph g = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//
//        g.insertVertex(q1);
//        g.insertVertex(q2);
//        g.insertVertex(q3);
//
//        assertEquals(q1, g.getFirstQuestion());
//    }
//
//    @Test
//    public void ensureMultipleUsesOfGetFirstQuestionStillReturnsTheFirstQuestion() {
//        Graph g = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//
//        g.insertVertex(q1);
//        g.insertVertex(q2);
//        g.insertVertex(q3);
//
//        assertEquals(q1, g.getFirstQuestion());
//        assertEquals(q1, g.getFirstQuestion());
//        assertEquals(q1, g.getFirstQuestion());
//    }
//
//    @Test
//    public void ensureOutgoingEdgesReturnsNullIfQuestionHasNotBeenInserted() {
//        Graph g = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//        
//        g.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//        g.insertEdge(q2, q3, new BinaryQuestionOption(Boolean.TRUE), 0);
//    
//        assertNull(g.outgoingEdges(q1));
//    }
//
//    @Test
//    public void ensureOutgoingEdgesWorks() {
//
//        Graph g = new Graph();
//
//        Question q1 = new BinaryQuestion("This is a yes/no question", "A424");
//        Question q2 = new BinaryQuestion("This is yet another yes/no question", "A123");
//        Question q3 = new BinaryQuestion("A wild binary question appears!", "A423");
//
//        g.insertEdge(q1, q2, new BinaryQuestionOption(Boolean.TRUE), 0);
//        g.insertEdge(q1, q3, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        g.insertEdge(q2, q1, new BinaryQuestionOption(Boolean.FALSE), 0);
//        g.insertEdge(q3, q1, new BinaryQuestionOption(Boolean.FALSE), 0);
//
//        Iterator<Edge> edgeIterator = g.outgoingEdges(q1).iterator();
//
//        Edge edge = edgeIterator.next();
//        assertEquals(new BinaryQuestionOption(Boolean.TRUE), edge.getElement());
//        assertEquals(q1, edge.getOriginVertexElement());
//        assertEquals(q2, edge.getDestinationVertexElement());
//
//        edge = edgeIterator.next();
//        assertEquals(new BinaryQuestionOption(Boolean.FALSE), edge.getElement());
//        assertEquals(q1, edge.getOriginVertexElement());
//        assertEquals(q3, edge.getDestinationVertexElement());
//    }
//}
