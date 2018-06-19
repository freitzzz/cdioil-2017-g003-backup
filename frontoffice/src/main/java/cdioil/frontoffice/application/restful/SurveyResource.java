package cdioil.frontoffice.application.restful;

import cdioil.frontoffice.application.restful.json.SurveyJSONService;
import cdioil.application.SurveyController;
import cdioil.application.authz.AuthenticationController;
import cdioil.domain.Product;
import cdioil.domain.Survey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.SystemUser;
import cdioil.frontoffice.application.api.SurveyAPI;
import cdioil.frontoffice.application.restful.xml.SurveyFluxXMLService;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Resource class that represents the resource that holds all authentication
 * services
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 * @since Version 5.0 of FeedbackMonkey
 */
@Path("/surveys")
public final class SurveyResource implements SurveyAPI, ResponseMessages {

    /**
     * List the Surveys via a JSON GET Request
     *
     * @param authenticationToken String the authentication token
     * @param paginationID Short with the surveys pagination ID
     * @param surveyFlux boolean true if the survey flux should be sent, false if not
     * @return Response with the JSON response with the list of the surveys
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Path("/useravailablesurveys/{authenticationToken}/")
    @Override
    public Response getSurveys(@PathParam("authenticationToken") String authenticationToken,
            @QueryParam("paginationID") short paginationID,@QueryParam("surveyFlux")boolean surveyFlux) {
        if (paginationID < 0) {
            return createInvalidPaginationIDResponse();
        }
        SystemUser user = new AuthenticationController().getUserByAuthenticationToken(authenticationToken);
        if (user == null) {
            return createInvalidAuthTokenResponse();
        }
        RegisteredUser registeredUser = new AuthenticationController().getUserAsRegisteredUser(user);
        if (registeredUser == null) {
            createInvalidUserResponse();
        }
        List<Survey> listTargetedSurvey = new SurveyController()
                .getUserSurveys(registeredUser, paginationID);
        if (listTargetedSurvey == null || listTargetedSurvey.isEmpty()) {
            return createNoAvailableSurveysResponse();
        }
        if(surveyFlux)return Response.status(Status.OK).entity(getSurveysAsXML(listTargetedSurvey)).build();
        return Response.status(Status.OK).entity(getSurveysAsJSON(listTargetedSurvey)).build();
    }
    
    /**
     * Gets the surveys a user has answered.
     * 
     * @param authenticationToken user's authentication token
     * @param paginationID pagination ID so the list doesn't contain all surveys at once
     * @return Response with the surveys a user has answered or an error Response in case 
     * several errors happen
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/useransweredsurveys/{authenticationToken}/")
    @Override
    public Response getUserAnsweredSurveys(@PathParam("authenticationToken") String authenticationToken,
            @QueryParam("paginationID") short paginationID){
        
        if(paginationID < 0){
           createInvalidPaginationIDResponse();
        }
        
        AuthenticationController authenticationController = new AuthenticationController();
        
        SystemUser user = authenticationController.getUserByAuthenticationToken(authenticationToken);
        if(user == null){
            createInvalidAuthTokenResponse();
        }
        
        RegisteredUser registeredUser = authenticationController.getUserAsRegisteredUser(user);
        if(registeredUser == null){
            createInvalidUserResponse();
        }
        
        List<Survey> answeredSurveys = new SurveyController().getUserAnsweredSurveys(registeredUser,
                paginationID);
        if(answeredSurveys == null || answeredSurveys.isEmpty()){
            createNoAvailableSurveysResponse();
        }
        return Response.status(Status.OK).entity(getSurveysAsJSON(answeredSurveys)).build();
    }

    /**
     * Retrieves all Surveys about a product via a JSON GET REQUEST
     *
     * @param code
     * @param authenticationToken
     * @return JSON Response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSurveysByProductCode/{authenticationToken}/{code}")
    @Override
    public Response getSurveysByProductCode(@PathParam("authenticationToken") String authenticationToken, @PathParam("code") String code) {
        AuthenticationController authenticationCtrl = new AuthenticationController();

        SystemUser user = authenticationCtrl.getUserByAuthenticationToken(authenticationToken);
        if (user == null) {
            return createInvalidAuthTokenResponse();
        }

        RegisteredUser registeredUser = authenticationCtrl.getUserAsRegisteredUser(user);
        if (registeredUser == null) {
            return createInvalidUserResponse();
        }

        SurveyController ctrl = new SurveyController();
        List<Product> products = ctrl.getProductsByCode(code);

        if (products == null || products.isEmpty()) {
            return createProductNotFoundResponse();
        }

        List<Survey> surveyList = ctrl.getSurveysByProduct(products, registeredUser);

        if (surveyList == null || surveyList.isEmpty()) {
            return createNoAvailableSurveysResponse();
        }
        return Response.status(Status.OK).entity(getSurveysAsJSON(surveyList)).build();
    }

    /**
     * Creates a Response for warning the user that the authentication token is
     * invalid
     *
     * @return Response with the response for warning the user that the invalid
     * authentication token is invalid
     */
    private Response createInvalidAuthTokenResponse() {
        return Response.status(Status.UNAUTHORIZED)
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
        return Response.status(Status.BAD_REQUEST)
                .entity(JSON_INVALID_USER)
                .build();
    }

    /**
     * Creates a Response for warning the user that there are currently no
     * available surveys for him to answer
     *
     * @return Response with the response for warning the user that there are
     * currently no available surveys for him to answer
     */
    private Response createNoAvailableSurveysResponse() {
        return Response.status(Status.BAD_REQUEST)
                .entity(JSON_NO_AVAILABLE_SURVEYS)
                .build();
    }

    /**
     * Creates a Response for warning the user that the pagination ID is invalid
     *
     * @return Response with the response for warning the user that the
     * pagination ID is invalid
     */
    private Response createInvalidPaginationIDResponse() {
        return Response.status(Status.BAD_REQUEST)
                .entity(JSON_INVALID_PAGINATION_ID)
                .build();
    }

    /**
     * Creates a Response for warning the user that product does not exist.
     *
     * @return Response warning the user that the product does not exist
     */
    private Response createProductNotFoundResponse() {
        return Response.status(Status.BAD_REQUEST)
                .entity(JSON_PRODUCT_NOT_FOUND)
                .build();
    }

    /**
     * Method that serializes a list of surveys into a JSON
     *
     * @param surveys List with all the surveys being serialized
     * @return String with a JSON String with all the surveys serialized
     */
    private String getSurveysAsJSON(List<Survey> surveys) {
        Gson gson = new Gson();
        List<SurveyJSONService> surveysAsJSON = new ArrayList<>();
        for (int i = 0; i < surveys.size(); i++) {
            surveysAsJSON.add(new SurveyJSONService(surveys.get(i)));
        }
        return gson.toJson(surveysAsJSON);
    }
    /**
     * Method that serializes a list of surveys into XML
     * @param surveys List with all the surveys being serialized
     * @return String with a XML document containg all surveys serialized
     */
    private String getSurveysAsXML(List<Survey> surveys){
        return SurveyFluxXMLService.create().createSurveyListXML(surveys);
    }
}
