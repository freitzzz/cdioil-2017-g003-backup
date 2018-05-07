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

/**
 * Resource class that represents the resource that holds all authentication services
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 5.0 of FeedbackMonkey
 */
@Path(value = "/authenticationresource")
public final class AuthenticationResource {
    /**
     * Constant that represents the success code (200)
     */
    private static final short SUCCESS_CODE=200;
    /**
     * Constant that represents the failure code (400)
     */
    private static final short FAILURE_CODE=400;
    /**
     * Logins into frontoffice via a JSON POST Request
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
        try{
            new AuthenticationController().login(userService.getEmail(),userService.getPassword());//Temporary
        }catch(AuthenticationException e){
            return Response.status(FAILURE_CODE).build();
        }
        return Response.status(SUCCESS_CODE).build();
    }
}
