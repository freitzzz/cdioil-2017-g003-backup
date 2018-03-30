package cdioil.graph;

/**
 * Interface that defines the general behaviour for a Graph.
 *
 * @author DEI-ESINF
 * @param <V> data type for elements stored in the Graph's vertices
 * @param <E> data type for elements stored in the Graph's edges
 */
public interface GraphInterface<V, E> {

    /**
     * Computes the number of vertices stored in the Graph.
     *
     * @return number of vertices
     */
    int numVertices();

    /**
     * Returns all the vertices of the graph as an Iterable Collection.
     *
     * @return all of the graph's vertices
     */
    Iterable<V> vertices();

    /**
     * An Iterable Collection with all the adjacent vertices of vert.
     *
     * @param vert
     * @return Iterable Collection with the adjacent vertices
     */
    Iterable<V> adjVertices(V vert);

    /**
     * Computes the number of edges stored in the Graph.
     *
     * @return number of edges
     */
    int numEdges();

    /**
     * Returns all of Graph's Edge wrapper classes as an Iterable Collection.
     *
     * @return Iterable Collection with all of the Graph's Edge
     */
    Iterable<Edge<V, E>> edges();

    /**
     * Returns the Edge wrapper Object from vOrig to vDest, or null if vertices
     * are not adjacent.
     *
     * @param vOrig
     * @param vDest
     * @return the Edge or null if vertices are not adjacent or have not been
     * inserted into this Graph instance
     */
    Edge<V, E> getEdge(V vOrig, V vDest);

    /**
     * Returns the vertices of edge e as an array of length two If the graph is
     * directed, the first vertex is the origin, and the second is the
     * destination. If the graph is undirected, the order is arbitrary.
     *
     * @param edge
     * @return array of two vertices or null if edge doesn't exist
     */
    V[] endVertices(Edge<V, E> edge);

    /**
     * Returns the vertex element that is opposite to vert on Edge edge.
     *
     * @param vert
     * @param edge
     * @return opposite vertex, or null if vertex or edge don't exist
     */
    V opposite(V vert, Edge<V, E> edge);

    /**
     * Returns the number of edges leaving vertex v For an undirected graph,
     * this is the same result returned by inDegree
     *
     * @param vert
     * @return number of edges leaving vertex v, -1 if vertex doesn't exist
     */
    int outDegree(V vert);

    /**
     * Returns the number of edges for which vertex v is the destination For an
     * undirected graph, this is the same result returned by outDegree
     *
     * @param vert
     * @return number of edges leaving vertex v, -1 if vertex doesn't exist
     */
    int inDegree(V vert);

    /**
     * Returns an iterable collection of edges for which vertex v is the origin
     * for an undirected graph, this is the same result returned by
     * incomingEdges
     *
     * @param vert
     * @return iterable collection of edges, null if vertex doesn't exist
     */
    Iterable<Edge<V, E>> outgoingEdges(V vert);

    /**
     * Returns an iterable collection of edges for which vertex v is the
     * destination For an undirected graph this is the same result as returned
     * by incomingEdges
     *
     * @param vert
     * @return iterable collection of edges reaching vertex, null if vertex
     * doesn't exist
     */
    Iterable<Edge<V, E>> incomingEdges(V vert);

    /**
     * Inserts a new vertex with some specific comparable type
     *
     * @param newVert
     * @return a true if insertion suceeds, false otherwise
     */
    boolean insertVertex(V newVert);

    /**
     * Adds a new edge between vertices u and v, with some specific comparable
     * type. If vertices u, v don't exist in the graph they are inserted
     *
     * @param vOrig
     * @param vDest
     * @param edge
     * @param eWeight edge weight
     * @return true if suceeds, or false if an edge already exists between the
     * two verts.
     */
    boolean insertEdge(V vOrig, V vDest, E edge, double eWeight);

    /**
     * Removes a vertex and all its incident edges from the graph
     *
     * @param vert
     * @return
     */
    boolean removeVertex(V vert);

    /**
     * Removes the edge between two vertices
     *
     * @param vOrig
     * @param vDest
     * @return
     */
    boolean removeEdge(V vOrig, V vDest);

}
