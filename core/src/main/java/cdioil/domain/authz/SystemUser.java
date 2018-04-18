package cdioil.domain.authz;

import cdioil.framework.domain.ddd.AggregateRoot;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;
import javax.persistence.*;

/**
 * Represents the users of the app.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "EMAIL"))
public class SystemUser implements Serializable, AggregateRoot<Email> {

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
     * User's Phone Number
     */
    @Embedded
    @Column(unique = true)
    private PhoneNumber phoneNumber;
    /**
     * User's Location
     */
    @Embedded
    private Location location;
    /**
     * User's birth date
     */
    @Embedded
    private BirthDate birthDate;
    /**
     * Boolean that represents if the User's account is activated or not
     */
    private boolean activated;
    /**
     * Boolean that represents if the User's account is imported or not
     */
    private boolean imported;
    /**
     * Long with the user activation code
     */
    private long activationCode;

    /**
     * Builds a SystemUser instance with an email, name and password, phone number, 
     * location and birth date
     *
     * @param email user's email
     * @param nome user's name
     * @param password user's password
     * @param phoneNumber PhoneNumber with the user phone number
     * @param location Location with the user location
     * @param birthDate BirthDate with the user birth date
     */
    public SystemUser(Email email, Name nome, Password password, PhoneNumber phoneNumber,
             Location location, BirthDate birthDate) {
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.activationCode=generateRandomCode();
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.birthDate = birthDate;
    }

    /**
     * Builds a SystemUser instance with an email, name and password
     * <br>This constructor is used for the construction of imported users
     * @param email user's email
     * @param nome user's name
     * @param password user's password
     */
    public SystemUser(Email email, Name nome, Password password) {
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.imported=true;
        this.activationCode=generateRandomCode();
    }

    protected SystemUser() {
        //For ORM
    }
    
    /**
     * Activates the current system user
     */
    public void activateAccount() {
        this.activated = true;
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
    //TO-DO: ADD REMOVE TOSTRING ADD DTO
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
                    String[] newNome = newField.split(" ");
                    if (newNome.length != 2) {
                        throw new IllegalArgumentException();
                    }
                    this.nome = new Name(newNome[0], newNome[1]);
                    break;
                case 2://changes the user's email
                    Email newEmail = new Email(newField);
                    this.email = newEmail;
                    break;
                case 3://changes the user's password
                    Password newPassword = new Password(newField);
                    this.password = newPassword;
                    break;
                case 4:
                    this.phoneNumber = new PhoneNumber(newField);
                    break;
                case 5:
                    this.location = new Location(newField);
                    break;
                case 6:
                    this.birthDate = new BirthDate(LocalDate.parse(newField));
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
    /**
     * Method that returns the generated random code for the current user
     * @return Long with the generated random code for the current user
     */
    public long getActivationCode(){return activationCode;}
    /**
     * Method that returns the current user name
     * @return Name with the current user name
     */
    public Name getName(){return nome;}
    /**
     * Method that generates a random code used to prove user authenticity
     * @return Long with the generated random code used to prove user authenticity
     */
    private long generateRandomCode(){return Math.abs(new Random().nextLong());}

}
