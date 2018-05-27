package cdioil.feedbackmonkey.authz;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.json.RegistrationJSONService;
import cdioil.feedbackmonkey.utils.ToastNotification;
import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Activity for the sign up process of the app.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class SignUpActivity extends AppCompatActivity {
    /**
     * Message indicating the user doesn't have an internet connection.
     */
    private static final String NO_INTERNET_CONNECTION="Não existe conexão à Internet!";
    /**
     * JSON field for invalid email while signing up.
     */
    private static final String JSON_FIELD_FOR_INVALID_EMAIL = "Whitelist";
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
     * JSON field for a registration error happening while signing up.
     */
    private static final String JSON_FIELD_FOR_REGISTRATION_ERROR = "Register";
    /**
     * Success message to let the user know the sign up was a success.
     */
    private static final String SIGNUP_SUCCESS = "O registo foi efetuado com sucesso!\nDeverá " +
            " receber um email com o seu código de ativaçao em poucos momentos.";
    /**
     * Text view for the user's first name.
     */
    private TextView firstNameTextView;
    /**
     * Text view for the user's last name.
     */
    private TextView lastNameTextView;
    /**
     * Text view for the user's email.
     */
    private TextView emailTextView;
    /**
     * Text view for the user's password.
     */
    private TextView passwordTextView;
    /**
     * Text view for the user's phone number.
     */
    private TextView phoneNumberTextView;
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
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstNameTextView = findViewById(R.id.signupFirstNameText);
        lastNameTextView = findViewById(R.id.signupLastNameText);
        emailTextView = findViewById(R.id.signupEmailText);
        passwordTextView = findViewById(R.id.signupPasswordText);
        phoneNumberTextView = findViewById(R.id.signupPhoneNumberText);
        createAccountButton = findViewById(R.id.signupCreateAccountButton);
        addExtraInfoButton = findViewById(R.id.signupAdditionalInfoButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread createAccount = new Thread(createAccount());
                createAccount.start();
            }
        });
        addExtraInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread continueSignUp = new Thread(continueSignup());
                continueSignUp.start();
            }
        });
    }

    /**
     * Starts the sign up process of the app.
     * @return Runnable that executes the sign up process of the mobile app
     */
    private Runnable createAccount(){
        return () ->{
            if(validateCredentials()){
                String firstName = firstNameTextView.getText().toString();
                String lastName = lastNameTextView.getText().toString();
                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                String phoneNumber = phoneNumberTextView.getText().toString();
                String restRequestBody = new Gson().toJson(new RegistrationJSONService(email,password,
                        firstName + " " + lastName
                        ,phoneNumber,null,null));
                Response restResponse;
                try {
                    restResponse = RESTRequest.
                            create(BuildConfig.SERVER_URL.
                                    concat(FeedbackMonkeyAPI.getAPIEntryPoint()).
                                    concat(FeedbackMonkeyAPI.getResourcePath("authentication")).
                                    concat(FeedbackMonkeyAPI.getSubResourcePath("authentication","register account"))).
                            withMediaType(RESTRequest.RequestMediaType.JSON).
                            withBody(restRequestBody).
                            POST();
                    if(restResponse.code() == HttpsURLConnection.HTTP_OK){
                        Intent backToLoginIntent = new Intent(SignUpActivity.this,LoginActivity.class);
                        Bundle backToLoginBundle = new Bundle();
                        backToLoginBundle.putString("toastText",SIGNUP_SUCCESS);
                        backToLoginIntent.putExtras(backToLoginBundle);
                        startActivity(backToLoginIntent);
                    }else if(restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST){
                        RegistrationJSONService restResponseBody = new Gson().
                                fromJson(restResponse.body().string(),RegistrationJSONService.class);
                        String restResponseMessage = restResponseBody.getMessage();
                        Intent backToLoginIntent = new Intent(SignUpActivity.this,LoginActivity.class);
                        Bundle backToLoginBundle = new Bundle();
                        backToLoginBundle.putString("toastText",restResponseMessage);
                        backToLoginIntent.putExtras(backToLoginBundle);
                        startActivity(backToLoginIntent);
                    }
                } catch (IOException e) {
                    ToastNotification.show(this,NO_INTERNET_CONNECTION);
                }
            }
        };
    }

    /**
     * Runnable that continues the sign up process to add the optional credentials
     * @return Runnable that runs an alert dialog if all the mandatory credentials are
     * acceptable
     */
    private Runnable continueSignup(){
        return () ->{
            if(validateCredentials()){
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
                        EditText locationText = promptView.findViewById(R.id.signupLocationText);
                        EditText birthDateText = promptView.findViewById(R.id.signupBirthDateText);
                        sendRegisterRequestButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String firstName = firstNameTextView.getText().toString();
                                String lastName = lastNameTextView.getText().toString();
                                String email = emailTextView.getText().toString();
                                String password = passwordTextView.getText().toString();
                                String phoneNumber = phoneNumberTextView.getText().toString();
                                String location = locationText.getText().toString();
                                String birthDate = birthDateText.getText().toString();
                                //rest request to register account
                                String restRequestBody = new Gson().toJson(new RegistrationJSONService(email,password,
                                        firstName + " " + lastName
                                        ,phoneNumber,location,birthDate));
                                Response restResponse = null;
                                try {
                                    restResponse = RESTRequest.
                                            create(BuildConfig.SERVER_URL.
                                                    concat(FeedbackMonkeyAPI.getAPIEntryPoint()).
                                                    concat(FeedbackMonkeyAPI.getResourcePath("authentication")).
                                                    concat(FeedbackMonkeyAPI.getSubResourcePath("authentication","register account")).
                                                    concat("?validate=true")).
                                            withMediaType(RESTRequest.RequestMediaType.JSON).
                                            withBody(restRequestBody).
                                            POST();
                                } catch (IOException e) {
                                    ToastNotification.show(SignUpActivity.this,NO_INTERNET_CONNECTION);
                                }
                                if(restResponse.code() == HttpsURLConnection.HTTP_OK){
                                    try{
                                        restResponse = RESTRequest.
                                                create(BuildConfig.SERVER_URL.
                                                        concat(FeedbackMonkeyAPI.getAPIEntryPoint()).
                                                        concat(FeedbackMonkeyAPI.getResourcePath("authentication")).
                                                        concat(FeedbackMonkeyAPI.getSubResourcePath("authentication","register"))).
                                                withMediaType(RESTRequest.RequestMediaType.JSON).
                                                withBody(restRequestBody).
                                                POST();
                                    }catch(IOException e){
                                        ToastNotification.show(SignUpActivity.this,NO_INTERNET_CONNECTION);
                                    }
                                    if(restResponse.code() == HttpsURLConnection.HTTP_OK){
                                        Intent backToLoginIntent = new Intent(SignUpActivity.this,LoginActivity.class);
                                        Bundle backToLoginBundle = new Bundle();
                                        backToLoginBundle.putString("toastText",SIGNUP_SUCCESS);
                                        backToLoginIntent.putExtras(backToLoginBundle);
                                        startActivity(backToLoginIntent);
                                    }else if(restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST){
                                        RegistrationJSONService restResponseBody = null;
                                        try {
                                            restResponseBody = new Gson().
                                                    fromJson(restResponse.body().string(),RegistrationJSONService.class);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        String restResponseMessage = restResponseBody.getMessage();
                                        Intent backToLoginIntent = new Intent(SignUpActivity.this,LoginActivity.class);
                                        Bundle backToLoginBundle = new Bundle();
                                        backToLoginBundle.putString("toastText",restResponseMessage);
                                        backToLoginIntent.putExtras(backToLoginBundle);
                                        startActivity(backToLoginIntent);
                                    }
                                }else if(restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST){
                                    RegistrationJSONService restResponseBody = new Gson().
                                            fromJson(restResponse.body().toString(),RegistrationJSONService.class);
                                    String restResponseField = restResponseBody.getField();
                                    String restResponseMessage = restResponseBody.getMessage();
                                    if(restResponseField.equals(JSON_FIELD_FOR_INVALID_LOCATION)){
                                        locationText.setError(restResponseMessage);
                                    }else if(restResponseField.equals(JSON_FIELD_FOR_INVALID_BIRTH_DATE)){
                                        birthDateText.setError(restResponseMessage);
                                    }
                                }
                            }
                        });
                        activateAccountAlert.show();
                    }
                });
            }
        };
    }


    /**
     * Validates the mandatory credentials that the user must insert in order to sign up
     * @return true if all the credentials are valid, false if otherwise
     */
    private boolean validateCredentials(){

        boolean validCredentials = true;

        String firstName = firstNameTextView.getText().toString();
        String lastName = lastNameTextView.getText().toString();
        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        String phoneNumber = phoneNumberTextView.getText().toString();
        String restRequestBody;
        if(firstName.isEmpty() && lastName.isEmpty() && email.isEmpty() &&
                password.isEmpty() && phoneNumber.isEmpty()){
            restRequestBody = new Gson().toJson(new RegistrationJSONService(null,
                    null,null,null,null,null));
        }else{
            restRequestBody = new Gson().toJson(new RegistrationJSONService(email,password,
                    firstName + " " + lastName
                    ,phoneNumber,null,null));
        }
        Response restResponse;
        try {
             restResponse = RESTRequest.
                    create(BuildConfig.SERVER_URL.
                            concat(FeedbackMonkeyAPI.getAPIEntryPoint()).
                            concat(FeedbackMonkeyAPI.getResourcePath("Authentication")).
                            concat(FeedbackMonkeyAPI.getSubResourcePath("Authentication","Register Account")).
                            concat("?validate=true")).
                    withMediaType(RESTRequest.RequestMediaType.JSON).
                    withBody(restRequestBody).
                    POST();
             String body = restResponse.body().string();
           RegistrationJSONService restResponseBody = new Gson().
                    fromJson(body,RegistrationJSONService.class);
           String restResponseField = restResponseBody.getField();
           String restResponseMessage = restResponseBody.getMessage();
           if(restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST){
               validCredentials = false;
               if(restResponseField.equals(JSON_FIELD_FOR_INVALID_EMAIL)){
                   SignUpActivity.this.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           emailTextView.setError(restResponseMessage);
                       }
                   });
               }else if(restResponseField.equals(JSON_FIELD_FOR_INVALID_NAME)){
                   SignUpActivity.this.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           firstNameTextView.setError(restResponseMessage);
                       }
                   });
               }else if(restResponseField.equals(JSON_FIELD_FOR_INVALID_PASSWORD)){
                   SignUpActivity.this.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           passwordTextView.setError(restResponseMessage);
                       }
                   });
               }else if(restResponseField.equals(JSON_FIELD_FOR_INVALID_PHONE_NUMBER)){
                   SignUpActivity.this.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           phoneNumberTextView.setError(restResponseMessage);
                       }
                   });
               }else if(restResponseField.equals(JSON_FIELD_FOR_MISSING_CREDENTIALS)){
                   ToastNotification.show(SignUpActivity.this,restResponseMessage);
               }
           }
        } catch (IOException e) {
            ToastNotification.show(this,NO_INTERNET_CONNECTION);
        }
        return validCredentials;
    }

}
