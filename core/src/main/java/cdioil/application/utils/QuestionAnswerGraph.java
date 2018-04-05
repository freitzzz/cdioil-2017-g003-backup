package cdioil.application.utils;

import cdioil.domain.Answer;
import cdioil.domain.Question;
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
 */
@Entity
public class QuestionAnswerGraph implements Serializable {

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
    @MapKey(name = "element")
    private Map<Question, QuestionVertex> vertices;

    /**
     * JPA Constructor.
     */
    protected QuestionAnswerGraph() {
    }

    /**
     * Instantiates an empty Graph.
     *
     * @param directed if true, each edge will only be added once; if false,
     * then an edge in the opposite direction will also be inserted
     */
    public QuestionAnswerGraph(boolean directed) {
        numVert = 0;
        numEdge = 0;
        isDirected = directed;
        vertices = new LinkedHashMap<>();
    }


    public int numVertices() {
        return numVert;
    }

    
    public Iterable<Question> vertices() {
        return vertices.keySet();
    }

    /**
     * Checks if the vertex has already been inserted into the graph.
     *
     * @param vert element to be checked
     * @return true, if vert has already been inserted into the graph; false
     * otherwise
     */
    public boolean validVertex(Question vert) {

        return vertices.get(vert) != null;
    }

    /**
     * Retrieves the vertex key value if it has been inserted into the graph.
     *
     * @param vert vertex
     * @return the vertex's key if it's been inserted into the graph; -1
     * otherwise
     */
    public int getKey(Question vert) {

        if (!validVertex(vert)) {
            return -1;
        }

        return vertices.get(vert).getVertexKey();
    }

    @SuppressWarnings("unchecked")
    public Question[] allkeyVerts() {

        Question vertElem = null;
        for (QuestionVertex vert : vertices.values()) {
            vertElem = vert.getElement();            // To get type
        }
        Question[] keyverts = (Question[]) Array.newInstance(vertElem.getClass(), numVert);

        for (QuestionVertex vert : vertices.values()) {
            keyverts[vert.getVertexKey()] = vert.getElement();
        }

        return keyverts;
    }

    
    public Iterable<Question> adjVertices(Question vert) {

        if (!validVertex(vert)) {
            return null;
        }

        QuestionVertex vertex = vertices.get(vert);

        return vertex.getAllAdjVerts();
    }
    
    public int numEdges() {
        return numEdge;
    }

    public Iterable<AnswerEdge> edges() {

        LinkedList<AnswerEdge> edges = new LinkedList<>();

        for (Map.Entry<Question, QuestionVertex> entry : vertices.entrySet()) {

            for (AnswerEdge edge : vertices.get(entry.getKey()).getAllOutEdges()) {
                edges.addLast(edge);
            }

        }

        return edges;
    }

    public AnswerEdge getEdge(Question vOrig, Question vDest) {

        if (!validVertex(vOrig) || !validVertex(vDest)) {
            return null;
        }

        QuestionVertex vorig = vertices.get(vOrig);

        return vorig.getEdge(vDest);
    }

    public Question[] endVertices(AnswerEdge edge) {

        if (edge == null) {
            return null;
        }

        if (!validVertex(edge.getVOrig()) || !validVertex(edge.getVDest())) {
            return null;
        }

        QuestionVertex vorig = vertices.get(edge.getVOrig());

        if (!edge.equals(vorig.getEdge(edge.getVDest()))) {
            return null;
        }

        return edge.getEndpoints();
    }
    
    public Question opposite(Question vert, AnswerEdge edge) {

        if (!validVertex(vert)) {
            return null;
        }

        QuestionVertex vertex = vertices.get(vert);

        return vertex.getAdjVert(edge);
    }

    public int outDegree(Question vert) {

        if (!validVertex(vert)) {
            return -1;
        }

        QuestionVertex vertex = vertices.get(vert);

        return vertex.numAdjVerts();
    }
    
    public int inDegree(Question vert) {

        if (!validVertex(vert)) {
            return -1;
        }

        int degree = 0;
        for (Question otherVert : vertices.keySet()) {
            if (getEdge(otherVert, vert) != null) {
                degree++;
            }
        }

        return degree;
    }
    
