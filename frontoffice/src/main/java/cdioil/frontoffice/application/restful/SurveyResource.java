package cdioil.frontoffice.application.restful;

import cdioil.domain.Survey;
import cdioil.domain.authz.SystemUser;
import cdioil.frontoffice.application.api.SurveyAPI;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.persistence.impl.UserSessionRepositoryImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Resource class that represents the resource that holds all authentication services
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 * @since Version 5.0 of FeedbackMonkey
 */
@Path("/surveyresource")
public final class SurveyResource implements SurveyAPI{
    /**
     * Constant that represents the JSON used on the response message for warning the user 
     * that his account is not currently authenticated
     */
    private static final String JSON_INVALID_AUTHENTICATION_TOKEN="{\n\t\"invalidauthenticationtoken\":\"true\"}";
    /**
     * Constant that represents the JSON used on the response message for warning the user 
     * that there are currently no available surveys for him to answer
     */
    private static final String JSON_NO_AVAILABLE_SURVEYS="{\n\t\"\"noavailablesurveys:\"true\"}";
    /**
     * List the Surveys via a JSON POST Request
     * @param authenticationToken String the authentication token
     * @return Response with the JSON response with the list of the surveys
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/allsurveys/{authenticationToken}/")
    @Override
    public Response getSurveys(@PathParam("authenticationToken") String authenticationToken){    
        SystemUser user = new UserSessionRepositoryImpl().getSystemUserByAuthenticationToken(authenticationToken);
        if(user==null)return createInvalidAuthTokenResponse();
        SurveyRepositoryImpl surveyRepoImpl = new SurveyRepositoryImpl();
        List<Survey> listTargetedSurvey = surveyRepoImpl
                .getUserTergetedSurveys(new RegisteredUserRepositoryImpl().findBySystemUser(user));
        if(listTargetedSurvey==null || listTargetedSurvey.isEmpty()){
            return createNoAvailableSurveysResponse();
        }
        return Response.status(Status.OK).entity(getSurveysAsJSON(listTargetedSurvey)).build();
    }
    
    /**
     * Creates a Response for warning the user that the authentication token is invalid
     * @return Response with the response for warning the user that the invalid authentication 
     * token is invalid
     */
    private Response createInvalidAuthTokenResponse(){
        return Response.status(Status.NOT_ACCEPTABLE)
                .entity(JSON_INVALID_AUTHENTICATION_TOKEN)
                .build();
    }
    /**
     * Creates a Response for warning the user that there are currently no available surveys 
     * for him to answer
     * @return Response with the response for warning the user that there are currently no 
     * available surveys for him to answer
     */
    private Response createNoAvailableSurveysResponse(){
        return Response.status(Status.BAD_REQUEST)
                .entity(JSON_NO_AVAILABLE_SURVEYS)
                .build();
    }
    private String getSurveysAsJSON(List<Survey> surveys){
        JsonArray jsonArray=new JsonArray(surveys.size());
        for(int i=0;i<surveys.size();i++){
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("surveyID",surveys.get(i).toString());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
}
