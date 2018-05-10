/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.frontoffice.application.restful;

import cdioil.domain.Survey;
import cdioil.domain.authz.SystemUser;
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
 * 
 * @author Ana Guerra (1161191)
 */
@Path("/surveyresource")
public class SurveyResource {
    
    /**
     * List the Surveys via a JSON POST Request
     * @param authenticationToken String the authentication token
     * @return Response with the JSON response with the list of the surveys
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/allsurveys/{authenticationToken}/")
    public Response listSurveys(@PathParam("authenticationToken") String authenticationToken){    
        SystemUser user = new UserSessionRepositoryImpl().getSystemUserByAuthenticationToken(authenticationToken);
        if(user == null){
            return Response.status(Status.NOT_ACCEPTABLE).entity("{\n\t\"invalidauthenticationtoken\":\"true\"}").build();
        }
        SurveyRepositoryImpl surveyRepoImpl = new SurveyRepositoryImpl();
        List<Survey> listTargetedSurvey = surveyRepoImpl
                .getUserTergetedSurveys(new RegisteredUserRepositoryImpl().findBySystemUser(user));
        if(listTargetedSurvey==null || listTargetedSurvey.isEmpty()){
            return Response.status(Status.NO_CONTENT).entity("{\n\t\"\"notargetsurveys:\"true\"}").build();
        }
        return Response.status(Status.OK).entity(getSurveysAsJSON(listTargetedSurvey)).build();
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
