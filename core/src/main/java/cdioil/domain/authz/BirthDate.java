package cdioil.domain.authz;

import cdioil.framework.domain.ddd.ValueObject;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Embeddable;

/**
 * Represents the birth date of a system user.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Embeddable
public class BirthDate implements ValueObject, Serializable {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * User's birth date.
     */
    //The NOSONAR comment below is due to LocalDateTime not being serializable in JPA 2.1
    private LocalDate dateOfBirth; //NOSONAR

    /**
     * Builds a birth date instance using a LocalDate object
     *
     * @param birthDate system user's birth date
     */
    public BirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("A data de nascimento não pode "
                    + "ser null",new Throwable(this.getClass().getSimpleName()));
        }
        this.dateOfBirth = birthDate;
    }

    /**
     * Empty constructor for JPA.
     */
    protected BirthDate() {
    }

    /**
     * BirthDate's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return dateOfBirth.hashCode();
    }

    /**
     * Checks if two BirthDates are equal.
     *
     * @param obj object to be compared
     * @return true if they are equal, false if otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BirthDate)) {
            return false;
        }
        final BirthDate other = (BirthDate) obj;
        return this.dateOfBirth.equals(other.dateOfBirth);
    }

    /**
     * Returns the date in a string format.
     *
     * @return string containing the birth date
     */
    @Override
    public String toString() {
        return dateOfBirth.toString();
    }
}
