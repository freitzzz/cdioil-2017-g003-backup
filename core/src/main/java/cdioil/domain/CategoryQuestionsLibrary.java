package cdioil.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;

/**
 * Represents a library that holds all questions related to categories (e.g. "Do
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
     * Map that contains questions (values) related to categories (keys).
     * TODO check JPA annotations
     */
    @ElementCollection
    @MapKeyColumn(name = "Category")
    @Column(name = "Questions")
    @CollectionTable(name = "CategoryQuestionsLibrary",joinColumns = @JoinColumn(name = "library id"))
    private Map<Category, HashSet<Question>> library;

    /**
     * Builds a CategoryQuestionsLibrary, creating a new hash map to hold the
     * questions and the categories.
     */
    public CategoryQuestionsLibrary() {
        this.library = new HashMap<>();
    }

    /**
     * Adds a category to the library.
     *
     * @param category category to be added
     * @return true if the category was added, false if otherwise
     */
    public boolean addCategory(Category category) {
        if (!doesCategoryExist(category)) {
            library.put(category, new HashSet<>());
            return true;
        }
        return false;
    }

    /**
     * Removes a category and all it's related questions from the library.
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
     * Checks whether a category already exists in the library.
     *
     * @param category category to be checked
     * @return true if it already exists in the library, false if otherwise
     */
    public boolean doesCategoryExist(Category category) {
        return library.containsKey(category);
    }

    /**
     * Adds a question related to a category to the hash map
     *
     * @param question question to be added
     * @param category category that the question is related to
     * @return true if the question was added, false if otherwise
     */
    public boolean addQuestion(Question question, Category category) {
        if (!doesCategoryExist(category)) {
            return false;
        }
        if (!doesQuestionExist(question, category)) {
            return library.get(category).add(question);
        }
        return false;
    }

    /**
     * Removes a question related to a category from the hasp map
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
            return library.get(category).remove(question);
        }
        return false;
    }

    /**
     * Checks whether a question related to a category already exists
     *
     * @param question question to be checked
     * @param category category that the question is related to
     * @return true if the question exists, false if otherwise
     */
    public boolean doesQuestionExist(Question question, Category category) {
        if (!doesCategoryExist(category)) {
            return false;
        }
        return library.get(category).contains(question);
    }

    /**
     * CategoryQuestionsLibrary's hash code.
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return this.library.hashCode();
    }

    /**
     * Checks if two CategoryQuestionsLibrary are the same through their hash 
     * map.
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
