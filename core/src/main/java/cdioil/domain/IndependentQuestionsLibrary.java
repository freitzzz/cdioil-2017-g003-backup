package cdioil.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Represents a library for all the independent questions (questions that are
 * neither related to a category or a product. e.g. "Do you find the packaging
 * appealing?").
 *
 * @author @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class IndependentQuestionsLibrary implements Serializable, QuestionLibrary {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Question> library;

    /**
     * Builds an instance of IndendepentQuestionsLibrary. Creates a new hash set
     * that will contain all the questions.
     */
    public IndependentQuestionsLibrary() {
        library = new HashSet<>();
    }

    /**
     * IndependentQuestionsLibrary's hash code
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return library.hashCode();
    }

    /**
     * Verifies if two IndependentQuestionsLibrary objects are the same.
     *
     * @param obj object to be compared
     * @return true if they are equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IndependentQuestionsLibrary)) {
            return false;
        }
        final IndependentQuestionsLibrary other = (IndependentQuestionsLibrary) obj;
        return this.library.equals(other.library);
    }

    /**
     * Adds a question to the set.
     *
     * @param question question to be added
     * @return true if the question is added, false if not.
     */
    public boolean addQuestion(Question question) {
        if (!doesQuestionExist(question)) {
            return library.add(question);
        }
        return false;
    }

    /**
     * Removes a question from the set.
     *
     * @param question question to be removed.
     * @return true if the question is removed, false if not
     */
    public boolean removeQuestion(Question question) {
        if (doesQuestionExist(question)) {
            return library.remove(question);
        }
        return false;
    }

    /**
     * Checks if a question already exists in the library.
     *
     * @param question question to be checked
     * @return true if it already exists, false if not
     */
    public boolean doesQuestionExist(Question question) {
        return library.contains(question);
    }

}
