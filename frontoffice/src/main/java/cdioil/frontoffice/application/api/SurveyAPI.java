package cdioil.frontoffice.application.api;

import javax.ws.rs.core.Response;

/**
 * Interface that represents the FeedbackMonkey Survey API
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public interface SurveyAPI {

    /**
     * Gets all surveys that an user can answer
     *
     * @param authenticationToken String with the user authentication token
     * @param paginationID Short with the surveys pagination ID
     * @param surveyFlux boolean true if the survey flux should be sent, false if not
     * @return Response with the all the surveys that an user can answer
     */
    public Response getSurveys(String authenticationToken, short paginationID,boolean surveyFlux);

    /**
     * Gets the surveys a user has answered
     * 
     * @param authenticationToken user's authentication token
     * @param paginationID pagination ID so the list doesn't contain all surveys at once
     * @return Response with the surveys a user has answered, or an error Response if 
     * several errors happen
     */
    public Response getUserAnsweredSurveys(String authenticationToken, short paginationID);
    
    /**
     * Retrieves all Surveys about a product via a JSON XXX REQUEST
     *
     * @param code
     * @param authenticationToken
     * @return JSON Response
     */
    public Response getSurveysByProductCode(String code, String authenticationToken);
}
