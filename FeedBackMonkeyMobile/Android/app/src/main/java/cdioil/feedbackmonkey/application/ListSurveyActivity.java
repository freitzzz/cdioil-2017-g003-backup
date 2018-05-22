package cdioil.feedbackmonkey.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ScrollView;

import cdioil.feedbackmonkey.R;

public class ListSurveyActivity extends AppCompatActivity {

    /**
     * Text field for list the surveys.
     */
    private EditText listSurveysText;
    /**
     * Scroll View with all surveys of the user.
     */
    private ScrollView listSurveysView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_survey_activity);
}
}
