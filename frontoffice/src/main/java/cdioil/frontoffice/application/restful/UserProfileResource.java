package cdioil.frontoffice.application.restful;

import cdioil.application.authz.AuthenticationController;
import cdioil.domain.Image;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.Suggestion;
import cdioil.domain.authz.SystemUser;
import cdioil.frontoffice.application.api.UserProfileAPI;
import static cdioil.frontoffice.application.restful.ResponseMessages.JSON_INVALID_AUTHENTICATION_TOKEN;
import static cdioil.frontoffice.application.restful.ResponseMessages.JSON_INVALID_USER;
import cdioil.frontoffice.application.restful.json.SuggestionJSONService;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Base64;

/**
 * Resource class that represents the resource that holds all profile services
 * <br>See <a href="https://bitbucket.org/lei-isep/cdioil-2017-g003/wiki
 * /EngenhariaRequisitos/RestAPI.md">for more info about the resource</a>
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 * @since Version 7.0 of FeedbackMonkey
 */
@Path("/userprofile")
public class UserProfileResource implements UserProfileAPI, ResponseMessages{
    /**
     * Produces an HTTP Response indicating whether the user's suggestion (with an image)
     * was saved or not.
     * 
     * @param authenticationToken user's authenticationToken
     * @param suggestionAsJSON String containing the suggestion content in JSON format
     * @param hasImage
     * @return HTTP Response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/savesuggestion/{authenticationToken}")
    @Override
    public Response saveSuggestion(@PathParam("authenticationToken")String authenticationToken,
            String suggestionAsJSON,@QueryParam("hasImage") boolean hasImage) {
        
        AuthenticationController authenticationController = new AuthenticationController();
        
        SystemUser user = authenticationController.getUserByAuthenticationToken(authenticationToken);
        if(user == null){
            return createInvalidAuthTokenResponse();
        }
        
        RegisteredUser registeredUser = authenticationController.getUserAsRegisteredUser(user);
        if(registeredUser == null){
            return createInvalidUserResponse();
        }
        
        SuggestionJSONService suggestionJSONService = new Gson().fromJson(suggestionAsJSON,
                SuggestionJSONService.class);
        
        Suggestion suggestion = createSuggestion(hasImage,suggestionJSONService);
        
        registeredUser.getProfile().addSuggestion(suggestion);
        
        registeredUser = new RegisteredUserRepositoryImpl().merge(registeredUser);
        
        if(registeredUser == null){
            return Response.status(Status.UNAUTHORIZED).entity(JSON_SUGGESTION_SUBMISSION_FAILED).build();
        }else{
            return Response.status(Status.OK).entity(JSON_SUGGESTION_SUBMISSION_SUCCESS).build();
        }        
    }
    
    /**
     * Creates a Suggestion object based on a SuggestionJSONService instance
     * @param hasImage boolean to know if the suggestion has an image associated to it or not
     * @param suggestionJSONService SuggestionJSONService instance
     * @return Suggestion object
     */
    private Suggestion createSuggestion(boolean hasImage, SuggestionJSONService suggestionJSONService){
        if(hasImage){
            byte[] imageBytes = Base64.getDecoder().decode(suggestionJSONService.getEncodedImageBytes());
            return new Suggestion(suggestionJSONService.getSuggestionText(),new Image(imageBytes));
        }else{
            return new Suggestion(suggestionJSONService.getSuggestionText());
        }
    }
    
    /**
     * Creates a Response for warning the user that the authentication token is
     * invalid
     *
     * @return Response with the response for warning the user that the invalid
     * authentication token is invalid
     */
    private Response createInvalidAuthTokenResponse() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(JSON_INVALID_AUTHENTICATION_TOKEN)
                .build();
    }

    /**
     * Creates a Response for warning the user that he is not authorized to
     * access surveys
     *
     * @return Response with the response warning the user that he is not
     * allowed to access surveys
     */
    private Response createInvalidUserResponse() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(JSON_INVALID_USER)
                .build();
    }
}
