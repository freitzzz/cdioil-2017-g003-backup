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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;

    /**
     * Category of the Template.
     */
    private Category category;

    /**
     * Creates an instance of Template, receiving a Category.
     *
     * @param category Category of the Template.
     */
    public Template(Category category) {

        if (category == null) {
            throw new IllegalArgumentException("Argumento não pode ser null");
        }

        this.questions = new LinkedList<>();
        this.category = category;
    }

    /**
     * Creates an empty protected instance for JPA.
     */
    protected Template() {
    }

    /**
     * Adds a Question to the list of questions of the Template.
     *
     * @param question Question to add
     * @return true, if the question is successfully added. Otherwise, returns
     * false
     */
    public boolean addQuestion(Question question) {
        if (question == null || isQuestionValid(question)) {
            return false;
        }
        return questions.add(question);
    }

    /**
     * Removes a Question from the list of questions of the Template.
     *
     * @param question Question to remove
     * @return true, if the question is successfully removed. Otherwise, returns
     * false
     */
    public boolean removeQuestion(Question question) {
        if (question == null || !isQuestionValid(question)) {
            return false;
        }
        return questions.remove(question);
    }

    /**
     * Checks if a Question already exists in the list of questions.
     *
     * @param question Question to check
     * @return true, if the question already exists. Otherwise, returns false
     */
    public boolean isQuestionValid(Question question) {
        return questions.contains(question);
    }

    /**
     * Access method to the Category of the Template.
     *
     * @return the Category of the Template
     */
    private Category getCategory() {
        return category;
    }

    /**
     * Access method to the list of questions of the Template.
     *
     * @return the list with all questions of the Template
     */
    private List<Question> getQuestionsList() {
        return questions;
    }

    /**
     * Describes the Template.
     *
     * @return a description of the Template
     */
    @Override
    public String toString() {
        return "\nCategoria: " + getCategory() + "\nLista de Questões: " + getQuestionsList();
    }

    /**
     * Generates an unique index for the Template.
     *
     * @return the generated hash value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.questions);
        hash = 43 * hash + Objects.hashCode(this.category);
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
        if (obj == null || !(obj instanceof Template)) {
            return false;
        }
        final Template other = (Template) obj;
        if (!this.questions.equals(other.questions)) {
            return false;
        }
        return this.category.equals(other.category);
    }
}
