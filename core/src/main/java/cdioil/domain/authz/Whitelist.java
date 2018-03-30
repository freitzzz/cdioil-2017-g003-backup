package cdioil.domain.authz;

import cdioil.persistence.Identifiable;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Whitelist class that represents an authorized domain/subdomain
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
@Entity
public class Whitelist implements Serializable, Identifiable<String> {
    /**
     * Serializable ID
     */
    private static final long serialVersionUID = 5L;
    
    /**
     * String that repesents the authorized
     */
    @Id
    @Column(name="WHITELISTED_DOMAIN")
    private String domain;

    /**
     * Builds a new instance of Whitelist with an authorized domain
     * @param domain String with the authorized domain
     */
    public Whitelist(String domain){
        this.domain=domain.toLowerCase();
    }
    /**
     * Method that verifies if two authorized domains/subdomains are equal
     * @param obj Whitelist with the authorized domain to be compared with the actual one
     * @return boolean true if domains are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return domain.equals(((Whitelist) obj).domain);
    }

    /**
     * Whitelist hashcode
     * @return Integer with the current Whitelist hashcode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.domain);
        return hash;
    }

    /**
     *Method that represents the textual representation of a Whitelist
     *
     * @return String with the textual information of the current whitelist
     */
    @Override
    public String toString() {
        return "Domain: " + domain;
    }

    /**
     * Proctected constructor in order to persist with JPA
     */
    protected Whitelist() {}

    /**
     * Returns the entity's identity.
     * @return whitelisted domain
     */
    @Override
    public String getID() {
       return this.domain;
    }
}
