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
            AuthenticationController authenticationController=new AuthenticationController();
            String authenticationToken=null;
            if(authenticationController.login(userService.getEmail(),userService.getPassword())){
                authenticationToken=authenticationController.getUserToken();
            }
            return buildAuthenticationResponse(authenticationToken);
        }catch(AuthenticationException e){
            return Response.status(Status.UNAUTHORIZED).build();
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
