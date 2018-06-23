package cdioil.frontoffice.application.api;

import javax.ws.rs.core.Response;

/**
 * Interface for the FeedbackMonkey UserProfile API.
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @since version 7.0 of FeedBackMonkey
 */
public interface UserProfileAPI {
    /**
     * Produces an HTTP Response indicating whether the user's suggestion (with an image)
     * was saved or not.
     * 
     * @param authenticationToken user's authenticationToken
     * @param suggestionAsJSON String that holds a Suggestion in JSON format
     * @param hasImage flag to know if the suggestion has an image or not
     * @return HTTP Response
     */
    public Response saveSuggestion(String authenticationToken,
            String suggestionAsJSON, boolean hasImage);
    
    /**
     * Produces an HTTP Response containing the user's info (name, age, location, etc.)
     * 
     * @param authenticationToken user's authentication token
     * @return HTTP Response
     */
    public Response getUserInfo(String authenticationToken);
}
