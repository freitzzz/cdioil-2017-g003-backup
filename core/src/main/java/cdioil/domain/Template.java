package cdioil.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Represents a Template.
 *
 * @author Ana Guerra (1161191)
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Template implements Serializable {

    /**
     * ID of the Template for JPA.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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
     * Template title
     */
    protected String title;

    protected Template(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Unable to instantiate a template without a valid title");
        }
        this.title = title;
    }

    /**
     * Creates an empty protected instance for JPA.
     */
    protected Template() {
    }

    /**
     * Returns the title of the template
     *
     * @return String with the title of the template
     */
    public String getTitle() {
        return title;
    }

    /**
     * Adds the given question to the Template.
     *
     * @param q new question
     * @return true - if the question was added successfully<p>
     * false - if the question already exists or is null
     */
    public abstract boolean addQuestion(Question q);

    /**
     * Removes the given question from the Template.
     *
     * @param q question to be removed
     * @return true if the question was removed successfully<p>
     * false - if it was not added previously or is null
     */
    public abstract boolean removeQuestion(Question q);

    /**
     * Retrieves an Iterable Collection of all the questions within the Template.
     * @return Iterable Collection of the Template's questions
     */
    public abstract Iterable<Question> getQuestions();

    /**
     * ToString of the class template
     *
     * @return title of template
     */
    @Override
    public String toString() {
        return title;
    }
}
