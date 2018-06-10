package cdioil.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Class that was created to allow the persistence of a map key (Category or
 * Product) that contains a set of values (Templates).
 *
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class TemplateGroup implements Serializable {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Database id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Title of the template group.
     */
    private String title;

    /**
     * Set of templates.
     */
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Template> templates;

    /**
     * Builds an instance of TemplateGroup, with a new hash set of templates.
     *
     * @param title Title of the template group;
     */
    public TemplateGroup(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("O título do grupo de templates "
                    + "não poder ser null nem vazio");
        }
        this.title = title;
        this.templates = new HashSet<>();
    }

    /**
     * Empty constructor for JPA.
     */
    protected TemplateGroup() {
    }

    /**
     * Returns a new hash set with all the templates.
     *
     * @return set with all templates
     */
    public Set<Template> getTemplates() {
        return new HashSet<>(templates);
    }

    /**
     * Checks if a template is in a set of templates.
     *
     * @param template template to be checked
     * @return true if the template exists in the set, false if otherwise
     */
    public boolean containsTemplate(Template template) {
        return templates.contains(template);
    }

    /**
     * Adds a template to the set.
     *
     * @param template template to be added
     * @return true if the template was added, false if otherwise
     */
    public boolean addTemplate(Template template) {
        if (!containsTemplate(template)) {
            return templates.add(template);
        }
        return false;
    }

    /**
     * Removes a template from the set
     *
     * @param template template to be removed
     * @return true if the template was removed, false if otherwise
     */
    public boolean removeTemplate(Template template) {
        if (containsTemplate(template)) {
            return templates.remove(template);
        }
        return false;
    }

    /**
     * TemplateGroup's hash code
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return this.title.hashCode() + this.templates.hashCode();
    }

    /**
     * Checks if two TemplateGroup instances are equal by comparing their title
     * and template set.
     *
     * @param obj object to be compared
     * @return true if they're equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TemplateGroup)) {
            return false;
        }
        final TemplateGroup other = (TemplateGroup) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return this.templates.equals(other.templates);
    }
}
