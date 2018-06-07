package cdioil.feedbackmonkey.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.authz.LoginActivity;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.DialogInterface;
import android.app.AlertDialog;

import java.io.IOException;

public class SubmitSuggestionActivity extends AppCompatActivity {

    /**
     * String with the current authentication token
     */
    private String authenticationToken;

    /**
     * OK Button
     */
    private Button okButton;

    /**
     * Text field for the suggestion.
     */
    private EditText suggestionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        okButton = findViewById(R.id.okButton);
        suggestionText = findViewById(R.id.suggestionText);
        authenticationToken=getIntent().getExtras().getString("authenticationToken");
        setContentView(R.layout.activity_submit_suggestion);
    }

    /**
     * Sets on click listener to submit the suggestion.
     */
    private void submitSuggestion() {
        okButton.setOnClickListener(view -> {
            if(suggestionText.getText().length() == 0){
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent mainMenuIntent = new Intent(SubmitSuggestionActivity.this, MainMenuActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("authenticationToken", authenticationToken);
                                mainMenuIntent.putExtras(bundle);
                                startActivity(mainMenuIntent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Pretende submeter inquérito sem sugestão?").setPositiveButton("Sim", dialogClickListener)
                        .setNegativeButton("Não", dialogClickListener).show();
            }else{
                String suggestion = suggestionText.getText().toString();
                //ReviewXMLService xmlService = new ReviewXMLService();
                //xmlService.saveSuggestion(suggestion);
                Intent mainMenuIntent = new Intent(SubmitSuggestionActivity.this, MainMenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("authenticationToken", authenticationToken);
                mainMenuIntent.putExtras(bundle);
                startActivity(mainMenuIntent);
            }
        });
    }
}
