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

/**
 * Represents a library that holds all templates related to all categories.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
public class CategoryTemplatesLibrary implements AggregateRoot<Map<Category, TemplateGroup>>,
        Serializable, TemplateLibrary {

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
     * Map that contains a template group (values) for each category (key).
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private Map<Category, TemplateGroup> library;

    /**
     * Builds a new CategoryTemplatesLibrary with a new HashMap.
     */
    public CategoryTemplatesLibrary() {
        this.library = new HashMap<>();
    }

    /**
     * Returns a template group that is associated to a category
     *
     * @param category category associated with the template group
     * @return set of templates
     */
    public Set<Template> categoryTemplateSet(Category category) {
        return doesCategoryExist(category) ? library.get(category).getTemplates() : null;
    }

    /**
     * Adds a category to the library.
     *
     * @param category category to be added
     * @return true if it was added, false if otherwise
     */
    public boolean addCategory(Category category) {
        if (!doesCategoryExist(category)) {
            library.put(category, new TemplateGroup(category.categoryPath() + "Template Group"));
            return true;
        }
        return false;
    }

    /**
     * Removes a category from the library.
     *
     * @param category category to be removed
     * @return true if it was removed, false if otherwise
     */
    public boolean removeCategory(Category category) {
        if (doesCategoryExist(category)) {
            library.remove(category);
            return true;
        }
        return false;
    }

    /**
     * Checks if a category exists within the library
     *
     * @param category category to be checked
     * @return true if it exists, false if otherwise
     */
    public boolean doesCategoryExist(Category category) {
        return library.containsKey(category);
    }

    /**
     * Adds a template associated with a category
     *
     * @param category category that the template is associated to
     * @param template template to be added
     * @return true if the template was added, false if otherwise
     */
    public boolean addTemplate(Category category, Template template) {
        if (!doesCategoryExist(category)) {
            return false;
        }
        if (!doesTemplateExist(category, template)) {
            return library.get(category).addTemplate(template);
        }
        return false;
    }

    /**
     * Removes a template associated with a category
     *
     * @param category category that the template is associated to
     * @param template template to be removed
     * @return true if the template was removed, false if otherwise
     */
    public boolean removeTemplate(Category category, Template template) {
        if (!doesCategoryExist(category)) {
            return false;
        }
        if (doesTemplateExist(category, template)) {
            return library.get(category).removeTemplate(template);
        }
        return false;
    }

    /**
     * Checks if a template exists for a certain category
     *
     * @param category category that the template is associated to
     * @param template template to be checked
     * @return true if the template exists, false if otherwise
     */
    public boolean doesTemplateExist(Category category, Template template) {
        if (!doesCategoryExist(category)) {
            return false;
        }
        return library.get(category).containsTemplate(template);
    }

    /**
     * CategoryTemplatesLibrary's hash code
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return this.library.hashCode();
    }

    /**
     * Checks whether two CategoryTemplatesLibrary are equal
     *
     * @param obj object to be compared
     * @return true if they're equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CategoryTemplatesLibrary)) {
            return false;
        }
        final CategoryTemplatesLibrary other = (CategoryTemplatesLibrary) obj;
        return this.library.equals(other.library);
    }

    /**
     * Returns the entity's identity (in this case it's the category, template
     * group map.
     *
     * @return a map of categories (keys) and template groups (values)
     */
    @Override
    public Map<Category, TemplateGroup> getID() {
        return this.library;
    }
}
