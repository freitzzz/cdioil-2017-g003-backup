package cdioil.domain.authz;

import cdioil.domain.Category;
import cdioil.persistence.Identifiable;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * Survey Manager
 *
 * Person responsible for the creation and configuration of surveys of a given
 * market structure.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"SYSTEMUSER"}))
public class Manager implements Serializable, Identifiable<Email> {

    @Id
    @GeneratedValue
    @Column(name = "MANAGER_ID")
    private long id;
    @Version
    private Long version;
    /**
     * SystemUser associated with a Manager.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "SYSTEMUSER")
    private SystemUser su;
    /**
     * Category List associated with a Manager.
     */
    private List<Category> categories;

    /**
     * Builds a Manager with a SystemUser and a Category List
     *
     * @param su SystemUser account
     * @param categories Category List
     */
    public Manager(SystemUser su, List<Category> categories) {
        this.su = su;
        this.categories = categories;
    }

    /**
     * Builds a Manager with a SystemUser
     *
     * @param su SystemUser account
     */
    public Manager(SystemUser su) {
        if (su == null) {
            throw new IllegalArgumentException("A instância de SystemUser "
                    + "não pode ser null.");
        }
        this.categories = new LinkedList<>();
        this.su = su;
    }

    /**
     * Returns a description of a manager
     *
     * @return description of it's SystemUser account
     */
    @Override
    public String toString() {
        return su.toString();
    }

    /**
     * Checks if two instances of Manager are the same
     *
     * @param o the object to be compared
     * @return true if both objects are the same, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Manager)) {
            return false;
        }

        Manager manager = (Manager) o;

        return su.equals(manager.su);
    }

    /**
     * Manager's hash code
     *
     * @return SystemUser's hash code.
     */
    @Override
    public int hashCode() {
        return su.hashCode();
    }

    protected Manager() {
        //For ORM
    }

    @Override
    public Email getID() {
        return su.getID();
    }

    /**
     * Associates categories to the manager.
     *
     * @param lc categories to add
     * @return true if they were added with success, false if not
     */
    public boolean addCategories(List<Category> lc) {
        try {
            return categories.addAll(lc);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Removes categories from the manager's list
     *
     * @param lc categories to remove
     * @return true se forem removidas com sucesso, false se não forem removidas
     */
    public boolean removeCategories(List<Category> lc) {
        try {
            return categories.removeAll(lc);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if manager is associated with a Category
     *
     * @param c category to check
     * @return true if manager is associated, false if not
     */
    public boolean isAssociatedWithCategory(Category c) {
        return this.categories.contains(c);
    }
}
