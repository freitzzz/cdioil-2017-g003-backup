package cdioil.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Class that represents a Simple Template (a Template with no graph associated
 * and just contains a Collection of Question).
 *
 * @author António Sousa [1161371]
 */
@Entity
public class SimpleTemplate extends Template {

    /**
     * List containing all of the Template's questions.
     */
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private List<Question> questions;

    /**
     * Instantiates a new <code>SimpleTemplate</code> with a given title.
     *
     * @param title the template's title
     */
    public SimpleTemplate(String title) {
        super(title);
        questions = new ArrayList<>();
    }

    /**
     * Empty constructor for JPA.
     */
    protected SimpleTemplate() {

    }

    @Override
    public boolean addQuestion(Question question) {

        if (question == null || questions.contains(question)) {
            return false;
        }

        return questions.add(question);
    }

    @Override
    public boolean removeQuestion(Question question) {

        if (question == null) {
            return false;
        }

        return questions.remove(question);
    }

    @Override
    public Iterable<Question> getQuestions() {
        return questions;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + questions.hashCode();
        hash = 71 * hash + title.hashCode();
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
        final SimpleTemplate other = (SimpleTemplate) obj;

        if (!this.title.equals(other.title)) {
            return false;
        }
        
        return this.questions.equals(other.questions);
    }

}
