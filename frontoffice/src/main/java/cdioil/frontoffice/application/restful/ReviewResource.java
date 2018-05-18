package cdioil.frontoffice.application.restful;

import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import cdioil.domain.Review;
import cdioil.domain.Survey;
import cdioil.domain.authz.Profile;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.ProfileRepositoryImpl;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.ReviewRepositoryImpl;
import cdioil.persistence.impl.SurveyRepositoryImpl;
import cdioil.persistence.impl.UserSessionRepositoryImpl;
import com.google.gson.Gson;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Resource class that holds all services related to reviewing a product (answering a survey).
 *
 * @author <a href="1160912@isep.ipp.pt">Rita Gonçalves</a>
 * @author <a href="1161380@isep.ipp.pt">Joana Pinheiro</a>
 *
 * @since Version 5.0 of FeedbackMonkey
 */
@Path("/question")
public class ReviewResource {

    /**
     * JSON used on the response message for warning the user that its account is not currently authenticated.
     */
    private static final String JSON_INVALID_AUTHENTICATION_TOKEN = "{\n\t\"invalidauthenticationtoken\":\"true\"\n}";

    /**
     * JSON used on the response message for warning the user that the survey can only be answered by RegisteredUsers.
     */
    private static final String JSON_INVALID_USER = "{\n\t\"invaliduser\":\"true\"\n}";

    /**
     * JSON used on the response message for warning the user that the chosen option is not valid.
     */
    private static final String JSON_INVALID_OPTION = "{\n\t\"invalidoption\":\"true\"\n}";

    /**
     * JSON used on the response message for warning the user that the chosen survey is not valid.
     */
    private static final String JSON_INVALID_SURVEY = "{\n\t\"invalidsurvey\":\"true\"\n}";

    /**
     * JSON used on the response message for warning the user that the review is not valid.
     */
    private static final String JSON_INVALID_REVIEW = "{\n\t\"invalidreview\":\"true\"\n}";

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
    public Response showQuestion(@PathParam("authenticationToken") String authenticationToken,
            @PathParam("surveyID") String surveyID, @PathParam("reviewID") String reviewID) {

        Review review = getReviewByID(reviewID);
        return Response.status(Response.Status.OK).entity(getQuestionAsJSON(review.getCurrentQuestion())).build();
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
    @Path("/answerQuestion/{authenticationToken}/{option}/{questionType}/{reviewID}")
    public Response answerQuestion(@PathParam("authenticationToken") String authenticationToken,
            @PathParam("option") String option, @PathParam("questionType") String questionType, @PathParam("reviewID") String reviewID) {

        SystemUser user = new UserSessionRepositoryImpl().getSystemUserByAuthenticationToken(authenticationToken);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(JSON_INVALID_AUTHENTICATION_TOKEN).build();
        }
        RegisteredUser registeredUser = new RegisteredUserRepositoryImpl().findBySystemUser(user);
        if (registeredUser == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(JSON_INVALID_USER).build();
        }

        Review review = getReviewByID(reviewID);

        QuestionOption questionOption = QuestionOption.getQuestionOption(questionType, option);

        if (questionOption == null || !review.answerQuestion(questionOption)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(JSON_INVALID_OPTION).build();
        }

        Profile profile = registeredUser.getProfile();

        for (Review r : profile.getReviews()) {
            if (!r.isFinished() && r.getSurvey().getItemList().equals(review.getSurvey().getItemList())) {
                profile.removeReview(r);
            }
        }

        if (!profile.addReview(review)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(JSON_INVALID_REVIEW).build();
        }

        new ProfileRepositoryImpl().merge(profile);

        return Response.status(Response.Status.OK).entity(getQuestionAsJSON(review.getCurrentQuestion())).build();
    }

    /**
     * Creates a review via JSON POST Request.
     *
     * @param surveyID ID of the survey
     * @return JSON response with the database ID of the review
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createReview/{surveyID}")
    private Response createReview(@PathParam("surveyID") String surveyID) {
        Survey survey = new SurveyRepositoryImpl().find(Long.parseLong(surveyID));

        if (survey == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(JSON_INVALID_SURVEY).build();
        }

        Review review = new Review(survey);
        ReviewRepositoryImpl reviewRepository = new ReviewRepositoryImpl();

        reviewRepository.merge(review);
        String reviewID = Long.toString(reviewRepository.getReviewID(review));

        return Response.status(Response.Status.CREATED).entity("{\n\t\"reviewID\":" + reviewID + "\n}").build();
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

    /**
     * Access method to a Review via its ID.
     *
     * @param reviewID ID of the review
     * @return the Review
     */
    private Review getReviewByID(String reviewID) {
        return new ReviewRepositoryImpl().find(Long.parseLong(reviewID));
    }
}
