package cdioil.domain;

import cdioil.framework.domain.ddd.AggregateRoot;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

/**
 * Represents a Template.
 *
 * @author Ana Guerra (1161191)
 */
@Entity
public class Template implements AggregateRoot<QuestionGroup>, Serializable {

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

    public Template(QuestionGroup questionGroup) {
        if(questionGroup == null || questionGroup.getQuestions().isEmpty()){
            throw new IllegalArgumentException("O conjunto de questões do "
                    + "template não pode ser vazio nem null");
        }
        this.questionGroup = questionGroup;
    }

    /**
     * Creates an empty protected instance for JPA.
     */
    protected Template() {
    }

    /**
     * Returns a copy of the group of questions
     *
     * @return QuestionGroup
     */
    public QuestionGroup getQuestionGroup() {
        String title = questionGroup.toString();
        Set<Question> setCopy = questionGroup.getQuestions();
        QuestionGroup questionGroupCopy = new QuestionGroup(title);
        questionGroupCopy.getQuestions().addAll(setCopy);
        return questionGroupCopy;
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

    /**
     * Returns the entity's identity (in this case it's the question group)
     *
     * @return QuestionGroup
     */
    @Override
    public QuestionGroup getID() {
        return this.questionGroup;
    }
}
