package cdioil.application.utils;

import cdioil.domain.*;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author António Sousa [1161371]
 */
public class EdgeTest {

    public EdgeTest() {
    }

    @Test
    public void testJPAConstructor() {
        Edge e = new Edge();

        assertNotNull(e);
    }

    /**
     * Test of getElement method, of class Edge.
     */
    @Test
    public void testGetElement() {
        System.out.println("getElement");

        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is the second question", "A14");
        Vertex vDest = new Vertex(q2);

        Edge edge = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0, vOrig, vDest);

        assertEquals(edge.getElement(), new BinaryQuestionOption(Boolean.TRUE));
        assertNotEquals(edge.getElement(), new BinaryQuestionOption(Boolean.FALSE));
    }

    /**
     * Test of getWeight method, of class Edge.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");

        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is the second question", "A14");
        Vertex vDest = new Vertex(q2);

        Edge edge = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0.0, vOrig, vDest);

        assertEquals(0.0, edge.getWeight(), 0.01);
    }

    @Test
    public void ensureGetOriginVertexElementReturnsNull() {
        Question q2 = new BinaryQuestion("This is the second question", "A14");
        Vertex vDest = new Vertex(q2);
        Edge edge = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0.0, null, vDest);
        assertNull(edge.getOriginVertexElement());
    }

    @Test
    public void ensureGetOriginVertexElementReturnsTheElementItself() {
        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is the second question", "A14");
        Vertex vDest = new Vertex(q2);

        Edge edge = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0.0, vOrig, vDest);

        assertNotEquals(vOrig, edge.getOriginVertexElement());

        Question expected = new BinaryQuestion("This is the first question", "A13");

        assertEquals(expected, edge.getOriginVertexElement());
    }

    @Test
    public void ensureGetDestinationVertexElementReturnsNull() {
        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Edge edge = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0.0, vOrig, null);
        assertNull(edge.getDestinationVertexElement());
    }

    @Test
    public void ensureGetDestinationVertexElementReturnsTheElementItself() {
        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is the second question", "A14");
        Vertex vDest = new Vertex(q2);

        Edge edge = new Edge(new BinaryQuestionOption(Boolean.TRUE), 0.0, vOrig, vDest);

        assertNotEquals(vDest, edge.getDestinationVertexElement());

        Question expected = new BinaryQuestion("This is the second question", "A14");

        assertEquals(expected, edge.getDestinationVertexElement());
    }

    @Test
    public void ensureHashCodeIsDifferentWhenElementIsDifferent() {

        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is the second question", "A14");
        Vertex vDest = new Vertex(q2);

        double weight = 0.5;

        Edge edge1 = new Edge(new BinaryQuestionOption(Boolean.TRUE), weight, vOrig, vDest);
        Edge edge2 = new Edge(new BinaryQuestionOption(Boolean.FALSE), weight, vOrig, vDest);

        assertNotEquals(edge1.hashCode(), edge2.hashCode());
    }

    @Test
    public void ensureHashCodeIsTheSameWhenElementIsTheSame() {

        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is the second question", "A14");
        Vertex vDest = new Vertex(q2);

        double weight = 0.5;

        Edge edge1 = new Edge(new BinaryQuestionOption(Boolean.TRUE), weight, vOrig, vDest);
        Edge edge2 = new Edge(new BinaryQuestionOption(Boolean.TRUE), weight, vOrig, vDest);

        assertEquals(edge1.hashCode(), edge2.hashCode());
    }

    @Test
    public void ensureHashCodeIsTheSameWhenOriginVertexIsDifferent() {

        Question question = new BinaryQuestion("This is the second question", "A14");
        Vertex vDest = new Vertex(question);

        double weight = 0.5;
        BinaryQuestionOption element = new BinaryQuestionOption(Boolean.FALSE);

        Question q1 = new BinaryQuestion("This is edge 1's first question", "A13");
        Vertex vertOrigin = new Vertex(q1);
        Edge edge1 = new Edge(element, weight, vertOrigin, vDest);

        Question q2 = new BinaryQuestion("This is edge 2's first question", "A15");
        Vertex vOrig = new Vertex(q2);
        Edge edge2 = new Edge(element, weight, vOrig, vDest);

        assertEquals(edge1.hashCode(), edge2.hashCode());
    }

    @Test
    public void ensureHashCodeIsTheSameWhenWeightIsDifferent() {
        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is the second question", "A14");
        Vertex vDest = new Vertex(q2);

        BinaryQuestionOption element = new BinaryQuestionOption(Boolean.FALSE);

        Edge edge1 = new Edge(element, 0.5, vOrig, vDest);
        Edge edge2 = new Edge(element, 1.0, vOrig, vDest);

        assertEquals(edge1.hashCode(), edge2.hashCode());
    }

    @Test
    public void ensureHashCodeIsTheSameWhenDestinationVertexIsDifferent() {
        Question question = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(question);

        double weight = 0.5;
        BinaryQuestionOption element = new BinaryQuestionOption(Boolean.FALSE);

        Question q1 = new BinaryQuestion("This is edge 1's second question", "A13");
        Vertex vertDest = new Vertex(q1);
        Edge edge1 = new Edge(element, weight, vOrig, vertDest);

        Question q2 = new BinaryQuestion("This is edge 2's second question", "A15");
        Vertex vDest = new Vertex(q2);
        Edge edge2 = new Edge(element, weight, vOrig, vDest);

        assertEquals(edge1.hashCode(), edge2.hashCode());
    }

    @Test
    public void ensureHashCodeIsTheSameWhenAllAtributesAreEqual() {

        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is edge 2's second question", "A15");
        Vertex vDest = new Vertex(q2);
        double weight = 0.0;
        BinaryQuestionOption element = new BinaryQuestionOption(Boolean.FALSE);

        Edge edge1 = new Edge(element, weight, vOrig, vDest);
        Edge edge2 = new Edge(element, weight, vOrig, vDest);

        assertEquals(edge1.hashCode(), edge2.hashCode());
    }

    @Test
    public void ensureSameReferenceIsEqual() {
        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is edge 2's second question", "A15");
        Vertex vDest = new Vertex(q2);
        double weight = 0.0;
        BinaryQuestionOption element = new BinaryQuestionOption(Boolean.FALSE);

        Edge edge = new Edge(element, weight, vOrig, vDest);

        assertEquals(edge, edge);
    }

    @Test
    public void ensureNullIsNotEqual() {
        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is edge 2's second question", "A15");
        Vertex vDest = new Vertex(q2);
        double weight = 0.0;
        BinaryQuestionOption element = new BinaryQuestionOption(Boolean.FALSE);

        Edge edge = new Edge(element, weight, vOrig, vDest);

        assertFalse(edge.equals(null)); //just to make sure that Edge's equals is being invoked
    }

    @Test
    public void ensureDifferentClassObjectsAreNotEqual() {
        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is edge 2's second question", "A15");
        Vertex vDest = new Vertex(q2);
        double weight = 0.0;
        BinaryQuestionOption element = new BinaryQuestionOption(Boolean.FALSE);

        Edge edge = new Edge(element, weight, vOrig, vDest);

        assertFalse(edge.equals(element)); //just to make sure that Edge's equals is being invoked
    }

    @Test
    public void ensureDifferentElementsMakeEdgesNotEqual() {
        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is edge 2's second question", "A15");
        Vertex vDest = new Vertex(q2);
        double weight = 0.0;
        BinaryQuestionOption element1 = new BinaryQuestionOption(Boolean.FALSE);
        BinaryQuestionOption element2 = new BinaryQuestionOption(Boolean.TRUE);

        Edge edge1 = new Edge(element1, weight, vOrig, vDest);
        Edge edge2 = new Edge(element2, weight, vOrig, vDest);

        assertNotEquals(edge1, edge2);
    }

    @Test
    public void ensureEdgesAreEqual() {
        Question q1 = new BinaryQuestion("This is the first question", "A13");
        Vertex vOrig = new Vertex(q1);
        Question q2 = new BinaryQuestion("This is edge 2's second question", "A15");
        Vertex vDest = new Vertex(q2);
        double weight = 0.0;
        BinaryQuestionOption element = new BinaryQuestionOption(Boolean.FALSE);

        Edge edge1 = new Edge(element, weight, vOrig, vDest);
        Edge edge2 = new Edge(element, weight, vOrig, vDest);

        assertEquals(edge1, edge2);
    }

    @Test
    public void ensureGetEndpointsReturnsNullIfBothVerticesAreNull() {
        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, null, null);

        assertNull(edge.getEndpoints());
    }

    @Test
    public void ensureGetEndpointsReturnsNullIfOriginIsNull() {

        Vertex destinationVertex = new Vertex(new BinaryQuestion("This is a question", "A341"));

        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, null, destinationVertex);

        assertNull(edge.getEndpoints());
    }

    @Test
    public void ensureGetEndpointsReturnsNullIfDestinationIsNull() {

        Vertex originVertex = new Vertex(new BinaryQuestion("This is a question", "A341"));

        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, originVertex, null);

        assertNull(edge.getEndpoints());
    }

    @Test
    public void ensureGetEndpointsWorks() {

        Vertex originVertex = new Vertex(new BinaryQuestion("This is a question", "A341"));
        Vertex destinationVertex = new Vertex(new BinaryQuestion("This is another question", "A421"));

        Edge edge = new Edge(new BinaryQuestionOption(Boolean.FALSE), 0, originVertex, destinationVertex);

        Question[] endpoints = edge.getEndpoints();

        Question expectedOriginElement = new BinaryQuestion("This is a question", "A341");
        Question expectedDestinationElement = new BinaryQuestion("This is another question", "A421");

        assertEquals(expectedOriginElement, endpoints[0]);
        assertEquals(expectedDestinationElement, endpoints[1]);
    }

}
