package cdioil.application.utils;

import cdioil.domain.Answer;
import cdioil.domain.Question;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Wrapper class for the Graph's edges.
 *
 * @author DEI-ESINF
 */
@Entity
public class AnswerEdge implements Comparable<Object>, Serializable {

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
     * Element stored in the AnswerEdge.
     */
    @OneToOne
    private Answer element;
    /**
     * AnswerEdge's weight value.
     */
    private double weight;
    /**
     * Origin Vertex.
     */
    @OneToOne
    private QuestionVertex vOrig;
    /**
     * Destination Vertex.
     */
    @OneToOne
    private QuestionVertex vDest;

    /**
     * Default constructor of Edge. Instantiates an Edge with all fields at null
     * or 0.
     */
    public AnswerEdge() {
        element = null;
        weight = 0.0;
        vOrig = null;
        vDest = null;
    }

    /**
     * Instantiates an Edge with a given element, weight and pair of vertices.
     *
     * @param eInf element
     * @param ew weight value
     * @param vo origin vertex
     * @param vd destination vertex
     */
    public AnswerEdge(Answer eInf, double ew, QuestionVertex vo, QuestionVertex vd) {
        element = eInf;
        weight = ew;
        vOrig = vo;
        vDest = vd;
    }

    /**
     * Retrieves the element stored in the AnswerEdge.
     *
     * @return element
     */
    public Answer getElement() {
        return element;
    }

    /**
     * Sets a new element for the AnswerEdge.
     *
     * @param eInf new element
     */
    public void setElement(Answer eInf) {
        element = eInf;
    }

    /**
     * Retrieves the AnswerEdge's weight value.
     *
     * @return weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets a new weight value.
     *
     * @param ew new weight value
     */
    public void setWeight(double ew) {
        weight = ew;
    }

    /**
     * Retrieves the origin Vertex's element.
     *
     * @return
     */
    public Question getVOrig() {
        if (this.vOrig != null) {
            return vOrig.getElement();
        }
        return null;
    }

    /**
     * Sets a new origin Vertex.
     *
     * @param vo new origin Vertex
     */
    public void setVOrig(QuestionVertex vo) {
        vOrig = vo;
    }

    /**
     * Retrieves the destination Vertex's element.
     *
     * @return
     */
    public Question getVDest() {
        if (this.vDest != null) {
            return vDest.getElement();
        }
        return null;
    }

    /**
     * Sets a new destination Vertex.
     *
     * @param vd
     */
    public void setVDest(QuestionVertex vd) {
        vDest = vd;
    }

    @SuppressWarnings("unchecked")
    public Question[] getEndpoints() {

        Question oElem = null;
        Question dElem = null;
        Question typeElem = null;

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

        Question[] endverts = (Question[]) Array.newInstance(typeElem.getClass(), 2);

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

        AnswerEdge otherEdge = (AnswerEdge) otherObj;

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
    public int compareTo(Object otherObject) {

        AnswerEdge other = (AnswerEdge) otherObject;
        if (this.weight < other.weight) {
            return -1;
        }
        if (this.weight == other.weight) {
            return 0;
        }
        return 1;
    }

    @Override
    public AnswerEdge clone() {

        AnswerEdge newEdge = new AnswerEdge();

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
