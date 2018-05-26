package cdioil.feedbackmonkey.authz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.application.MainMenuActivity;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.json.UserJSONService;
import cdioil.feedbackmonkey.utils.ToastNotification;
import okhttp3.Response;

/**
 * LoginActivity class for the mobile app's login system.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Constant that represents the message that ocures if the user tries to login when he has no
     * internet connection
     */
    private static final String NO_INTERNET_CONNECTION="Não existe conexão à Internet!";
    /**
     * Login button.
     */
    private Button loginButton;
    /**
    * Sign up button.
    */
    private Button signupButton;
    /**
     * Activate account button.
     */
    private Button activateAccountButton;
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
        signupButton = findViewById(R.id.signupButton);
        activateAccountButton = findViewById(R.id.activateAccountButton);
        startLogin();
        startActivateAccount();
        startSignUp();
    }

    /**
     * Sets on click listener to begin the login process.
     */
    private void startLogin(){
        loginButton.setOnClickListener(view -> {
            Thread loginThread = new Thread(login());
            loginThread.start();
        });
    }

    /**
     * Sets on click listener to begin the activating account process.
     */
    private void startActivateAccount(){
        activateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog activateAccountAlert =
                                new AlertDialog.Builder(LoginActivity.this).create();
                        activateAccountAlert.setTitle("Ativação de Conta");
                        activateAccountAlert.setMessage("Insira o seu código de ativação");
                        activateAccountAlert.setIcon(R.drawable.ic_info_black_18dp);
                        final EditText activationCodeInput = new EditText(LoginActivity.this);
                        activationCodeInput.setHint("Código de Ativação Aqui");
                        activationCodeInput.setGravity(Gravity.CENTER);
                        activateAccountAlert.setView(activationCodeInput);
                        activateAccountAlert.setButton(DialogInterface.BUTTON_NEUTRAL,"Ativar Conta",
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //REST request to activate account
                                //show toast or other component with success message
                            }
                        });
                        activateAccountAlert.show();
                    }
                });
            }
        });
    }

    /**
     * Sets on click listener to begin the sign up process.
     */
    private void startSignUp(){
        signupButton.setOnClickListener(view -> {
            Intent signupIntent = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(signupIntent);
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
            try {
                Response restResponse = RESTRequest.create(BuildConfig.SERVER_URL
                        .concat(FeedbackMonkeyAPI
                                .getAPIEntryPoint()
                                .concat(FeedbackMonkeyAPI.getResourcePath("authentication")
                                        .concat(FeedbackMonkeyAPI.getSubResourcePath("authentication", "login")))))
                        .withMediaType(RESTRequest.RequestMediaType.JSON)
                        .withBody("{\n\t\"email\":" + emailText.getText().toString() + ",\"password\":" +
                                passwordText.getText().toString() + "\n}").POST();
                String restResponseBodyContent = "";
                try {
                    restResponseBodyContent = restResponse.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (restResponse.code() == HttpsURLConnection.HTTP_OK) {
                    Intent mainMenuIntent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("authenticationToken", getAuthenticationToken(restResponseBodyContent));
                    mainMenuIntent.putExtras(bundle);
                    emailText.getText().clear();
                    passwordText.getText().clear();
                    startActivity(mainMenuIntent);
                } else if (restResponse.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                    showLoginErrorMessage("Login Inválido",
                            "\nCredenciais inválidas, tente novamente!\n");
                } else if (restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                    showLoginErrorMessage("Login Inválido",
                            "A sua conta não está ativada!");
                }
            }catch(IOException ioException){
                ToastNotification.show(this,NO_INTERNET_CONNECTION);
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
                    emailText.getText().clear();
                    passwordText.getText().clear();
                });
            }
        }.start();
    }
}
