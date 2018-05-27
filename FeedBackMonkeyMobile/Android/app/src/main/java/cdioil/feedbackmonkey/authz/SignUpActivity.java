package cdioil.feedbackmonkey.authz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import okhttp3.Response;

/**
 * Activity for the sign up process of the app.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class SignUpActivity extends AppCompatActivity {
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
                //Warn user they'll be sent an activation code, go back to LoginActivity
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
            /*if(validateCredentials()){
                //Intent popupIntent = new Intent(SignUpActivity.this,SignUpPopUpActivity.class);
                //startActivity(popupIntent);
            }*/
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

        Response restResponse = null;
        try {
            restResponse = RESTRequest.
                    create(BuildConfig.SERVER_URL.
                            concat(FeedbackMonkeyAPI.getAPIEntryPoint()).
                            concat(FeedbackMonkeyAPI.getResourcePath("")).
                            concat(FeedbackMonkeyAPI.getSubResourcePath("regiserwhatever","aougha")))
                    .POST();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String restResponseBodyContent = "";
        try {
            restResponseBodyContent = restResponse.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(restResponseBodyContent.equals("invalidFirstName:true")){
            //txtView.setAlert
            firstNameTextView.setError("mano tens o 1º nome errado lmao xDDDDDDDDD");
            return false;
        }else if(restResponseBodyContent.equals("invalidLastName:true")){
            //TxtView.setAlert
            return false;
        }else if(restResponseBodyContent.equals("invalidEmail:true")){
            //TxtView.setAlert
            return false;
        }else if(restResponseBodyContent.equals("invalidPassword:true")){
            //TxtView.setAlert
            return false;
        }else if(restResponseBodyContent.equals("invalidPhoneNumber:true")){
            //TxtView.setAlert
            return false;
        }
        return true;
    }
}
