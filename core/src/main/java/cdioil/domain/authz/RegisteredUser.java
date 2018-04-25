package cdioil.domain.authz;

import cdioil.framework.domain.ddd.AggregateRoot;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Represents a registered user of the app.
 *
 */
@Entity
public class RegisteredUser implements Serializable, AggregateRoot<SystemUser>, User {

    @Id
    @GeneratedValue
    @Column(name = "REGISTEREDUSER_ID")
    private long id;
    @Version
    private Long version;
    /**
     * SystemUser account associated to the registered user.
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "SYSTEMUSER")
    private SystemUser sysUser;
    /**
     * The registered user's profile.
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Profile profile;

    /**
     * Builds a new RegisteredUser
     *
     * @param sysUser SystemUser account to associate to
     */
    public RegisteredUser(SystemUser sysUser) {
        if (sysUser == null) {
            throw new IllegalArgumentException("Instância de SystemUser atribuida é null");
        }
        this.sysUser = sysUser;
        this.profile = new Profile();
    }

    /**
     * Checks if two instances of RegisteredUser are the same
     *
     * @param obj object to be compared to
     * @return true if they're equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        RegisteredUser that = (RegisteredUser) obj;

        return sysUser.equals(that.sysUser);
    }

    /**
     * Returns a text description of the user
     *
     * @return SystemUser's toString
     */
    @Override
    public String toString() {
        return sysUser.toString();
    }

    /**
     * RegisteredUser's hashCode
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return sysUser.hashCode();
    }

    /**
     * Empty constructor for JPA.
     */
    protected RegisteredUser() {
    }

    /**
     * Returns the identity of the registered user.
     *
     * @return system user associated to the registered user
     */
    @Override
    public SystemUser getID() {
        return sysUser;
    }
}
