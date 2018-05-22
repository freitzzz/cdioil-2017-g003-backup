/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
     * Shows the question via JSON GET Request.
     *
     * @param authenticationToken Authentication token of the user
     * @param surveyID ID of the survey in question
     * @param reviewID ID of the review
     * @return JSON response with the current question
     */
    public Response showQuestion(String authenticationToken, String surveyID, String reviewID);

    /**
     * Allows the user to answer a question via JSON PUT Request.
     *
     * @param authenticationToken Authentication token of the user
     * @param option Chosen option
     * @param questionType Type of question
     * @param surveyID ID of the survey
     * @param reviewID ID of the review
     * @return JSON response with the next question
     */
    public Response answerQuestion(String authenticationToken, String option, String questionType, String surveyID, String reviewID);
    
     /**
     * Creates a review via JSON POST Request.
     *
     * @param authenticationToken Authentication token of the user
     * @param surveyID ID of the survey
     * @return JSON response with the database ID of the review
     */
    public Response createReview(String authenticationToken, String surveyID);
}
