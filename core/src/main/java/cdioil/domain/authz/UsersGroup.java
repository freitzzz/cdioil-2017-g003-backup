package cdioil.domain.authz;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Represents a group of users.
 *
 * @author Rita Gonçalves (1160912)
 */
@Entity
public class UsersGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the UsersGroup for JPA.
     */
    @Column(name = "ID_USERSGROUP")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Version for JPA.
     */
    @Version
    private Long version;

    /**
     * Manager that created the UsersGroup.
     */
    @OneToOne(cascade = CascadeType.PERSIST)
    private Manager manager;

    /**
     * List with all the RegisteredUsers of the UsersGroup.
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<RegisteredUser> users;

    /**
     * Creates an instance of UsersGroup, receiving the Manager that created it.
     *
     * @param manager Manager that created the UsersGroup
     */
    public UsersGroup(Manager manager) {
        if (!isManagerValid(manager)) {
            this.manager = manager;
            this.users = new LinkedList<>();
        } else {
            throw new IllegalArgumentException("A instância de Gestor é null");
        }
    }

    /**
     * Creates an empty protected instance of UsersGroup for JPA.
     */
    protected UsersGroup() {
    }

    /**
     * Checks if a manager is valid - not null.
     *
     * @param manager Manager to check
     * @return true, if the manager is valid. Otherwise, returns false
     */
    public boolean isManagerValid(Manager manager) {
        return manager == null;
    }

    /**
     * Checks if a user is valid - the users list contains the user.
     *
     * @param u RegisteredUser to check
     * @return true, if the users list contains the user to add. Otherwise,
     * returns false
     */
    public boolean isUserValid(RegisteredUser u) {
        return users.contains(u);
    }

    /**
     * Adds a RegisteredUser to the list of users.
     *
     * @param u RegisteredUser to add
     * @return true, if the RegisteredUser is successfully added. Otherwise,
     * returns false
     */
    public boolean addUser(RegisteredUser u) {
        if (u == null || isUserValid(u)) {
            return false;
        }
        return users.add(u);
    }

    /**
     * Removes a RegisteredUser from the list of users.
     *
     * @param u RegisteredUser to remove
     * @return true, if the RegisteredUser is successfully removed. Otherwise,
     * returns false
     */
    public boolean removeUser(RegisteredUser u) {
        if (u == null || !isUserValid(u)) {
            return false;
        }
        return users.remove(u);
    }

    /**
     * Lists the RegisteredUsers and the Manager of the UsersGroup.
     *
     * @return a description of the UsersGroup
     */
    @Override
    public String toString() {
        return String.format("GESTOR RESPONSÁVEL:\n%s\nUSERS:\n%s", manager, Arrays.toString(users.toArray()));
    }

    /**
     * Generates an unique index for the UsersGroup.
     *
     * @return the generated hash value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        return hash;
    }

    /**
     * Compares the UsersGroup with a received Object.
     *
     * @param obj Object to compare
     * @return true, if the two Objects have the same ID. Otherwise, returns
     * false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        UsersGroup other = (UsersGroup) obj;

        return this.users.equals(other.users);
    }
}
