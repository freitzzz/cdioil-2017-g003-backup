package cdioil.feedbackmonkey.authz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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

    private String AUTHENTICATION_RESOURCE_PATH = FeedbackMonkeyAPI.getResourcePath("Authentication");

    private String ACTIVATE_ACCOUNT_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Authentication", "Activate Account");

    private String LOGIN_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Authentication", "Login");

    private String REQUEST_ACTIVATION_CODE_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Authentication", "Request Activation Code");

    private String CONFIRM_ACTIVATION_CODE_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Authentication", "Confirm Activation Code");

    private String CHANGE_PASSWORD_RESOURCE_PATH = FeedbackMonkeyAPI.getSubResourcePath("Authentication", "Change Password");
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
        loginButton = findViewById(R.id.loginButton);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        signupButton = findViewById(R.id.signupButton);
        activateAccountButton = findViewById(R.id.activateAccountButton);
        passwordRecoveryButton = findViewById(R.id.passwordRecoveryButton);
        loginProgressBar = findViewById(R.id.circle_loading_bar);
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
                    activateAccountAlert.setTitle(getString(R.string.account_activation_title));
                    activateAccountAlert.setMessage(R.string.account_activation_msg);
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
        AlertDialog.Builder dialogBuilder =
                new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.password_recovery_header);
        dialogBuilder.setMessage(getString(R.string.password_recovery_email_msg));
        dialogBuilder.setIcon(R.drawable.ic_info_black_18dp);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.password_recovery_email_and_code_request, null);
        dialogBuilder.setView(promptView);
        Button sendCodeRequestButton = promptView.findViewById(R.id.requestPasswordRecoveryCodeButton);
        sendCodeRequestButton.setText(R.string.request_password_recovery_code);
        EditText emailText = promptView.findViewById(R.id.pwdRecoveryEmailEditText);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        sendCodeRequestButton.setOnClickListener(view1 -> {

            String email = emailText.getText().toString().trim();

            if (email.isEmpty()) {
                emailText.setError(getString(R.string.email_edit_text_error));
                return;
            }

            String jsonBodyContent = new Gson().toJson(new UserJSONService(email, null));

            AtomicInteger responseCode = new AtomicInteger();
            AtomicReference<String> responseBody = new AtomicReference<>();

            Thread requestThread = new Thread(() -> {
                try {
                    Response response = RESTRequest.create(BuildConfig.SERVER_URL
                            .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                            .concat(AUTHENTICATION_RESOURCE_PATH)
                            .concat(REQUEST_ACTIVATION_CODE_RESOURCE_PATH))
                            .withMediaType(RESTRequest.RequestMediaType.JSON)
                            .withBody(jsonBodyContent).POST();

                    responseCode.set(response.code());
                    responseBody.set(response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            requestThread.start();
            try {
                requestThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (responseCode.get() == HttpsURLConnection.HTTP_OK) {
                showInsertPasswordRecoveryCodeDialog(email);
                dialog.dismiss();
            } else if (responseCode.get() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                emailText.setError(getString(R.string.user_not_registered_error));
            } else if (responseCode.get() == HttpsURLConnection.HTTP_UNAVAILABLE) {
                ToastNotification.show(this, getString(R.string.email_dispatch_error));
            } else {
                ToastNotification.show(this, "Erro: " + responseCode.toString());
            }
        });
        dialogBuilder.show();
    }

    /**
     * Presents a dialog to the user so they can insert the password recovery code to change
     * their password.
     */
    private void showInsertPasswordRecoveryCodeDialog(String email) {
        AlertDialog.Builder dialogBuilder =
                new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.password_recovery_header);
        dialogBuilder.setMessage(getString(R.string.password_recovery_code_msg));
        dialogBuilder.setIcon(R.drawable.ic_info_black_18dp);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.insert_activation_code, null);
        dialogBuilder.setView(promptView);
        Button requestPasswordChangeButton = promptView.findViewById(R.id.sendActivationRequestButton);
        requestPasswordChangeButton.setText(R.string.change_password);
        EditText passwordRecoveryCodeEditText = promptView.findViewById(R.id.activateAccountCode);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        requestPasswordChangeButton.setOnClickListener(view1 -> {

            String code = passwordRecoveryCodeEditText.getText().toString().trim();

            if (code.isEmpty()) {
                passwordRecoveryCodeEditText.setError(getString(R.string.code_edit_text_error));
                return;
            }

            String jsonBodyContent = new Gson().toJson(new UserJSONService(email, null, code));

            AtomicInteger responseCode = new AtomicInteger();
            AtomicReference<String> responseBody = new AtomicReference<>();

            Thread requestThread = new Thread(() -> {
                try {
                    Response response = RESTRequest.create(BuildConfig.SERVER_URL
                            .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                            .concat(AUTHENTICATION_RESOURCE_PATH)
                            .concat(CONFIRM_ACTIVATION_CODE_RESOURCE_PATH))
                            .withMediaType(RESTRequest.RequestMediaType.JSON)
                            .withBody(jsonBodyContent).POST();

                    responseCode.set(response.code());
                    responseBody.set(response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            requestThread.start();
            try {
                requestThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (responseCode.get() == HttpsURLConnection.HTTP_OK) {
                showChangePasswordDialog(email, code);
                dialog.dismiss();
            } else if (responseCode.get() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                //No need to check if a user exists since it's already verified in the previous dialog
                if (responseBody.get().contains("validactivationcode")) {
                    passwordRecoveryCodeEditText.setError(getString(R.string.invalid_code_edit_text_error));
                }
            } else {
                ToastNotification.show(this, "Erro: " + responseCode.toString());
            }
        });

        dialog.show();
    }

    private void showChangePasswordDialog(String email, String code) {
        AlertDialog.Builder dialogBuilder =
                new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.password_recovery_header);
        dialogBuilder.setMessage(getString(R.string.new_password_msg));
        dialogBuilder.setIcon(R.drawable.ic_info_black_18dp);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.password_recovery_set_new_password, null);
        dialogBuilder.setView(promptView);
        Button setNewPassword = promptView.findViewById(R.id.setNewPassword);
        EditText newPasswordEditText = promptView.findViewById(R.id.newPasswordEditText);
        EditText rewriteNewPasswordEditText = promptView.findViewById(R.id.rewriteNewPasswordEditText);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        setNewPassword.setOnClickListener(view1 -> {

            String firstPassword = newPasswordEditText.getText().toString();

            if (firstPassword.isEmpty()) {
                newPasswordEditText.setError(getString(R.string.empty_password_edit_text_error));
                return;
            }

            String secondPassword = rewriteNewPasswordEditText.getText().toString();

            if (!firstPassword.equals(secondPassword)) {
                rewriteNewPasswordEditText.setError(getString(R.string.different_password_edit_text_error));
                return;
            }

            String jsonBodyContent = new Gson().toJson(new UserJSONService(email, firstPassword, code));

            AtomicInteger responseCode = new AtomicInteger();
            AtomicReference<String> responseBody = new AtomicReference<>();

            Thread requestThread = new Thread(() -> {
                try {
                    Response response = RESTRequest.create(BuildConfig.SERVER_URL
                            .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                            .concat(AUTHENTICATION_RESOURCE_PATH)
                            .concat(CHANGE_PASSWORD_RESOURCE_PATH))
                            .withMediaType(RESTRequest.RequestMediaType.JSON)
                            .withBody(jsonBodyContent).PUT();

                    responseCode.set(response.code());
                    responseBody.set(response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            requestThread.start();
            try {
                requestThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (responseCode.get() == HttpsURLConnection.HTTP_OK) {
                ToastNotification.show(this, getString(R.string.password_changed_success));
                dialog.dismiss();
            } else if (responseCode.get() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                if (responseBody.get().contains("passwordchanged")) {
                    newPasswordEditText.setError(getString(R.string.not_strong_password_edit_text_error));
                }
            } else {
                ToastNotification.show(this, "Erro: " + responseCode.toString());
            }
        });
        dialog.show();
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
                                .concat(AUTHENTICATION_RESOURCE_PATH
                                        .concat(LOGIN_RESOURCE_PATH))))
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
                ToastNotification.show(this, getString(R.string.no_internet_connection));
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
        String authenticationToken = new Gson().fromJson(jsonBody, UserJSONService.class).getAuthenticationToken();
        saveAuthenticationTokenInSharedPreferences(authenticationToken);
        return authenticationToken;
    }

    /**
     * Saves the user's authenticationToken to the app's Shared Preferences.
     *
     * @param authenticationToken String with the user's authentication token
     */
    private void saveAuthenticationTokenInSharedPreferences(String authenticationToken) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("authenticationToken",
                authenticationToken).apply();
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
                    .concat(AUTHENTICATION_RESOURCE_PATH)
                    .concat(ACTIVATE_ACCOUNT_RESOURCE_PATH))
                    .withMediaType(RESTRequest.RequestMediaType.JSON)
                    .withBody(new Gson().toJson(new UserJSONService(email, password, activationCode)))
                    .POST();
            switch (responseBody.code()) {
                case HttpsURLConnection.HTTP_OK:
                    showToastMessage(getString(R.string.account_activation_success));
                    break;
                case HttpsURLConnection.HTTP_BAD_REQUEST:
                    showToastMessage(getString(R.string.account_activation_error));
                    break;
                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                    showToastMessage(getString(R.string.account_already_activated));
                    break;
            }
        } catch (IOException ioException) {
            showToastMessage(getString(R.string.no_internet_connection));
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
