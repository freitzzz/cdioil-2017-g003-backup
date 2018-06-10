package cdioil.feedbackmonkey.restful.utils.json;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName(value = "email",alternate = {"Email","EMAIL"})
    private String email;
    /**
     * String with the user trying to login password
     */
    @SerializedName(value="password",alternate={"Password","PASSWORD"})
    private String password;
    /**
     * String with the user activation code
     */
    @SerializedName(value="activationCode",alternate = {"ActivationCode","activationcode","ACTIVATIONCODE"})
    private String activationCode;
    /**
     * String with the user authentication token
     */
    @SerializedName(value="Code",alternate = {"code","CODE"})
    private String code;

    /**
     * Builds a new UserJSONService with user activation code
     * @param email String with the user email
     * @param password String with the user password
     * @param activationCode String with the user activation code
     */
    public UserJSONService(String email,String password,String activationCode){
        this.email=email;
        this.password=password;
        this.activationCode=activationCode;
    }

    /**
     * Builds a new UserJSONService with the user email & password
     * @param email String with the user email
     * @param password String with the user password
     */
    public UserJSONService(String email,String password){
        this.email=email;
        this.password=password;
    }
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

    /**
     * Returns the user authentication token
     * @return String with the user authentication token
     */
    public String getAuthenticationToken(){return code;}
}