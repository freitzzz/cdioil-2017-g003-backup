package cdioil.domain.authz;

import cdioil.framework.domain.ddd.AggregateRoot;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Represents an administrator of the application.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Table(name="ADMINISTRATOR", uniqueConstraints = @UniqueConstraint(columnNames = {"SYSTEMUSER_ID"}))
public class Admin implements Serializable,AggregateRoot<SystemUser>,User{
    @Id
    @Column(name = "ADMINISTRATOR_ID")
    @GeneratedValue
    private long id;
    private static final long serialVersionUID = 1L;
    
    @Version
    private Long version;

    /**
     * SystemUser associated with the admin.
     */
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "SYSTEMUSER_ID")
    private SystemUser sysUser;

    /**
     * Builds an Admin with a SystemUser.
     *
     * @param sysUser SystemUser to be associated with the admin
     */
    public Admin(SystemUser sysUser) {
        if (sysUser == null) {
            throw new IllegalArgumentException("O utilizador atribuido ao admin "
                    + "não deve ser null.");
        }
        this.sysUser = sysUser;
    }

    protected Admin() {
        //Para ORM
    }

    /**
     * Admin's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return sysUser.hashCode();
    }

    /**
     * Checks if two instances of Admin are the same by comparing
     * their SystemUsers.
     *
     * @param obj object to be compared
     * @return true if it's the same admin, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Admin)) {
            return false;
        }
        final Admin other = (Admin) obj;
        return this.sysUser.equals(other.sysUser);
    }

    /**
     * Returns a description of an admin
     *
     * @return description of an admin
     */
    @Override
    public String toString() {
        return this.sysUser.toString();
    }

    @Override
    public SystemUser getID(){
        return sysUser;
    }

}
