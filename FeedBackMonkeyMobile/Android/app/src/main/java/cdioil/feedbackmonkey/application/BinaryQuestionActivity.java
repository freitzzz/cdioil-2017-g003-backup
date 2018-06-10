package cdioil.feedbackmonkey.application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;

@Deprecated
public class BinaryQuestionActivity extends AppCompatActivity {

    private TextView questionTextView;

    private Button yesButton;

    private Button noButton;

    private String authenticationToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_question);
        questionTextView = findViewById(R.id.question);
        yesButton = findViewById(R.id.yes_button);
        noButton = findViewById(R.id.no_button);
        authenticationToken = getIntent().getExtras().getString("authenticationToken");
        configureView();
    }

    private void configureView() {
        if (getIntent().getExtras() != null) {
            questionTextView.setText(getIntent().getExtras().getString("questionText"));
        }
        yesButton.setOnClickListener(view -> {
            startQuestionActivity("true");
        });
        noButton.setOnClickListener(view -> {
            startQuestionActivity("false");
        });
    }

    /**
     * Starts a new question activity.
     */
    private void startQuestionActivity(String answer) {

        try {
            ReviewXMLService xmlService = ReviewXMLService.instance();

            boolean displayNextQuestion = xmlService.saveAnswer(answer);

            if (!displayNextQuestion) {
                submitSuggestion();
            } else {

                Bundle questionBundle = ReviewXMLService.instance().getCurrentQuestionBundle();

                String currentQuestionType = questionBundle.getString("currentQuestionType");

                if (currentQuestionType == null) {
                    return;
                }

                switch (currentQuestionType) {
                    case "B":
                        Intent binaryIntent = new Intent(BinaryQuestionActivity.this, BinaryQuestionActivity.class);
                        binaryIntent.putExtra("authenticationToken", authenticationToken);
                        binaryIntent.putExtras(questionBundle);
                        startActivity(binaryIntent);
                        break;
                    case "Q":
                        Intent quantitativeIntent = new Intent(BinaryQuestionActivity.this, QuantitativeQuestionActivity.class);
                        quantitativeIntent.putExtra("authenticationToken", authenticationToken);
                        quantitativeIntent.putExtras(questionBundle);
                        startActivity(quantitativeIntent);
                        break;
                    case "MC":
                        Intent multipleChoiceIntent = new Intent(BinaryQuestionActivity.this, MultipleChoiceQuestionActivity.class);
                        multipleChoiceIntent.putExtra("authenticationToken", authenticationToken);
                        multipleChoiceIntent.putExtras(questionBundle);
                        startActivity(multipleChoiceIntent);
                        break;
                    default:
                        break;
                }
            }

        } catch (TransformerException | SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds an AlertDialog and starts a SubmitSuggestionActivity.
     */
    private void submitSuggestion() {
        runOnUiThread(() -> {
            AlertDialog.Builder wantToSubmitSuggestionDialog =
                    new AlertDialog.Builder(BinaryQuestionActivity.this);
            wantToSubmitSuggestionDialog.setTitle("Submeter Sugestão");
            wantToSubmitSuggestionDialog.setMessage("Quer submeter uma sugestão acerca do item avaliado?");
            wantToSubmitSuggestionDialog.setIcon(R.drawable.ic_info_black_18dp);
            wantToSubmitSuggestionDialog.setPositiveButton("Sim", (dialog, which) -> {

                Intent submitSuggestionIntent = new Intent(BinaryQuestionActivity.this, SubmitSuggestionActivity.class);
                submitSuggestionIntent.putExtra("authenticationToken", authenticationToken);
                startActivity(submitSuggestionIntent);
            });
            wantToSubmitSuggestionDialog.setNegativeButton("Não", (dialog, which) -> {
                Intent skipSuggestionIntent = new Intent(BinaryQuestionActivity.this, MainMenuActivity.class);
                skipSuggestionIntent.putExtra("authenticationToken", authenticationToken);
                startActivity(skipSuggestionIntent);
            });
            final AlertDialog alertDialog = wantToSubmitSuggestionDialog.create();
            alertDialog.setOnShowListener(dialog -> {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#3d3d3d"));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#3d3d3d"));
            });
            alertDialog.show();
        });
    }
}
