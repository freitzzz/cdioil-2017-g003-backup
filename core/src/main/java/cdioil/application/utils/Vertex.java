package cdioil.application.utils;

import cdioil.domain.Question;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

/**
 * Class representing a Graph's Vertex.
 *
 * @author António Sousa [1161371]
 */
@Entity
@SequenceGenerator(name = "vertexSeq",initialValue = 1,allocationSize = 1)
public class Vertex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "vertexSeq")
    private long id;

    @Version
    private long version;
    /**
     * Element being stored in this element.
     */
    @ManyToOne
    private Question element;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<EdgeQuestion> outgoingEdgeQuestions;

    /**
     * JPA Constructor.
     */
    protected Vertex() {

    }

    /**
     * Instantiates a <code>Vertex</code> with a given element.
     * <p>
     * Note: Despite the class being public, this constructor should remain
     * package private, since vertices should only be constructed by the Graph.
     *
     * @param element element to be stored in the <code>Vertex</code>.
     */
    Vertex(Question element) {
        this.element = element;
        outgoingEdgeQuestions = new LinkedHashSet<>();
    }

    /**
     * Retrieves the element currently stored in this <code>Vertex</code>.
     *
     * @return element stored in the <code>Vertex</code>.
     */
    public Question getElement() {
        return element;
    }

    /**
     * Adds an adjacent <code>Vertex</code> with a given <code>Edge</code> and
     * element.
     *
     * @param edge <code>Edge</code> to connect the two elements
     * @param adjacentElement element to be stored in the adjacent
     * <code>Vertex</code>.
     */
    public void addAdjacentVertex(Edge edge, Question adjacentElement) {

        EdgeQuestion edgeQuestion = new EdgeQuestion(edge, adjacentElement);

        /*
        While a Set's add method removes an equal object from the Set, it also changes it's insertion order, adding it to the end.
        A Map's put method, however, overlaps the existent entry, thus maintaining order.
        In order to overcome this, a copy of the set must be made and the new element must be added in the desired order.
         */
        if (outgoingEdgeQuestions.contains(edgeQuestion)) {

            Set<EdgeQuestion> copy = new LinkedHashSet<>(outgoingEdgeQuestions);
            outgoingEdgeQuestions.clear();

            for (EdgeQuestion existentEdgeQuestion : copy) {
                if (existentEdgeQuestion.equals(edgeQuestion)) {
                    outgoingEdgeQuestions.add(edgeQuestion);
                } else {
                    outgoingEdgeQuestions.add(existentEdgeQuestion);
                }
            }
        } else {
            outgoingEdgeQuestions.add(edgeQuestion);
        }
    }

    /**
     * Retrieves an adjacent <code>Vertex</code> with a given <code>Edge</code>.
     *
     * @param edge <code>Edge</code> connecting the two vertices.
     * @return the element within the <code>Vertex</code> being connected with
     * the given <code>Edge</code>.
     */
    public Question getAdjacentVertex(Edge edge) {

        for (EdgeQuestion edgeQuestion : outgoingEdgeQuestions) {
            if (edgeQuestion.getEdge().equals(edge)) {
                return edgeQuestion.getOutgoingQuestion();
            }
        }
        return null;
    }

    /**
     * Removes an adjacent <code>Vertex</code> with a given element.
     *
     * @param adjacentElement element stored in the adjacent
     * <code>Vertex</code>.
     */
    public void removeAdjacentVertex(Question adjacentElement) {

        for (Edge edge : getEdges(adjacentElement)) {
            for (EdgeQuestion edgeQuestion : outgoingEdgeQuestions) {
                if (edgeQuestion.getEdge().equals(edge)) {
                    outgoingEdgeQuestions.remove(edgeQuestion);
                    break;
                }
            }
        }
    }

    /**
     * Retrieves an Iterable Collection of Edges connecting this
     * <code>Vertex</code> and the <code>Vertex</code> containing the given
     * element.
     *
     * @param adjacentElement element stored in the adjacent
     * <code>Vertex</code>.
     * @return <code>Edge</code>
     */
    public Iterable<Edge> getEdges(Question adjacentElement) {

        LinkedHashSet<Edge> edgesQuestions = new LinkedHashSet<>();

        for (EdgeQuestion edgeQuestion : outgoingEdgeQuestions) {
            if (edgeQuestion.getOutgoingQuestion().equals(adjacentElement)) {
                edgesQuestions.add(edgeQuestion.getEdge());
            }
        }

        if (edgesQuestions.isEmpty()) {
            return null;
        }

        return edgesQuestions;
    }

    /**
     * Computes the number of adjacent vertices.
     *
     * @return number of adjacent vertices.
     */
    public int numAdjacentVertices() {
        int result = 0;

        /*The set is used rather than using the map's size, since multiple edges
        can be used to connect two vertices and that would not return the true 
        number of adjacent vertices*/
        for (Question q : getAllAdjacentVertexElements()) {
            result++;
        }

        return result;
    }

    /**
     * Computes the number of outgoing edges.
     *
     * @return number of outgoing edges.
     */
    public int numOutgoingEdges() {
        return outgoingEdgeQuestions.size();
    }

    /**
     * Retrieves an Iterable Collection of adjacent Questions.
     *
     * @return Iterable Collection of adjacent Questions.
     */
    public Iterable<Question> getAllAdjacentVertexElements() {

        Set<Question> adjacentVertices = new LinkedHashSet<>();

        /*An auxiliary set is used since the map's values method can contain 
        duplicates*/
        for (EdgeQuestion edgeQuestion : outgoingEdgeQuestions) {
            adjacentVertices.add(edgeQuestion.getOutgoingQuestion());
        }

        return adjacentVertices;
    }

    /**
     * Retrieves an Iterable Collection of all outgoing Edges.
     *
     * @return Iterable Collection of outgoing Edges.
     */
    public Iterable<Edge> getAllOutgoingEdges() {
        LinkedHashSet<Edge> edges = new LinkedHashSet<>();

        for (EdgeQuestion edgeQuestion : outgoingEdgeQuestions) {
            edges.add(edgeQuestion.getEdge());
        }

        return edges;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.element);
        hash = 53 * hash + Objects.hashCode(this.outgoingEdgeQuestions);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Vertex other = (Vertex) obj;

        if (!this.element.equals(other.element)) {
            return false;
        }

        if (this.numAdjacentVertices() != other.numAdjacentVertices()) {
            return false;
        }

        if (this.numOutgoingEdges() != other.numOutgoingEdges()) {
            return false;
        }

        //Check for content equality rather than order equality
        for (EdgeQuestion thisEdgeQuestion : this.outgoingEdgeQuestions) {
            boolean exists = false;
            for (EdgeQuestion otherEdgeQuestion : other.outgoingEdgeQuestions) {

                Edge thisEdge = thisEdgeQuestion.getEdge();
                Edge otherEdge = otherEdgeQuestion.getEdge();

                Question thisQuestion = thisEdgeQuestion.getOutgoingQuestion();
                Question otherQuestion = otherEdgeQuestion.getOutgoingQuestion();

                if (thisEdge.equals(otherEdge) && thisQuestion.equals(otherQuestion)) {
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
