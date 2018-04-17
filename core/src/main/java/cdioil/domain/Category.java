package cdioil.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * Represents a category from the market structure.
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "path"))
public class Category extends SurveyItem {

    /**
     * Database identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Database version number.
     */
    @Version
    private long version;

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
    @Column(updatable = true, nullable = false)
    private String path;

    /**
     * List of products of the Category.
     */
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Product> products = new HashSet<>();

    /**
     * String representing the Category's path splitter.
     */
    private static final String SPLITTER = "-";

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
     * @param path Path of the Category
     */
    public Category(String name, String path) {
        if (isNameValid(name) && isPathValid(path)) {
            this.name = name;
            this.path = path;

            String pathIdentifiers[] = path.split(SPLITTER);
            //Identifier is always the path's last element
            this.identifier = pathIdentifiers[pathIdentifiers.length - 1];

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
     * Returns an iterator of the set of products associated to the Category.
     *
     * @return iterator of the set of products
     */
    public Iterator<Product> getProductSetIterator() {
        return products.iterator();
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
     * Returns the path of the Category.
     *
     * @return string with the path of the category
     */
    public String categoryPath() {
        return path;
    }

    /**
     * Returns the Category's identifier.
     *
     * @return
     */
    public String categoryIdentifier() {
        return identifier;
    }

    /**
     * List of identifiers in the Category's path.
     *
     * @return
     */
    public List<String> categoryPathIdentifiers() {
        return Arrays.asList(path.split(SPLITTER));
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
