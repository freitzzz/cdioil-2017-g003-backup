package cdioil.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
 * Represents a library that holds all questions related to products (e.g. "How
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
     * Map that contains all questions (values) related to products (keys). TODO
     * check JPA annotations
     */
    @ElementCollection
    @MapKeyColumn(name = "Product")
    @Column(name = "Questions")
    @CollectionTable(name = "ProductQuestionsLibrary", joinColumns = @JoinColumn(name = "library id"))
    private Map<Product, HashSet<Question>> library;

    /**
     * Builds a ProductQuestionsLibrary, creating a new hash map to hold the
     * questions and the products.
     */
    public ProductQuestionsLibrary() {
        this.library = new HashMap<>();
    }

    /**
     * Returns a set of questions related to a product.
     *
     * @param product product to which the questions are related to
     * @return null if the product doesn't exist, the set of questions related
     * to the product if it does exist.
     */
    public HashSet<Question> productQuestionSet(Product product) {
        return doesProductExist(product) ? library.get(product) : null;
    }

    /**
     * Adds a product to the library.
     *
     * @param product product to be added
     * @return true if the product was added, false if otherwise
     */
    public boolean addProduct(Product product) {
        if (!doesProductExist(product)) {
            library.put(product, new HashSet<>());
            return true;
        }
        return false;
    }

    /**
     * Removes a product and all it's related questions from the library.
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
     * Checks whether a product already exists in the library.
     *
     * @param product product to be checked
     * @return true if it already exists in the library, false if otherwise
     */
    public boolean doesProductExist(Product product) {
        return library.containsKey(product);
    }

    /**
     * Adds a question related to a product to the hash map
     *
     * @param question question to be added
     * @param product product that the question is related to
     * @return true if the question was added, false if otherwise
     */
    public boolean addQuestion(Question question, Product product) {
        if (!doesProductExist(product)) {
            return false;
        }
        if (!doesQuestionExist(question, product)) {
            return library.get(product).add(question);
        }
        return false;
    }

    /**
     * Removes a question related to a category from the hasp map
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
            return library.get(product).remove(question);
        }
        return false;
    }

    /**
     * Checks whether a question related to a product already exists
     *
     * @param question question to be checked
     * @param product product that the question is related to
     * @return true if the question exists, false if otherwise
     */
    public boolean doesQuestionExist(Question question, Product product) {
        if (!doesProductExist(product)) {
            return false;
        }
        return library.get(product).contains(question);
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
