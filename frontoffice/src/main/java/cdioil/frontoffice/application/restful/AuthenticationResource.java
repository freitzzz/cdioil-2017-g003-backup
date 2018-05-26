package cdioil.frontoffice.application.restful;

import cdioil.frontoffice.application.api.AuthenticationAPI;
import cdioil.application.authz.AuthenticationController;
import cdioil.application.domain.authz.exceptions.AuthenticationException;
import cdioil.frontoffice.application.authz.RegisterUserController;
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
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @since Version 5.0 of FeedbackMonkey
 */
@Path(value = "/authentication")
public final class AuthenticationResource implements AuthenticationAPI, ResponseMessages{

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
     * user successfully logged in or not
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    @Override
    public Response login(String json){
        UserJSONService userService=new Gson().fromJson(json,UserJSONService.class);
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
    @Override
    public Response activateAccount(String jsonActivationCode){
        UserJSONService userService=new Gson().fromJson(jsonActivationCode,UserJSONService.class);
        return createActivateAccountResponse(userService.getEmail(),userService.getPassword(),userService.getActivationCode());
    }
    /**
     * Registers an account using the FeedbackMonkey API
     * @param jsonRegistration String with the register request body
     * @return Response with the response regarding the account registration
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/register")
    @Override
    public Response registerAccount(String jsonRegistration){
        RegistrationJSONService registrationService=new Gson().fromJson(jsonRegistration,RegistrationJSONService.class);
        return createRegisterAccountResponse(registrationService.getEmail(),registrationService.getPassword()
                ,registrationService.getName(),registrationService.getPhoneNumber(),registrationService.getLocation()
                ,registrationService.getBirthDate());
    }
    /**
     * Creates a Response for the Login Request
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
                    ? Response.status(Status.OK).entity(JSON_ACCOUNT_ACTIVATED_SUCCESS).build()
                    : Response.status(Status.UNAUTHORIZED).entity(JSON_ACCOUNT_ACTIVATED_FAILURE).build();
        }catch(AuthenticationException|IllegalArgumentException authenticationFailureException){
            return createInvalidAuthenticationResponse(authenticationFailureException);
            //#TO-DO: Discuss UX in terms of "invalid JSON request formats"
        }
    }
    /**
     * Creates a Response for the Register Account request
     * @param registerForms Array of Strings with the registration form
     * @return Response with the response for the register account request
     */
    private Response createRegisterAccountResponse(String... registerForms){
        try{
            new RegisterUserController(registerForms).registerUser();
        }catch(IllegalArgumentException|IllegalStateException|IndexOutOfBoundsException registerException){
           return Response.status(Status.BAD_REQUEST).entity(registerException.getMessage()).build();
        }
        return Response.status(Status.OK).entity(JSON_ACCOUNT_CREATED_WITH_SUCCESS).build();
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
            }else if(((AuthenticationException)exception).getAuthenticationExceptionCause().equals(AuthenticationException.AuthenticationExceptionCause.ALREADY_ACTIVATED)){
                return Response.status(Status.UNAUTHORIZED)
                        .entity(JSON_ACCOUNT_ALREADY_ACTIVATED).build();
            }else{
                return Response.status(Status.BAD_REQUEST)
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
                    "{\n\t\"Code\":\""+authenticationToken+"\"\n}").build()
                : Response.status(Status.UNAUTHORIZED).build();
    }
}
