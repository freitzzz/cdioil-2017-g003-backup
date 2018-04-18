package cdioil.application.utils;

import cdioil.domain.Question;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Class representing a Graph's Vertex.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class Vertex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;
    /**
     * Element being stored in this element.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private Question element;

    /**
     * Map with adjacent vertices and the edges connecting them.
     */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @MapKey(name = "questionID")
    private Map<Edge, Question> outgoingEdges;

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
        outgoingEdges = new LinkedHashMap<>();
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
        outgoingEdges.put(edge, adjacentElement);
    }

    /**
     * Retrieves an adjacent <code>Vertex</code> with a given <code>Edge</code>.
     *
     * @param edge <code>Edge</code> connecting the two vertices.
     * @return the element within the <code>Vertex</code> being connected with
     * the given <code>Edge</code>.
     */
    public Question getAdjacentVertex(Edge edge) {
        for (Edge e : outgoingEdges.keySet()) {
            if (e.equals(edge)) {
                return outgoingEdges.get(e);
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
            outgoingEdges.remove(edge);
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

        LinkedHashSet<Edge> edges = new LinkedHashSet<>();

        for (Edge e : outgoingEdges.keySet()) {
            if (adjacentElement.equals(outgoingEdges.get(e))) {
                edges.add(e);
            }
        }

        if (edges.isEmpty()) {
            return null;
        }

        return edges;
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
        return outgoingEdges.size();
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
        for (Question q : outgoingEdges.values()) {
            adjacentVertices.add(q);
        }

        return adjacentVertices;
    }

    /**
     * Retrieves an Iterable Collection of all outgoing Edges.
     *
     * @return Iterable Collection of outgoing Edges.
     */
    public Iterable<Edge> getAllOutgoingEdges() {
        return outgoingEdges.keySet();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.element);
        hash = 53 * hash + Objects.hashCode(this.outgoingEdges);
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
        for (Map.Entry<Edge, Question> thisEntry : this.outgoingEdges.entrySet()) {
            boolean exists = false;
            for (Map.Entry<Edge, Question> otherEntry : other.outgoingEdges.entrySet()) {
                Edge thisEdge = thisEntry.getKey();
                Edge otherEdge = otherEntry.getKey();

                Question thisQuestion = thisEntry.getValue();
                Question otherQuestion = otherEntry.getValue();

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
