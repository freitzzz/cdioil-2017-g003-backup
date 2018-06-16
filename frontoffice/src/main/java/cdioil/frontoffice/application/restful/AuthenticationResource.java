package cdioil.frontoffice.application.restful;

import cdioil.frontoffice.application.restful.json.RegistrationJSONService;
import cdioil.frontoffice.application.restful.json.UserJSONService;
import cdioil.frontoffice.application.api.AuthenticationAPI;
import cdioil.application.authz.AuthenticationController;
import cdioil.application.authz.EmailSenderService;
import cdioil.application.domain.authz.exceptions.AuthenticationException;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.SystemUser;
import cdioil.emails.EmailSender;
import cdioil.frontoffice.application.authz.RegisterUserController;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;
import com.google.gson.Gson;
import java.time.format.DateTimeParseException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Resource class that represents the resource that holds all authentication services
 * <br>See <a href="https://bitbucket.org/lei-isep/cdioil-2017-g003/wiki
 * /EngenhariaRequisitos/RestAPI.md">for more info about the request</a>
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
     * <br>Resource URI: <code><b>/register</b></code>
     * <br>Method Type: <code><b>POST</b></code>
     * <br>Method Data Type: <code><b>JSON</b></code>
     * <br><b>POST</b> Request data should have a pair-value for each registration 
     * field
     * @param jsonRegistration String with the register request body
     * @return Response with the response regarding the account registration
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/register")
    @Override
    public Response registerAccount(String jsonRegistration,@QueryParam(value = "validate")boolean validate){
        RegistrationJSONService registrationService=new Gson().fromJson(jsonRegistration,RegistrationJSONService.class);
        return createRegisterAccountResponse(validate,registrationService.getEmail(),registrationService.getPassword()
                ,registrationService.getName(),registrationService.getPhoneNumber(),registrationService.getLocation()
                ,registrationService.getBirthDate());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/requestactivationcode")
    @Override
    public Response requestActivationCode(String jsonEmail) {

        UserJSONService userService = new Gson().fromJson(jsonEmail, UserJSONService.class);
        String email = userService.getEmail();
        
        SystemUser sysUser = new UserRepositoryImpl().findByEmail(new Email(email));

        if (sysUser == null) {
            //create Response informing the user there's no system user with the given email address
            //code 400
            return Response.status(Status.BAD_REQUEST).entity(JSON_INVALID_USER).build();
        }

        //send email
        boolean sentSucessfully = new EmailSenderService(sysUser).sendPasswordResetCode();

        if (!sentSucessfully) {
            //create Response informing the user an error occured while sending activation code email
            //Code 503
            return Response.status(Status.SERVICE_UNAVAILABLE).entity(JSON_ACTIVATION_CODE_DISPATCH_FAILED).build();
        }

        //code 200
        return Response.status(Status.OK).entity(JSON_ACTIVATION_CODE_DISPATCH_SUCESS).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/confirmactivationcode")
    @Override
    public Response confirmActivationCode(String jsonReactivationData) {

        /*Note: While this method could appear redundant, since the changePassword 
        method also requires the activation code, it helps improve the user 
        experience, since it'll be possible to warn the user if the code is valid 
        as soon as they finish typing it in, since the Mobile APP presents
        separate dialogs for user input*/
    
        UserJSONService userService = new Gson().fromJson(jsonReactivationData, UserJSONService.class);
        String email = userService.getEmail();
        String activationCode = userService.getActivationCode();

        SystemUser sysUser = new UserRepositoryImpl().findByEmail(new Email(email));

        if (sysUser == null) {
            //create Response informing the user there's no system user with the given email address
            //code 400
            return Response.status(Status.BAD_REQUEST).entity(JSON_INVALID_USER).build();
        }

        if (!activationCode.equals(sysUser.getActivationCode())) {
            //create Response informing the user there's no system user with the given email address
            //code 400
            return Response.status(Status.BAD_REQUEST).entity(JSON_INVALID_ACTIVATION_CODE).build();
        }

        return Response.status(Status.OK).entity(JSON_VALID_ACTIVATION_CODE).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/changepassword")
    @Override
    public Response changePassword(String jsonChangePasswordData) {

        /*Note: This method also uses the activation code in order to prevent 
        other users from changing a user's password*/
        
        UserJSONService userService = new Gson().fromJson(jsonChangePasswordData, UserJSONService.class);

        String email = userService.getEmail();
        String activationCode = userService.getActivationCode();
        String passwordText = userService.getPassword();

        SystemUser sysUser = new UserRepositoryImpl().findByEmail(new Email(email));

        if (sysUser == null) {
            //create Response informing the user there's no system user with the given email address
            //code 400
            return Response.status(Status.BAD_REQUEST).entity(JSON_INVALID_USER).build();
        }

        if (!activationCode.equals(sysUser.getActivationCode())) {
            //create Response informing the user there's no system user with the given email address
            //code 400
            return Response.status(Status.BAD_REQUEST).entity(JSON_INVALID_ACTIVATION_CODE).build();
        }

        boolean changed = sysUser.changeUserDatafield(passwordText, SystemUser.CHANGE_PASSWORD_OPTION);

        if (!changed) {
            //create Response informing the user that data was unable to be changed
            //code 400
            return Response.status(Status.BAD_REQUEST).entity(JSON_PASSWORD_CHANGE_FAILED).build();
        }

        //update system user with new data
        new UserRepositoryImpl().merge(sysUser);
        
        return Response.status(Status.OK).entity(JSON_PASSWORD_CHANGE_SUCCESS).build();
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
    private Response createRegisterAccountResponse(boolean validate,String... registerForms){
        try{
           RegisterUserController controller=new RegisterUserController(registerForms);
           if(validate)return Response.status(Status.OK).entity(JSON_VALID_REGISTRATION_FORM).build();
           controller.registerUser();
        }catch(IllegalArgumentException|IllegalStateException|DateTimeParseException registerException){
           return Response.status(Status.BAD_REQUEST)
                    .entity(new Gson()
                            .toJson(new RegistrationJSONService(registerException.getMessage()
                                    ,registerException.getCause().getMessage())))
                   .build();
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
            }else if(((AuthenticationException)exception).getAuthenticationExceptionCause().equals(AuthenticationException.AuthenticationExceptionCause.ACCOUNT_LOCKED)){
                return Response.status(Status.FORBIDDEN)
                        .entity(JSON_ACCOUNT_LOCKED).build();
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
