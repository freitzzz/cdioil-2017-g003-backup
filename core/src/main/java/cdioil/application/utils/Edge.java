package cdioil.application.utils;

import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

/**
 * Class representing a Graph's Edge.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class Edge implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Database identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Database version number;
     */
    @Version
    private long version;

    /**
     * Option associated to this Edge.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private QuestionOption element;

    /**
     * Weight associated to this Edge.
     */
    private double weight;

    /**
     * Edge's origin endpoint.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private Vertex originVertex;

    /**
     * Edge's destination endpoint.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private Vertex destinationVertex;

    /**
     * JPA Constructor.
     */
    protected Edge() {

    }

    /**
     * Constructs an <code>Edge</code> with a given element, weight and
     * vertices.
     * <p>
     * Note: Despite the class being public, this constructor should remain
     * package private, since edges should only be constructed by the Graph.
     *
     * @param edgeElement element to be stored within the Edge
     * @param edgeWeight weight associated to the Edge (useful for data
     * analysis?)
     * @param origin origin Vertex
     * @param destination destination Vertex
     */
    Edge(QuestionOption edgeElement, double edgeWeight, Vertex origin, Vertex destination) {
        element = edgeElement;
        weight = edgeWeight;
        originVertex = origin;
        destinationVertex = destination;
    }

    /**
     * Retrieves the element (option) stored within the Edge.
     *
     * @return option associated to the Edge.
     */
    public QuestionOption getElement() {
        return element;
    }

    /**
     * Retrieves the Edge's weight.
     *
     * @return Edge's weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Retrieves the Question stored within the Edge's origin Vertex.
     *
     * @return Question within the origin Vertex.
     */
    public Question getOriginVertexElement() {
        if (originVertex != null) {
            return originVertex.getElement();
        }
        return null;
    }

    /**
     * Retrieves the Question stored within the Edge's destination Vertex.
     *
     * @return Question within the destination Vertex.
     */
    public Question getDestinationVertexElement() {
        if (destinationVertex != null) {
            return destinationVertex.getElement();
        }
        return null;
    }

    /**
     * Retrieves both of the of the elements stored in the Vertices being
 connected by the Edge.
     *
     * @return Questions
     */
    public Question[] getEndpoints() {

        Question originElement = null;
        Question destinationElement = null;

        if (this.originVertex != null) {
            originElement = originVertex.getElement();
        }

        if (this.destinationVertex != null) {
            destinationElement = destinationVertex.getElement();
        }

        if (originElement == null || destinationElement == null) {
            return null;
        }

        Question[] endVertices = new Question[2];

        endVertices[0] = originElement;
        endVertices[1] = destinationElement;

        return endVertices;
    }

    /*
    NOTE: Neither hashCode nor equals take into account the vertices.
    What truly makes an Edge unique is the element stored within in it, both 
    originVertex and destinationVertex are only used for flexibility.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.element);
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
        final Edge other = (Edge) obj;

        return Objects.equals(this.element, other.element);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "element=" + element +
                '}';
    }
}
