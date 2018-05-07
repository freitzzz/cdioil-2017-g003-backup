package cdioil.frontoffice.application.restful;

/**
 * Service that represents a user that is trying to log in
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public final class UserService {
    /**
     * String with the user trying to login email
     */
    private String email;
    /**
     * String with the user trying to login password
     */
    private String password;
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
}