    public Iterable<AnswerEdge> outgoingEdges(Question vert) {

        if (!validVertex(vert)) {
            return null;
        }

        QuestionVertex vertex = vertices.get(vert);

        return vertex.getAllOutEdges();
    }

    
    public Iterable<AnswerEdge>incomingEdges(Question vert) {

        if (!validVertex(vert)) {
            return null;
        }

        LinkedList<AnswerEdge> incomingEdges = new LinkedList<>();

        for (AnswerEdge e : edges()) {

            Question endVertex = e.getVDest();

            if (endVertex.equals(vert)) {
                incomingEdges.add(e);
            }
        }

        return incomingEdges;
    }

    public boolean insertVertex(Question vert) {

        if (validVertex(vert)) {
            return false;
        }

        QuestionVertex vertex = new QuestionVertex(numVert, vert);
        vertices.put(vert, vertex);
        numVert++;

        return true;
    }

    public boolean insertEdge(Question vOrig, Question vDest, Answer eInf, double eWeight) {

        if (getEdge(vOrig, vDest) != null) {
            return false;
        }

        if (!validVertex(vOrig)) {
            insertVertex(vOrig);
        }

        if (!validVertex(vDest)) {
            insertVertex(vDest);
        }

        QuestionVertex vorig = vertices.get(vOrig);
        QuestionVertex vdest = vertices.get(vDest);

        AnswerEdge newEdge = new AnswerEdge(eInf, eWeight, vorig, vdest);
        vorig.addAdjVert(vDest, newEdge);
        numEdge++;

        //if graph is not direct insert other edge in the opposite direction 
        if (!isDirected) // if vDest different vOrig
        {
            if (getEdge(vDest, vOrig) == null) {
                AnswerEdge otherEdge = new AnswerEdge(eInf, eWeight, vdest, vorig);
                vdest.addAdjVert(vOrig, otherEdge);
                numEdge++;
            }
        }

        return true;
    }

    
    public boolean removeVertex(Question vert) {

        if (!validVertex(vert)) {
            return false;
        }

        //remove all edges that point to vert
        for (AnswerEdge edge : incomingEdges(vert)) {
            Question vadj = edge.getVOrig();
            removeEdge(vadj, vert);
        }

        QuestionVertex vertex = vertices.get(vert);

        //update the keys of subsequent vertices in the map
        for (QuestionVertex v : vertices.values()) {
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
    
    public boolean removeEdge(Question vOrig, Question vDest) {

        if (!validVertex(vOrig) || !validVertex(vDest)) {
            return false;
        }

        AnswerEdge edge = getEdge(vOrig, vDest);

        if (edge == null) {
            return false;
        }

        QuestionVertex vorig = vertices.get(vOrig);

        vorig.remAdjVert(vDest);
        numEdge--;

        //if graph is not direct 
        if (!isDirected) {
            edge = getEdge(vDest, vOrig);
            if (edge != null) {
                QuestionVertex vdest = vertices.get(vDest);
                vdest.remAdjVert(vOrig);
                numEdge--;
            }
        }
        return true;
    }

    //Returns a clone of the graph 
    @Override
    public QuestionAnswerGraph clone() {

        QuestionAnswerGraph newObject = new QuestionAnswerGraph(this.isDirected);

        //insert all vertices
        for (Question vert : vertices.keySet()) {
            newObject.insertVertex(vert);
        }

        //insert all edges
        for (Question vert1 : vertices.keySet()) {
            for (AnswerEdge e : this.outgoingEdges(vert1)) {
                if (e != null) {
                    Question vert2 = this.opposite(vert1, e);
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

        QuestionAnswerGraph otherGraph = (QuestionAnswerGraph) otherObj;

        if (numVert != otherGraph.numVertices() || numEdge != otherGraph.numEdges()) {
            return false;
        }

        //graph must have same vertices
        boolean eqvertex;
        for (Question v1 : this.vertices()) {
            eqvertex = false;
            for (Question v2 : otherGraph.vertices()) {
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
            for (QuestionVertex vert : vertices.values()) {
                s += vert + "\n";
            }
        }
        return s;
    }
}
