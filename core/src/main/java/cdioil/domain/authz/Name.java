package cdioil.domain.authz;

import cdioil.framework.domain.ddd.ValueObject;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;

/**
 * Represents a users name.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Embeddable
public class Name implements Serializable, ValueObject {

    private static final long serialVersionUID = 1L;

    /**
     * Regex for a valid name.
     */
    public static final Pattern VALID_NAME_REGEX = Pattern.compile("^([A-Z]+[a-z"
            + "A-Z ]+|\\p{L}+)$", Pattern.CASE_INSENSITIVE);

    /**
     * User's first name.
     */
    private String firstName;
    /**
     * User's surname.
     */
    private String surname;

    /**
     * Builds an instance of Name with a first name and a surname.
     *
     * @param firstName user's first name
     * @param surname user's surname
     */
    public Name(String firstName, String surname) {
        if (firstName == null || surname == null || firstName.isEmpty() || surname.isEmpty()) {
            throw new IllegalArgumentException("O primeiro nome e o apelido não devem ser vazios.");
        }

        Matcher matcher = VALID_NAME_REGEX.matcher(firstName);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Primeiro nome inválido: " + firstName);
        }

        matcher = VALID_NAME_REGEX.matcher(surname);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Apelido inválido: " + surname);
        }

        this.firstName = firstName;
        this.surname = surname;
    }

    protected Name() {
        //For ORM
    }

    /**
     * Name's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return firstName.hashCode() + surname.hashCode();
    }

    /**
     * Checks if two instances of Name are the same.
     *
     * @param obj object to be compared
     * @return true if they're the same, false if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Name)) {
            return false;
        }
        final Name other = (Name) obj;
        if (!this.firstName.equals(other.firstName)) {
            return false;
        }
        return this.surname.equals(other.surname);
    }

    /**
     * Returns a user's description.
     *
     * @return user's full name (first name + surname)
     */
    @Override
    public String toString() {
        return firstName + " " + surname;
    }
}
