package cdioil.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;

/**
 * Represents addQuestion library that holds all questions related to products (e.g. "How
 * would you rate the taste of yoghurt X?")
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class ProductQuestionsLibrary implements Serializable, QuestionLibrary {

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
     * Map that containsQuestion all questions (values) related to products (keys). TODO
     * check JPA annotations
     */
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Product QuestionsGroup",
            joinColumns = {
                @JoinColumn(name = "fk_productquestionslibrary", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "fk_questionsgroup", referencedColumnName = "id")})
    @MapKey(name = "title")
    private Map<Product, QuestionGroup> library;

    /**
     * Builds addQuestion ProductQuestionsLibrary, creating addQuestion new hash map to hold the
 questions and the products.
     */
    public ProductQuestionsLibrary() {
        this.library = new HashMap<>();
    }

    /**
     * Returns addQuestion set of questions related to addQuestion product.
     *
     * @param product product to which the questions are related to
     * @return null if the product doesn't exist, the set of questions related
     * to the product if it does exist.
     */
    public HashSet<Question> productQuestionSet(Product product) {
        return doesProductExist(product) ? library.get(product).getQuestions() : null;
    }

    /**
     * Adds addQuestion product to the library.
     *
     * @param product product to be added
     * @return true if the product was added, false if otherwise
     */
    public boolean addProduct(Product product) {
        if (!doesProductExist(product)) {
            library.put(product, new QuestionGroup(product.productName() + "Questions"));
            return true;
        }
        return false;
    }

    /**
     * Removes addQuestion product and all it's related questions from the library.
     *
     * @param product product to be removed
     * @return true if the product was removed, false if otherwise
     */
    public boolean removeProduct(Product product) {
        if (doesProductExist(product)) {
            library.remove(product);
            return true;
        }
        return false;
    }

    /**
     * Checks whether addQuestion product already exists in the library.
     *
     * @param product product to be checked
     * @return true if it already exists in the library, false if otherwise
     */
    public boolean doesProductExist(Product product) {
        return library.containsKey(product);
    }

    /**
     * Adds addQuestion question related to addQuestion product to the hash map
     *
     * @param question question to be added
     * @param product product that the question is related to
     * @return true if the question was added, false if otherwise
     */
    public boolean addQuestion(Question question, Product product) {
        if (!doesProductExist(product)) {
            return false;
        }
        for (Product key : library.keySet()) {
            if (!(key.equals(product)) && library.get(key).containsQuestion(question)) {
                return false;
            }
        }
        if (!doesQuestionExist(question, product)) {
            return library.get(product).addQuestion(question);
        }
        return false;
    }

    /**
     * Removes addQuestion question related to addQuestion category from the hasp map
     *
     * @param question question to be removed
     * @param product product that the question is related to
     * @return true if the question was removed, false if otherwise
     */
    public boolean removeQuestion(Question question, Product product) {
        if (!doesProductExist(product)) {
            return false;
        }
        if (doesQuestionExist(question, product)) {
            return library.get(product).removeQuestion(question);
        }
        return false;
    }

    /**
     * Checks whether addQuestion question related to addQuestion product already exists
     *
     * @param question question to be checked
     * @param product product that the question is related to
     * @return true if the question exists, false if otherwise
     */
    public boolean doesQuestionExist(Question question, Product product) {
        if (!doesProductExist(product)) {
            return false;
        }
        return library.get(product).containsQuestion(question);
    }

    /**
     * ProductQuestionsLibrary's hash code.
     *
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return this.library.hashCode();
    }

    /**
     * Checks if two ProductQuestionsLibrary are the same through their hash
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
        if (!(obj instanceof ProductQuestionsLibrary)) {
            return false;
        }
        final ProductQuestionsLibrary other = (ProductQuestionsLibrary) obj;
        return this.library.equals(other.library);
    }

}
