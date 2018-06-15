package cdioil.feedbackmonkey.application.services;

import android.content.Context;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import javax.xml.parsers.ParserConfigurationException;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.restful.exceptions.RESTfulException;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;
import okhttp3.Response;

/**
 * RequestNewReviewController class that controls all the requests regarding the start
 * of a new Review
 * @author @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */

public final class RequestNewReviewController {
    /**
     * Constant that represents the Reviews resource path.
     */
    private static final String REVIEWS_RESOURCE_PATH = FeedbackMonkeyAPI.getResourcePath("Reviews");
    /**
     * Constant that represents the new review resource path under reviews resource.
     */
    private static final String NEW_REVIEW_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Reviews", "Create New Review");
    /**
     * SurveyService with the survey which the new review will be requested
     */
    private final SurveyService surveyService;

    /**
     * Builds a new RequestNewReviewController with the survey which the new review will be requested
     * @param surveyService SurveyService with the survey which new review will be requested
     */
    public RequestNewReviewController(SurveyService surveyService){this.surveyService=surveyService;}

    /**
     * Method that requests a new review of a certain survey
     * @param authenticationToken String with the authentication token of the user which
     * is requesting a new review
     * @return String with the survey flux represented as a String
     * @throws IOException Throws {@link IOException} if an error occurred while requesting
     * the data of the survey
     */
    public String requestNewReview(String authenticationToken) throws IOException {
        Response reviewRestResponse = RESTRequest.create(BuildConfig.SERVER_URL.
                concat(FeedbackMonkeyAPI.getAPIEntryPoint().
                        concat(REVIEWS_RESOURCE_PATH).
                        concat(NEW_REVIEW_RESOURCE_PATH).
                        concat("/" + authenticationToken).
                        concat("/" + surveyService.getSurveyID()))).
                GET();
        String responseBody=reviewRestResponse.body().string();
        if(reviewRestResponse.code()== HttpURLConnection.HTTP_OK){
            return responseBody;
        }
        throw new RESTfulException(responseBody,(short)reviewRestResponse.code());
    }

    /**
     * Method that creates a new Review based on a certain Survey Flux which will be stored on the
     * local user mobile internal storage
     * @param surveyFlux String with the survey flux which review will be created
     * @param context Context with the context which the application is being called
     * @throws ParserConfigurationException Throws {@link ParserConfigurationException} if an error occurred
     * while parsing the survey flux
     * @throws SAXException Throws {@link SAXException} if an error occurred while parsing the survey flux
     * @throws IOException Throws {@link IOException} if an error occurred while creating the file which handles
     * the survey flux
     */
    public void createNewReview(String surveyFlux,Context context) throws ParserConfigurationException, SAXException, IOException {
        ReviewXMLService.instance().createNewReviewFile(getPendingReviewsDirectory(context),surveyFlux);
    }
    /**
     * Creates a pending reviews directory or fetches the existing one.
     * @param context Context with the context which the reviews directory request is
     * being called
     * @return pending reviews directory
     */
    private File getPendingReviewsDirectory(Context context) {
        String pendingReviews = "pending_reviews";
        File filesDirectory = context.getFilesDir();
        File pendingReviewsDirectory = new File(filesDirectory, pendingReviews);
        if (!pendingReviewsDirectory.exists()) {
            pendingReviewsDirectory.mkdir();
        }
        return pendingReviewsDirectory;
    }
}
