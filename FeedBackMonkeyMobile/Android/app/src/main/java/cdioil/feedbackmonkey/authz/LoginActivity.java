package cdioil.feedbackmonkey.authz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;


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
     * Constant that represents the message that occurs if the user tries to login when he has no
     * internet connection
     */
    private static final String NO_INTERNET_CONNECTION = "Não existe conexão à Internet!";
    /**
     * Constant that represents the message that occurs if the user activates his account with success
     */
    private static final String ACCOUNT_ACTIVATED_WITH_SUCCESS = "Conta activada com successo!";
    /**
     * Constant that represents the message that occurs if the user activates his account with success
     */
    private static final String ACCOUNT_NOT_ACTIVATED_WITH_SUCCESS = "Ocorreu um erro ao ativar a conta!";
    /**
     * Constant that represents the message that occurs if the user activates his account with success
     */
    private static final String ACCOUNT_ALREADY_ACTIVATED = "A conta já se encontra activada!";
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
     * Password recovery button.
     */
    private Button passwordRecoveryButton;
    /**
     * Text field for email address.
     */
    private EditText emailText;
    /**
     * Text field for password.
     */
    private EditText passwordText;
    /**
     * Progress bar with the progress bar used on the login
     */
    private ProgressBar loginProgressBar;
    /**
     * Alert Dialog with that holds the login progress bar
     */
    private AlertDialog loginProgressDialog;
    /**
     * Static boolean that is used to check if the application just started up
     */
    private static boolean onStartup;

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
        passwordRecoveryButton = findViewById(R.id.passwordRecoveryButton);
        loginProgressBar = findViewById(R.id.circle_loading_bar);
        System.out.println(loginProgressBar);
        startLogin();
        startActivateAccount();
        startSignUp();
        startPasswordRecovery();
        showSignUpInfo();
    }

    /**
     * Shows info regarding a registration process if one occurred
     */
    private void showSignUpInfo() {
        if (onStartup) {
            ToastNotification.show(LoginActivity.this, this.getIntent().
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
        activateAccountButton.setOnClickListener(view ->
                LoginActivity.this.runOnUiThread(() -> {
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
                        public void onClick(View view1) {
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
                }));
    }

    /**
     * Sets on click listener to begin the sign up process.
     */
    private void startSignUp() {
        signupButton.setOnClickListener(view -> {
            onStartup = true;
            Intent signupIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(signupIntent);
        });
    }

    /**
     * Sets on click listener to begin the password recovery process.
     */
    private void startPasswordRecovery() {
        passwordRecoveryButton.setOnClickListener(view -> {
            showRequestEmailForPasswordRecoveryDialog();
        });
    }

    /**
     * Presents a dialog to the user requesting their email so a code to recover their password
     * can be sent.
     */
    private void showRequestEmailForPasswordRecoveryDialog() {
        AlertDialog.Builder requestPasswordRecoveryCodeDialog =
                new AlertDialog.Builder(this);
        requestPasswordRecoveryCodeDialog.setTitle(R.string.password_recovery_header);
        requestPasswordRecoveryCodeDialog.setMessage("Insira o seu email para receber um código que permitirá a " +
                "mudança de password");
        requestPasswordRecoveryCodeDialog.setIcon(R.drawable.ic_info_black_18dp);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.password_recovery_email_and_code_request, null);
        requestPasswordRecoveryCodeDialog.setView(promptView);
        Button sendCodeRequestButton = promptView.findViewById(R.id.requestPasswordRecoveryCodeButton);
        sendCodeRequestButton.setText(R.string.request_password_recovery_code);
        EditText emailText = promptView.findViewById(R.id.pwdRecoveryEmailEditText);
        sendCodeRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                //TODO RESTRequest to confirm email and launch the code insertion dialog only if the request returns code 200
                showInsertPasswordRecoveryCodeDialog();
            }
        });
        requestPasswordRecoveryCodeDialog.show();
    }

    /**
     * Presents a dialog to the user so they can insert the password recovery code to change
     * their password.
     */
    private void showInsertPasswordRecoveryCodeDialog(){
        AlertDialog.Builder requestPasswordRecoveryCodeDialog =
                new AlertDialog.Builder(this);
        requestPasswordRecoveryCodeDialog.setTitle(R.string.password_recovery_header);
        requestPasswordRecoveryCodeDialog.setMessage("Insira o código que foi enviado para o seu email para " +
                "alterar a sua password");
        requestPasswordRecoveryCodeDialog.setIcon(R.drawable.ic_info_black_18dp);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.insert_activation_code, null);
        requestPasswordRecoveryCodeDialog.setView(promptView);
        Button requestPasswordChangeButton = promptView.findViewById(R.id.sendActivationRequestButton);
        requestPasswordChangeButton.setText(R.string.change_password);
        EditText passwordRecoveryCodeEditText = promptView.findViewById(R.id.activateAccountCode);
        requestPasswordChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                //TODO RESTRequest to confirm code and launch the change password dialog only if the request returns code 200
                showChangePasswordDialog();
            }
        });
        requestPasswordRecoveryCodeDialog.show();
    }

    private void showChangePasswordDialog(){
        AlertDialog.Builder setNewPasswordDialog =
                new AlertDialog.Builder(this);
        setNewPasswordDialog.setTitle(R.string.password_recovery_header);
        setNewPasswordDialog.setMessage("Insira a sua nova password");
        setNewPasswordDialog.setIcon(R.drawable.ic_info_black_18dp);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.password_recovery_set_new_password, null);
        setNewPasswordDialog.setView(promptView);
        Button setNewPassword = promptView.findViewById(R.id.setNewPassword);
        EditText newPasswordEditText = promptView.findViewById(R.id.newPasswordEditText);
        EditText rewriteNewPasswordEditText = promptView.findViewById(R.id.rewriteNewPasswordEditText);
        setNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                /**
                 * TODO RESTRequest to confirm and set the new password and automatically log in the user
                 * to the app only if the request returns code 200
                 */
                ToastNotification.show(LoginActivity.this,"Hey hey hey");
            }
        });
        setNewPasswordDialog.show();
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
                runOnUiThread(this::startLoadingDialog);
                Response restResponse = RESTRequest.create(BuildConfig.SERVER_URL
                        .concat(FeedbackMonkeyAPI
                                .getAPIEntryPoint()
                                .concat(FeedbackMonkeyAPI.getResourcePath("authentication")
                                        .concat(FeedbackMonkeyAPI.getSubResourcePath("authentication", "login")))))
                        .withMediaType(RESTRequest.RequestMediaType.JSON)
                        .withBody(getUserLoginForm()).POST();
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
                    runOnUiThread(this::stopLoadingDialog);
                    runOnUiThread(this::clearPasswordText);
                    startActivity(mainMenuIntent);
                    finish();
                } else if (restResponse.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                    runOnUiThread(this::stopLoadingDialog);
                    showLoginErrorMessage("Login Inválido",
                            "\nCredenciais inválidas, tente novamente!\n");
                } else if (restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                    runOnUiThread(this::stopLoadingDialog);
                    showLoginErrorMessage("Login Inválido",
                            "A sua conta não está ativada!");
                } else if (restResponse.code() == HttpsURLConnection.HTTP_FORBIDDEN) {
                    runOnUiThread(this::stopLoadingDialog);
                    showLoginErrorMessage("Conta bloqueada",
                            "A sua conta foi bloqueada temporariamente devido a " +
                                    "várias tentativas de login sem sucesso. Por favor aguarde uns momentos");
                }
            } catch (IOException ioException) {
                runOnUiThread(this::stopLoadingDialog);
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
     * Returns the user login form as a JSON form based on the user input from the email & password fields
     *
     * @return String with the user login form as a JSON form based on the user input from the email & password fields
     */
    private String getUserLoginForm() {
        return new Gson().toJson(new UserJSONService(emailText.getText().toString(), passwordText.getText().toString()));
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
                    clearPasswordText();
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
                case HttpsURLConnection.HTTP_OK:
                    showToastMessage(ACCOUNT_ACTIVATED_WITH_SUCCESS);
                    break;
                case HttpsURLConnection.HTTP_BAD_REQUEST:
                    showToastMessage(ACCOUNT_NOT_ACTIVATED_WITH_SUCCESS);
                    break;
                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                    showToastMessage(ACCOUNT_ALREADY_ACTIVATED);
                    break;
            }
        } catch (IOException ioException) {
            showToastMessage(NO_INTERNET_CONNECTION);
        }
    }

    /**
     * Shows a Toast on the current activity with a certain message
     *
     * @param toastMessage String with the toast message
     */
    private void showToastMessage(String toastMessage) {
        LoginActivity.this.runOnUiThread(() -> ToastNotification.show(LoginActivity.this, toastMessage));
    }

    /**
     * Clears password edit text
     */
    private void clearPasswordText() {
        passwordText.getText().clear();
    }

    /**
     * Starts the login progress dialog
     */
    private void startLoadingDialog() {
        if (loginProgressDialog == null) createLoginProgressDialog();
        loginProgressDialog.show();
    }

    /**
     * Stops the login progress dialog
     */
    private void stopLoadingDialog() {
        loginProgressDialog.cancel();
        loginProgressBar.setVisibility(View.GONE);
    }

    /**
     * Creates the login progress dialog used to keep the user alert from the login action
     */
    private void createLoginProgressDialog() {
        loginProgressBar = (LayoutInflater.from(this).inflate(R.layout.circle_loading_bar, null))
                .findViewById(R.id.circle_loading_bar);
        loginProgressDialog = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(R.string.login_progress_dialog_info)
                .setView(loginProgressBar)
                .setCancelable(false)
                .create();
        loginProgressDialog.setOnShowListener(dialogInterface -> loginProgressBar.setVisibility(View.VISIBLE));
    }
}
