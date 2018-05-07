package cdioil.application.domain.authz;

import cdioil.domain.authz.BirthDate;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Location;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.PhoneNumber;
import cdioil.domain.authz.SystemUser;
import java.time.LocalDate;

/**
 * Builder class that builds a new SystemUser
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
public final class SystemUserBuilder {
    /**
     * Current SystemUser being built email
     */
    private Email email;
    /**
     * Current SystemUser being built password
     */
    private Password password;
    /**
     * Current SystemUser being built first name
     */
    private Name name;
    /**
     * Current SystemUser being built phone number
     */
    private PhoneNumber phoneNumber;
    /**
     * Current SystemUser being built location
     */
    private Location location;
    /**
     * Current SystemUser being built email
     */
    private BirthDate birthDate;
    /**
     * Hides default constructor
     */
    private SystemUserBuilder(){}
    /**
     * Creates a new SystemUser builder
     * @return SystemUserBuilder with the new SystemUser builder
     */
    public static SystemUserBuilder create(){return new SystemUserBuilder();}
    /**
     * Builds and creates a new SystemUser with the previous built properties
     * @return SystemUser with the preivous built properties
     */
    public SystemUser build(){
        return new SystemUser(email,name,password,phoneNumber,location,birthDate);
    }
    /**
     * Adds a certain email to the SystemUser being build properties
     * @param email String with the user email
     * @return SystemUserBuilder with the builder updated with the new SystemUser email
     */
    public SystemUserBuilder withEmail(String email){
        this.email=new Email(email);
        return this;
    }
    /**
     * Adds a certain password to the SystemUser being build properties
     * @param password String with the user password
     * @return SystemUserBuilder with the builder updated with the new SystemUser password
     */
    public SystemUserBuilder withPassword(String password){
        this.password=new Password(password);
        return this;
    }
    /**
     * Adds a certain name to the SystemUser being build properties
     * @param firstName String with the user first name
     * @param lastName String with the user last name
     * @return SystemUserBuilder with the builder updated with the new SystemUser name
     */
    public SystemUserBuilder withName(String firstName,String lastName){
        this.name=new Name(firstName,lastName);
        return this;
    }
    /**
     * Adds a certain phone number to the SystemUser being build properties
     * @param phoneNumber String with the user phone number
     * @return SystemUserBuilder with the builder updated with the new SystemUser phone number
     */
    public SystemUserBuilder withPhoneNumber(String phoneNumber){
        this.phoneNumber=new PhoneNumber(phoneNumber);
        return this;
    }
    /**
     * Adds a certain location to the SystemUser being build properties
     * @param location String with the user location
     * @return SystemUserBuilder with the builder updated with the new SystemUser location
     */
    public SystemUserBuilder withLocation(String location){
        this.location=new Location(location);
        return this;
    }
    /**
     * Adds a certain birth date to the SystemUser being build properties
     * @param birthDate String with the user birth date
     * @return SystemUserBuilder with the builder updated with the new SystemUser date birth
     */
    public SystemUserBuilder withBirthDate(String birthDate){
        this.birthDate=new BirthDate(LocalDate.parse(birthDate));
        return this;
    }
}
