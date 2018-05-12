package cdioil.domain.authz;

import cdioil.domain.Category;
import cdioil.framework.domain.ddd.AggregateRoot;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * Survey Manager
 * <p>
 * Person responsible for the creation and configuration of surveys of a given
 * market structure.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"SYSTEMUSER"}))
public class Manager implements Serializable, AggregateRoot<SystemUser>, User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "MANAGER_ID")
    private long id;
    @Version
    private Long version;
    /**
     * SystemUser associated with a Manager.
     */
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "SYSTEMUSER")
    private SystemUser sysUser;

    /**
     * Category List associated with a Manager.
     */
    @OneToMany
    private List<Category> categories;

    /**
     * Builds a Manager with a SystemUser and a Category List
     *
     * @param su SystemUser account
     * @param categories Category List
     */
    public Manager(SystemUser su, List<Category> categories) {
        this.sysUser = su;
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
        this.sysUser = su;
    }

    protected Manager() {
        //For ORM
    }

    /**
     * Returns a description of a manager
     *
     * @return description of it's SystemUser account
     */
    @Override
    public String toString() {
        return sysUser.toString();
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

        return sysUser.equals(manager.sysUser);
    }

    /**
     * Manager's hash code
     *
     * @return SystemUser's hash code.
     */
    @Override
    public int hashCode() {
        return sysUser.hashCode();
    }

    /**
     * Returns the manager's identity
     * @return SystemUser associated to the manager
     */
    @Override
    public SystemUser getID() {
        return sysUser;
    }

    /**
     * Associates categories to the manager.
     *
     * @param lc categories to add
     * @return true if they were added with success, false if not
     */
    public boolean addCategories(List<Category> lc) {
        if (lc == null || lc.isEmpty()) {
            return false;
        }
        lc.stream().filter(cat -> (!isAssociatedWithCategory(cat))).forEachOrdered(cat -> 
            categories.add(cat)
        );
        return true;
    }

    /**
     * Removes categories from the manager's list
     *
     * @param lc categories to remove
     * @return true if they were removed with success, false if not
     */
    public boolean removeCategories(List<Category> lc) {
        return lc != null ? categories.removeAll(lc) : false;
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

    /**
     * Returns all the categories given to the manager
     *
     * @return all categories
     */
    public List<Category> categoriesFromManager() {
        return categories;
    }

}
