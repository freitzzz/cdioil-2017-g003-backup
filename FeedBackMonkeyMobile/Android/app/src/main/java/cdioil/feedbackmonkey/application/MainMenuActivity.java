package cdioil.feedbackmonkey.application;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;

import cdioil.feedbackmonkey.application.services.RequestNewReviewController;
import cdioil.feedbackmonkey.application.services.SurveyService;
import cdioil.feedbackmonkey.application.services.SurveyServiceController;
import cdioil.feedbackmonkey.restful.exceptions.RESTfulException;
import cdioil.feedbackmonkey.authz.UserProfileActivity;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;
import cdioil.feedbackmonkey.utils.ToastNotification;
import okhttp3.Response;

public class MainMenuActivity extends AppCompatActivity {
    private static final int CURRENT_WINDOW_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    /**
     * Constant that represents the Reviews resource path.
     */
    private static final String REVIEWS_RESOURCE_PATH = FeedbackMonkeyAPI.getResourcePath("Reviews");
    /**
     * Constant that represents the save review resource path.
     */
    private static final String SAVE_REVIEW_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Reviews", "Save Review");
    private String[] menuOptions = {"Listar Inquéritos", "Scan de Códigos", "Perfil", "Partilhe as suas ideias connosco!"};
    private int[] menuOptionImages = {R.drawable.survey_icon, R.drawable.code_search_icon, R.drawable.profile_icon,
            R.drawable.question_mark};
    private String[] menuBackgroundColors = {"#ffffff", "#ffd05b", "#ffffff", "#ffd05b"};
    private String authenticationToken;
    private int optionIndex;
    /**
     * Receiver responsible for receiving signals regarding network state changes.
     */
    private ConnectionReceiver connectionReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        configureView();
        authenticationToken = getIntent().getExtras().getString("authenticationToken");
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        connectionReceiver = new ConnectionReceiver();
        registerReceiver(connectionReceiver, filter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(connectionReceiver);
        super.onPause();
    }

