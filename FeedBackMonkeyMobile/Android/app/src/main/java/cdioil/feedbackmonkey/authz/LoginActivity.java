package cdioil.feedbackmonkey.authz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import okhttp3.ResponseBody;

/**
 * LoginActivity class for the mobile app's login system.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Constant that represents the message that occurs if the user tries to login when he has no
     * internet connection
     */
    private static final String NO_INTERNET_CONNECTION = "Não existe conexão à Internet!";
    /**
     * Constant that represents the message that occurs if the user activates his account with success
     */
    private static final String ACCOUNT_ACTIVATED_WITH_SUCCESS="Conta activada com successo!";
    /**
     * Constant that represents the message that occurs if the user activates his account with success
     */
    private static final String ACCOUNT_NOT_ACTIVATED_WITH_SUCCESS="Ocorreu um erro ao ativar a conta!";
    /**
     * Constant that represents the message that occurs if the user activates his account with success
     */
    private static final String ACCOUNT_ALREADY_ACTIVATED="A conta já se encontra activada!";
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
        showSignUpInfo();
    }

    /**
     * Shows info regarding a registration process if one occurred
     */
    private void showSignUpInfo(){
        if(this.getIntent().getExtras() != null){
            ToastNotification.show(LoginActivity.this,this.getIntent().
                    getExtras().getString("toastText"));
        }
    }

    /**
     * Sets on click listener to begin the login process.
     */
    private void startLogin() {
        loginButton.setOnClickListener(view -> {
            Thread loginThread = new Thread(login());
            loginThread.start();
        });
    }

    /**
     * Sets on click listener to begin the activating account process.
     */
    private void startActivateAccount() {
        activateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder activateAccountAlert =
                                new AlertDialog.Builder(LoginActivity.this);
                        activateAccountAlert.setTitle("Ativação de Conta");
                        activateAccountAlert.setMessage("Insira os dados seguintes para ativar a sua conta");
                        activateAccountAlert.setIcon(R.drawable.ic_info_black_18dp);
                        LayoutInflater layoutInflater = LayoutInflater.from(LoginActivity.this);
                        View promptView = layoutInflater.inflate(R.layout.activate_account_view, null);
                        activateAccountAlert.setView(promptView);
                        Button sendActivationRequestButton = promptView.findViewById(R.id.sendActivationRequestButton);
                        EditText activateAccountEmail = promptView.findViewById(R.id.activateAccountEmail);
                        EditText activateAccountPassword = promptView.findViewById(R.id.activateAccountPassword);
                        EditText activateAccountCode = promptView.findViewById(R.id.activateAccountCode);
                        sendActivationRequestButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String email = activateAccountEmail.getText().toString();
                                String password = activateAccountPassword.getText().toString();
                                String code = activateAccountCode.getText().toString();
                                Thread thread = new Thread(() -> sendActivationCode(email, password, code));
                                thread.setDaemon(true);
                                thread.start();
                                //rest request to activate account
                                //show toast with success/failure message and go back to login screen
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
    private void startSignUp() {
        signupButton.setOnClickListener(view -> {
            Intent signupIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(signupIntent);
        });
    }

    /**
     * Initializes the application.
     */
    private void initializeApplication() {
        System.setProperty("javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
        FeedbackMonkeyAPI.create(getApplicationContext());
    }

    /**
     * Runs the rest request to perform the login of the app
     *
     * @return Runnable with the thread the performs the rest request to confirm the login of a user
     */
    private Runnable login() {
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
                    runOnUiThread(this::clearActivityComponents);
                    startActivity(mainMenuIntent);
                } else if (restResponse.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                    showLoginErrorMessage("Login Inválido",
                            "\nCredenciais inválidas, tente novamente!\n");
                } else if (restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                    showLoginErrorMessage("Login Inválido",
                            "A sua conta não está ativada!");
                }
            } catch (IOException ioException) {
                ToastNotification.show(this, NO_INTERNET_CONNECTION);
            }
        };
    }

    /**
     * Returns the user's authentication token from the JSON rest response body
     *
     * @param jsonBody body of the rest response sent in JSON format
     * @return String with the user's authentication token
     */
    private String getAuthenticationToken(String jsonBody) {
        return new Gson().fromJson(jsonBody, UserJSONService.class).getAuthenticationToken();
    }

    /**
     * Creates a new thread to display a login error message using an AlertBuilder
     *
     * @param messageTitle   title of the message
     * @param messageContent content of the message
     */
    private void showLoginErrorMessage(String messageTitle, String messageContent) {
        new Thread() {
            public void run() {
                LoginActivity.this.runOnUiThread(() -> {
                    AlertDialog.Builder invalidCredentialsAlert =
                            new AlertDialog.Builder(LoginActivity.this);
                    invalidCredentialsAlert.setTitle(messageTitle);
                    invalidCredentialsAlert.setMessage(messageContent);
                    invalidCredentialsAlert.setIcon(R.drawable.ic_error_black_18dp);
                    invalidCredentialsAlert.setPositiveButton("OK", null);
                    final AlertDialog alertDialog = invalidCredentialsAlert.create();
                    alertDialog.show();
                    Button closeAlertWindowButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    closeAlertWindowButton.setBackgroundResource(R.color.zxing_transparent);
                    closeAlertWindowButton.setTextColor(Color.parseColor("#3D3D3D"));
                    closeAlertWindowButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    emailText.getText().clear();
                    passwordText.getText().clear();
                });
            }
        }.start();
    }

    /**
     * Sends the user activation code
     *
     * @param email          String with the user email
     * @param password       String with the user password
     * @param activationCode String with the user activation code
     */
    private void sendActivationCode(String email, String password, String activationCode) {
        try {
            Response responseBody = RESTRequest.create(BuildConfig.SERVER_URL
                    .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                    .concat(FeedbackMonkeyAPI.getResourcePath("authentication"))
                    .concat(FeedbackMonkeyAPI.getSubResourcePath("authentication"
                            , "Activate Account")))
                    .withMediaType(RESTRequest.RequestMediaType.JSON)
                    .withBody(new Gson().toJson(new UserJSONService(email, password, activationCode)))
                    .POST();
            switch (responseBody.code()) {
                case 200:
                    showToastMessage(ACCOUNT_ACTIVATED_WITH_SUCCESS);
                    break;
                case 400:
                    showToastMessage(ACCOUNT_NOT_ACTIVATED_WITH_SUCCESS);
                    break;
                case 401:
                    showToastMessage(ACCOUNT_ALREADY_ACTIVATED);
            }
        } catch (IOException ioException) {
            showToastMessage(NO_INTERNET_CONNECTION);
        }
    }

    /**
     * Shows a Toast on the current activity with a certain message
     * @param toastMessage String with the toast message
     */
    private void showToastMessage(String toastMessage) {
        LoginActivity.this.runOnUiThread(() -> ToastNotification.show(LoginActivity.this, toastMessage));
    }

    /**
     * Clears the current activity components
     */
    private void clearActivityComponents(){
        emailText.getText().clear();
        passwordText.getText().clear();
    }
}
