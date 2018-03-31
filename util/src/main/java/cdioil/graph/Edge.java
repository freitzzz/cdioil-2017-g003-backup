package cdioil.graph;

import java.lang.reflect.Array;
import java.util.Objects;

/**
 * Wrapper class for the Graph's edges.
 *
 * @author DEI-ESINF
 * @param <V> data type for elements stored in the Graph's vertices
 * @param <E> data type for elements stored in the Graph's edges
 */
public class Edge<V, E> implements Comparable<Object> {

    /**
     * Element stored in the Edge.
     */
    private E element;
    /**
     * Edge's weight value.
     */
    private double weight;
    /**
     * Origin Vertex.
     */
    private Vertex<V, E> vOrig;
    /**
     * Destination Vertex.
     */
    private Vertex<V, E> vDest;

    /**
     * Default constructor of Edge. Instantiates an Edge with all fields at null
     * or 0.
     */
    public Edge() {
        element = null;
        weight = 0.0;
        vOrig = null;
        vDest = null;
    }

    /**
     * Instantiates an Edge with a given element, weight and pair of vertices.
     * @param eInf element
     * @param ew weight value
     * @param vo origin vertex
     * @param vd destination vertex
     */
    public Edge(E eInf, double ew, Vertex<V, E> vo, Vertex<V, E> vd) {
        element = eInf;
        weight = ew;
        vOrig = vo;
        vDest = vd;
    }

    /**
     * Retrieves the element stored in the Edge.
     * @return element
     */
    public E getElement() {
        return element;
    }

    /**
     * Sets a new element for the Edge.
     * @param eInf new element
     */
    public void setElement(E eInf) {
        element = eInf;
    }

    /**
     * Retrieves the Edge's weight value.
     * @return weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets a new weight value.
     * @param ew new weight value
     */
    public void setWeight(double ew) {
        weight = ew;
    }

    /**
     * Retrieves the origin Vertex's element.
     * @return 
     */
    public V getVOrig() {
        if (this.vOrig != null) {
            return vOrig.getElement();
        }
        return null;
    }

    /**
     * Sets a new origin Vertex.
     * @param vo new origin Vertex
     */
    public void setVOrig(Vertex<V, E> vo) {
        vOrig = vo;
    }

    /**
     * Retrieves the destination Vertex's element.
     * @return 
     */
    public V getVDest() {
        if (this.vDest != null) {
            return vDest.getElement();
        }
        return null;
    }

    /**
     * Sets a new destination Vertex.
     * @param vd 
     */
    public void setVDest(Vertex<V, E> vd) {
        vDest = vd;
    }

    @SuppressWarnings("unchecked")
    public V[] getEndpoints() {

        V oElem = null;
        V dElem = null;
        V typeElem = null;

        if (this.vOrig != null) {
            oElem = vOrig.getElement();
        }

        if (this.vDest != null) {
            dElem = vDest.getElement();
        }

        if (oElem == null && dElem == null) {
            return null;
        }

        if (oElem != null) // To get type
        {
            typeElem = oElem;
        }

        if (dElem != null) {
            typeElem = dElem;
        }

        V[] endverts = (V[]) Array.newInstance(typeElem.getClass(), 2);

        endverts[0] = oElem;
        endverts[1] = dElem;

        return endverts;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.element);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.weight) ^ (Double.doubleToLongBits(this.weight) >>> 32));
        hash = 97 * hash + Objects.hashCode(this.vOrig);
        hash = 97 * hash + Objects.hashCode(this.vDest);
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

        Edge<V, E> otherEdge = (Edge<V, E>) otherObj;

        // if endpoints vertices are not equal
        if ((this.vOrig == null && otherEdge.vOrig != null)
                || (this.vOrig != null && otherEdge.vOrig == null)) {
            return false;
        }

        if ((this.vDest == null && otherEdge.vDest != null)
                || (this.vDest != null && otherEdge.vDest == null)) {
            return false;
        }

        if (this.vOrig != null && otherEdge.vOrig != null
                && !this.vOrig.equals(otherEdge.vOrig)) {
            return false;
        }

        if (this.vDest != null && otherEdge.vDest != null
                && !this.vDest.equals(otherEdge.vDest)) {
            return false;
        }

        if (this.weight != otherEdge.weight) {
            return false;
        }

        if (this.element != null && otherEdge.element != null) {
            return this.element.equals(otherEdge.element);
        }

        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(Object otherObject) {

        Edge<V, E> other = (Edge<V, E>) otherObject;
        if (this.weight < other.weight) {
            return -1;
        }
        if (this.weight == other.weight) {
            return 0;
        }
        return 1;
    }

    @Override
    public Edge<V, E> clone() {

        Edge<V, E> newEdge = new Edge<>();

        newEdge.element = element;
        newEdge.weight = weight;
        newEdge.vOrig = vOrig;
        newEdge.vDest = vDest;

        return newEdge;
    }

    @Override
    public String toString() {
        String st = "";
        if (element != null) {
            st = "      (" + element + ") - ";
        } else {
            st = "\t ";
        }

        if (weight != 0) {
            st += weight + " - " + vDest.getElement() + "\n";
        } else {
            st += vDest.getElement() + "\n";
        }

        return st;
    }

}
