package cdioil.feedbackmonkey.restful.utils.json;

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
     * User's name.
     *
     * @return user's name
     */
    public String getName(){
        return name;
    }
    /**
     * User's location.
     *
     * @return user's location.
     */
    public String getLocation(){
        return location;
    }
    /**
     * User's age.
     *
     * @return user's age.
     */
    public String getAge(){
        return age;
    }
}
