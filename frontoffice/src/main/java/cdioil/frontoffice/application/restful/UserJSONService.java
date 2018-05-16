package cdioil.frontoffice.application.restful;

/**
 * JSON Service that represents the serialization/deserialization of an user
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public final class UserJSONService {
    /**
     * String with the user trying to login email
     */
    private String email;
    /**
     * String with the user trying to login password
     */
    private String password;
    /**
     * String with the user activation code
     */
    private String activationCode;
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
     * Returns the user activation code
     * @return String with the user activation code
     */
    public String getActivationCode(){return activationCode;}
}
