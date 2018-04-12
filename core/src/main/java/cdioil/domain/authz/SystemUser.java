package cdioil.domain.authz;

import cdioil.framework.domain.Identifiable;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Represents the users of the app.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "EMAIL"))
public class SystemUser implements Serializable, Identifiable<Email> {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;
    @Version
    private Long version;
    /**
     * ID of the SystemUser for JPA.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SYSTEMUSER_ID")
    private Long id;

    /**
     * User's email.
     */
    @Embedded
    @Column(unique = true)
    private Email email;
    /**
     * User's name.
     */
    @Embedded
    private Name nome;
    /**
     * User's password.
     */
    @Embedded
    private Password password;

    /**
     * Builds a SystemUser instance with an email, name and password
     *
     * @param email user's email
     * @param nome user's name
     * @param password user's password
     */
    public SystemUser(Email email, Name nome, Password password) {
        this.email = email;
        this.nome = nome;
        this.password = password;
    }

    protected SystemUser() {
        //For ORM
    }

    /**
     * Checks if a given password matches the user's password
     *
     * @param password given pwd
     * @return true if they match, false if they don't match
     */
    public boolean samePassword(String password) {
        return this.password.verifyPassword(password);
    }

    /**
     * SystemUser's hash code.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return this.email.hashCode();
    }

    /**
     * Checks if two SystemUser instances are the same comparing their emails.
     *
     * @param obj object to be compared
     * @return true if it's the same user, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SystemUser)) {
            return false;
        }
        final SystemUser other = (SystemUser) obj;
        return this.email.equals(other.email);
    }

    /**
     * Returns a description of a user (name + email)
     *
     * @return user's description
     */
    @Override
    public String toString() {
        return "Nome: " + nome + "\nEmail: " + email + "\n";
    }

    /**
     * Changes a property of a SystemUser (email, name or password)
     *
     * @param newField info given by the user
     * @param option integer that indicates which property is going to be
     * updated
     * @return true if the update was successful, false if the new info was not
     * valid
     */
    public boolean changeUserDatafield(String newField, int option) {
        try {
            switch (option) {
                case 1://changes the user's name
                    String[] nome = newField.split(" ");
                    if (nome.length != 2) {
                        throw new IllegalArgumentException();
                    }
                    this.nome = new Name(nome[0], nome[1]);
                    break;
                case 2://changes the user's email
                    Email email = new Email(newField);
                    this.email = email;
                    break;
                case 3://changes the user's password
                    Password password = new Password(newField);
                    this.password = password;
                    break;
                default:
                    break;
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the user's ID (email)
     *
     * @return email
     */
    @Override
    public Email getID() {
        return email;
    }
    
}
