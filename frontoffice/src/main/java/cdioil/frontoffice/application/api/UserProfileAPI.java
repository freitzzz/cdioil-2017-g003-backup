package cdioil.frontoffice.application.api;

import javax.ws.rs.core.Response;

/**
 * Interface for the FeedbackMonkey UserProfile API.
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
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
}
