package cdioil.feedbackmonkey.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.application.services.SurveyService;
import cdioil.feedbackmonkey.application.services.SurveyServiceController;
import cdioil.feedbackmonkey.authz.UserProfileActivity;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.json.ReviewJSONService;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;
import cdioil.feedbackmonkey.utils.ToastNotification;
import okhttp3.Response;

/**
 * ListSurveyActivity that represents the activity that lists the current available
 * surveys that an user can answer
 *
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 */
public class ListSurveyActivity extends AppCompatActivity {

    /**
     * Constant that represents the Reviews resource path.
     */
    private static final String REVIEWS_RESOURCE_PATH = FeedbackMonkeyAPI.getResourcePath("Reviews");
    /**
     * Constant that represents the new review resource path under reviews resource.
     */
    private static final String NEW_REVIEW_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Reviews", "Create New Review");
    /**
     * Constant that represents the get review answer map resource path under reviews resource.
     */
    private static final String GET_REVIEW_ANSWER_MAP_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Reviews", "Get Review Answer Map");
    /**
     * ListView that is hold by the scroll view
     */
    private ListView listSurveysListView;
    /**
     * SurveyItemListViewAdapter with the current view adapter
     */
    private SurveyItemListViewAdapter currentAdapter;
    /**
     * Integer with the current pagination ID
     */
    private int currentPaginationID;
    /**
     * String with the current authentication token
     */
    private String authenticationToken;

    /**
     * HTTP Response code when attempting to retrieve new review data.
     */
    private int reviewRestResponseCode;

    /**
     * HTTP Response body when attempting to retrieve new review data.
     */
    private String reviewRestResponseBody;
    /**
     * List with all current surveys displayed on the list.
     */
    private List<SurveyService> currentSurveys;
    /**
     * Map that holds SurveyServices of the Surveys that have a saved pending review and its corresponding file.
     */
    private Map<SurveyService, File> pendingReviewsMap;

    /**
     * Boolean flag to know if the activity was started by the UserProfileActivity or not.
     */
    private boolean userProfileActivityFlag;

    /**
     * Creates the current activity.
     *
     * @param savedInstanceState Bundle with the previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfileActivityFlag = false;
        configureActivityStart(getIntent().getBundleExtra(ListSurveyActivity.class.getSimpleName()));
        setContentView(R.layout.activity_list_survey_activity);
        configure();
    }

    /**
     * Configures the activity start based on a bundle that was passed
     *
     * @param bundle Bundle with the bundle which was brought by another activity
     */
    private void configureActivityStart(Bundle bundle) {
        authenticationToken = bundle.getString("authenticationToken");
        configureUserProfileBundle(bundle);
        int surveysToAdd = bundle.size() - 1;
        pendingReviewsMap = buildSurveyServiceFileMap();
        currentSurveys = new ArrayList<>(surveysToAdd);

        for (int i = 0; i < surveysToAdd; i++) {
            SurveyService surveyService = (SurveyService) bundle.getSerializable("" + i);
            currentSurveys.add(surveyService);
        }
        currentPaginationID++;
    }

    /**
     * Sets a flag if this activity was started by UserProfileActivity
     *
     * @param bundle Bundle that was passed by another activity
     */
    private void configureUserProfileBundle(Bundle bundle) {
        String flag = bundle.getString("sentFromProfileActivity");
        if (flag != null && flag.equals(UserProfileActivity.class.getSimpleName())) {
            userProfileActivityFlag = true;
            bundle.remove("sentFromProfileActivity");
        }
    }

    /**
     * Configures the current view and view adapter properties.
     */
    private void configure() {
        listSurveysListView = findViewById(R.id.listViewListSurveys);
        currentAdapter = new SurveyItemListViewAdapter(getActivity());
        currentAdapter.addAll(currentSurveys);
        listSurveysListView.setAdapter(currentAdapter);
        configureListViewOnItemClick();
        configureListViewOnLongItemClick();
    }

    /**
     * Configures the onItemClick event of the current list view
     */
    private void configureListViewOnItemClick() {

        if (userProfileActivityFlag) {
            setOnItemClickListenerForAnsweredSurveys();
        } else {
            setOnItemClickListenerForAnswerSurvey();
        }
    }

