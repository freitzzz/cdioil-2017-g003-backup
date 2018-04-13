package cdioil.domain;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * Represents a Template.
 *
 * @author Ana Guerra (1161191)
 */
@Entity
public class Template implements Serializable {

    /**
     * Version for JPA.
     */
    @Version
    private Long version;

    /**
     * Serialization code.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID of the Template for JPA.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * List of Questions of the Template.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private QuestionGroup questionGroup;

    public Template(String title) {
        this.questionGroup = new QuestionGroup(title);
    }

    /**
     * Creates an empty protected instance for JPA.
     */
    protected Template() {
    }

    public QuestionGroup getQuestionGroup() {
        return questionGroup;
    }

    /**
     * Generates an unique index for the Template.
     *
     * @return the generated hash value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.questionGroup);
        return hash;
    }

    /**
     * Compares the Template with another Object.
     *
     * @param obj Object to compare
     * @return true, if the two Objects have the same attributes. Otherwise,
     * returns false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Template)) {
            return false;
        }
        final Template other = (Template) obj;
        return this.questionGroup.equals(other.questionGroup);
    }
}
