package cdioil.feedbackmonkey.authz;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.application.MainMenuActivity;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.json.RegistrationJSONService;
import cdioil.feedbackmonkey.restful.utils.json.UserJSONService;
import cdioil.feedbackmonkey.utils.ToastNotification;
import okhttp3.Response;

/**
 * Activity for the sign up process of the app.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class SignUpActivity extends AppCompatActivity {
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
     * Constant that represents the message that occurs if the user doesn't rewrite their password correctly.
     */
    private static final String REWRITE_PASSWORD_MESSAGE = "As passwords não são iguais! Tente novamente.";
    /**
     * Message indicating the user doesn't have an internet connection.
     */
    private static final String NO_INTERNET_CONNECTION = "Não existe conexão à Internet!";
    /**
     * JSON field for invalid email domain while signing up.
     */
    private static final String JSON_FIELD_FOR_INVALID_EMAIL_DOMAIN = "Whitelist";
    /**
     * JSON field for invalid email address.
     */
    private static final String JSON_FIELD_FOR_INVALID_EMAIL_ADDRESS = "Email";
    /**
     * JSON field for invalid password while signing up.
     */
    private static final String JSON_FIELD_FOR_INVALID_PASSWORD = "Password";
    /**
     * JSON field for invalid name while signing up.
     */
    private static final String JSON_FIELD_FOR_INVALID_NAME = "Name";
    /**
     * JSON field for invalid phone number while signing up.
     */
    private static final String JSON_FIELD_FOR_INVALID_PHONE_NUMBER = "PhoneNumber";
    /**
     * JSON field for invalid location while signing up.
     */
    private static final String JSON_FIELD_FOR_INVALID_LOCATION = "Location";
    /**
     * JSON field for invalid birth date while signing up.
     */
    private static final String JSON_FIELD_FOR_INVALID_BIRTH_DATE = "BirthDate";
    /**
     * JSON field for missing mandatory credentials while signing up.
     */
    private static final String JSON_FIELD_FOR_MISSING_CREDENTIALS = "Form";
    /**
     * Edit Text for the user's email.
     */
    private EditText emailEditText;
    /**
     * Edit Text for the user's password.
     */
    private EditText passwordEditText;
    /**
     * Edit Text for the user to rewrite their password.
     */
    private EditText rewritePasswordEditTexT;
    /**
     * Button for the user to confirm their registration.
     */
    private Button createAccountButton;
    /**
     * Button for the user to continue the sign up process.
     */
    private Button addExtraInfoButton;

    /**
     * Creates the SignUp Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emailEditText = findViewById(R.id.signupEmailText);
        passwordEditText = findViewById(R.id.signupPasswordText);
        rewritePasswordEditTexT = findViewById(R.id.signupRetypePasswordText);
        createAccountButton = findViewById(R.id.signupCreateAccountButton);
        addExtraInfoButton = findViewById(R.id.signupAdditionalInfoButton);
        startSignUp();
        startAddExtraInfo();
    }

    /**
     * Sets an OnClickListener to the create account button to start the register process.
     */
    private void startSignUp() {
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPasswordEditTexts()) {
                    Thread createAccount = new Thread(createAccount());
                    createAccount.start();
                }
            }
        });
    }

    /**
     * Sets an OnClickListener to the add extra info button to start the adding extra info process.
     */
    private void startAddExtraInfo() {
        addExtraInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPasswordEditTexts()) {
                    Thread continueSignUp = new Thread(continueSignup());
                    continueSignUp.start();
                }
            }
        });
    }

    /**
     * Starts the sign up process of the app.
     *
     * @return Runnable that executes the sign up process of the mobile app
     */
    private Runnable createAccount() {
        return () -> {
            if (validateCredentials()) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String restRequestBody = new Gson().toJson(new RegistrationJSONService(email, password,
                        null
                        , null, null, null));
                Response restResponse;
                try {
                    restResponse = RESTRequest.
                            create(BuildConfig.SERVER_URL.
                                    concat(FeedbackMonkeyAPI.getAPIEntryPoint()).
                                    concat(FeedbackMonkeyAPI.getResourcePath("authentication")).
                                    concat(FeedbackMonkeyAPI.getSubResourcePath("authentication", "register account"))).
                            withMediaType(RESTRequest.RequestMediaType.JSON).
                            withBody(restRequestBody).
                            POST();
                    if (restResponse.code() == HttpsURLConnection.HTTP_OK) {
                        activateAccount();
                    } else if (restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                        showRegistrationError();
                    }
                } catch (IOException e) {
                    ToastNotification.show(this, NO_INTERNET_CONNECTION);
                }
            }
        };
    }

    /**
     * Shows a Toast to the user with an error message indicating the registration wasn't sucessful
     */
    private void showRegistrationError() {
        ToastNotification.show(SignUpActivity.this, "ERRO AO REGISTAR");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                passwordEditText.getText().clear();
                rewritePasswordEditTexT.getText().clear();
            }
        });
    }

    /**
     * Runnable that continues the sign up process to add the optional credentials
     *
     * @return Runnable that runs an alert dialog if all the mandatory credentials are
     * acceptable
     */
    private Runnable continueSignup() {
        return () -> {
            if (validateCredentials()) {
                SignUpActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder activateAccountAlert =
                                new AlertDialog.Builder(SignUpActivity.this);
                        activateAccountAlert.setIcon(R.drawable.ic_info_black_18dp);
                        LayoutInflater layoutInflater = LayoutInflater.from(SignUpActivity.this);
                        View promptView = layoutInflater.inflate(R.layout.signup_optional_info_view, null);
                        activateAccountAlert.setView(promptView);
                        Button sendRegisterRequestButton = promptView.findViewById(R.id.signupRequestButton);
                        EditText firstNameText = promptView.findViewById(R.id.signupFirstNameText);
                        EditText lastNameText = promptView.findViewById(R.id.signupLastNameText);
                        EditText phoneNumberText = promptView.findViewById(R.id.signupPhoneNumberText);
                        EditText locationText = promptView.findViewById(R.id.signupLocationText);
                        EditText birthDateText = promptView.findViewById(R.id.signupBirthDateText);
                        sendRegisterRequestButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String firstName = firstNameText.getText().toString();
                                        String lastName = lastNameText.getText().toString();
                                        String email = emailEditText.getText().toString();
                                        String password = passwordEditText.getText().toString();
                                        String phoneNumber = phoneNumberText.getText().toString();
                                        String location = locationText.getText().toString();
                                        String birthDate = birthDateText.getText().toString();
                                        String fullName = firstName.concat(" ").concat(lastName);
                                        //rest request to register account
                                        if(firstName.isEmpty() || lastName.isEmpty()){
                                            fullName = null;
                                        }
                                        if(phoneNumber.isEmpty()){
                                            phoneNumber = null;
                                        }
                                        if(location.isEmpty()){
                                            location = null;
                                        }
                                        if(birthDate.isEmpty()){
                                            birthDate = null;
                                        }
                                        String restRequestBody = new Gson().toJson(new RegistrationJSONService(email, password,
                                                fullName, phoneNumber, location, birthDate));
                                        /**
                                         * The credentials are validated twice here for the sake of UX.
                                         * If a user chooses to add optional information while signing up
                                         * and he has mandatory credentials that are wrong he shouldn't be
                                         * able to add the optional info.
                                         */
                                        Response validateCredentialsRestResponse =
                                                getRestRequestToValidateCredentials(restRequestBody,"true");
                                        if (validateCredentialsRestResponse.code() == HttpsURLConnection.HTTP_OK) {
                                            Response registerAccountRestResponse =
                                                    getRestRequestToValidateCredentials(restRequestBody,"false");
                                            if (registerAccountRestResponse.code() == HttpsURLConnection.HTTP_OK) {
                                                activateAccount();
                                            } else if (registerAccountRestResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                                                showRegistrationError();
                                            }
                                        } else if (validateCredentialsRestResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST) {
                                            showOptionalDataErrors(validateCredentialsRestResponse, lastNameText, firstNameText,
                                                    locationText, birthDateText, phoneNumberText);
                                        }
                                    }
                                }).start();
                            }
                        });
                        activateAccountAlert.show();
                    }
                });
            }
        };
    }

    /**
     * Sets an error on the edit texts that match the optional info the user inserted that is invalid
     *
     * @param restResponse    REST Response containing the invalid field and its error message
     * @param lastNameText    edit text for the user's last name
     * @param firstNameText   edit text for the user's first name
     * @param locationText    edit text for the user's location
     * @param birthDateText   edit text for the user's birth date
     * @param phoneNumberText edit text for the user's phone number
     */
    private void showOptionalDataErrors(Response restResponse, EditText lastNameText, EditText firstNameText, EditText locationText, EditText birthDateText, EditText phoneNumberText) {
        RegistrationJSONService restResponseBody = getRegistrationJSONService(restResponse);
        String restResponseField = restResponseBody.getField();
        String restResponseMessage = restResponseBody.getMessage();
        switch (restResponseField) {
            case JSON_FIELD_FOR_INVALID_NAME:
                if (restResponseMessage.contains("Apelido")) {
                    setErrorOnWrongCredential(lastNameText, restResponseMessage);
                } else if (restResponseMessage.contains("primeiro")
                        && restResponseMessage.contains("apelido")) {
                    setErrorOnWrongCredential(firstNameText, restResponseMessage);
                } else {
                    setErrorOnWrongCredential(firstNameText, restResponseMessage);
                }
                break;
            case JSON_FIELD_FOR_INVALID_LOCATION:
                setErrorOnWrongCredential(locationText, restResponseMessage);
                break;
            case JSON_FIELD_FOR_INVALID_BIRTH_DATE:
                setErrorOnWrongCredential(birthDateText, restResponseMessage);
                break;
            case JSON_FIELD_FOR_INVALID_PHONE_NUMBER:
                setErrorOnWrongCredential(phoneNumberText, restResponseMessage);
                break;
            case JSON_FIELD_FOR_MISSING_CREDENTIALS:
                ToastNotification.show(SignUpActivity.this, restResponseMessage);
                break;
            default:
                break;
        }
    }

    /**
     * Starts the process of activating the newly registered account.
     */
    private void activateAccount() {
        runOnUiThread(() -> {
            AlertDialog.Builder activateAccountAlert =
                    new AlertDialog.Builder(SignUpActivity.this);
            activateAccountAlert.setTitle("Ativação de Conta");
            activateAccountAlert.setMessage("Foi enviado para o seu email um código de ativação. Insira o código para" +
                    " ativar a sua conta e efetuar login!");
            activateAccountAlert.setIcon(R.drawable.ic_info_black_18dp);
            LayoutInflater layoutInflater = LayoutInflater.from(SignUpActivity.this);
            View promptView = layoutInflater.inflate(R.layout.insert_activation_code, null);
            activateAccountAlert.setView(promptView);
            Button sendActivationRequestButton = promptView.findViewById(R.id.sendActivationRequestButton);
            sendActivationRequestButton.setText(R.string.send_activate_account_and_login_on_register);
            EditText activateAccountCode = promptView.findViewById(R.id.activateAccountCode);
            sendActivationRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    String code = activateAccountCode.getText().toString();
                    Thread thread = new Thread(() -> sendActivationCode(code));
                    thread.setDaemon(true);
                    thread.start();
                }
            });
            activateAccountAlert.show();
        });
    }

    /**
     * Sends the user activation code
     *
     * @param activationCode String with the user activation code
     */
    private void sendActivationCode(String activationCode) {
        try {
            Response responseBody = RESTRequest.create(BuildConfig.SERVER_URL
                    .concat(FeedbackMonkeyAPI.getAPIEntryPoint())
                    .concat(FeedbackMonkeyAPI.getResourcePath("authentication"))
                    .concat(FeedbackMonkeyAPI.getSubResourcePath("authentication"
                            , "Activate Account")))
                    .withMediaType(RESTRequest.RequestMediaType.JSON)
                    .withBody(new Gson().toJson(new UserJSONService(emailEditText.getText().toString(),
                            passwordEditText.getText().toString(), activationCode)))
                    .POST();
            switch (responseBody.code()) {
                case HttpsURLConnection.HTTP_OK:
                    ToastNotification.show(SignUpActivity.this, ACCOUNT_ACTIVATED_WITH_SUCCESS);
                    loginAfterRegister();
                    break;
                case HttpsURLConnection.HTTP_BAD_REQUEST:
                    ToastNotification.show(SignUpActivity.this, ACCOUNT_NOT_ACTIVATED_WITH_SUCCESS);
                    break;
                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                    ToastNotification.show(SignUpActivity.this, ACCOUNT_ALREADY_ACTIVATED);
                    break;
            }
        } catch (IOException ioException) {
            ToastNotification.show(SignUpActivity.this, NO_INTERNET_CONNECTION);
        }
    }

    /**
     * Logs the user into the app after the activating the account
     */
    private void loginAfterRegister() {
        try {
            Response restResponse = RESTRequest.create(BuildConfig.SERVER_URL
                    .concat(FeedbackMonkeyAPI
                            .getAPIEntryPoint()
                            .concat(FeedbackMonkeyAPI.getResourcePath("authentication")
                                    .concat(FeedbackMonkeyAPI.getSubResourcePath("authentication", "login")))))
                    .withMediaType(RESTRequest.RequestMediaType.JSON)
                    .withBody("{\n\t\"email\":" + emailEditText.getText().toString() + ",\"password\":" +
                            passwordEditText.getText().toString() + "\n}").POST();
            String restResponseBodyContent = "";
            try {
                restResponseBodyContent = restResponse.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (restResponse.code() == HttpsURLConnection.HTTP_OK) {
                Intent mainMenuIntent = new Intent(SignUpActivity.this, MainMenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("authenticationToken", getAuthenticationToken(restResponseBodyContent));
                mainMenuIntent.putExtras(bundle);
                startActivity(mainMenuIntent);
            }
        } catch (IOException ioException) {
            ToastNotification.show(this, NO_INTERNET_CONNECTION);
        }
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
     * Validates the mandatory credentials that the user must insert in order to sign up
     *
     * @return true if all the credentials are valid, false if otherwise
     */
    private boolean validateCredentials() {

        boolean validCredentials = true;

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String restRequestBody;
        if (email.isEmpty() && password.isEmpty()) {
            restRequestBody = new Gson().toJson(new RegistrationJSONService(null,
                    null, null, null, null, null));
        } else {
            restRequestBody = new Gson().toJson(new RegistrationJSONService(email, password,
                    null
                    , null, null, null));
        }
        Response restResponse = getRestRequestToValidateCredentials(restRequestBody,"true");
        RegistrationJSONService restResponseBody = getRegistrationJSONService(restResponse);
        String restResponseField = restResponseBody.getField();
        String restResponseMessage = restResponseBody.getMessage();
        if (restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST) {
            validCredentials = false;
            switch (restResponseField) {
                case JSON_FIELD_FOR_INVALID_EMAIL_DOMAIN:
                    setErrorOnWrongCredential(emailEditText, restResponseMessage);
                    break;
                case JSON_FIELD_FOR_INVALID_EMAIL_ADDRESS:
                    setErrorOnWrongCredential(emailEditText, restResponseMessage);
                    break;
                case JSON_FIELD_FOR_INVALID_PASSWORD:
                    setErrorOnWrongCredential(passwordEditText, restResponseMessage);
                    break;
                case JSON_FIELD_FOR_MISSING_CREDENTIALS:
                    ToastNotification.show(SignUpActivity.this, restResponseMessage);
                    break;
                default:
                    break;
            }
        }
        return validCredentials;
    }

    /**
     * Instantiates a RegistrationJSONService from a REST Response
     *
     * @param restResponse REST Response from the server
     * @return RegistrationJSONService object
     */
    private RegistrationJSONService getRegistrationJSONService(Response restResponse) {
        try {
            return new Gson().
                    fromJson(restResponse.body().string(), RegistrationJSONService.class);
        } catch (IOException e) {
            ToastNotification.show(SignUpActivity.this, NO_INTERNET_CONNECTION);
            return null;
        }
    }

    /**
     * Returns a REST Response for validating the sign up credentials of a user
     *
     * @param restRequestBody REST Request Body containing the credentials to be validated
     * @return REST response to whether the sign up credentials are valid or not
     */
    private Response getRestRequestToValidateCredentials(String restRequestBody, String validateParameter) {
        try {
            return RESTRequest.
                    create(BuildConfig.SERVER_URL.
                            concat(FeedbackMonkeyAPI.getAPIEntryPoint()).
                            concat(FeedbackMonkeyAPI.getResourcePath("Authentication")).
                            concat(FeedbackMonkeyAPI.getSubResourcePath("Authentication", "Register Account")).
                            concat("?validate=".concat(validateParameter))).
                    withMediaType(RESTRequest.RequestMediaType.JSON).
                    withBody(restRequestBody).
                    POST();
        } catch (IOException e) {
            ToastNotification.show(SignUpActivity.this, NO_INTERNET_CONNECTION);
            return null;
        }
    }

    /**
     * Checks if the text present in both password edit texts are equal.
     * @return true if they are, false if otherwise
     */
    private boolean checkPasswordEditTexts(){
        if(!passwordEditText.getText().toString().equals(rewritePasswordEditTexT.getText().toString())){
            setErrorOnEditText(passwordEditText,REWRITE_PASSWORD_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Checks which mandatory credential has mistakes and sets error on it's corresponding text view
     *
     * @param editText            edit text in which the error has occurred
     * @param restResponseMessage error message to set on the credential's text view
     */
    private void setErrorOnWrongCredential(EditText editText, String restResponseMessage) {
        setErrorOnEditText(editText, restResponseMessage);
    }

    /**
     * Creates a runnable on the UI Thread to set an error in an edit text
     *
     * @param editText     edit text in question
     * @param errorMessage error message to set in the edit text
     */
    private void setErrorOnEditText(EditText editText, String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.setError(errorMessage);
            }
        });
    }
}
