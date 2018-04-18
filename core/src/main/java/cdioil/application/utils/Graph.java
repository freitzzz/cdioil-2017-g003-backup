package cdioil.application.utils;

import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.Version;

/**
 * Class used for defining a Survey's flow when answering questions.
 * <p>
 * Based on ESINF's adjacency map Graph implementation, though it has a few
 * modifications since it allows for multiple edges to connect two vertices,
 * this is because diferent options can lead to the same adjacent question,
 * however, one option can only lead to a single question, on top of this it
 * also does not allow loops, since a question's option can't lead to that same
 * question.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class Graph implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Database identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Database version number.
     */
    @Version
    private long version;

    /**
     * Number of options currently present in the <code>Graph</code>.
     */
    private int numVertices;

    /**
     * Number of options currently present in the <code>Graph</code>
     */
    private int numEdges;

    /**
     * Map containing all of the inserted elements and the vertices containing
     * them.
     */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @MapKey(name = "element")
    private Map<Question, Vertex> vertices;

    /**
     * Creates a new instance of <code>Graph</code>.
     */
    public Graph() {
        numVertices = 0;
        numEdges = 0;
        vertices = new LinkedHashMap<>();
    }

    /**
     * Creates a new instance of <code>Graph</code> that copies a given
     * <code>Graph</code>.
     *
     * @param g original Graph.
     */
    public Graph(Graph g) {
        this();
        for (Vertex vertex : g.vertices.values()) {
            for (Edge edge : vertex.getAllOutgoingEdges()) {
                this.insertEdge(edge.getOriginVertexElement(), edge.getDestinationVertexElement(), edge.getElement(), edge.getWeight());
            }
        }
    }

    /**
     * Checks if the vertex has already been inserted into the graph.
     *
     * @param vertexElement element to be checked
     * @return true - if the element has already been inserted into the graph<p>
     * false - otherwise
     */
    public boolean vertexExists(Question vertexElement) {
        return vertices.get(vertexElement) != null;
    }

    /**
     * Retrieves all the elements stored in vertices adjacent to the given
     * element.
     *
     * @param vertexElement
     * @return Iterable Collection of elements in adjacent vertices.
     */
    public Iterable<Question> adjacentQuestions(Question vertexElement) {

        if (!vertexExists(vertexElement)) {
            return null;
        }

        Vertex vertex = vertices.get(vertexElement);

        return vertex.getAllAdjacentVertexElements();
    }

    /**
     * Rerieves the number of options in this <code>Graph</code>.
     *
     * @return number of options in the <code>Graph</code>.
     */
    public int numEdges() {
        return numEdges;
    }

    /**
     * Retrieves the number of questions in this <code>Graph</code>.
     *
     * @return number of questions in the <code>Graph</code>.
     */
    public int numVertices() {
        return numVertices;
    }

    /**
     * Retrieves an Iterable Collection of all the Edges in the Graph.
     *
     * @return Iterable Collection of all Edges
     */
    public Iterable<Edge> allEdges() {

        LinkedList<Edge> edges = new LinkedList<>();

        for (Vertex vert : vertices.values()) {
            for (Edge edge : vert.getAllOutgoingEdges()) {
                edges.add(edge);
            }
        }
        return edges;
    }

    /**
     * Retrieves an Iterable Collection of all Edges connecting the given
     * elements.
     *
     * @param origElement origin element
     * @param destElement destination element
     * @return Iterable Collection of Edges
     */
    public Iterable<Edge> edgesConnectingQuestions(Question origElement, Question destElement) {

        if (!vertexExists(origElement) || !vertexExists(destElement)) {
            return null;
        }
        Vertex origVertex = vertices.get(origElement);

        return origVertex.getEdges(destElement);
    }

    /**
     * Retrives the elements stored in the Edge's endpoints.
     *
     * @param edge
     * @return an array of size 2, containing the elements in the origin and
     * destination vertices, or null if the edge is null, either of the vertices
     * are null or has not been inserted into the graph
     */
    public Question[] endVertices(Edge edge) {
        if (edge == null) {
            return null;
        }

        Question originElement = edge.getOriginVertexElement();
        Question destinationElement = edge.getDestinationVertexElement();

        if (!vertexExists(originElement) || !vertexExists(destinationElement)) {
            return null;
        }

        Iterable<Edge> edges = edgesConnectingQuestions(originElement, destinationElement);

        if (edges == null) {
            return null;
        }

        boolean exists = false;

        for (Edge e : edges) {
            if (e.equals(edge)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            return null;
        }

        return edge.getEndpoints();
    }

    /**
     * Returns all of the incoming edges of a Vertex containing the given
     * element.
     *
     * @param element
     * @return an Iterable Collection of all incoming edges or null if a Vertex
     * with the given element has not been inserted.
     */
    public Iterable<Edge> incomingEdges(Question element) {

        if (!vertexExists(element)) {
            return null;
        }

        LinkedList<Edge> incomingEdges = new LinkedList<>();

        for (Edge e : allEdges()) {

            Question endVertex = e.getDestinationVertexElement();

            if (endVertex.equals(element)) {
                incomingEdges.add(e);
            }
        }

        return incomingEdges;

    }

    /**
     * Retrieves an Iterable Collection of the Vertex's outgoing edges.
     *
     * @param element element contained within a Vertex
     * @return all of the Vertex's outgoing edges.
     */
    public Iterable<Edge> outgoingEdges(Question element) {

        if (!vertexExists(element)) {
            return null;
        }

        return vertices.get(element).getAllOutgoingEdges();
    }

    /**
     * Inserts a new Vertex containing the given element into the Graph.
     *
     * @param element element to insert
     * @return true - if a element has not yet been inserted into the graph<p>
     * false - otherwise
     */
    public boolean insertVertex(Question element) {

        if (vertexExists(element)) {
            return false;
        }
        Vertex vertex = new Vertex(element);
        vertices.put(element, vertex);
        numVertices++;

        return true;
    }

    /**
     * Removes a Vertex from the Graph and all of its connections.
     *
     * @param element
     * @return
     */
    public boolean removeVertex(Question element) {

        if (!vertexExists(element)) {
            return false;
        }

        for (Edge edge : incomingEdges(element)) {
            Question adjacentQuestion = edge.getOriginVertexElement();
            removeEdges(adjacentQuestion, element);
        }

        //Removing the vertex also removes all of its outgoing edges
        vertices.remove(element);
        numVertices--;
        return true;
    }

    /**
     * Inserts a new Edge into the Graph connecting the two given elements, with
     * a given option and weight associated with the Edge.<p>
     * If the elements are equal, then neither the elements nor the edge is
     * inserted into the Graph. If the elements have not yet been inserted into
     * the Graph, then new vertices are inserted, holding these elements
     *
     * @param origElement element (question) associated with the origin Vertex
     * @param destElement element (question) associated with the destination
     * Vertex
     * @param edgeElement edge's element (option)
     * @param edgeWeight edge's weight
     * @return true - if both the vertices are not equal and if those vertices
     * don't already have an equal edge connecting them<p>
     * false - otherwise
     */
    public boolean insertEdge(Question origElement, Question destElement, QuestionOption edgeElement, double edgeWeight) {

        //Do not allow loops
        if (origElement.equals(destElement)) {
            return false;
        }

        if (!vertexExists(origElement)) {
            insertVertex(origElement);
        }

        if (!vertexExists(destElement)) {
            insertVertex(destElement);
        }

        Vertex originVertex = vertices.get(origElement);

        //Do not allow an equal edge to be inserted twice
        for (Edge edge : originVertex.getAllOutgoingEdges()) {

            if (edge.getElement().equals(edgeElement)) {
                return false;
            }
        }

        Vertex destVertex = vertices.get(destElement);

        Edge edge = new Edge(edgeElement, edgeWeight, originVertex, destVertex);
        originVertex.addAdjacentVertex(edge, destElement);
        numEdges++;

        return true;
    }

    /**
     * Removes all edges connecting the given Vertex elements.
     *
     * @param originElement
     * @param destinationElement
     * @return false - if neither of the elements exist or if they have no edges
     * connecting them.
     */
    public boolean removeEdges(Question originElement, Question destinationElement) {
        if (!vertexExists(originElement) || !vertexExists(destinationElement)) {
            return false;
        }

        Iterable<Edge> edges = edgesConnectingQuestions(originElement, destinationElement);

        if (edges == null) {
            return false;
        }

        int numRemovedEdges = 0;

        Iterator<Edge> edgesIterator = edges.iterator();

        while (edgesIterator.hasNext()) {
            edgesIterator.next();
            numRemovedEdges++;
        }

        Vertex originVertex = vertices.get(originElement);

        originVertex.removeAdjacentVertex(destinationElement);

        numEdges -= numRemovedEdges;

        return true;
    }

    /**
     * Returns the first Question in the Graph.
     *
     * @return the first Question.
     */
    public Question getFirstQuestion() {

        if (vertices.isEmpty()) {
            return null;
        }

        return vertices.keySet().iterator().next();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.numVertices;
        hash = 43 * hash + this.numEdges;
        hash = 43 * hash + Objects.hashCode(this.vertices);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Graph other = (Graph) obj;
        if (this.numVertices != other.numVertices) {
            return false;
        }
        if (this.numEdges != other.numEdges) {
            return false;
        }

        /*By using the Vertex equals method rather than checking if the 
        elements are equal, connections are also checked*/
        for (Vertex vert : vertices.values()) {
            boolean exists = false;
            for (Vertex otherVert : other.vertices.values()) {
                if (vert.equals(otherVert)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                return false;
            }
        }

        return true;
    }

}
