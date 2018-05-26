package cdioil.frontoffice.application.api;

import javax.ws.rs.core.Response;

/**
 * Interface that represents the FeedbackMonkey Authentication API
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public interface AuthenticationAPI {
    /**
     * Logins into the FeedbackMonkey API
     * @param requestBody String with the login request body
     * @return Response with the response regarding the login
     */
    public Response login(String requestBody);
    /**
     * Activates an account using the FeedbackMonkey API
     * @param requestBody String with the login request body
     * @return Response with the response regarding the account activation
     */
    public Response activateAccount(String requestBody);
    /**
     * Registers an account using the FeedbackMonkey API
     * @param requestBody String with the register request body
     * @return Response with the response regarding the account registration
     */
    public Response registerAccount(String requestBody);
}
