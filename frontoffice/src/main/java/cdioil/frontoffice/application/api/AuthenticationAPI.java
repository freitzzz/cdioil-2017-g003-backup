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
     * @param validate Boolean if the registration is to be validated instead of 
     * register
     * @return Response with the response regarding the account registration
     */
    public Response registerAccount(String requestBody,boolean validate);

    /**
     * Requests that an email be sent to an account's email address using the
     * FeedbackMonkeyAPI.
     *
     * @param requestBody String representing the request's body with the user's
     * email address
     * @return Response informing the user of the request's status
     */
    public Response requestActivationCode(String requestBody);

    /**
     * Checks if the request's body information regarding the account activation
     * code matches with the user's actual activation code using the
     * FeedbackMonkeyAPI.
     *
     * @param requestBody String representing the request's body containing the
     * user's email address and account activation code
     * @return Response informing the user of the request's status
     */
    public Response confirmActivationCode(String requestBody);

    /**
     * Changes the user's password using the FeedbackMonkeyAPI.
     *
     * @param requestBody String representing the request's body containing the
     * user's email address, activation code and new password
     * @return Response informing the user of the request's status
     */
    public Response changePassword(String requestBody);
}
