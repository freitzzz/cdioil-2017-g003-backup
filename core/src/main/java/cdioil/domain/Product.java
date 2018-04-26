package cdioil.domain;

import cdioil.framework.domain.ddd.AggregateRoot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Represents a Product in a Market Structure's Category.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class Product extends SurveyItem implements AggregateRoot<SKU> {

    /**
     * Constant representing the default content of a Product's image.
     */
    private static final String IMAGEM_PRODUTO_DEFAULT = "Produto sem Imagem";
    private static final long serialVersionUID = 1L;

    /**
     * Product's name.
     */
    @Column(name = "NOME")
    private String name;

    /**
     * Product's Stock Keeping Unit.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private SKU sku;
    
    /**
     * List of the Product's Codes.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Code> codes = new ArrayList<>();
    /**
     * Quantity of the product.
     */
    @Column(name = "QUANTIDADE")
    private String quantity;
    /**
     * The Product's image.
     */
    private Image productImage;

    /**
     * Empty Constructor for JPA.
     */
    protected Product() {
    }

    /**
     * Instantiates a new Product with a given name and 1 or more codes.
     *
     *
     * @param name the product's name
     * @param sku product's stock keeping unit
     * @param codes 0 or more codes
     * @param quantity quantity
     */
    public Product(String name, SKU sku, String quantity,Code... codes) {

        if (name == null || sku == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid parameters.");
        }

        this.name = name;

        this.sku = sku;

        this.codes.addAll(Arrays.asList(codes));
        
        this.quantity = quantity;

        this.productImage = new Image(IMAGEM_PRODUTO_DEFAULT.getBytes());
    }

    /**
     * Method for changing the Product's current image.
     *
     *
     * @param imagem Byte Array with the image's content
     * @return boolean true if the Image was altered successfully, false if an error has occured
     *
     */
    public boolean changeProductImage(byte[] imagem) {
        try {
            productImage = new Image(imagem);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the product's name.
     *
     * @return string with the product's name
     */
    public String productName() {
        return name;
    }

    /**
     * Describes the Product through its attributes.
     *
     * @return the Product's textual representation
     */
    @Override
    public String toString() {

        String result = String.format("Nome: %s\n", name);

        result += "SKU:\n";
        
        result += sku.toString();
        
        result += "Códigos:\n";

        for (Code c : codes) {

            result += c.toString();
        }

        return result;
    }

    /**
     * Generates an hash value based on the Product's list of codes.
     *
     * @return the generated hash value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.sku);
        return hash;
    }

    /**
     * Compares this instance with another Object.
     *
     * @param obj object to compare to
     * @return true, if the Products have the same list of Code elements, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;

        return Objects.equals(this.sku, other.sku);
    }

    @Override
    public SKU getID() {
        return sku;
    }
}
