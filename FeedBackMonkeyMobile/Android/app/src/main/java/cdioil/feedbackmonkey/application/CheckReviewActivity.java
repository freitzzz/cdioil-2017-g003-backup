package cdioil.feedbackmonkey.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cdioil.feedbackmonkey.R;

/**
 * Activity responsible for showing the user the answers they gave to a survey's questions.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class CheckReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_review);
    }
}
