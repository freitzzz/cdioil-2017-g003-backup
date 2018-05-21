package cdioil.feedbackmonkey.authz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.utils.RESTRequest;
import okhttp3.Response;

/**
 * LoginActivity class for the mobile app's login system.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Code that represents a successful login
     */
    private static final int SUCCESSFUL_LOGIN_CODE = 200;
    /**
     * Code that represents a failed login
     */
    private static final int FAILED_LOGIN_CODE = 401;
    /**
     * JSON Body String for invalid credentials.
     */
    private static final String JSON_BODY_INVALID_CREDENTIALS =  "{\n\t\"invalidcredentials\":\"true\"\n}";
    /**
     * JSON Body String for account that hasn't been activated.
     */
    private static final String JSON_BODY_ACTIVATION_REQUIRED = "{\n\t\"activationcode\":\"required\"\n}";
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
        loginButton = findViewById(R.id.loginButton);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rest request
                Thread loginThread = new Thread(login());
                loginThread.start();
            }
        });
    }

    private Runnable login(){
        return new Runnable(){
            @Override
            public void run(){
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
                if(restResponse.code() == SUCCESSFUL_LOGIN_CODE){
                        String authToken = getAuthenticationToken(restResponseBodyContent);
                        //TODO go to app's main activity
                    }else if(restResponse.code() == FAILED_LOGIN_CODE){
                       if(restResponseBodyContent.equals(JSON_BODY_INVALID_CREDENTIALS)){
                           showLoginErrorMessage("Login Inválido",
                                   "\nCredenciais inválidas, tente novamente!\n");
                       }
                       if(restResponseBodyContent.equals(JSON_BODY_ACTIVATION_REQUIRED)){
                           showLoginErrorMessage("Login Inválido",
                                   "A sua conta não está ativada!");
                       }
                    }
            }
        };
    }

    /**
     * Returns the user's authentication token from the JSON rest response body
     * @param jsonBody body of the rest response sent in JSON format
     * @return String witht he user's authentication token
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
