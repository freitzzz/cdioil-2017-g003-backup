package cdioil.feedbackmonkey.authz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.application.ListSurveyActivity;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
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
        System.setProperty("javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
        setContentView(R.layout.activity_login);
        FeedbackMonkeyAPI.create(getApplicationContext());
        loginButton = findViewById(R.id.loginButton);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        emailText.setText("joao@email.com");
        passwordText.setText("Password123");
        loginButton.setOnClickListener(view -> {
            //rest request
            Thread loginThread = new Thread(login());
            loginThread.start();
        });
    }

    private Runnable login(){
        return () -> {
            //TODO replace server url with resources from feedback_monkey_api.xml
                Response restResponse =
            RESTRequest.create("http://ndest.ddns.net:35066/feedbackmonkeyapi/authentication/login")
                    .withMediaType(RESTRequest.RequestMediaType.JSON)
                    .withBody("{\n\t\"email\":"+emailText.getText().toString()+",\"password\":"+
        passwordText.getText().toString()+"\n}").POST();
                String restResponseBodyContent = "";
            try {
                restResponseBodyContent = restResponse.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Temporary System.outs to see if rest responses are working correctly
            if(restResponse.code() == HttpsURLConnection.HTTP_OK){
                    String authToken = getAuthenticationToken(restResponseBodyContent);
                    //TODO go to app's main activity, pass authToken
                Intent listSurveyIntent=new Intent(LoginActivity.this, ListSurveyActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("authenticationToken",authToken);
                listSurveyIntent.putExtras(bundle);
                startActivity(listSurveyIntent);
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
        String authToken;
        String[] temp = jsonBody.split("\":\"");
        authToken = temp[1].replaceAll("\"","");
        return authToken;
    }

    /**
     * Creates a new thread to display a login error message using an AlertBuilder
     * @param messageTitle title of the message
     * @param messageContent content of the message
     */
    private void showLoginErrorMessage(String messageTitle, String messageContent) {
        new Thread() {
            public void run() {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
                    }
                });
            }
        }.start();
    }
}
