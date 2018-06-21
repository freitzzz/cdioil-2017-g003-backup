package cdioil.frontoffice.application.restful;

import cdioil.application.authz.AuthenticationController;
import cdioil.application.authz.UserActionHistoryController;
import cdioil.application.domain.authz.UserAction;
import cdioil.domain.Image;
import cdioil.domain.QuestionOption;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.SystemUser;
import cdioil.frontoffice.application.api.ReviewAPI;
import static cdioil.frontoffice.application.restful.ResponseMessages.JSON_INVALID_AUTHENTICATION_TOKEN;
import static cdioil.frontoffice.application.restful.ResponseMessages.JSON_INVALID_USER;
import cdioil.frontoffice.application.restful.json.ReviewJSONService;
import cdioil.frontoffice.application.restful.xml.ReviewXMLService;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.ReviewRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.persistence.impl.UserSessionRepositoryImpl;
import com.google.gson.GsonBuilder;
import java.util.Base64;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Resource class that holds all services related to reviewing a product
 * (answering a survey).
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 * @author <a href="1161380@isep.ipp.pt">Joana Pinheiro</a>
 * @author <a href="1161371@isep.ipp.pt">António Sousa</a>
 *
 * @since Version 6.0 of FeedbackMonkey
 */
@Path("/reviews")
public class ReviewResource implements ReviewAPI, ResponseMessages {

