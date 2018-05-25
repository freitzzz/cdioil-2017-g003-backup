package cdioil.feedbackmonkey.authz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.application.ListSurveyActivity;
import cdioil.feedbackmonkey.application.MainMenuActivity;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.json.UserJSONService;
import okhttp3.Response;

/**
 * LoginActivity class for the mobile app's login system.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Login button.
     */
    private Button loginButton;
    /**
     * Text field for email address.
     */
    private EditText emailText;
    /**
     * Text field for password.
     */
    private EditText passwordText;
    /**
     * Method that runs the mobile app's login.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeApplication();
        loginButton = findViewById(R.id.loginButton);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        loginButton.setOnClickListener(view -> {
            //rest request
            Thread loginThread = new Thread(login());
            loginThread.start();
        });
    }

    /**
     * Initializes the application.
     */
    private void initializeApplication(){
        System.setProperty("javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
        FeedbackMonkeyAPI.create(getApplicationContext());
    }

    /**
     * Runs the rest request to perform the login of the app
     * @return Runnable with the thread the performs the rest request to confirm the login of a user
     */
    private Runnable login(){
        return () -> {
                Response restResponse =
            RESTRequest.create(BuildConfig.SERVER_URL
                    .concat(FeedbackMonkeyAPI
                    .getAPIEntryPoint()
                    .concat(FeedbackMonkeyAPI.getResourcePath("authentication")
                    .concat(FeedbackMonkeyAPI.getSubResourcePath("authentication","login")))))
                    .withMediaType(RESTRequest.RequestMediaType.JSON)
                    .withBody("{\n\t\"email\":"+emailText.getText().toString()+",\"password\":"+
                    passwordText.getText().toString()+"\n}").POST();
                String restResponseBodyContent = "";
            try {
                restResponseBodyContent = restResponse.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(restResponse.code() == HttpsURLConnection.HTTP_OK){
                    //TODO go to app's main activity, pass authToken
                Intent mainMenuIntent=new Intent(LoginActivity.this, MainMenuActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("authenticationToken",getAuthenticationToken(restResponseBodyContent));
                mainMenuIntent.putExtras(bundle);
                startActivity(mainMenuIntent);
                }else if(restResponse.code() == HttpsURLConnection.HTTP_UNAUTHORIZED){
                       showLoginErrorMessage("Login Inválido",
                               "\nCredenciais inválidas, tente novamente!\n");
                }else if(restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST){
                        showLoginErrorMessage("Login Inválido",
                        "A sua conta não está ativada!");
            }
        };
    }

    /**
     * Returns the user's authentication token from the JSON rest response body
     * @param jsonBody body of the rest response sent in JSON format
     * @return String with the user's authentication token
     */
    private String getAuthenticationToken(String jsonBody){
        return new Gson().fromJson(jsonBody, UserJSONService.class).getAuthenticationToken();
    }

    /**
     * Creates a new thread to display a login error message using an AlertBuilder
     * @param messageTitle title of the message
     * @param messageContent content of the message
     */
    private void showLoginErrorMessage(String messageTitle, String messageContent) {
        new Thread() {
            public void run() {
                LoginActivity.this.runOnUiThread(() -> {
                    AlertDialog invalidCredentialsAlert =
                            new AlertDialog.Builder(LoginActivity.this).create();
                    invalidCredentialsAlert.setTitle(messageTitle);
                    invalidCredentialsAlert.setMessage(messageContent);
                    invalidCredentialsAlert.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            invalidCredentialsAlert.dismiss();
                        }
                    });
                    invalidCredentialsAlert.setIcon(R.drawable.ic_error_black_18dp);
                    invalidCredentialsAlert.show();
                });
            }
        }.start();
    }
}
