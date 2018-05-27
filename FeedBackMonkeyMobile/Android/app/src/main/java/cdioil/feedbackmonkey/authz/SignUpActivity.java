package cdioil.feedbackmonkey.authz;

import android.content.Intent;
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
                String restResponseBodyContent = "";
                Response restResponse = null;
                try {
                    restResponse = RESTRequest.
                            create(BuildConfig.SERVER_URL.
                                    concat(FeedbackMonkeyAPI.getAPIEntryPoint()).
                                    concat(FeedbackMonkeyAPI.getResourcePath("authentication")).
                                    concat(FeedbackMonkeyAPI.getSubResourcePath("authentication","register"))).
                            withMediaType(RESTRequest.RequestMediaType.JSON).
                            withBody(restRequestBody).
                            POST();
                    if(restResponse.code() == HttpsURLConnection.HTTP_OK){
                        //warn user they'll receive an activation code in their email
                        //go back to login activity
                    }else if(restResponse.code() == HttpsURLConnection.HTTP_UNAUTHORIZED){
                        //warn user they're not authorized(????) to register
                        // go back to login activity
                    }else if(restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST){
                        //warn user about something
                        // go back to login activity
                    }
                } catch (IOException e) {
                    ToastNotification.show(this,NO_INTERNET_CONNECTION);
                }
            }
        };
    }

    /**
     * Runnable that continues the sign up process to add the option info
     * @return Runnable that runs the SignUpPopUp Activity if all the mandatory credentials are
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
                                //show toast with success/failure message and go back to login screen
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
        String restRequestBody = new Gson().toJson(new RegistrationJSONService(email,password,
                firstName + " " + lastName
                ,phoneNumber,null,null));
        String restResponseBodyContent = "";
        Response restResponse = null;
        try {
             restResponse = RESTRequest.
                    create(BuildConfig.SERVER_URL.
                            concat(FeedbackMonkeyAPI.getAPIEntryPoint()).
                            concat(FeedbackMonkeyAPI.getResourcePath("authentication")).
                            concat(FeedbackMonkeyAPI.getSubResourcePath("authentication","register")).
                            concat("?validate=true")).
                    withMediaType(RESTRequest.RequestMediaType.JSON).
                    withBody(restRequestBody).
                    POST();
            restResponseBodyContent = restResponse.body().string();
        } catch (IOException e) {
            ToastNotification.show(this,NO_INTERNET_CONNECTION);
        }
        return true;
    }

}
