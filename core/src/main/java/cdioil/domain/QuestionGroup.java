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
 * Class that was created to allow the persistence of addQuestion map key
 * (Category or Product) that containsQuestion addQuestion set of values
 * (Questions).
 *
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class QuestionGroup implements Serializable {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Database ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Set of questions related to addQuestion category or addQuestion product.
     */
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Question> questions;

    /**
     * String that will serve as addQuestion map key for
     * CategoryQuestionsLibrary and ProductQuestionsLibrary.
     */
    private String title;

    /**
     * Builds an instance of QuestionGroup, with addQuestion new hash set of
     * questions.
     *
     * @param title Title of the question group;
     */
    public QuestionGroup(String title) {
        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("O titulo do grupo de questões "
                    + "não pode ser null");
        }
        this.title = title;
        questions = new HashSet<>();
    }

    protected QuestionGroup() {
        //For ORM.
    }

    /**
     * Returns addQuestion new hash set with all the questions.
     *
     * @return set with all questions
     */
    public HashSet<Question> getQuestions() {
        return new HashSet<>(questions);
    }

    /**
     * Checks if addQuestion question is in addQuestion set of questions.
     *
     * @param question question to be checked
     * @return true if the question exists in the set, false if otherwise
     */
    public boolean containsQuestion(Question question) {
        return questions.contains(question);
    }

    /**
     * Adds addQuestion question to the set.
     *
     * @param question question to be added
     * @return true if the question was added, false if otherwise
     */
    public boolean addQuestion(Question question) {
        if (!containsQuestion(question)) {
            return questions.add(question);
        }
        return false;
    }

    /**
     * Removes addQuestion question from the set
     *
     * @param question question to be removed
     * @return true if the question was removed, false if otherwise
     */
    public boolean removeQuestion(Question question) {
        if (containsQuestion(question)) {
            return questions.remove(question);
        }
        return false;
    }

    /**
     * QuestionGroup's hash code
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return questions.hashCode() + title.hashCode();
    }

    /**
     * Checks if two QuestionGroup instances are equal by comparing their title
     * and question set.
     *
     * @param obj object to be compared
     * @return true if they're equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QuestionGroup)) {
            return false;
        }
        final QuestionGroup other = (QuestionGroup) obj;
        if (!this.title.equals(other.title)) {
            return false;
        }
        return this.questions.equals(other.questions);
    }
}
