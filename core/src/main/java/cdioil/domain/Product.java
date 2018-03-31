package cdioil.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * Represents a Product in a Market Structure's Category.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class Product implements Serializable,SurveyItem {

    /**
     * Serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constant representing the default content of a Product's image.
     */
    private static final String IMAGEM_PRODUTO_DEFAULT = "Produto sem Imagem";

    /**
     * Auto-generated database identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    private Long id;
    
    @Version
    private Long version;

    /**
     * Product's name.
     */
    @Column(name = "NOME")
    private String name;

    /**
     * List of the Product's Codes.
     */
    @OneToMany
    @JoinColumn(name = "FK_PRODUCT")
    private List<Code> codes = new ArrayList<>();

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
     * @param code code
     * @param codes 0 or more codes
     */
    public Product(String name, Code code, Code... codes) {
        this.name = name;

        this.codes.add(code);

        for (Code cod : codes) {
            this.codes.add(cod);
        }

        this.productImage = new Image(IMAGEM_PRODUTO_DEFAULT.getBytes());
    }

    /**
     * Method for changing the Product's current image.
     *
     *
     * @param imagem Byte Array with the image's content
     * @return boolean true if the Image was altered successfully, false if an
     * error has occured
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
        hash = 67 * hash + Objects.hashCode(this.codes);
        return hash;
    }

    /**
     * Compares this instance with another Object.
     *
     * @param obj object to compare to
     * @return true, if the Products have the same list of Code elements, false
     * otherwise
     */
    @Override
    public boolean equals(Object obj
    ) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;

        if (!Objects.equals(this.codes, other.codes)) {
            return false;
        }
        return true;
    }

}
