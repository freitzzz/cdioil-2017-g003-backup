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
import android.preference.PreferenceManager;
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

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;

import cdioil.feedbackmonkey.application.services.RequestNewReviewController;
import cdioil.feedbackmonkey.application.services.SurveyService;
import cdioil.feedbackmonkey.application.services.SurveyServiceController;
import cdioil.feedbackmonkey.authz.LoginActivity;
import cdioil.feedbackmonkey.restful.exceptions.RESTfulException;
import cdioil.feedbackmonkey.authz.UserProfileActivity;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;
import cdioil.feedbackmonkey.utils.ToastNotification;
import cdioil.feedbackmonkey.utils.WaitingDialog;
import okhttp3.Response;

public class MainMenuActivity extends AppCompatActivity {
    /**
     * Index for the MainMenuActivity's List View Item that starts ListSurveysActivity.
     */
    private static final int LIST_SURVEYS_INDEX = 0;
    /**
     * Index for the MainMenuActivity's List View Item that starts the code scan process.
     */
    private static final int SCAN_CODES_INDEX = 1;
    /**
     * Index for the MainMenuActivity's List View Item that starts UserProfileActivity.
     */
    private static final int USER_PROFILE_INDEX = 2;
    /**
     * Index for the MainMenuActivity's List View Item that starts SubmitSuggestionActivity.
     */
    private static final int SUBMIT_SUGGESTION_INDEX = 3;
    /**
     * Index for the MainMenuActivity's List View Item that starts the log out process.
     */
    private static final int LOGOUT_INDEX = 4;
    private static final int CURRENT_WINDOW_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    /**
     * Constant that represents the Reviews resource path.
     */
    private static final String REVIEWS_RESOURCE_PATH = FeedbackMonkeyAPI.getResourcePath("Reviews");
    /**
     * Constant that represents the save review resource path.
     */
    private static final String SAVE_REVIEW_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Reviews", "Save Review");
    private String[] menuOptions = {"Listar Inquéritos", "Scan de Códigos", "Perfil", "Partilhe as suas ideias connosco!",
            "Logout"};
    private int[] menuOptionImages = {R.drawable.survey_icon, R.drawable.code_search_icon, R.drawable.profile_icon,
            R.drawable.question_mark, R.drawable.logout_icon};
    private String[] menuBackgroundColors = {"#ffffff", "#ffd05b", "#ffffff", "#ffd05b", "#ffffff"};
    private String authenticationToken;
    private int optionIndex;
    /**
     * Receiver responsible for receiving signals regarding network state changes.
     */
    private ConnectionReceiver connectionReceiver;
    /**
     * WaitingDialog with the dialog that keeps the user waiting for the surveys to be fetched
     */
    private WaitingDialog fetchingSurveysWaitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        configureView();
        authenticationToken = PreferenceManager.getDefaultSharedPreferences(this).getString("authenticationToken",
                getString(R.string.no_authentication_token));
        if (authenticationToken.equals(getString(R.string.no_authentication_token))) {
            ToastNotification.show(this, "Não foi possível iniciar sessão, tente fazer login novamente!");
            finish();
        }
        fetchingSurveysWaitingDialog = WaitingDialog.create(this).setWaitingDialogTitle(getString(R.string.fetching_surveys_waiting_dialog));
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
                case LIST_SURVEYS_INDEX:
                    prepareListSurveyActivityStart();
                    break;
                case SCAN_CODES_INDEX:
                    scanItemCode();
                    break;
                case USER_PROFILE_INDEX:
                    startUserProfileActivity();
                    break;
                case SUBMIT_SUGGESTION_INDEX:
                    startSuggestionActivity();
                    break;
                case LOGOUT_INDEX:
                    deleteAuthenticationTokenFromSharedPreferences();
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
     * Deletes the current authentication token from the app's Shared Preferences and launches LoginActivity.
     */
    private void deleteAuthenticationTokenFromSharedPreferences() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().remove("authenticationToken").apply();
        if (PreferenceManager.getDefaultSharedPreferences(this).getString("authenticationToken",
                getString(R.string.no_authentication_token)).equals(getString(R.string.no_authentication_token))) {
            Intent backToLoginIntent = new Intent(MainMenuActivity.this, LoginActivity.class);
            startActivity(backToLoginIntent);
            finish();
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

    private void startSuggestionActivity() {
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
     *
     * @param productCode String with the scanned product code
     * @return Runnable with the runnable which will fetch the active surveys which products
     * being reviewed have a certain scanned code
     */
    private Runnable fetchScannedCodeSurveys(String productCode) {
        return () -> {
            try {
                runOnUiThread(this::showFetchingSurveysWaitingDialog);
                List<SurveyService> surveysToAnswer = new SurveyServiceController(authenticationToken).getSurveysByProductCode(productCode);
                if (surveysToAnswer.size() == 1) {
                    startAnswerSurveyActivity(surveysToAnswer.get(0));
                } else {
                    startListSurveyActivity(surveysToAnswer);
                }
                runOnUiThread(this::hideFetchingSurveysWaitingDialog);
            } catch (RESTfulException restfulException) {
                runOnUiThread(this::hideFetchingSurveysWaitingDialog);
                switch (restfulException.getCode()) {
                    case HttpsURLConnection.HTTP_BAD_REQUEST:
                        ToastNotification.show(MainMenuActivity.this, getString(R.string.no_surveys_with_certain_code));
                        break;
                    case HttpsURLConnection.HTTP_UNAUTHORIZED:
                        //#TO-DO: Authentication Token is not valid anymore, Go back to LoginActivity
                        break;
                    default:
                        ToastNotification.show(MainMenuActivity.this, "MENSAGEM DE ERRO CONEXAO AO SERVIDOR REST");
                }
            } catch (IOException ioException) {
                runOnUiThread(this::hideFetchingSurveysWaitingDialog);
                ToastNotification.show(MainMenuActivity.this, getString(R.string.no_internet_connection));
            }
        };
    }

    /**
     * Method fetches all active surveys that the current user can answer based on a
     * pagination ID
     *
     * @return Runnable with the runnable action which will fetch the surveys to answer
     */
    private Runnable fetchSurveysToAnswers() {
        return () -> {
            List<SurveyService> surveysToAnswer = getLocalSurveyServices();
            try {
                List<SurveyService> newSurveysToAnswer = new SurveyServiceController(authenticationToken).getSurveysByPaginationID((short) 0);

                for (SurveyService surveyService : newSurveysToAnswer) {
                    if (!surveysToAnswer.contains(surveyService)) {
                        surveysToAnswer.add(surveyService);
                    }
                }
            } catch (RESTfulException restfulException) {
                if (surveysToAnswer.isEmpty()) {
                    switch (restfulException.getCode()) {
                        case HttpsURLConnection.HTTP_BAD_REQUEST:
                            ToastNotification.show(MainMenuActivity.this, getString(R.string.no_available_surveys));
                            break;
                        case HttpsURLConnection.HTTP_UNAUTHORIZED:
                            //#TO-DO: Authentication Token is not valid anymore, Go back to LoginActivity
                            break;
                        default:
                            ToastNotification.show(MainMenuActivity.this, "MENSAGEM DE ERRO CONEXAO AO SERVIDOR REST");
                    }
                }
            } catch (IOException ioException) {
                if (surveysToAnswer.isEmpty()) {
                    ToastNotification.show(MainMenuActivity.this, getString(R.string.no_internet_connection));
                }
            } finally {
                if (!surveysToAnswer.isEmpty()) {
                    startListSurveyActivity(surveysToAnswer);
                }
            }
        };
    }

    /**
     * Starts a ListSurveyActivity with a certain list of surveys
     *
     * @param surveyServices List with the surveys to show on the ListSurveyActivity
     */
    private void startListSurveyActivity(List<SurveyService> surveyServices) {
        Intent questionIntent = new Intent(MainMenuActivity.this, ListSurveyActivity.class);
        Bundle bundle = new Bundle(surveyServices.size() + 1);
        bundle.putString("authenticationToken", authenticationToken);
        for (int i = 0; i < surveyServices.size(); i++) {
            bundle.putSerializable("" + i, surveyServices.get(i));
        }
        questionIntent.putExtra(ListSurveyActivity.class.getSimpleName(), bundle);
        MainMenuActivity.this.startActivity(questionIntent);
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
     * Add SurveyService instances from Files to currentSurveys list (these are surveys that already have
     * pending reviews saved locally)
     *
     * @return list of strings that contain the survey ID of a pending review
     */
    private List<SurveyService> getLocalSurveyServices() {
        List<SurveyService> surveyServiceList = new ArrayList<>();

        for (File surveyServiceFile : getSurveyServicesDirectory().listFiles()) {
            try (FileReader fileReader = new FileReader(surveyServiceFile.getAbsolutePath());
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                SurveyService surveyService = new Gson().fromJson(bufferedReader, SurveyService.class);
                surveyServiceList.add(surveyService);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return surveyServiceList;
    }


    /**
     * Starts a new AnswerSurveyActivity with a certain SurveyService which represents
     * the survey being reviewed
     */
    private void startAnswerSurveyActivity(SurveyService surveyService) {
        Thread requestReviewThread = new Thread(startReviewRequest(surveyService));
        requestReviewThread.start();

    }

    private Runnable startReviewRequest(SurveyService surveyService) {
        return () -> {
            try {
                RequestNewReviewController requestNewReview = new RequestNewReviewController(surveyService);
                try {
                    requestNewReview.createNewReview(requestNewReview.requestNewReview(authenticationToken), MainMenuActivity.this);
                    Intent questionIntent = new Intent(MainMenuActivity.this, QuestionActivity.class);
                    questionIntent.putExtra("authenticationToken", authenticationToken);
                    MainMenuActivity.this.startActivity(questionIntent);
                } catch (ParserConfigurationException | SAXException e) {
                    System.out.println(e.getMessage());
                }
            } catch (RESTfulException restfulException) {
                switch (restfulException.getCode()) {
                    case HttpsURLConnection.HTTP_BAD_REQUEST:
                        //#TO-DO: There are a lot of codes here to treat
                        ToastNotification.show(MainMenuActivity.this, "#TO-DO (400) newReview");
                        break;
                    case HttpsURLConnection.HTTP_NOT_FOUND:
                        ToastNotification.show(MainMenuActivity.this, getString(R.string.survey_not_availalbe));
                        break;
                    default:
                        ToastNotification.show(MainMenuActivity.this, "MENSAGEM DE ERRO CONEXAO AO SERVIDOR REST");
                }
            } catch (IOException ioException) {
                ToastNotification.show(MainMenuActivity.this, getString(R.string.no_internet_connection));
            }
        };
    }

    /**
     * Shows the fetching surveys waiting dialog
     */
    private void showFetchingSurveysWaitingDialog() {
        fetchingSurveysWaitingDialog.show();
    }

    /**
     * Hides the fetching surveys waiting dialog
     */
    private void hideFetchingSurveysWaitingDialog() {
        fetchingSurveysWaitingDialog.hide();
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
                File[] reviewFiles = getFinishedReviewsDirectory().listFiles();

                for (File reviewFile : reviewFiles) {

                    String fileContent = ReviewXMLService.parseReviewFile(reviewFile);

                    String reviewID = reviewFile.getName().split("_")[1];

                    String surveyEndDate = ReviewXMLService.getSurveyEndDate(fileContent);
                    String[] parsedSurveyEndDate = surveyEndDate.split(" ");
                    String[] dateSplit = parsedSurveyEndDate[0].split("-");
                    String[] timeSplit = parsedSurveyEndDate[1].split(":");
                    Calendar endDate = new GregorianCalendar(Integer.parseInt(dateSplit[0])
                            , Integer.parseInt(dateSplit[1])
                            , Integer.parseInt(dateSplit[2])
                            , Integer.parseInt(timeSplit[0])
                            , Integer.parseInt(timeSplit[1])
                            , Integer.parseInt(timeSplit[2]));
                    if (Calendar.getInstance().compareTo(endDate) < 0) {
                        String saveReviewURL = BuildConfig.SERVER_URL
                                .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                                .concat(REVIEWS_RESOURCE_PATH)
                                .concat(SAVE_REVIEW_RESOURCE_PATH)
                                .concat("/" + authenticationToken + "/")
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
                            reviewFile.delete();
                        } else {
                            return;
                        }
                    } else {
                        reviewFile.delete();
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
