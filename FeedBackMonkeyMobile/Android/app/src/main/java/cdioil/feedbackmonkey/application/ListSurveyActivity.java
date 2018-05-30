package cdioil.feedbackmonkey.application;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.json.SurveyJSONService;
import okhttp3.Response;

/**
 * ListSurveyActivity that represents the activity that lists the current available
 * surveys that an user can answer
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
 */
public class ListSurveyActivity extends AppCompatActivity {
    /**
     * Constant that represents the Surveys resource path
     */
    private static final String SURVEYS_RESOURCE_PATH=FeedbackMonkeyAPI.getResourcePath("Surveys");
    /**
     * Constant that represents the user available surveys resource path under survey resource
     */
    private static final String USER_AVAILABLE_RESOURCE_PATH=FeedbackMonkeyAPI.getSubResourcePath("Surveys","Available User Surveys");
    /**
     * Constant that represents the surveys available to the user with a given code resource path under survey resource.
     */
    private static final String PRODUCT_CODE_AVAILABLE_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Surveys", "Available Surveys By Product Code");
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
     * Creates the current view
     * @param savedInstanceState Bundle with the previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationToken=getIntent().getExtras().getString("authenticationToken");
        if(getIntent().getExtras().containsKey("itemCode")){
            itemCode = getIntent().getExtras().getString("itemCode");
        }
        setContentView(R.layout.activity_list_survey_activity);
        configure();
    }

    /**
     * Configures the current view
     */
    private void configure(){
        listSurveysListView =findViewById(R.id.listViewListSurveys);
        currentAdapter=new SurveyItemListViewAdapter(this);
        listSurveysListView.setAdapter(currentAdapter);
        fetchSurveys();
    }

    /**
     * Method that fetches the next surveys being listed according to a certain pagination ID
     */
    private void fetchSurveys() {
        Thread thread=new Thread(fetchNextSurveys());
        thread.start();
    }

    /**
     * Fetches next surveys being listed according to the current pagination ID
     * @return Runnable with the thread being fetched the next surveys being listed
     * according to the current pagination ID
     */
    private Runnable fetchNextSurveys(){
        return () -> {
            try {
                List<String> nextSurveys = getNextSurveys();
                if(nextSurveys!=null){
                    ListSurveyActivity.this.runOnUiThread(addSurveys(nextSurveys));
                }
            }catch(IOException ioException){
                System.out.println("-------------------- >"+ioException.getMessage());
            }
        };
    }

    /**
     * Gets the next current surveys available for the current user depending on the pagination ID
     * @return List with all the surveys available for the current user depending on the pagination ID,
     * null if an error ocured
     * @throws IOException Throws IOException if an error ocured while sending the REST Request
     */
    private List<String> getNextSurveys() throws IOException {
        String userAvailableSurveysURL;

        if(itemCode == null){
            userAvailableSurveysURL=BuildConfig.SERVER_URL
                    .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                    .concat(SURVEYS_RESOURCE_PATH)
                    .concat(USER_AVAILABLE_RESOURCE_PATH)
                    .concat(authenticationToken)
                    .concat("?paginationID="+(currentPaginationID++));
        }else{
            userAvailableSurveysURL = BuildConfig.SERVER_URL
                    .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                    .concat(SURVEYS_RESOURCE_PATH)
                    .concat(PRODUCT_CODE_AVAILABLE_RESOURCE_PATH)
                    .concat(authenticationToken)
                    .concat("/")
                    .concat(itemCode);
        }
        Response requestResponse=RESTRequest.create(userAvailableSurveysURL)
                .GET();
        String responseBody=requestResponse.body().string();
        switch(requestResponse.code()){
            case 200:
                List<String> surveyItems=new ArrayList<>();
                Gson gson=new Gson();
                Type surveyJSONServicesType=new TypeToken<ArrayList<SurveyJSONService>>(){}.getType();
                List<SurveyJSONService> surveyJSONServices=gson.fromJson(responseBody,surveyJSONServicesType);
                for(int i=0;i<surveyJSONServices.size();i++){
                    surveyItems.add(surveyJSONServices.get(i).getSurveyName());
                }
                return surveyItems;
            case 400:
                System.out.println("400 ->>>\n"+responseBody);
                return null;
            case 401:
                System.out.println("401 ->>>\n"+responseBody);
            case 404:
                //happens when no products are found
                break;

        }
        return null;
    }

    /**
     * Adds to the current adapter a list of surveys
     * @param surveys List with the new list of surveys being added to the adapter
     */
    private Runnable addSurveys(List<String> surveys){
        return () -> currentAdapter.addAll(surveys);
    }
    /**
     * SurveyItemListViewAdapter that represents a custom adapter used for the ListView that holds the
     * surveys that an user can currently answer
     * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
     * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
     */
    private class SurveyItemListViewAdapter extends ArrayAdapter<String>{
        /**
         * Builds a new {@link SurveyItemListViewAdapter} being adapted on a certain activity ListView
         * @param activity Activity with the activity which the ListView is being added the adapter
         * @param surveyItems List with a previous survey items predefined
         */
        public SurveyItemListViewAdapter(Activity activity,List<String> surveyItems) {
            super(activity,R.layout.survey_item_list_row,surveyItems);
        }

        /**
         * Builds a new {@link SurveyItemListViewAdapter} being adapted on a certain activity ListView
         * @param activity Activity with the activity which the ListView is being added the adapter
         */
        public SurveyItemListViewAdapter(Activity activity){
            super(activity,R.layout.survey_item_list_row);
        }
        /**
         * Returns a converted view item for the adapter on a certain item position
         * @param position Integer with the adapter item position
         * @param convertView View with the view being converted as item
         * @param parent ViewGroup with the group parent
         * @return View with the converted view as a adapter item
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView==null){
                convertView=LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.survey_item_list_row,parent,false);
            }
            TextView viewSurveyName=convertView.findViewById(R.id.textViewSurveyItemListRow);
            viewSurveyName.setText(getItem(position));
            return convertView;
        }
    }

}
