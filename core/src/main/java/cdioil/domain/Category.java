package cdioil.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * Represents a category from the market structure.
 */
@Entity
public class Category implements Serializable {

    /**
     * Serialization code.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Version for JPA.
     */
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CATEGORY", nullable = false, updatable = false)
    /**
     * ID of the Category for JPA.
     */
    private Long id;

    /**
     * Name of the Category.
     */
    private String name;

    /**
     * Identifier of the Category (number + sufix).
     */
    private String identifier;

    /**
     * Path of the Category in the Market Structure.
     */
    private String path;

    /**
     * List of products of the Category.
     */
    @OneToMany
    private Set<Product> products = new HashSet<>();

    /**
     * Regular expression to validate the identifiers of the Category.
     */
    private final static String IDENTIFIER_REGEX = "[0-9]+(" + Sufixes.SUFIX_CAT + "|" + Sufixes.SUFIX_SCAT
            + "|" + Sufixes.SUFIX_UB + "|" + Sufixes.SUFIX_UN + "|" + Sufixes.SUFIX_DC + ")";

    /**
     * Regular expression to validate the path of the Category in the Market
     * Structure.
     */
    private final static String PATH_REGEX = "[0-9]+" + Sufixes.SUFIX_DC + "((-[0-9]+" + Sufixes.SUFIX_UN + "(-[0-9]+"
            + Sufixes.SUFIX_CAT + "(-[0-9]+" + Sufixes.SUFIX_SCAT + "(-[0-9]+" + Sufixes.SUFIX_UB + ")?)?)?)?)";

    /**
     * Sufixes for the identifier of the Category (DC, UN, CAT, SCAT or UB).
     */
    public enum Sufixes {
        SUFIX_DC() {
            @Override
            public String toString() {
                return "DC";
            }
        },
        SUFIX_UN() {
            @Override
            public String toString() {
                return "UN";
            }
        },
        SUFIX_CAT() {
            @Override
            public String toString() {
                return "CAT";
            }
        },
        SUFIX_SCAT() {
            @Override
            public String toString() {
                return "SCAT";
            }
        },
        SUFIX_UB() {
            @Override
            public String toString() {
                return "UB";
            }
        };
    }

    /**
     * Empty protected constructor for JPA.
     */
    protected Category() {
    }

    /**
     * Creates an instance of Category, receiving its name, path and identifier.
     *
     * @param name Name of the Category
     * @param identifier Idenfier of the Category
     * @param path Path of the Category
     */
    public Category(String name, String identifier, String path) {
        if (isNameValid(name) && isIdentifierValid(identifier)
                && isPathValid(path) && path.endsWith(identifier)) {
            this.name = name;
            this.path = path;
            this.identifier = identifier;
            products = new HashSet<>();
        } else {
            throw new IllegalArgumentException("Dados de entrada inválidos.");
        }
    }

    /**
     * Checks if the name of the Category is valid.
     *
     * @param name String to check
     * @return true, if the name is valid. Otherwise, returns false
     */
    private boolean isNameValid(String name) {
        return name != null
                && !name.trim().isEmpty();
    }

    /**
     * Checks if the identifier of the Category is valid.
     *
     * @param identifier String to check
     * @return true, if the identifier is valid. Otherwise, returns false
     */
    private boolean isIdentifierValid(String identifier) {
        return identifier != null
                && (identifier.matches(IDENTIFIER_REGEX) || identifier.equals("RAIZ"));
    }

    /**
     * Checks if the path of the Category is valid.
     *
     * @param name String to check
     * @return true, if the path is valid. Otherwise, returns false
     */
    private boolean isPathValid(String path) {
        return path != null
                && (path.matches(PATH_REGEX) || path.equals("RAIZ"));
    }

    /**
     * Adds a product to the list of products of the Category.
     *
     * @param p product to add
     * @return true if the product is successfully added. Otherwise, returns
     * false
     */
    public boolean addProduct(Product p) {
        if (p == null) {
            return false;
        }
        return products.add(p);
    }

    /**
     * Returns the name of the Category.
     *
     * @return string with the name of the category
     */
    public String categoryName() {
        return name;
    }

    /**
     * Describes a Category through its name and identifier.
     *
     * @return description of the Category
     */
    @Override
    public String toString() {
        return String.format("Nome: %s\nDescritivo: %s\n", name, identifier);
    }

    /**
     * Generates an unique index for the Category.
     *
     * @return the hash value
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.path);
        return hash;
    }

    /**
     * Compares a Category with another Object.
     *
     * @param obj Object to compare
     * @return true, if the two Categories have the same path. Otherwise,
     * returns false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Category other = (Category) obj;
        return this.path.equals(other.path);
    }
}
