package cdioil.frontoffice.application.restful;

import cdioil.frontoffice.application.restful.json.ReviewJSONService;
import cdioil.application.authz.AuthenticationController;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.SystemUser;
import cdioil.frontoffice.application.AnswerSurveyController;
import cdioil.frontoffice.application.api.ReviewAPI;
import static cdioil.frontoffice.application.restful.ResponseMessages.JSON_INVALID_AUTHENTICATION_TOKEN;
import static cdioil.frontoffice.application.restful.ResponseMessages.JSON_INVALID_USER;
import com.google.gson.Gson;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Resource class that holds all services related to reviewing a product
 * (answering a survey).
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 * @author <a href="1161380@isep.ipp.pt">Joana Pinheiro</a>
 *
 * @since Version 5.0 of FeedbackMonkey
 */
@Path("/review")
public class ReviewResource implements ReviewAPI, ResponseMessages {

    /**
     * Shows the question via JSON GET Request.
     *
     * @param authenticationToken Authentication token of the user
     * @param surveyID ID of the survey in question
     * @param reviewID ID of the review
     * @return JSON response with the current question
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/showQuestion/{authenticationToken}/{surveyID}/{reviewID}")
    @Override
    public Response showQuestion(@PathParam("authenticationToken") String authenticationToken,
            @PathParam("surveyID") String surveyID, @PathParam("reviewID") String reviewID) {

        AuthenticationController authenticationCtrl = new AuthenticationController();

        SystemUser user = authenticationCtrl.getUserByAuthenticationToken(authenticationToken);
        if (user == null) {
            return createInvalidAuthTokenResponse();
        }

        RegisteredUser registeredUser = authenticationCtrl.getUserAsRegisteredUser(user);
        if (registeredUser == null) {
            return createInvalidUserResponse();
        }

        return createShowQuestionResponse(new AnswerSurveyController(registeredUser, surveyID).getReviewByID(reviewID).getCurrentQuestion());
    }

    /**
     * Allows the user to answer a question via JSON PUT Request.
     *
     * @param authenticationToken Authentication token of the user
     * @param option Chosen option
     * @param questionType Type of question
     * @param reviewID ID of th review
     * @return JSON response with the next question
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/answerQuestion/{authenticationToken}/{option}/{questionType}/{surveyID}/{reviewID}")
    @Override
    public Response answerQuestion(@PathParam("authenticationToken") String authenticationToken,
            @PathParam("option") String option, @PathParam("questionType") String questionType,
            @PathParam("surveyID") String surveyID, @PathParam("reviewID") String reviewID) {

        AuthenticationController authenticationCtrl = new AuthenticationController();

        SystemUser user = authenticationCtrl.getUserByAuthenticationToken(authenticationToken);
        if (user == null) {
            return createInvalidAuthTokenResponse();
        }

        RegisteredUser registeredUser = authenticationCtrl.getUserAsRegisteredUser(user);
        if (registeredUser == null) {
            return createInvalidUserResponse();
        }

        AnswerSurveyController ctrl = new AnswerSurveyController(registeredUser, surveyID);

        Review review = ctrl.getReviewByID(reviewID);

        if (review.isFinished()) {
            return createFinishedReviewResponse();
        }
        QuestionOption questionOption = QuestionOption.getQuestionOption(questionType, option);

        ctrl.answerQuestion(questionOption);

        if (!ctrl.saveReview()) {
            return createInvalidReviewResponse();
        }
        return createValidReviewResponse(review.getCurrentQuestion());
    }

    /**
     * Creates a review via JSON POST Request.
     *
     * @param authenticationToken Authentication token of the user
     * @param surveyID ID of the survey
     * @return JSON response with the database ID of the review
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createReview/{authenticationToken}/{surveyID}")
    @Override
    public Response createReview(@PathParam("authenticationToken") String authenticationToken,
            @PathParam("surveyID") String surveyID) {

        AuthenticationController authenticationCtrl = new AuthenticationController();

        SystemUser user = authenticationCtrl.getUserByAuthenticationToken(authenticationToken);
        if (user == null) {
            return createInvalidAuthTokenResponse();
        }

        RegisteredUser registeredUser = authenticationCtrl.getUserAsRegisteredUser(user);
        if (registeredUser == null) {
            return createInvalidUserResponse();
        }

        AnswerSurveyController ctrl = new AnswerSurveyController(registeredUser, surveyID);
        Survey survey = ctrl.findSurveyByID(surveyID);

        if (survey == null) {
            return createSurveyNotFoundResponse();
        }

        return createSuccessfullyCreatedReviewResponse(ctrl.createNewReview(survey));
    }

    /**
     * Submits a suggestion via a JSON PUT Request
     *
     * @param suggestion suggestion to submit
     * @param surveyID ID of the survey
     * @param reviewID id of the review
     * @param authenticationToken Authentication token of the user
     * @return JSON response
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/submitSuggestion/{authenticationToken}/{surveyID}/{reviewID}")
    @Override
    public Response submitSuggestion(String suggestion, @PathParam("surveyID") String surveyID, @PathParam("reviewID") String reviewID, @PathParam("authenticationToken") String authenticationToken) {

        AuthenticationController authenticationCtrl = new AuthenticationController();

        SystemUser user = authenticationCtrl.getUserByAuthenticationToken(authenticationToken);
        if (user == null) {
            return createInvalidAuthTokenResponse();
        }

        RegisteredUser registeredUser = authenticationCtrl.getUserAsRegisteredUser(user);
        if (registeredUser == null) {
            return createInvalidUserResponse();
        }

        AnswerSurveyController ctrl = new AnswerSurveyController(registeredUser, surveyID);

        Review review = ctrl.getReviewByID(reviewID);
        if (review == null) {
            return createReviewNotFoundResponse();
        }
        if (ctrl.reviewIsFinished() && !ctrl.reviewHasSuggestion()) {
            ctrl.submitSuggestion(suggestion);
        } else {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity(JSON_INCOMPLETE_REVIEW).build();
        }
        return ctrl.saveUnfinishedReview() ? Response.status(Response.Status.OK).build() : createReviewNotFoundResponse();
    }

    /**
     * Method that serializes the current question into a JSON.
     *
     * @param question Question to answer
     * @return String with a JSON String with the current question serialized
     */
    private String getQuestionAsJSON(Question question) {
        Gson gSon = new Gson();
        String x = gSon.toJson(new ReviewJSONService(question));
        return x;
    }

