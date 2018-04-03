package cdioil.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Represents a library that contains all the other ones
 * (ProductQuestionsLibrary, CategoryQuestionsLibrary and
 * IndependentQuestionsLibrary).
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class GlobalLibrary implements Serializable {

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
     * Library that contains all questions related to categories.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private CategoryQuestionsLibrary catQuestionsLibrary;

    /**
     * Library that contains all questions related to products.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private ProductQuestionsLibrary prodQuestionsLibrary;

    /**
     * Library that contains all questions that aren't related to categories or
     * products.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private IndependentQuestionsLibrary indQuestionsLibrary;

    /**
     * Builds a new instance of GlobalLibrary, creating new libraries for
     * category questions, product questions and independent questions.
     */
    public GlobalLibrary() {
        this.catQuestionsLibrary = new CategoryQuestionsLibrary();
        this.prodQuestionsLibrary = new ProductQuestionsLibrary();
        this.indQuestionsLibrary = new IndependentQuestionsLibrary();
    }

    /**
     * Returns the library that holds all questions related to categories.
     *
     * @return CategoryQuestionsLibrary
     */
    public CategoryQuestionsLibrary getCatQuestionsLibrary() {
        return catQuestionsLibrary;
    }

    /**
     * Returns the library that holds all questions related to products.
     *
     * @return ProductQuestionsLibrary
     */
    public ProductQuestionsLibrary getProdQuestionsLibrary() {
        return prodQuestionsLibrary;
    }

    /**
     * Returns the library that holds all independent questions.
     *
     * @return IndependentQuestionsLibrary
     */
    public IndependentQuestionsLibrary getIndQuestionsLibrary() {
        return indQuestionsLibrary;
    }

    /**
     * GlobalLibrary's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return catQuestionsLibrary.hashCode() + prodQuestionsLibrary.hashCode()
                + indQuestionsLibrary.hashCode();
    }

    /**
     * Checks if two instances of GlobalLibrary are equal.
     *
     * @param obj object to be compared.
     * @return true if they're equal, false if otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GlobalLibrary)) {
            return false;
        }
        final GlobalLibrary other = (GlobalLibrary) obj;
        if (!(this.catQuestionsLibrary.equals(other.catQuestionsLibrary))) {
            return false;
        }
        if (!(this.prodQuestionsLibrary.equals(other.prodQuestionsLibrary))) {
            return false;
        }
        return this.indQuestionsLibrary.equals(other.indQuestionsLibrary);
    }

}