    private void setOnItemClickListenerForAnsweredSurveys() {
        listSurveysListView.setOnItemClickListener((parent, view, position, id) -> {

            //Start a new Thread responsible for establishing a connection to the HTTP server
            Thread connectionThread = new Thread(requestReviewAnswerMap(position));
            connectionThread.start();
            try {
                connectionThread.join();
                if (reviewRestResponseCode == HttpsURLConnection.HTTP_OK) {
                    ReviewJSONService reviewJSONService = new Gson().fromJson(reviewRestResponseBody, ReviewJSONService.class);
                    startCheckReviewActivity(reviewJSONService);
                } else {
                    //TODO: create message dialog informing user an error occurred
                    ToastNotification.show(this, "Erro: " + reviewRestResponseCode);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });
    }

    /**
     * Sets on item click listeners so the user can answer surveys
     */
    private void setOnItemClickListenerForAnswerSurvey() {
        listSurveysListView.setOnItemClickListener((parent, view, position, id) -> {

            SurveyService chosenSurveyService = currentSurveys.get(position);
            File reviewFile = pendingReviewsMap.get(chosenSurveyService);

            try {
                if (reviewFile != null) {
                    /**
                     * There is a pending review file saved locally.
                     */
                    ReviewXMLService.instance().resumeReview(reviewFile);
                    startQuestionActivity();
                    finish();
                } else {
                    /**
                     * There is no pending review file saved locally, so a request must be made for a new review.
                     * A file with the SurveyService in JSON format is also created for future use.
                     */
                    File surveyServiceFile = new File(getSurveyServicesDirectory(), "survey_".concat(chosenSurveyService.getSurveyID().concat(".json")));

                    try (FileOutputStream fileOutputStream = new FileOutputStream(surveyServiceFile)) {
                        String surveyServiceAsJSON = new Gson().toJson(chosenSurveyService);
                        fileOutputStream.write(surveyServiceAsJSON.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Start a new Thread responsible for establishing a connection to the HTTP server
                    Thread connectionThread = new Thread(requestNewReview(position));
                    connectionThread.start();

                    //wait for the connection thread in order to get the REST response containing review data
                    try {
                        connectionThread.join();
                        if (reviewRestResponseCode == HttpsURLConnection.HTTP_OK) {
                            String fileContent = reviewRestResponseBody;
                            ReviewXMLService.instance().createNewReviewFile(getPendingReviewsDirectory(), fileContent);
                            startQuestionActivity();
                            finish();
                        } else {
                            //TODO: create messagediaolog informing user an error occured
                            ToastNotification.show(this, "Erro: " + reviewRestResponseCode);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                ToastNotification.show(this, "Ocorreu um erro a carregar a sua avaliação!");
            }
        });
    }

    /**
     * Configures the onLongItemClick event of the current list view
     */
    private void configureListViewOnLongItemClick() {
        listSurveysListView.setOnItemLongClickListener((adapterView, view, position, timeClicked) -> {
            SurveyDescriptionDialog surveyDialog = new SurveyDescriptionDialog(ListSurveyActivity.this
                    , currentSurveys.get(position));
            surveyDialog.show();
            return true;
        });
    }

    /**
     * Creates a survey services directory or fetches the existing one.
     *
     * @return survey services directory
     */
    private File getSurveyServicesDirectory() {
        String surveyServices = "survey_services";

        File filesDirectory = getFilesDir();

        File surveyServicesDirectory = new File(filesDirectory, surveyServices);

        if (!surveyServicesDirectory.exists()) {
            surveyServicesDirectory.mkdir();
        }

        return surveyServicesDirectory;
    }

    /**
     * Fetches all the survey IDs that have pending reviews.
     *
     * @return list of strings that contain the survey ID of a pending review
     */
    private Map<SurveyService, File> buildSurveyServiceFileMap() {
        Map<SurveyService, File> result = new HashMap<>();
        for (File file : getPendingReviewsDirectory().listFiles()) {
            String surveyID = file.getName().split("_")[2].replace(".xml", "");
            result.put(new SurveyService(surveyID), file);
        }
        return result;
    }

    /**
     * Creates a pending reviews directory or fetches the existing one.
     *
     * @return pending reviews directory
     */
    private File getPendingReviewsDirectory() {
        String pendingReviews = "pending_reviews";

        File filesDirectory = getFilesDir();

        File pendingReviewsDirectory = new File(filesDirectory, pendingReviews);

        if (!pendingReviewsDirectory.exists()) {
            pendingReviewsDirectory.mkdir();
        }

        return pendingReviewsDirectory;
    }

    /**
     * Starts a new question activity.
     */
    private void startQuestionActivity() {
        Intent questionIntent = new Intent(ListSurveyActivity.this, QuestionActivity.class);
        questionIntent.putExtra("authenticationToken", authenticationToken);
        startActivity(questionIntent);
    }

    /**
     * Starts the CheckReviewActivity.
     */
    private void startCheckReviewActivity(ReviewJSONService reviewJSONService) {
        Intent checkReviewActivityIntent = new Intent(ListSurveyActivity.this, CheckReviewActivity.class);
        Bundle bundle = new Bundle();
        ArrayList<String> questions = new ArrayList<>(reviewJSONService.getQuestionAnswerMap().keySet());
        ArrayList<String> answers = new ArrayList<>(reviewJSONService.getQuestionAnswerMap().values());
        bundle.putStringArrayList("questions", questions);
        bundle.putStringArrayList("answers", answers);
        checkReviewActivityIntent.putExtras(bundle);
        startActivity(checkReviewActivityIntent);
    }

    /**
     * Creates a new REST request for fetching review data.
     *
     * @param position list view
     * @return Runnable that processes the REST response
     */
    private Runnable requestNewReview(int position) {
        return () -> {
            try {
                String surveyID = currentSurveys.get(position).getSurveyID();
                Response reviewRestResponse = RESTRequest.create(BuildConfig.SERVER_URL.
                        concat(FeedbackMonkeyAPI.getAPIEntryPoint().
                                concat(REVIEWS_RESOURCE_PATH).
                                concat(NEW_REVIEW_RESOURCE_PATH).
                                concat("/" + authenticationToken).
                                concat("/" + surveyID))).
                        GET();

                reviewRestResponseCode = reviewRestResponse.code();
                reviewRestResponseBody = reviewRestResponse.body().string();

            } catch (IOException e) {
                e.printStackTrace();
                ToastNotification.show(this, getString(R.string.no_internet_connection));
            }
        };
    }

    /**
     * Creates a new REST Request for fetching a review answer map.
     *
     * @param position index of the list view's item that was clicked
     * @return Runnable that processes the REST Response
     */
    private Runnable requestReviewAnswerMap(int position) {
        return () -> {
            try {
                String surveyID = currentSurveys.get(position).getSurveyID();
                Response reviewAnswerMapResponse = RESTRequest.create(BuildConfig.SERVER_URL.
                        concat(FeedbackMonkeyAPI.getAPIEntryPoint().
                                concat(REVIEWS_RESOURCE_PATH).
                                concat(GET_REVIEW_ANSWER_MAP_RESOURCE_PATH).
                                concat("/" + authenticationToken)
                                .concat("/" + surveyID))).
                        GET();

                reviewRestResponseCode = reviewAnswerMapResponse.code();
                reviewRestResponseBody = reviewAnswerMapResponse.body().string();

            } catch (IOException e) {
                e.printStackTrace();
                ToastNotification.show(this, getString(R.string.no_internet_connection));
            }
        };
    }

    /**
     * Method that fetches the next surveys being listed according to a certain pagination ID
     */
    private void fetchSurveys() {
        Thread thread = new Thread(fetchNextSurveys());
        thread.start();
    }

    /**
     * Fetches next surveys being listed according to the current pagination ID
     *
     * @return Runnable with the thread being fetched the next surveys being listed
     * according to the current pagination ID
     */
    private Runnable fetchNextSurveys() {
        return () -> {
            try {
                List<SurveyService> nextSurveys = getNextSurveys();
                if (nextSurveys != null) {
                    ListSurveyActivity.this.runOnUiThread(addSurveys(nextSurveys));
                }
            } catch (IOException ioException) {
                //TODO: log exceptions to logger file
                ToastNotification.show(this, getString(R.string.no_internet_connection));
            }
        };
    }

    /**
     * Gets the next current surveys available for the current user depending on the pagination ID
     *
     * @return List with all the surveys available for the current user depending on the pagination ID,
     * null if an error occurred
     * @throws IOException Throws IOException if an error occurred while sending the REST Request
     */
    private List<SurveyService> getNextSurveys() throws IOException {
        return new SurveyServiceController(authenticationToken).getSurveysByPaginationID((short) currentPaginationID++);
    }

    /**
     * Adds to the current adapter a list of surveys
     *
     * @param surveys List with the new list of surveys being added to the adapter
     */
    private Runnable addSurveys(List<SurveyService> surveys) {
        return () -> currentAdapter.addAll(surveys);
    }

    /**
     * Returns this instance of the activity. This is useful when access to the outer class is needed.
     *
     * @return this instance of the activty.
     */
    private ListSurveyActivity getActivity() {
        return ListSurveyActivity.this;
    }

    /**
     * SurveyItemListViewAdapter that represents a custom adapter used for the ListView that holds the
     * surveys that an user can currently answer
     *
     * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
     * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
     */
    private class SurveyItemListViewAdapter extends ArrayAdapter<SurveyService> {
        /**
         * Builds a new {@link SurveyItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity    Activity with the activity which the ListView is being added the adapter
         * @param surveyItems List with a previous survey items predefined
         */
        public SurveyItemListViewAdapter(Activity activity, List<SurveyService> surveyItems) {
            super(activity, R.layout.survey_list_row_item, surveyItems);
        }

        /**
         * Builds a new {@link SurveyItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity Activity with the activity which the ListView is being added the adapter
         */
        public SurveyItemListViewAdapter(Activity activity) {
            super(activity, R.layout.survey_list_row_item);
        }

        /**
         * Returns a converted view item for the adapter on a certain item position
         *
         * @param position    Integer with the adapter item position
         * @param convertView View with the view being converted as item
         * @param parent      ViewGroup with the group parent
         * @return View with the converted view as a adapter item
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.survey_list_row_item, parent, false);
            }
            TextView viewSurveyName = convertView.findViewById(R.id.textViewSurveyItemListRow);
            viewSurveyName.setText(getItem(position).getSurveyName());
            return convertView;
        }
    }

}
