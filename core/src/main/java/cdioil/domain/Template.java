package cdioil.domain;

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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Template implements Serializable {

    /**
     * Version for JPA.
     */
    @Version
    private long version;

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
     * Template title
     */
    private String title;

    /**
     * List of Questions of the Template.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private QuestionGroup questionGroup;

    public Template(String title, QuestionGroup questionGroup) {
        this.title = title;

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
     * Returns the title of the template
     * @return String with the title of the template
     */
    public String getTitle(){
        return title;
    }
    /**
     * Returns a copy of the group of questions
     *
     * @return QuestionGroup
     */
    public QuestionGroup getQuestionGroup() {
        String questionGroupTitle = questionGroup.toString();
        Set<Question> setCopy = questionGroup.getQuestions();
        QuestionGroup questionGroupCopy = new QuestionGroup(questionGroupTitle);
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
     * ToString of the class template
     *
     * @return title of template
     */
    public String toString() {
        return title;
    }
}