    /**
     * Creates a new review and adds it to the user's profile.
     *
     * @param authenticationToken Authentication token of the user
     * @param surveyID ID of the survey to answer
     * @return Response with JSON Response containing a XML formatted String
     * with the graph.
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/newreview/{authenticationToken}/{surveyID}")
    @Override
    public Response newReview(@PathParam("authenticationToken") String authenticationToken, @PathParam("surveyID") String surveyID) {

        AuthenticationController authCtrl = new AuthenticationController();

        SystemUser sysUser = authCtrl.getUserByAuthenticationToken(authenticationToken);
        if (sysUser == null) {
            return createInvalidAuthTokenResponse();
        }

        RegisteredUser registeredUser = authCtrl.getUserAsRegisteredUser(sysUser);
        if (registeredUser == null) {
            return createInvalidUserResponse();
        }

        Survey survey = new SurveyRepositoryImpl().find(Long.parseLong(surveyID));

        if (survey == null) {
            return createSurveyNotFoundResponse();
        }

        Review newReview = new Review(survey);

        newReview = new ReviewRepositoryImpl().add(newReview);

        if (newReview == null) {
            //return a response warning the user that it was not possible to create the review
            return createUnableToCreateReviewResponse();
        }

        boolean addedSuccessfully = registeredUser.getProfile().addReview(newReview);

        if (!addedSuccessfully) {
            //return a response warning the user that the review could not be added
            return createUnableToAddReviewToProfileResponse();
        }

        registeredUser = new RegisteredUserRepositoryImpl().merge(registeredUser);

        if (registeredUser == null) {
            //return a response warning the user that the profile could not be updated
            return createUnableToUpdateUserDataResponse();
        }
        new UserActionHistoryController(new UserSessionRepositoryImpl().getUserSessionByAuthenticationToken(authenticationToken))
                .logUserAction(UserAction.STARTED_ANSWER_SURVEY);
        String messageBody = ReviewXMLService.createReviewXML(newReview);

        return messageBody == null ? createInvalidReviewResponse()
                : createValidReviewResponse(messageBody);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/userreviewmap/{authenticationToken}/{surveyID}")
    @Override
    public Response getQuestionAnswerMap(@PathParam("authenticationToken") String authenticationToken,
            @PathParam("surveyID") String surveyID) {

        AuthenticationController authenticationController = new AuthenticationController();

        SystemUser user = authenticationController.getUserByAuthenticationToken(authenticationToken);

        if (user == null) {
            return createInvalidAuthTokenResponse();
        }

        RegisteredUser registeredUser = authenticationController.getUserAsRegisteredUser(user);

        if (registeredUser == null) {
            return createInvalidUserResponse();
        }

        Survey survey = new SurveyRepositoryImpl().find(Long.parseLong(surveyID));

        if (survey == null) {
            return createSurveyNotFoundResponse();
        }

        List<Review> userReviews = registeredUser.getProfile().getReviews();

        if (userReviews == null || userReviews.isEmpty()) {
            return createNoReviewsFoundResponse();
        }

        for (Review userReview : userReviews) {
            if (userReview.getSurvey().equals(survey) && userReview.isFinished()) {
                return Response.status(Status.OK).
                        entity(new GsonBuilder()
                                .enableComplexMapKeySerialization().create()
                                .toJson(new ReviewJSONService(userReview.getReviewQuestionAnswers(),
                                        userReview.getSuggestion()))).build();
            }
        }

        return createInvalidReviewResponse();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/savereview/{reviewID}")
    @Override
    public Response saveReview(@PathParam("reviewID") String reviewID, String fileContent) {

        Review reviewToAnswer = new ReviewRepositoryImpl().find(Long.parseLong(reviewID));

        List<QuestionOption> answers = ReviewXMLService.getAnswerList(fileContent);
        String suggestion = ReviewXMLService.getSuggestion(fileContent);
        String suggestionImage = ReviewXMLService.getSuggestionImage(fileContent);

        if (reviewToAnswer == null) {
            return createInvalidReviewResponse();
        }

        for (QuestionOption answer : answers) {
            reviewToAnswer.answerQuestion(answer);
        }

        if (suggestionImage == null) {
            if (suggestion != null) {
                reviewToAnswer.submitSuggestion(suggestion);
            }
        }else{
            if(suggestion != null){
                byte[] imageBytes = Base64.getDecoder().decode(suggestionImage);
                reviewToAnswer.submitSuggestionWithImage(suggestion,new Image(imageBytes));
            }
        }

        reviewToAnswer = new ReviewRepositoryImpl().merge(reviewToAnswer);

        if (reviewToAnswer == null) {
            return createInvalidReviewResponse();
        }
//        new UserActionHistoryController(new UserSessionRepositoryImpl().getUserSessionByAuthenticationToken(authenticationToken))
//                .logUserAction(UserAction.ENDED_ANSWER_SURVEY);
        return createSavedReviewWithSuccessResponse();
    }

    /* Response methods */
    /**
     * Creates a Response with the status code 401, warning the user that their
     * account is not currently authenticated.
     *
     * @return <code>Response</code> with the response for warning the user that
     * the invalid authentication token is invalid
     */
    private Response createInvalidAuthTokenResponse() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(JSON_INVALID_AUTHENTICATION_TOKEN)
                .build();
    }

    /**
     * Creates a Response with the status code 400, warning the user that they
     * aren't authorized to answer the survey.
     *
     * @return <code>Response</code> with the response warning the user that
     * they aren't authorized to answer the survey
     */
    private Response createInvalidUserResponse() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(JSON_INVALID_USER)
                .build();
    }

    /**
     * Creates a Response with the status code 400, warning the user that the
     * review is invalid.
     *
     * @return <code>Response</code> with the response warning the user that the
     * review is invalid
     */
    private Response createInvalidReviewResponse() {
        return Response.status(Response.Status.BAD_REQUEST).
                entity(JSON_INVALID_REVIEW).
                build();
    }

    /**
     * Creates a response with the status code 400, warning the user that the
     * new review was not persisted.
     *
     * @return <code>Response</code> warning the user that the new review was
     * not persisted.
     */
    private Response createUnableToCreateReviewResponse() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(JSON_REVIEW_CREATION_FAILURE)
                .build();
    }

    /**
     * Creates a response with the status code 400, warning the user that it was
     * not possible to add the new review to their profile.
     *
     * @return <code>Responde</code> warning the user that the new review was
     * not added to their profile.
     */
    private Response createUnableToAddReviewToProfileResponse() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(JSON_REVIEW_ADDED_TO_PROFILE_FAILURE)
                .build();
    }

    /**
     * Creates a response with the status code 400, warning the user that their
     * data failed to be updated.
     *
     * @return <code>Response</code> warning the user that their data failed to
     * be updated.
     */
    private Response createUnableToUpdateUserDataResponse() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(JSON_USER_DATA_UPDATED_FAILURE)
                .build();
    }

    /**
     * Creates a response with the status code 404, warning the user that the
     * survey was not found.
     *
     * @return <code>Response</code> warning the user that the survey wasn't
     * found
     */
    private Response createSurveyNotFoundResponse() {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(JSON_SURVEY_NOT_FOUND)
                .build();
    }

    /**
     * Creates a response with the status code 200, informing the user that the
     * review was successfully created.
     *
     * @return <code>Response</code> informing the user that the review is valid
     */
    private Response createValidReviewResponse(String messageBody) {
        return Response.status(Response.Status.OK)
                .entity(messageBody)
                .build();
    }

    /**
     * Creates a response with the status code 200, informing the user that the
     * review was successfully created.
     *
     * @param messageBody message body of the response
     * @return <code>Response</code> informing the user that the review was
     * submitted with success
     */
    private Response createSavedReviewWithSuccessResponse() {
        return Response.status(Response.Status.OK)
                .entity(JSON_REVIEW_SAVED_WITH_SUCCESS)
                .build();
    }

    /**
     * Creates a response with the status code 404, informing the user that the
     * review was not found.
     *
     * @return <code>Response</code> informing the user that the review wasn't
     * found
     */
    private Response createNoReviewsFoundResponse() {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(JSON_REVIEWS_NOT_FOUND)
                .build();
    }
}
