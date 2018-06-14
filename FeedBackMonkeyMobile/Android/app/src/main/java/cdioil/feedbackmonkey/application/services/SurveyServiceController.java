package cdioil.feedbackmonkey.application.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.restful.exceptions.RESTfulException;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.json.SurveyJSONService;
import okhttp3.Response;

/**
 * SurveyServiceController class that represents the controller used to
 * control all services that involve surveys
 * @author @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */

public final class SurveyServiceController {
    /**
     * Constant that represents the Surveys resource path
     */
    private static final String SURVEYS_RESOURCE_PATH = FeedbackMonkeyAPI.getResourcePath("Surveys");
    /**
     * Constant that represents the surveys available to the user with a given code resource path under survey resource.
     */
    private static final String PRODUCT_CODE_AVAILABLE_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Surveys", "Available Surveys By Product Code");
    /**
     * Constant that represents the user available surveys resource path under survey resource
     */
    private static final String USER_AVAILABLE_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Surveys", "Available User Surveys");
    /**
     * String with the current user authentication token
     */
    private final String authenticationToken;

    /**
     * Builds a new SurveyServiceController with the user authentication token
     * @param authenticationToken String with the user authentication token
     */
    public SurveyServiceController(String authenticationToken){this.authenticationToken=authenticationToken;}
    /**
     * Method that returns all current active surveys relatively to a certain product code
     * @param productCode String with the product code
     * @return List with all the current active surveys relatively to a certain product code
     * @throws IOException Throws {@link IOException} if an error occurred during the surveys retrieval
     */
    public List<SurveyService> getSurveysByProductCode(String productCode) throws IOException {
        Response response= RESTRequest.create(
                BuildConfig.SERVER_URL
                .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                .concat(SURVEYS_RESOURCE_PATH)
                .concat(PRODUCT_CODE_AVAILABLE_RESOURCE_PATH)
                .concat(authenticationToken)
                .concat("/")
                .concat(productCode))
                .GET();
        return getSurveysByResponse(response);
    }

    /**
     * Method that returns all current active surveys that the current user can answer with
     * a certain pagination ID
     * @param paginationID Short with the surveys pagination ID
     * @return List with all the current active surveys that the current user can answer with
     * a certain pagination ID
     * @throws IOException Throws {@link IOException} if an error occurred during the surveys retrieval
     */
    public List<SurveyService> getSurveysByPaginationID(short paginationID) throws IOException {
        Response response=RESTRequest.create(BuildConfig.SERVER_URL
                        .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                        .concat(SURVEYS_RESOURCE_PATH)
                        .concat(USER_AVAILABLE_RESOURCE_PATH)
                        .concat(authenticationToken)
                        .concat("?paginationID="+(paginationID)))
                .GET();
        return getSurveysByResponse(response);
    }

    /**
     * Method that gets all surveys contained on a certain response body
     * <br>Throws {@link RESTfulException} if the response was not successful
     * @param response Response with the response that holds the surveys
     * @return List with all the surveys contained on the response body
     * @throws IOException Throws {@link IOException} if an occurred while accessing the response body
     */
    private List<SurveyService> getSurveysByResponse(Response response) throws IOException {
        String responseBody=response.body().string();
        if(response.code()==HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type surveyJSONServicesType = new TypeToken<ArrayList<SurveyJSONService>>() {
            }.getType();
            List<SurveyJSONService> surveyJSONServices = gson.fromJson(responseBody, surveyJSONServicesType);
            List<SurveyService> surveyServices=new ArrayList<>();
            for(int i=0;i<surveyJSONServices.size();i++)surveyServices.add(new SurveyService(surveyJSONServices.get(i)));
            return surveyServices;
        }else{
            throw new RESTfulException(responseBody,(short)response.code());
        }
    }
}
