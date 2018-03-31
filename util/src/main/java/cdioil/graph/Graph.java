package cdioil.graph;

import java.io.Serializable;
import java.lang.reflect.Array;
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
 * Adjacency map Graph implementation of GraphInterface.
 *
 * @author DEI-ESINF
 * @param <V> data type for elements stored in the Graph's vertices
 * @param <E> data type for elements stored in the Graph's edges
 */
@Entity
public class Graph<V extends Serializable, E extends Serializable> implements GraphInterface<V, E>, Serializable {

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
     * Number of vertices in this instance.
     */
    private int numVert;
    /**
     * Number of edges in this instance.
     */
    private int numEdge;
    /**
     * Flag for checking whether the graph is directed or undirected.
     */
    private boolean isDirected;
    /**
     * Map containing all of the vertices and their connections
     */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "ADJANCECY_MAP",
            joinColumns = {@JoinColumn(name = "FK_VERTEX_ELEMENT", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "FK_VERTEX", referencedColumnName = "id")})
    @MapKey(name = "vertexKey")
    private Map<V, Vertex<V, E>> vertices;

    /**
     * JPA Constructor.
     */
    protected Graph() {
    }

    /**
     * Instantiates an empty Graph.
     *
     * @param directed if true, each edge will only be added once; if false,
     * then an edge in the opposite direction will also be inserted
     */
    public Graph(boolean directed) {
        numVert = 0;
        numEdge = 0;
        isDirected = directed;
        vertices = new LinkedHashMap<>();
    }

    @Override
    public int numVertices() {
        return numVert;
    }

    @Override
    public Iterable<V> vertices() {
        return vertices.keySet();
    }

    /**
     * Checks if the vertex has already been inserted into the graph.
     *
     * @param vert element to be checked
     * @return true, if vert has already been inserted into the graph; false
     * otherwise
     */
    public boolean validVertex(V vert) {

        return vertices.get(vert) != null;
    }

    /**
     * Retrieves the vertex key value if it has been inserted into the graph.
     *
     * @param vert vertex
     * @return the vertex's key if it's been inserted into the graph; -1
     * otherwise
     */
    public int getKey(V vert) {

        if (!validVertex(vert)) {
            return -1;
        }

        return vertices.get(vert).getVertexKey();
    }

    @SuppressWarnings("unchecked")
    public V[] allkeyVerts() {

        V vertElem = null;
        for (Vertex<V, E> vert : vertices.values()) {
            vertElem = vert.getElement();            // To get type
        }
        V[] keyverts = (V[]) Array.newInstance(vertElem.getClass(), numVert);

        for (Vertex<V, E> vert : vertices.values()) {
            keyverts[vert.getVertexKey()] = vert.getElement();
        }

        return keyverts;
    }

    @Override
    public Iterable<V> adjVertices(V vert) {

        if (!validVertex(vert)) {
            return null;
        }

        Vertex<V, E> vertex = vertices.get(vert);

        return vertex.getAllAdjVerts();
    }

    @Override
    public int numEdges() {
        return numEdge;
    }

    @Override
    public Iterable<Edge<V, E>> edges() {

        LinkedList<Edge<V, E>> edges = new LinkedList<>();

        for (Map.Entry<V, Vertex<V, E>> entry : vertices.entrySet()) {

            for (Edge<V, E> edge : vertices.get(entry.getKey()).getAllOutEdges()) {
                edges.addLast(edge);
            }

        }

        return edges;
    }

    @Override
    public Edge<V, E> getEdge(V vOrig, V vDest) {

        if (!validVertex(vOrig) || !validVertex(vDest)) {
            return null;
        }

        Vertex<V, E> vorig = vertices.get(vOrig);

        return vorig.getEdge(vDest);
    }

    @Override
    public V[] endVertices(Edge<V, E> edge) {

        if (edge == null) {
            return null;
        }

        if (!validVertex(edge.getVOrig()) || !validVertex(edge.getVDest())) {
            return null;
        }

        Vertex<V, E> vorig = vertices.get(edge.getVOrig());

        if (!edge.equals(vorig.getEdge(edge.getVDest()))) {
            return null;
        }

        return edge.getEndpoints();
    }

    @Override
    public V opposite(V vert, Edge<V, E> edge) {

        if (!validVertex(vert)) {
            return null;
        }

        Vertex<V, E> vertex = vertices.get(vert);

        return vertex.getAdjVert(edge);
    }

    @Override
    public int outDegree(V vert) {

        if (!validVertex(vert)) {
            return -1;
        }

        Vertex<V, E> vertex = vertices.get(vert);

        return vertex.numAdjVerts();
    }

    @Override
    public int inDegree(V vert) {

        if (!validVertex(vert)) {
            return -1;
        }

        int degree = 0;
        for (V otherVert : vertices.keySet()) {
            if (getEdge(otherVert, vert) != null) {
                degree++;
            }
        }

        return degree;
    }

    @Override
    public Iterable<Edge<V, E>> outgoingEdges(V vert) {

        if (!validVertex(vert)) {
            return null;
        }

        Vertex<V, E> vertex = vertices.get(vert);

        return vertex.getAllOutEdges();
    }

    @Override
    public Iterable<Edge<V, E>> incomingEdges(V vert) {

        if (!validVertex(vert)) {
            return null;
        }

        LinkedList<Edge<V, E>> incomingEdges = new LinkedList<>();

        for (Edge<V, E> e : edges()) {

            V endVertex = e.getVDest();

            if (endVertex.equals(vert)) {
                incomingEdges.add(e);
            }
        }

        return incomingEdges;
    }

    @Override
    public boolean insertVertex(V vert) {

        if (validVertex(vert)) {
            return false;
        }

        Vertex<V, E> vertex = new Vertex<>(numVert, vert);
        vertices.put(vert, vertex);
        numVert++;

        return true;
    }

    @Override
    public boolean insertEdge(V vOrig, V vDest, E eInf, double eWeight) {

        if (getEdge(vOrig, vDest) != null) {
            return false;
        }

        if (!validVertex(vOrig)) {
            insertVertex(vOrig);
        }

        if (!validVertex(vDest)) {
            insertVertex(vDest);
        }

        Vertex<V, E> vorig = vertices.get(vOrig);
        Vertex<V, E> vdest = vertices.get(vDest);

        Edge<V, E> newEdge = new Edge<>(eInf, eWeight, vorig, vdest);
        vorig.addAdjVert(vDest, newEdge);
        numEdge++;

        //if graph is not direct insert other edge in the opposite direction 
        if (!isDirected) // if vDest different vOrig
        {
            if (getEdge(vDest, vOrig) == null) {
                Edge<V, E> otherEdge = new Edge<>(eInf, eWeight, vdest, vorig);
                vdest.addAdjVert(vOrig, otherEdge);
                numEdge++;
            }
        }

        return true;
    }

    @Override
    public boolean removeVertex(V vert) {

        if (!validVertex(vert)) {
            return false;
        }

        //remove all edges that point to vert
        for (Edge<V, E> edge : incomingEdges(vert)) {
            V vadj = edge.getVOrig();
            removeEdge(vadj, vert);
        }

        Vertex<V, E> vertex = vertices.get(vert);

        //update the keys of subsequent vertices in the map
        for (Vertex<V, E> v : vertices.values()) {
            int keyVert = v.getVertexKey();
            if (keyVert > vertex.getVertexKey()) {
                keyVert = keyVert - 1;
                v.setVertexKey(keyVert);
            }
        }
        //The edges that live from vert are removed with the vertex    
        vertices.remove(vert);

        numVert--;

        return true;
    }

    @Override
    public boolean removeEdge(V vOrig, V vDest) {

        if (!validVertex(vOrig) || !validVertex(vDest)) {
            return false;
        }

        Edge<V, E> edge = getEdge(vOrig, vDest);

        if (edge == null) {
            return false;
        }

        Vertex<V, E> vorig = vertices.get(vOrig);

        vorig.remAdjVert(vDest);
        numEdge--;

        //if graph is not direct 
        if (!isDirected) {
            edge = getEdge(vDest, vOrig);
            if (edge != null) {
                Vertex<V, E> vdest = vertices.get(vDest);
                vdest.remAdjVert(vOrig);
                numEdge--;
            }
        }
        return true;
    }

    //Returns a clone of the graph 
    @Override
    public Graph<V, E> clone() {

        Graph<V, E> newObject = new Graph<>(this.isDirected);

        //insert all vertices
        for (V vert : vertices.keySet()) {
            newObject.insertVertex(vert);
        }

        //insert all edges
        for (V vert1 : vertices.keySet()) {
            for (Edge<V, E> e : this.outgoingEdges(vert1)) {
                if (e != null) {
                    V vert2 = this.opposite(vert1, e);
                    newObject.insertEdge(vert1, vert2, e.getElement(), e.getWeight());
                }
            }
        }

        return newObject;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.numVert;
        hash = 89 * hash + this.numEdge;
        hash = 89 * hash + Objects.hashCode(this.vertices);
        return hash;
    }

    /* equals implementation
     * @param the other graph to test for equality
     * @return true if both objects represent the same graph
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object otherObj) {

        if (this == otherObj) {
            return true;
        }

        if (otherObj == null || this.getClass() != otherObj.getClass()) {
            return false;
        }

        Graph<V, E> otherGraph = (Graph<V, E>) otherObj;

        if (numVert != otherGraph.numVertices() || numEdge != otherGraph.numEdges()) {
            return false;
        }

        //graph must have same vertices
        boolean eqvertex;
        for (V v1 : this.vertices()) {
            eqvertex = false;
            for (V v2 : otherGraph.vertices()) {
                if (v1.equals(v2)) {
                    eqvertex = true;
                }
            }

            if (!eqvertex) {
                return false;
            }
        }
        return true;
    }

    //string representation
    @Override
    public String toString() {
        String s = "";
        if (numVert == 0) {
            s = "\nGraph not defined!!";
        } else {
            s = "Graph: " + numVert + " vertices, " + numEdge + " edges\n";
            for (Vertex<V, E> vert : vertices.values()) {
                s += vert + "\n";
            }
        }
        return s;
    }
}