    /* Response methods */
    /**
     * Creates a Response for warning the user that its account is not currently
     * authenticated.
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
     * Creates a Response for warning the user that they aren't authorized to
     * answer the survey.
     *
     * @return Response with the response warning the user that they aren't
     * authorized to answer the survey
     */
    private Response createInvalidUserResponse() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(JSON_INVALID_USER)
                .build();
    }

    /**
     * Creates a Response for warning the user that the chosen review is
     * invalid, because it has already been answered.
     *
     * @return Response with the response warning the user that the chosen
     * review is already finished
     */
    private Response createFinishedReviewResponse() {
        return Response.status(Response.Status.UNAUTHORIZED).
                entity(JSON_FINISHED_REVIEW).
                build();
    }

    /**
     * Creates a Response for warning the user that the review is valid.
     *
     * @param question Current question of the Review
     * @return Response with the response warning the user that the review is
     * valid
     */
    private Response createValidReviewResponse(Question question) {
        return Response.status(Response.Status.OK).
                entity(getQuestionAsJSON(question))
                .build();
    }

    /**
     * Creates a Response for warning the user that the review is invalid.
     *
     * @return Response with the response warning the user that the review is
     * invalid
     */
    private Response createInvalidReviewResponse() {
        return Response.status(Response.Status.BAD_REQUEST).
                entity(JSON_INVALID_REVIEW).
                build();
    }

    /**
     * Creates a Response for warning the user that the review was successfully
     * created.
     *
     * @param id ID of the created Review
     * @return Response with the response warning the user that the review was
     * created
     */
    private Response createSuccessfullyCreatedReviewResponse(String id) {
        return Response.status(Response.Status.CREATED).entity("{\n\t\"reviewID\":" + id + "\n}").build();
    }

    /**
     * Creates a Response for warning the user that the survey was not found.
     *
     * @return Response with the response warning the user that the survey
     * wasn't found
     */
    private Response createSurveyNotFoundResponse() {
        return Response.status(Response.Status.NOT_FOUND).entity(JSON_INVALID_SURVEY).build();
    }

    /**
     * Creates a Response for warning the user.
     *
     * @param question
     * @return
     */
    private Response createShowQuestionResponse(Question question) {
        return Response.status(Response.Status.OK).entity(getQuestionAsJSON(question)).build();
    }

    /**
     * Creates a Response for warning the user that the review was not found
     *
     * @return Response with the response warning the user that the review
     * wasn't found
     */
    private static Response createReviewNotFoundResponse() {
        return Response.status(Response.Status.NOT_FOUND).entity(JSON_REVIEW_NOT_FOUND).build();
    }
}
