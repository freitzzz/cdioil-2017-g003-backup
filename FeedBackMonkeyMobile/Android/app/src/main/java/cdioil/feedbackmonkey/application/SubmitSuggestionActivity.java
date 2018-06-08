package cdioil.feedbackmonkey.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;

import android.widget.Button;
import android.widget.EditText;

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
        authenticationToken=getIntent().getExtras().getString("authenticationToken");
        configureView();
    }

    /**
     * Sets on click listener to submit the suggestion.
     */
    private void configureView() {
        submitSuggestionButton.setOnClickListener(view -> {

            String suggestionText = suggestionEditText.getText().toString();

            if(suggestionText.trim().isEmpty()){
                suggestionEditText.setError("A sugestão não pode ser vazia!");
                return;
            }


            ReviewXMLService xmlService = ReviewXMLService.newInstance();
            xmlService.saveSuggestion(suggestionEditText.getText().toString());

            Intent mainMenuIntent = new Intent(SubmitSuggestionActivity.this, MainMenuActivity.class);
            mainMenuIntent.putExtra("authenticationToken", authenticationToken);
            startActivity(mainMenuIntent);

//            if(suggestionEditText.getText().length() == 0){
//                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which){
//                            case DialogInterface.BUTTON_POSITIVE:
//                                Intent mainMenuIntent = new Intent(SubmitSuggestionActivity.this, MainMenuActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("authenticationToken", authenticationToken);
//                                mainMenuIntent.putExtras(bundle);
//                                startActivity(mainMenuIntent);
//                                break;
//
//                            case DialogInterface.BUTTON_NEGATIVE:
//
//                                break;
//                        }
//                    }
//                };
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setMessage("Pretende submeter inquérito sem sugestão?").setPositiveButton("Sim", dialogClickListener)
//                        .setNegativeButton("Não", dialogClickListener).show();
//            }else{
//                String suggestion = suggestionEditText.getText().toString();
//                //ReviewXMLService xmlService = new ReviewXMLService();
//                //xmlService.saveSuggestion(suggestion);
//                Intent mainMenuIntent = new Intent(SubmitSuggestionActivity.this, MainMenuActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("authenticationToken", authenticationToken);
//                mainMenuIntent.putExtras(bundle);
//                startActivity(mainMenuIntent);
//            }
        });
    }
}
