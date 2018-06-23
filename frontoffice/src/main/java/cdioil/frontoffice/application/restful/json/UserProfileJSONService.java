package cdioil.frontoffice.application.restful.json;

import com.google.gson.annotations.SerializedName;

/**
 * JSON Service that represents the serialization/deserialization of info that must be show on the user's profile
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @since Version 7.0 of FeedbackMonkey
 */
public class UserProfileJSONService {
    /**
     * User's first name.
     */
    @SerializedName(value = "name",alternate = {"NAME","Name"})
    private String name;
    /**
     * User's location.
     */
    @SerializedName(value = "location", alternate = {"LOCATION","Location"})
    private String location;
    /**
     * User's age.
     */
    @SerializedName(value = "age", alternate = {"AGE","Age"})
    private String age;
    /**
     * Builds a UserProfileJSONService receiving a user's first and last name, location and age
     * 
     * @param name user's name
     * @param location user's location
     * @param age user's age
     */
    public UserProfileJSONService(String name, String location, String age){
        this.name = name;
        this.location = location;
        this.age = age;
    }
}
