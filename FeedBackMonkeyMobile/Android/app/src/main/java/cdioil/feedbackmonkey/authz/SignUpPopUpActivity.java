package cdioil.feedbackmonkey.authz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import cdioil.feedbackmonkey.R;

/**
 * Popup Activity for the sign up process of the mobile app
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class SignUpPopUpActivity extends AppCompatActivity {
    /**
     * Text View for the Location of the user.
     */
    private TextView locationTextView;
    /**
     * Text View for the user's birth date.
     */
    private TextView birthDateTextView;
    /**
     * Submit extra info button.
     */
    private Button submitExtraInfoButton;

    /**
     * Sets up the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_pop_up);
        locationTextView = findViewById(R.id.signUpPopUpLocationTextView);
        birthDateTextView = findViewById(R.id.signUpPopUpBirthDateTextView);
        submitExtraInfoButton = findViewById(R.id.signUpPopUpSubmitAdditionalInfoButton);
        submitExtraInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if optional info is valid, warn user, go back to LoginActivity
            }
        });
    }
}
