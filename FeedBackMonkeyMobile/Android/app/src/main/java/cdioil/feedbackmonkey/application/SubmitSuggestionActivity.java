package cdioil.feedbackmonkey.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import javax.xml.transform.TransformerException;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;

public class SubmitSuggestionActivity extends AppCompatActivity {

    /**
     * String with the current authentication token
     */
    private String authenticationToken;

    /**
     * OK Button
     */
    private Button submitSuggestionButton;

    /**
     * Text field for the suggestion.
     */
    private EditText suggestionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_suggestion);
        submitSuggestionButton = findViewById(R.id.submitSuggestionButton);
        suggestionEditText = findViewById(R.id.submitSuggestionEditText);
        authenticationToken = getIntent().getExtras().getString("authenticationToken");
        configureView();
    }

    /**
     * Sets on click listener to submit the suggestion.
     */
    private void configureView() {
        submitSuggestionButton.setOnClickListener(view -> {

            String suggestionText = suggestionEditText.getText().toString();

            if (suggestionText.trim().isEmpty()) {
                suggestionEditText.setError("A sugestão não pode ser vazia!");
                return;
            }


            ReviewXMLService xmlService = ReviewXMLService.instance();
            try {
                xmlService.saveSuggestion(suggestionEditText.getText().toString());
            } catch (TransformerException e) {
                e.printStackTrace();
            }

            Intent mainMenuIntent = new Intent(SubmitSuggestionActivity.this, MainMenuActivity.class);
            mainMenuIntent.putExtra("authenticationToken", authenticationToken);
            startActivity(mainMenuIntent);
            finish();
        });
    }
}
