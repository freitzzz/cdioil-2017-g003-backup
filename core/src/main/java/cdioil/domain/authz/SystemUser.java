package cdioil.domain.authz;

import cdioil.framework.domain.ddd.AggregateRoot;
import cdioil.framework.dto.DTOable;
import cdioil.framework.dto.SystemUserDTO;

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
public class SystemUser implements DTOable, Serializable, AggregateRoot<Email> {

    /**
     * Constant that represents the number of digits of the activation code
     */
    private static final short ACTIVATION_CODE_DIGITS = 4;
    /**
     * Constant that represents all digits in a plain String
     */
    private static final String ALL_DIGITS = "0123456789";
    /**
     * Constant that represents the change name option
     */
    public static final int CHANGE_NAME_OPTION = 1;
    /**
     * Constant that represents the change email option
     */
    public static final int CHANGE_EMAIL_OPTION = 2;
    /**
     * Constant that represents the change password option
     */
    public static final int CHANGE_PASSWORD_OPTION = 3;
    /**
     * Constant that represents the change phone number option
     */
    public static final int CHANGE_PHONE_NUMBER_OPTION = 4;
    /**
     * Constant that represents the change locality option
     */
    public static final int CHANGE_LOCALITY_OPTION = 5;
    /**
     * Constant that represents the change birth date option
     */
    public static final int CHANGE_BIRTH_DATE_OPTION = 6;
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
    private Name name;
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
    @Column(nullable = true)
    private Location location;
    /**
     * User's birth date
     */
    @Embedded
    @Column(nullable = true)
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
     * String with the user activation code
     */
    private String activationCode;

    /**
     * Builds a SystemUser instance with an email, name and password, phone number, location and birth date
     *
     * @param email user's email
     * @param name user's name
     * @param password user's password
     * @param phoneNumber PhoneNumber with the user phone number
     * @param location Location with the user location
     * @param birthDate BirthDate with the user birth date
     */
    public SystemUser(Email email, Name name, Password password, PhoneNumber phoneNumber,
            Location location, BirthDate birthDate) {
        if (email == null) {
            throw new IllegalArgumentException("O email não pode ser null!");
        }
        if (password == null) {
            throw new IllegalArgumentException("A password não pode ser null!");
        }
        if (name == null) {
            throw new IllegalArgumentException("O nome não pode ser null!");
        }
        if (phoneNumber == null) {
            throw new IllegalArgumentException("O número de telemóvel não pode ser null!");
        }
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.birthDate = birthDate;
        this.activationCode = generateRandomCode();
    }

    /**
     * Builds a SystemUser instance with an email, name and password
     * <br>This constructor is used for the construction of imported users
     *
     * @param email user's email
     * @param nome user's name
     * @param password user's password
     */
    public SystemUser(Email email, Name nome, Password password) {
        this.email = email;
        this.name = nome;
        this.password = password;
        this.imported = true;
        this.activationCode = generateRandomCode();
    }

    protected SystemUser() {
        //For ORM
    }

    /**
     * Activates the current system user
     *
     * @param activationCode String with the activation code
     * @return boolean treu if the account was activated with success, false if not
     */
    public boolean activateAccount(String activationCode) {
        if (this.activationCode.equals(activationCode)) {
            this.activated = true;
            return true;
        }
        return false;
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
        return "Nome: " + name + "\nEmail: " + email + "\n";
    }

    /**
     * Changes a property of a SystemUser (email, name or password)
     *
     * @param newField info given by the user
     * @param option integer that indicates which property is going to be updated
     * @return true if the update was successful, false if the new info was not valid
     */
    public boolean changeUserDatafield(String newField, int option) {
        try {
            switch (option) {
                case CHANGE_NAME_OPTION://changes the user's name
                    changeName(newField);
                    break;
                case CHANGE_EMAIL_OPTION://changes the user's email
                    Email newEmail = new Email(newField);
                    this.email = newEmail;
                    break;
                case CHANGE_PASSWORD_OPTION://changes the user's password
                    Password newPassword = new Password(newField);
                    this.password = newPassword;
                    break;
                case CHANGE_PHONE_NUMBER_OPTION:
                    this.phoneNumber = new PhoneNumber(newField);
                    break;
                case CHANGE_LOCALITY_OPTION:
                    this.location = new Location(newField);
                    break;
                case CHANGE_BIRTH_DATE_OPTION:
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
     * Changes the name of the SystemUser.
     *
     * @param newName New name
     */
    private void changeName(String newName) {
        String[] newNome = newName.split(" ");
        if (newNome.length != 2) {
            throw new IllegalArgumentException();
        }
        this.name = new Name(newNome[0], newNome[1]);
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
     *
     * @return String with the generated random code for the current user
     */
    public String getActivationCode() {
        return activationCode;
    }

    /**
     * Method that returns the current user name
     *
     * @return Name with the current user name
     */
    public Name getName() {
        return name;
    }

    /**
     * Method that merges a current imported SystemUser with a certain SystemUser
     *
     * @param user SystemUser with the imported previously imported SystemUser
     * @return boolean true if the imported SystemUser was merged with success, false if not
     */
    public boolean mergeImportedSystemUser(SystemUser user) {
        if (!imported) {
            return false;
        }
        this.password = user.password;
        this.phoneNumber = user.phoneNumber;
        this.location = user.location;
        this.birthDate = user.birthDate;
        return true;
    }

    /**
     * Method that checks if the current user was previously imported or not
     *
     * @return boolean true if the user was previously imported, false if not
     */
    public boolean isUserImported() {
        return imported;
    }

    /**
     * Method that checks if the current user is activated
     *
     * @return boolean true if the user is activated, false if not
     */
    public boolean isUserActivated() {
        return activated;
    }

    /**
     * Method that generates a random code used to prove user authenticity
     *
     * @return String with the generated random code used to prove user authenticity
     */
    private String generateRandomCode() {
        StringBuilder randomCode = new StringBuilder(40);
        for (int i = 0; i < ACTIVATION_CODE_DIGITS; i++) {
            randomCode.append(Character.toString(ALL_DIGITS.charAt(new Random().nextInt(ALL_DIGITS.length()))));
        }

        return randomCode.toString();
    }

    /**
     * Creates a Data Transfer Object with name and email
     *
     * @return GenericDTO
     */
    @Override
    public SystemUserDTO toDTO() {
        String[] fullName = name.toString().split(" ");
        final String firstName = fullName[0];
        StringBuilder lastName = new StringBuilder(128);

        for (int i = 1; i < fullName.length; i++) {
            lastName.append(fullName[i]).append(" ");
        }

        return new SystemUserDTO("systemUser", firstName, lastName.toString(), email.toString());
    }
}
