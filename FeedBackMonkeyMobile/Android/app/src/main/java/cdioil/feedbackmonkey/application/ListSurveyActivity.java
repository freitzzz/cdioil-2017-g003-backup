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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.application.services.SurveyService;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.json.SurveyJSONService;
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
     * Constant that represents the Surveys resource path
     */
    private static final String SURVEYS_RESOURCE_PATH = FeedbackMonkeyAPI.getResourcePath("Surveys");
    /**
     * Constant that represents the user available surveys resource path under survey resource
     */
    private static final String USER_AVAILABLE_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Surveys", "Available User Surveys");
    /**
     * Constant that represents the surveys available to the user with a given code resource path under survey resource.
     */
    private static final String PRODUCT_CODE_AVAILABLE_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Surveys", "Available Surveys By Product Code");
    /**
     * Constant that represents the Reviews resource path.
     */
    private static final String REVIEWS_RESOURCE_PATH = FeedbackMonkeyAPI.getResourcePath("Reviews");
    /**
     * Constant that represents the new review resource path under reviews resource.
     */
    private static final String NEW_REVIEW_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Reviews", "Create New Review");
    /**
     * Constant representing an error message to be displayed when a connection error occurs.
     */
    private static final String ERROR_CONNECTION_LOST = "Ocorreu um erro com a sua ligação à internet";

    /**
     * Constant representing an error message to be displayed when an error occurs whilst parsing a file.
     */
    private static final String ERROR_PARSING_FILE = "Ocorreu um erro na leitura do ficheiro recebido";
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
     * String with the scanned item code.
     */
    private String itemCode;

    /**
     * HTTP Response code when attempting to retrieve new review data.
     */
    private int reviewRestResponseCode;

    /**
     * HTTP Response body when attempting to retrieve new review data.
     */
    private String reviewRestResponseBody;
    /**
     * List with all current surveys displayed on the list
     */
    private List<SurveyService> currentSurveys;


    /**
     * Creates the current activity.
     *
     * @param savedInstanceState Bundle with the previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationToken = getIntent().getExtras().getString("authenticationToken");
        if (getIntent().getExtras().containsKey("itemCode")) {
            itemCode = getIntent().getExtras().getString("itemCode");
        }
        setContentView(R.layout.activity_list_survey_activity);
        configure();
        fetchSurveys();
    }

    /**
     * Configures the current view and view adapter properties.
     */
    private void configure() {
        currentSurveys=new ArrayList<>();
        listSurveysListView = findViewById(R.id.listViewListSurveys);
        currentAdapter = new SurveyItemListViewAdapter(getActivity());
        listSurveysListView.setAdapter(currentAdapter);
        configureListViewOnItemClick();
        configureListViewOnLongItemClick();
    }

    /**
     * Configures the onItemClick event of the current list view
     */
    private void configureListViewOnItemClick(){
        listSurveysListView.setOnItemClickListener((parent, view, position, id) -> {

            //Start a new Thread responsible for establishing a connection to the HTTP server
            Thread connectionThread = new Thread(requestNewReview(position));
            connectionThread.start();

            //wait for the connection thread in order to get the REST response containing review data
            try {
                connectionThread.join();
                if (reviewRestResponseCode == HttpsURLConnection.HTTP_OK) {
                    try {

                        String fileContent = reviewRestResponseBody;

                        //TODO: Add check when creating file, depending on whether or not that survey has a pending review
                        ReviewXMLService.instance().createNewReviewFile(getPendingReviewsDirectory(), fileContent);
                        startQuestionActivity();
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                        ToastNotification.show(this, ERROR_CONNECTION_LOST);
                    } catch (ParserConfigurationException | SAXException e) {
                        e.printStackTrace();
                        ToastNotification.show(this, ERROR_PARSING_FILE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //TODO: create messagediaolog informing user an error occured
                    ToastNotification.show(this, "Erro: " + reviewRestResponseCode);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Configures the onLongItemClick event of the current list view
     */
    private void configureListViewOnLongItemClick(){
        listSurveysListView.setOnItemLongClickListener((adapterView, view, position, timeClicked)->{
            SurveyDescriptionDialog surveyDialog=new SurveyDescriptionDialog(ListSurveyActivity.this
                    ,currentSurveys.get(position));
            surveyDialog.show();
            return true;
        });
    }
    /**
     * Creates a pending reviews directory or fetches the existing one.
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
                ToastNotification.show(this, ERROR_CONNECTION_LOST);
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
                List<String> nextSurveys = getNextSurveys();
                if (nextSurveys != null) {
                    ListSurveyActivity.this.runOnUiThread(addSurveys(nextSurveys));
                }
            } catch (IOException ioException) {
                //TODO: log exceptions to logger file
                ToastNotification.show(this, ERROR_CONNECTION_LOST);
            }
        };
    }

    /**
     * Gets the next current surveys available for the current user depending on the pagination ID
     *
     * @return List with all the surveys available for the current user depending on the pagination ID,
     * null if an error ocured
     * @throws IOException Throws IOException if an error occurred while sending the REST Request
     */
    private List<String> getNextSurveys() throws IOException {
        String userAvailableSurveysURL;

        if (itemCode == null) {
            userAvailableSurveysURL = BuildConfig.SERVER_URL
                    .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                    .concat(SURVEYS_RESOURCE_PATH)
                    .concat(USER_AVAILABLE_RESOURCE_PATH)
                    .concat(authenticationToken)
                    .concat("?paginationID=" + (currentPaginationID++));
        } else {
            userAvailableSurveysURL = BuildConfig.SERVER_URL
                    .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                    .concat(SURVEYS_RESOURCE_PATH)
                    .concat(PRODUCT_CODE_AVAILABLE_RESOURCE_PATH)
                    .concat(authenticationToken)
                    .concat("/")
                    .concat(itemCode);
        }
        Response requestResponse = RESTRequest.create(userAvailableSurveysURL)
                .GET();
        String responseBody = requestResponse.body().string();
        switch (requestResponse.code()) {
            case 200:
                List<String> surveyItems = new ArrayList<>();
                Gson gson = new Gson();
                Type surveyJSONServicesType = new TypeToken<ArrayList<SurveyJSONService>>() {
                }.getType();
                List<SurveyJSONService> surveyJSONServices = gson.fromJson(responseBody, surveyJSONServicesType);
                for (int i = 0; i < surveyJSONServices.size(); i++) {
                    currentSurveys.add(new SurveyService(surveyJSONServices.get(i)));
                    surveyItems.add(currentSurveys.get(currentSurveys.size()-1).getSurveyName());
                }
                return surveyItems;
            case 400:
                System.out.println("400 ->>>\n" + responseBody);
                break;
            case 401:
                System.out.println("401 ->>>\n" + responseBody);
                break;
            case 404:
                //happens when no products are found
                break;

        }
        return null;
    }

    /**
     * Adds to the current adapter a list of surveys
     *
     * @param surveys List with the new list of surveys being added to the adapter
     */
    private Runnable addSurveys(List<String> surveys) {
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
    private class SurveyItemListViewAdapter extends ArrayAdapter<String> {
        /**
         * Builds a new {@link SurveyItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity    Activity with the activity which the ListView is being added the adapter
         * @param surveyItems List with a previous survey items predefined
         */
        public SurveyItemListViewAdapter(Activity activity, List<String> surveyItems) {
            super(activity, R.layout.survey_item_list_row, surveyItems);
        }

        /**
         * Builds a new {@link SurveyItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity Activity with the activity which the ListView is being added the adapter
         */
        public SurveyItemListViewAdapter(Activity activity) {
            super(activity, R.layout.survey_item_list_row);
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
                        .inflate(R.layout.survey_item_list_row, parent, false);
            }
            TextView viewSurveyName = convertView.findViewById(R.id.textViewSurveyItemListRow);
            viewSurveyName.setText(getItem(position));
            return convertView;
        }
    }

}