    private void configureView() {
        ListView mainMenuListView = findViewById(R.id.mainMenuListView);
        mainMenuListView.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i) {
                case 0:
                    prepareListSurveyActivityStart();
                    break;
                case 1:
                    scanItemCode();
                    break;
                case 2:
                    startUserProfileActivity();
                    break;
                case 3:
                    startSuggestionActivity();
                    break;
            }
        });
        MainMenuItemListViewAdapter mainMenuItemListViewAdapter = new MainMenuItemListViewAdapter(MainMenuActivity.this);
        mainMenuListView.setAdapter(mainMenuItemListViewAdapter);
        for (int i = 0; i < menuOptions.length; i++) {
            mainMenuItemListViewAdapter.add(i);
        }
    }

    /**
     * Creates a finished reviews directory or fetches the existing one.
     *
     * @return finished reviews directory
     */
    private File getFinishedReviewsDirectory() {
        String finishedReviews = "finished_reviews";

        File filesDirectory = getFilesDir();

        File finishedReviewsDirectory = new File(filesDirectory, finishedReviews);

        if (!finishedReviewsDirectory.exists()) {
            finishedReviewsDirectory.mkdir();
        }

        return finishedReviewsDirectory;
    }

    /**
     * Starts the ListSurveyActivity with authentication token as the single content within the bundle.
     */
    private void prepareListSurveyActivityStart() {
        new Thread(fetchSurveysToAnswers()).start();
    }

    /**
     * Starts the UserProfileActivity.
     */
    private void startUserProfileActivity() {
        Intent userProfileActivityIntent = new Intent(MainMenuActivity.this, UserProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("authenticationToken", authenticationToken);
        userProfileActivityIntent.putExtras(bundle);
        startActivity(userProfileActivityIntent);
    }

    private void startSuggestionActivity(){
        Intent submitSuggestionIntent = new Intent(MainMenuActivity.this, SubmitSuggestionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("authenticationToken", authenticationToken);
        submitSuggestionIntent.putExtras(bundle);
        startActivity(submitSuggestionIntent);
    }


    /**
     * Scans a SurveyItem's code.
     */
    private void scanItemCode() {

        IntentIntegrator integrator = new IntentIntegrator(this);

        //Set integrator settings here
        //Define which codes can be read
        integrator.setBeepEnabled(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setOrientationLocked(false);     //orientation is set within the manifest
        integrator.setPrompt("Por favor aponte a sua câmera para um código válido");

        integrator.initiateScan();
    }

    //Used by code scanner
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Leitura Cancelada", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("MainActivity", "Scanned");

                String itemCode = result.getContents();

                if (!itemCode.trim().isEmpty()) {
                    Toast.makeText(this, "Código Lido: " + itemCode, Toast.LENGTH_LONG).show();
                    new Thread(fetchScannedCodeSurveys(itemCode)).start();
                } else {
                    Toast.makeText(this, "Por favor leia um código válido", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Method that fetches all active surveys which products being reviewed
     * have a certain code which was previously scanned by the user
     * @param productCode String with the scanned product code
     * @return Runnable with the runnable which will fetch the active surveys which products
     * being reviewed have a certain scanned code
     */
    private Runnable fetchScannedCodeSurveys(String productCode){
        return () -> {
            try {
                List<SurveyService> surveysToAnswer = new SurveyServiceController(authenticationToken).getSurveysByProductCode(productCode);
                if(surveysToAnswer.size()==1){
                    startAnswerSurveyActivity(surveysToAnswer.get(0));
                }else{
                    startListSurveyActivity(surveysToAnswer);
                }
            }catch(RESTfulException restfulException){
                switch(restfulException.getCode()){
                    case HttpsURLConnection.HTTP_BAD_REQUEST:
                        ToastNotification.show(MainMenuActivity.this,getString(R.string.no_surveys_with_certain_code));
                        break;
                    case HttpsURLConnection.HTTP_UNAUTHORIZED:
                        //#TO-DO: Authentication Token is not valid anymore, Go back to LoginActivity
                        break;
                    default:
                        ToastNotification.show(MainMenuActivity.this,"MENSAGEM DE ERRO CONEXAO AO SERVIDOR REST");
                }
            }catch(IOException ioException){
                ToastNotification.show(MainMenuActivity.this,getString(R.string.no_internet_connection));
            }
        };
    }

    /**
     * Method fetches all active surveys that the current user can answer based on a
     * pagination ID
     * @return Runnable with the runnable action which will fetch the surveys to answer
     */
    private Runnable fetchSurveysToAnswers(){
        return () -> {
            try {
                List<SurveyService> surveysToAnswer = new SurveyServiceController(authenticationToken).getSurveysByPaginationID((short)0);
                startListSurveyActivity(surveysToAnswer);
            }catch(RESTfulException restfulException){
                switch(restfulException.getCode()){
                    case HttpsURLConnection.HTTP_BAD_REQUEST:
                        ToastNotification.show(MainMenuActivity.this,getString(R.string.no_available_surveys));
                        break;
                    case HttpsURLConnection.HTTP_UNAUTHORIZED:
                        //#TO-DO: Authentication Token is not valid anymore, Go back to LoginActivity
                        break;
                    default:
                        ToastNotification.show(MainMenuActivity.this,"MENSAGEM DE ERRO CONEXAO AO SERVIDOR REST");
                }
            }catch(IOException ioException){
                ToastNotification.show(MainMenuActivity.this,getString(R.string.no_internet_connection));
            }
        };
    }
    /**
     * Starts a ListSurveyActivity with a certain list of surveys
     * @param surveyServices List with the surveys to show on the ListSurveyActivity
     */
    private void startListSurveyActivity(List<SurveyService> surveyServices){
        Intent questionIntent = new Intent(MainMenuActivity.this,ListSurveyActivity.class);
        Bundle bundle=new Bundle(surveyServices.size()+1);
        bundle.putString("authenticationToken", authenticationToken);
        for(int i=0;i<surveyServices.size();i++)bundle.putSerializable(""+i,surveyServices.get(i));
        questionIntent.putExtra(ListSurveyActivity.class.getSimpleName(),bundle);
        MainMenuActivity.this.startActivity(questionIntent);
    }
    /**
     * Starts a new AnswerSurveyActivity with a certain SurveyService which represents
     * the survey being reviewed
     */
    private void startAnswerSurveyActivity(SurveyService surveyService){
        Thread requestReviewThread=new Thread(startReviewRequest(surveyService));
        requestReviewThread.start();

    }
    private Runnable startReviewRequest(SurveyService surveyService){
        return () -> {
            try {
                RequestNewReviewController requestNewReview=new RequestNewReviewController(surveyService);
                try {
                    requestNewReview.createNewReview(requestNewReview.requestNewReview(authenticationToken),MainMenuActivity.this);
                    Intent questionIntent = new Intent(MainMenuActivity.this, QuestionActivity.class);
                    questionIntent.putExtra("authenticationToken", authenticationToken);
                    MainMenuActivity.this.startActivity(questionIntent);
                } catch (ParserConfigurationException | SAXException e) {
                    System.out.println(e.getMessage());
                }
            }catch(RESTfulException restfulException){
                switch(restfulException.getCode()){
                    case HttpsURLConnection.HTTP_BAD_REQUEST:
                        //#TO-DO: There are a lot of codes here to treat
                        ToastNotification.show(MainMenuActivity.this,"#TO-DO (400) newReview");
                        break;
                    case HttpsURLConnection.HTTP_NOT_FOUND:
                        ToastNotification.show(MainMenuActivity.this,getString(R.string.survey_not_availalbe));
                        break;
                    default:
                        ToastNotification.show(MainMenuActivity.this,"MENSAGEM DE ERRO CONEXAO AO SERVIDOR REST");
                }
            }catch(IOException ioException){
                ToastNotification.show(MainMenuActivity.this,getString(R.string.no_internet_connection));
            }
        };
    }

    /**
     * MainMenuItemListViewAdapter that represents a custom adapter used for the ListView that holds the
     * main menu options
     *
     * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
     * @author <a href="1161191@isep.ipp.pt">Margarida Guerra</a>
     */
    private class MainMenuItemListViewAdapter extends ArrayAdapter<Integer> {

        /**
         * Builds a new {@link ListSurveyActivity.SurveyItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity    Activity with the activity which the ListView is being added the adapter
         * @param surveyItems List with a previous survey items predefined
         */
        public MainMenuItemListViewAdapter(Activity activity, List<Integer> surveyItems) {
            super(activity, R.layout.survey_list_row_item, surveyItems);
        }

        /**
         * Builds a new {@link ListSurveyActivity.SurveyItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity Activity with the activity which the ListView is being added the adapter
         */
        public MainMenuItemListViewAdapter(Activity activity) {
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
                        .inflate(R.layout.main_menu_option, parent, false);
            }
            if (optionIndex < menuOptionImages.length) {
                RelativeLayout relativeLayout = convertView.findViewById(R.id.mainMenuOptionRelativeLayout);
                relativeLayout.getLayoutParams().height = CURRENT_WINDOW_HEIGHT / 4;
                relativeLayout.setBackgroundColor(Color.parseColor(menuBackgroundColors[optionIndex]));

                ImageView imageView = convertView.findViewById(R.id.mainMenuOptionImgView);
                TextView textView = convertView.findViewById(R.id.mainMenuOptionTxtView);
                textView.setText(menuOptions[optionIndex]);
                if (optionIndex % 2 == 0) {
                    textView.setTextColor(Color.parseColor("#3d3d3d"));
                }
                imageView.setImageResource(menuOptionImages[optionIndex]);
                optionIndex++;
            }
            return convertView;
        }

    }

    /**
     * Receiver responsible for checking for active internet connection.
     */
    private class ConnectionReceiver extends BroadcastReceiver {

        /**
         * HTTP Response code when attempting to submit a review.
         */
        private int reviewRestResponseCode;

        private ConnectionReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (checkConnectivity()) {
                submitPendingReviews();
            }
        }

        /**
         * Method that attempts to submit all pending reviews if an internet connection is active.
         */
        private void submitPendingReviews() {

            try {
                File[] files = getFinishedReviewsDirectory().listFiles();

                for (File f : files) {

                    String fileContent = ReviewXMLService.parseReviewFile(f);

                    String reviewID = f.getName().split("_")[1];

                    String saveReviewURL = BuildConfig.SERVER_URL
                            .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                            .concat(REVIEWS_RESOURCE_PATH)
                            .concat(SAVE_REVIEW_RESOURCE_PATH)
                            .concat("/")
                            .concat(reviewID);

                    Thread connectionThread = new Thread(() -> {
                        try {
                            Response saveReviewResponse = RESTRequest.create(saveReviewURL)
                                    .withMediaType(RESTRequest.RequestMediaType.XML).withBody(fileContent).POST();
                            reviewRestResponseCode = saveReviewResponse.code();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    connectionThread.start();

                    connectionThread.join();
                    if (reviewRestResponseCode == HttpsURLConnection.HTTP_OK) {
                        f.delete();
                    } else {
                        return;
                    }
                }
            } catch (ParserConfigurationException | IOException | SAXException | InterruptedException | TransformerException e) {
                e.printStackTrace();
            }
        }

        /**
         * Checks if an Internet connection is active.
         *
         * @return true, if an Internet connection is currently active
         */
        private boolean checkConnectivity() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
    }
}
