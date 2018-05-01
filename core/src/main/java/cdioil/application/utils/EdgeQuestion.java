package cdioil.application.utils;

import cdioil.domain.Question;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Join class to be used within class Vertex, in order to overcome JPA's
 * inability to persist duplicate data within a collection. This is because two
 * questions can be associated multiple times as long as the Edge connecting
 * them is different.
 *
 * @author Antonio Sousa
 */
@Entity
public class EdgeQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    @OneToOne(cascade = CascadeType.ALL)
    private Edge edge;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Question outgoingQuestion;

    /**
     * Protected JPA constructor.
     */
    protected EdgeQuestion() {

    }

    EdgeQuestion(Edge edge, Question question) {
        this.edge = edge;
        this.outgoingQuestion = question;
    }

    public Edge getEdge() {
        return edge;
    }

    public Question getOutgoingQuestion() {
        return outgoingQuestion;
    }

    /*NOTE: Since this Entity basically functions as a Map Entry and the Edge 
    would be the key, both hashCode and equals methods only take the Edge into 
    consideration when comparing instances*/
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.edge);
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
        final EdgeQuestion other = (EdgeQuestion) obj;

        return this.edge.equals(other.edge);
    }

    @Override
    public String toString() {
        return "EdgeQuestion{" + "edge=" + edge + ", outgoingQuestion=" + outgoingQuestion + '}';
    }
}
