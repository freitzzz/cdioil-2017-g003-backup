package cdioil.domain;

import cdioil.framework.domain.ddd.AggregateRoot;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * Represents a library for all the independent Templates.
 *
 * @author António Sousa [1161371]
 */
@Entity
public class IndependentTemplatesLibrary implements AggregateRoot<Set<Template>>,
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

    @OneToMany(cascade = CascadeType.ALL)
    private final Set<Template> library;

    /**
     * Creates a new instance of IndependentTemplatesLibrary.
     */
    public IndependentTemplatesLibrary() {
        this.library = new HashSet<>();
    }

    /**
     * Checks if the template already exists within the library.
     *
     * @param template Template being checked
     * @return true - if the Template exists<p>
     * false - otherwise
     */
    public boolean doesTemplateExist(Template template) {
        return library.contains(template);
    }

    /**
     * Adds a Template to the library.
     *
     * @param template Template being added
     * @return true - if the Template did not previously exist<p>
     * false - otherwise
     */
    public boolean addTemplate(Template template) {
        if (!doesTemplateExist(template)) {
            return library.add(template);
        }
        return false;
    }

    /**
     * Removes a Template from the library.
     *
     * @param template Template being removed
     * @return true - if it was removed<p>
     * false - otherwise
     */
    public boolean removeTemplate(Template template) {

        if (doesTemplateExist(template)) {
            return library.remove(template);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.library.hashCode();
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
        final IndependentTemplatesLibrary other = (IndependentTemplatesLibrary) obj;

        return this.library.equals(other.library);
    }

    @Override
    public Set<Template> getID() {
        return library;
    }

}
