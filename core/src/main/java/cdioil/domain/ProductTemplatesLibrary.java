package cdioil.domain;

import cdioil.framework.domain.ddd.AggregateRoot;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

/**
 * Represents a library for all the Templates associated with a Product.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class ProductTemplatesLibrary implements AggregateRoot<Map<Product, TemplateGroup>>,
        Serializable, TemplateLibrary {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Database object version number.
     */
    @Version
    private long version;

    @ManyToMany(cascade = CascadeType.ALL)
    private final Map<Product, TemplateGroup> library;

    /**
     * Creates a new instance of ProductTemplatesLibrary.
     */
    public ProductTemplatesLibrary() {
        this.library = new HashMap<>();
    }

    /**
     * Checks if a Product exists within the library.
     *
     * @param product Product being checked
     * @return true - if it exists<p>
     * false - otherwise
     */
    public boolean doesProductExist(Product product) {
        return library.containsKey(product);
    }

    /**
     * Checks if a Template is mapped to a given Product.
     *
     * @param product Product that the Template is mapped to
     * @param template Template to be checked
     * @return true - if the template exists<p>
     * false - otherwise
     */
    public boolean doesTemplateExist(Product product, Template template) {
        if (!doesProductExist(product)) {
            return false;
        }
        return library.get(product).containsTemplate(template);
    }

    /**
     * Returns a Set of Template that is mapped to the product.
     *
     * @param product Product
     * @return Set of Template
     */
    public Set<Template> productTemplateSet(Product product) {
        return doesProductExist(product) ? library.get(product).getTemplates() : null;
    }

    /**
     * Adds a Product to the library.
     *
     * @param product Product being added
     * @return true - if the product did not previously exist<p>
     * false - otherwise
     */
    public boolean addProduct(Product product) {
        if (!doesProductExist(product)) {
            library.put(product, new TemplateGroup(product.getID().toString() + " Template Group"));
            return true;
        }
        return false;
    }

    /**
     * Removes a Product from the library.
     *
     * @param product Product being removed
     * @return true - if it was removed<p>
     * false - otherwise
     */
    public boolean removeProduct(Product product) {
        if (doesProductExist(product)) {
            library.remove(product);
            return true;
        }
        return false;
    }

    /**
     * Adds a Template to the given Product.
     *
     * @param product Product that the Template will be mapped to
     * @param template Template being added
     * @return true - if the template was added<p>
     * false - otherwise
     */
    public boolean addTemplate(Product product, Template template) {
        if (!doesProductExist(product)) {
            return false;
        }
        if (!doesTemplateExist(product, template)) {
            return library.get(product).addTemplate(template);
        }

        return false;
    }

    /**
     * Removes a Template mapped to the given Product.
     *
     * @param product Product that the Template is mapped to
     * @param template Template being removed
     * @return true - if the template was removed<p>
     * false - otherwise
     */
    public boolean removeTemplate(Product product, Template template) {
        if (!doesProductExist(product)) {
            return false;
        }
        if (doesTemplateExist(product, template)) {
            return library.get(product).removeTemplate(template);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.library.hashCode();
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
        final ProductTemplatesLibrary other = (ProductTemplatesLibrary) obj;

        return this.library.equals(other.library);
    }

    @Override
    public Map<Product, TemplateGroup> getID() {
        return library;
    }

}
