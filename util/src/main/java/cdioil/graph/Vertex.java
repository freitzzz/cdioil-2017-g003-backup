package cdioil.graph;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
 * Wrapper class for the Graph's vertices.
 *
 * @author DEI-ESINF
 * @param <V> data type for elements stored in the Graph's vertices
 * @param <E> data type for elements stored in the Graph's edges
 */
@Entity
public class Vertex<V extends Serializable, E extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Database ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Database Version value.
     */
    @Version
    private Long version;

    /**
     * Vertex key value.
     */
    private int vertexKey;
    /**
     * Element stored in the Vertex.
     */
    private V element;
    /**
     * Map of directly adjacent outgoing vertices and edges connecting them.
     */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "OUTGOING_VERTICES",
            joinColumns = {
                @JoinColumn(name = "FK_VERTEX_ELEMENT", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "FK_VERTEX", referencedColumnName = "id")})
    @MapKey(name = "vertexKey")
    private Map<V, Edge<V, E>> outVerts;

    /**
     * Default constructor of Vertex.<p>
 Sets the value of vertexKey to -1 and its element is null.
     */
    public Vertex() {
        vertexKey = -1;
        element = null;
        outVerts = new LinkedHashMap<>();
    }

    /**
     * Instantiates a Vertex with a given vertexKey and element.
     *
     * @param k vertexKey
     * @param vInf element
     */
    public Vertex(int k, V vInf) {
        vertexKey = k;
        element = vInf;
        outVerts = new LinkedHashMap<>();
    }

    /**
     * Instantiates a copy of a given Vertex.
     *
     * @param v
     */
    public Vertex(Vertex<V, E> v) {
        vertexKey = v.getVertexKey();
        element = v.getElement();
        outVerts = new LinkedHashMap<>();
        for (V vert : v.outVerts.keySet()) {
            Edge<V, E> edge = v.outVerts.get(vert);
            outVerts.put(vert, edge);
        }
    }

    /**
     * Retrieves the vertexKey value.
     *
     * @return vertexKey
     */
    public int getVertexKey() {
        return vertexKey;
    }

    /**
     * Sets the vertexKey value.
     *
     * @param k new vertexKey value
     */
    public void setVertexKey(int k) {
        vertexKey = k;
    }

    /**
     * Retrieves the element.
     *
     * @return element
     */
    public V getElement() {
        return element;
    }

    /**
     * Sets the element.
     *
     * @param vInf new element
     */
    public void setElement(V vInf) {
        element = vInf;
    }

    /**
     * Adds a new adjacent vertex.
     *
     * @param vAdj adjacent vertex
     * @param edge edge connecting the two vertices
     */
    public void addAdjVert(V vAdj, Edge<V, E> edge) {
        outVerts.put(vAdj, edge);
    }

    /**
     * Retrieves the adjacent vertex from a given Edge.
     *
     * @param edge
     * @return adjacent vertex
     */
    public V getAdjVert(Edge<V, E> edge) {

        for (Map.Entry<V, Edge<V, E>> e : outVerts.entrySet()) {
            if (edge.equals(outVerts.get(e.getKey()))) {
                return e.getKey();
            }
        }

        return null;
    }

    /**
     * Removes an adjacent vertex.
     *
     * @param vAdj
     */
    public void remAdjVert(V vAdj) {
        outVerts.remove(vAdj);
    }

    /**
     * Retrieves the Edge connecting this vertex to the adjacent vertex.
     *
     * @param vAdj
     * @return edge connecting the two vertices
     */
    public Edge<V, E> getEdge(V vAdj) {
        return outVerts.get(vAdj);
    }

    /**
     * Computes the number of adjacent vertices.
     *
     * @return
     */
    public int numAdjVerts() {
        return outVerts.size();
    }

    /**
     * Retrieves an Iterable Collection with all of the adjacent vertices.
     *
     * @return
     */
    public Iterable<V> getAllAdjVerts() {
        return outVerts.keySet();
    }

    /**
     * Retrieves an Iterable Collection with all of the outgoing edges.
     *
     * @return
     */
    public Iterable<Edge<V, E>> getAllOutEdges() {
        return outVerts.values();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.vertexKey;
        hash = 31 * hash + Objects.hashCode(this.element);
        return hash;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object otherObj) {

        if (this == otherObj) {
            return true;
        }

        if (otherObj == null || this.getClass() != otherObj.getClass()) {
            return false;
        }

        Vertex<V, E> otherVertex = (Vertex<V, E>) otherObj;

        if (this.vertexKey != otherVertex.vertexKey) {
            return false;
        }

        if (this.element != null && otherVertex.element != null
                && !this.element.equals(otherVertex.element)) {
            return false;
        }

        //adjacency vertices should be equal
        if (this.numAdjVerts() != otherVertex.numAdjVerts()) {
            return false;
        }

        //and edges also
        Iterator<Edge<V, E>> it1 = this.getAllOutEdges().iterator();
        while (it1.hasNext()) {
            Iterator<Edge<V, E>> it2 = otherVertex.getAllOutEdges().iterator();
            boolean exists = false;
            while (it2.hasNext()) {
                if (it1.next().equals(it2.next())) {
                    exists = true;
                }
            }
            if (!exists) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Vertex<V, E> clone() {

        Vertex<V, E> newVertex = new Vertex<>();

        newVertex.setVertexKey(vertexKey);
        newVertex.setElement(element);

        for (V vert : outVerts.keySet()) {
            newVertex.addAdjVert(vert, this.getEdge(vert));
        }

        return newVertex;
    }

    @Override
    public String toString() {
        String st = "";
        if (element != null) {
            st = element + " (" + vertexKey + "): \n";
        }
        if (!outVerts.isEmpty()) {
            for (Map.Entry<V, Edge<V, E>> e : outVerts.entrySet()) {
                st += outVerts.get(e.getKey());
            }
        }

        return st;
    }

}
