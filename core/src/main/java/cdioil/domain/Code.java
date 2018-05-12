package cdioil.domain;

import cdioil.framework.domain.ddd.ValueObject;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Abstract class for a Product's productCode.
 *
 * @author António Sousa [1161371]
 * @param <T> type of productCode
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Code<T extends Serializable> implements Serializable, ValueObject {

    /**
     * Serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Auto-generated primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * The productCode's value.
     */
    @Column(unique = true)
    protected T productCode;
    
    /**
     * Empty constructor for JPA.
     */
    protected Code() {
    }

    /**
     * Returns the instance's hash.
     * @return the hash value
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.productCode) + Objects.hashCode(this.getClass());
        /*Uma vez que isto será usado por todas as subclasses, o tipo de classe deverá contribuir para um hash diferente*/
        return hash;
    }

    /**
     * Checks for true equality with another object based on the productCode's value.
     * @param obj object to compare to
     * @return true if the objects are truly equal, false otherwise
     */
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
        final Code other = (Code) obj;
        
        return Objects.equals(this.productCode, other.productCode);
    }

    /**
     * Describes the instance based on its productCode.
     *
     * @return a textual representation of this instance.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + productCode.toString() + "\n";
    }

}
