package cdioil.frontoffice.application.api;

import javax.ws.rs.core.Response;

/**
 * Interface that represents the FeedbackMonkey Review API.
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 * @author <a href="1161380@isep.ipp.pt">Joana Pinheiro</a>
 */
public interface ReviewAPI {

    /**
     * Produces an HTTP Response whereby the response's body contains an XML
     * representation of a Review.
     *
     * @param authenticationToken user's session authentication token
     * @param surveyID database identifier of the survey being answered
     * @return HTTP Response with an XML representation of a new Review if no
     * errors have occured.
     */
    public Response newReview(String authenticationToken, String surveyID);

    /**
     * Produces an HTTP Response indicating whether a review was saved correctly
     * or not
     *
     * @param authenticationToken String with the user authentication token
     * @param reviewID the review's database id
     * @param fileContent String that represents an XML File Content
     * @return HTTP Response indicating whether the review was saved correctly
     * or not
     */
    public Response saveReview(String authenticationToken,String reviewID, String fileContent);
    
    /**
     * Produces an HTTP Response with the question answer map of a user's review
     * 
     * @param authenticationToken user's authentication token
     * @param surveyID ID of the survey that the user is about
     * @return HTTP Response indicating whether the question answer map was saved
     * correctly or not
     */
    public Response getQuestionAnswerMap(String authenticationToken, String surveyID);
}
