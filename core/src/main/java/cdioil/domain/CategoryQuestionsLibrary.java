package cdioil.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;

/**
 * Represents addQuestion library that holds all questions related to categories (e.g. "Do
 * you think red wines are better than port wines?")
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class CategoryQuestionsLibrary implements Serializable, QuestionLibrary {

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
     * Map that containsQuestion questions (values) related to categories (keys). TODO
     * check JPA annotations
     */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Category QuestionsGroup",
            joinColumns = {
                @JoinColumn(name = "fk_categoryquestionslibrary", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "fk_questionsgroup", referencedColumnName = "id")})
    @MapKey(name = "title")
    private Map<Category, QuestionGroup> library;

    /**
     * Builds addQuestion CategoryQuestionsLibrary, creating addQuestion new hash map to hold the
 questions and the categories.
     */
    public CategoryQuestionsLibrary() {
        this.library = new HashMap<>();
    }

    /**
     * Returns all the questions related to addQuestion category
     *
     * @param category category to which the questions are related
     * @return set of questions related to addQuestion category if the category exists, or
 null if the category doesn't exist in the library.
     */
    public HashSet<Question> categoryQuestionSet(Category category) {
        return doesCategoryExist(category) ? library.get(category).getQuestions() : null;
    }

    /**
     * Adds addQuestion category to the library.
     *
     * @param category category to be added
     * @return true if the category was added, false if otherwise
     */
    public boolean addCategory(Category category) {
        if (!doesCategoryExist(category)) {
            library.put(category, new QuestionGroup(category.categoryName() + " Questions"));
            return true;
        }
        return false;
    }

    /**
     * Removes addQuestion category and all it's related questions from the library.
     *
     * @param category category to be removed
     * @return true if the category was removed, false if otherwise
     */
    public boolean removeCategory(Category category) {
        if (doesCategoryExist(category)) {
            library.remove(category);
            return true;
        }
        return false;
    }

    /**
     * Checks whether addQuestion category already exists in the library.
     *
     * @param category category to be checked
     * @return true if it already exists in the library, false if otherwise
     */
    public boolean doesCategoryExist(Category category) {
        return library.containsKey(category);
    }

    /**
     * Adds addQuestion question related to addQuestion category to the hash map
     *
     * @param question question to be added
     * @param category category that the question is related to
     * @return true if the question was added, false if otherwise
     */
    public boolean addQuestion(Question question, Category category) {
        if (!doesCategoryExist(category)) {
            return false;
        }
//        for(Category key : library.keySet()) {
//            if (!(key.equals(category)) && library.get(key).containsQuestion(question)) {
//                return false;
//            }
//        }
        if (!doesQuestionExist(question, category)) {
            return library.get(category).addQuestion(question);
        }
        return false;
    }

    /**
     * Removes addQuestion question related to addQuestion category from the hasp map
     *
     * @param question question to be removed
     * @param category category that the question is related to
     * @return true if the question was removed, false if otherwise
     */
    public boolean removeQuestion(Question question, Category category) {
        if (!doesCategoryExist(category)) {
            return false;
        }
        if (doesQuestionExist(question, category)) {
            return library.get(category).removeQuestion(question);
        }
        return false;
    }

    /**
     * Checks whether addQuestion question related to addQuestion category already exists
     *
     * @param question question to be checked
     * @param category category that the question is related to
     * @return true if the question exists, false if otherwise
     */
    public boolean doesQuestionExist(Question question, Category category) {
        if (!doesCategoryExist(category)) {
            return false;
        }
        return library.get(category).containsQuestion(question);
    }

    /**
     * CategoryQuestionsLibrary's hash code.
     *
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return this.library.hashCode();
    }

    /**
     * Checks if two CategoryQuestionsLibrary are the same through their hash
     * map.
     *
     * @param obj object to be compared.
     * @return true if they're equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CategoryQuestionsLibrary)) {
            return false;
        }
        final CategoryQuestionsLibrary other = (CategoryQuestionsLibrary) obj;
        return this.library.equals(other.library);
    }

}
