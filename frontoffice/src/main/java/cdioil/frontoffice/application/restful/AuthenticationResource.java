package cdioil.frontoffice.application.restful;

import cdioil.application.authz.AuthenticationController;
import cdioil.application.domain.authz.exceptions.AuthenticationException;
import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Resource class that represents the resource that holds all authentication services
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 5.0 of FeedbackMonkey
 */
@Path(value = "/authenticationresource")
public final class AuthenticationResource {
    /**
     * Constant that represents the JSON used on the response message for warning the user 
     * that his account needs to be activated
     */
    private static final String JSON_ACTIVATION_CODE_REQUIRED="{\n\t\"activationcode\":\"required\"\n}";
    /**
     * Constant that represents the JSON used on the response message for warning the user that 
     * the credentials that were given are invalid
     */
    private static final String JSON_INVALID_CREDENTIALS="{\n\t\"invalidcredentials\":\"true\"\n}";
    /**
     * Constant that represents the JSON used on the response message for warning the user that 
     * the account was activated with success
     */
    private static final String JSON_ACCOUNT_ACTIVATED_SUCCESS="{\n\t\"accountactivated\":\"true\"\n}";
    /**
     * Constant that represents the JSON used on the response message for warning the user that 
     * the account was not activated with success
     */
    private static final String JSON_ACCOUNT_ACTIVATED_FAILURE="{\n\t\"accountactivated\":\"false\"\n}";
    /**
     * Constant that represents the JSON used on the response message for warning the user that 
     * the account has already been activated
     */
    private static final String JSON_ACCOUNT_ALREADY_ACTIVATED="{\n\t\"accountalreadyactivated\":\"true\"\n}";
    /**
     * Logins into frontoffice via a JSON POST Request
     * <br>Resource URI: <code><b>/login</b></code>
     * <br>Method Type: <code><b>POST</b></code>
     * <br>Method Data Type: <code><b>JSON</b></code>
     * <br><b>POST</b> Request Data should be like this
     * <br><br> - {&#09;<br>"email":"youremailhere@email.com",
     * <br>"password":"yourreallysecretpasswordhere"<br>}
     * @param json String with a JSON form with the email and password of the email
     * @return Response with the JSON response with the confirmation if the 
     * user successfuly logged in or not
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(String json){
        UserService userService=new Gson().fromJson(json,UserService.class);
        return createLoginResponse(userService.getEmail(),userService.getPassword());
    }
    /**
     * Activates a certain user account
     * <br>Resource URI: <code><b>/activate</b></code>
     * <br>Method Type: <code><b>POST</b></code>
     * <br>Method Data Type: <code><b>JSON</b></code>
     * <br><b>POST</b> Request Data should be like this
     * <br><br> - {&#09;<br>"email":"youremailhere@email.com",
     * <br>"password":"yourreallysecretpasswordhere",
     * <br>"activationCode":"yourcodehere"}
     * @param jsonActivationCode String with the JSON with the email, password and 
     * activation code of the user that wants to activate his account
     * @return Response with the JSON response with the success about the account activation
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/activate")
    public Response activateAccount(String jsonActivationCode){
        UserService userService=new Gson().fromJson(jsonActivationCode,UserService.class);
        return createActivateAccountResponse(userService.getEmail(),userService.getPassword(),userService.getActivationCode());
    }
    /**
     * Creates a Reponse for the Login Request
     * <br>Check JSON messages for the content that is going to be sent on the response
     * @param email String with the user email
     * @param password String with the user password
     * @return Response with the response given for the credentials given to login
     */
    private Response createLoginResponse(String email,String password){
        try{
            AuthenticationController authenticationController=new AuthenticationController();
            String authenticationToken=null;
            if(authenticationController.login(email,password)){
                authenticationToken=authenticationController.getUserToken();
            }
            return buildAuthenticationResponse(authenticationToken);
        }catch(AuthenticationException |IllegalArgumentException authenticationFailureException){
            return createInvalidAuthenticationResponse(authenticationFailureException);
        }
    }
    /**
     * Creates a Response for the Activate Account Request
     * @param email String with the user email
     * @param password String with the user password
     * @param activationCode String with the account activation code
     * @return Response with the response for the activate account request
     */
    private Response createActivateAccountResponse(String email,String password,String activationCode){
        AuthenticationController authenticationController=new AuthenticationController();
        try{
            return authenticationController.activateAccount(email,password,activationCode)
                    ? Response.status(Status.ACCEPTED).entity(JSON_ACCOUNT_ACTIVATED_SUCCESS).build()
                    : Response.status(Status.UNAUTHORIZED).entity(JSON_ACCOUNT_ACTIVATED_FAILURE).build();
        }catch(AuthenticationException|IllegalArgumentException authenticationFailureException){
            return createInvalidAuthenticationResponse(authenticationFailureException);
            //#TO-DO: Discuss UX in terms of "invalid JSON request formats" and the problem about activating the account multiple times
        }
    }
    /**
     * Creates an Response message with a response message for when the user authentication fails
     * @param exception RuntimeException (AuthenticationException or IllegalArgumentException) with the exception 
     * that ocured on the authentication moment
     * @return Response with the response message for when the user authentication fails
     */
    private Response createInvalidAuthenticationResponse(RuntimeException exception){
        if(exception instanceof IllegalArgumentException
                    ||((AuthenticationException)exception).getAuthenticationExceptionCause().equals(AuthenticationException.
                        AuthenticationExceptionCause.INVALID_CREDENTIALS)){
                return Response.status(Status.UNAUTHORIZED)
                        .entity(JSON_INVALID_CREDENTIALS).build();
            }else{
                return Response.status(Status.UNAUTHORIZED)
                        .entity(JSON_ACTIVATION_CODE_REQUIRED).build();
            }
    }
    /**
     * Method that builds a response regarding the user authentication 
     * @param authenticationToken String with the user authentication token
     * @return Response with the response regarding the user authentication, depending 
     * on the authentication token
     */
    private Response buildAuthenticationResponse(String authenticationToken){
        return authenticationToken!= null 
                ? Response.status(Status.OK).entity(
                    "{\n\t\"Code:\":\""+authenticationToken+"\"\n}").build()
                : Response.status(Status.UNAUTHORIZED).build();
    }
}
