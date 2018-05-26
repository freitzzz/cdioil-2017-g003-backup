package cdioil.frontoffice.application.restful;

import com.google.gson.annotations.SerializedName;

/**
 * JSON Service that represents the serialization/deserialization of a user account 
 * registration form 
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public final class RegistrationJSONService {
    /**
     * String with the user email
     */
    @SerializedName(value = "email",alternate = {"Email","EMAIL"})
    private String email;
    /**
     * String with the user password
     */
    @SerializedName(value="password",alternate={"Password","PASSWORD"})
    private String password;
    /**
     * String with the user name
     */
    @SerializedName(value="name",alternate = {"Name","NAME"})
    private String name;
    /**
     * String with the user phone number
     */
    @SerializedName(value="phoneNumber",alternate = {"PhoneNumber","PHONENUMBER"})
    private String phoneNumber;
    /**
     * String with the user location
     */
    @SerializedName(value="location",alternate = {"Location","LOCATION"})
    private String location;
    /**
     * String with the user birth date
     */
    @SerializedName(value="birthDate",alternate = {"BirthDate","BIRTHDATE"})
    private String birthDate;
    /**
     * Returns the user email
     * @return String with the user email
     */
    public String getEmail(){return email;}
    /**
     * Returns the user password
     * @return String with the user password
     */
    public String getPassword(){return password;}
    /**
     * Returns the user name
     * @return String with the user name
     */
    public String getName(){return name;}
    /**
     * Returns the user phone number
     * @return String with the user phone number
     */
    public String getPhoneNumber(){return phoneNumber;}
    /**
     * Returns the user location
     * @return String with the user location
     */
    public String getLocation(){return location;}
    /**
     * Returns the user birth date
     * @return String with the user birth date
     */
    public String getBirthDate(){return birthDate;}
}
