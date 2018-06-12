package cdioil.feedbackmonkey.application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;
import cdioil.feedbackmonkey.utils.ToastNotification;

/**
 * Activity responsible for listing a question and its options.
 */
public class QuestionActivity extends AppCompatActivity implements OnAnswerListener {

    /**
     * TextView holding the current question.
     */
    private TextView questionTextView;

    /**
     * User's authentication token.
     * NOTE: This attribute is important since it allows for multiple answers in a single session.
     */
    private String authenticationToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        questionTextView = findViewById(R.id.question);

        if (getIntent().getExtras() != null) {
            authenticationToken = getIntent().getExtras().getString("authenticationToken");
        } else {
            ToastNotification.show(this, "Não tem uma sessão");
            return;
        }

        loadQuestionInfo();
    }

    private void loadQuestionInfo() {
        Bundle currentQuestionBundle = ReviewXMLService.instance().getCurrentQuestionBundle();
        questionTextView.setText(currentQuestionBundle.getString("questionText"));

        String currentQuestionType = currentQuestionBundle.getString("currentQuestionType");

        if (currentQuestionType == null) {
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment newFragment = null;

        if (currentQuestionType.compareTo("B") == 0) {
            newFragment = BinaryQuestionFragment.newInstance();
        } else if (currentQuestionType.compareTo("Q") == 0) {
            newFragment = QuantitativeQuestionFragment.newInstance();
        } else if (currentQuestionType.compareTo("MC") == 0) {
            newFragment = MultipleChoiceQuestionFragment.newInstance();
        }

        fragmentTransaction.replace(R.id.fragment_container, newFragment);
        fragmentTransaction.commit();
    }

    /**
     * Builds an AlertDialog and starts a SubmitSuggestionActivity.
     */
    private void submitSuggestion() {
        runOnUiThread(() -> {
            AlertDialog.Builder wantToSubmitSuggestionDialog =
                    new AlertDialog.Builder(QuestionActivity.this);
            wantToSubmitSuggestionDialog.setTitle("Submeter Sugestão");
            wantToSubmitSuggestionDialog.setMessage("Quer submeter uma sugestão acerca do item avaliado?");
            wantToSubmitSuggestionDialog.setIcon(R.drawable.ic_info_black_18dp);
            wantToSubmitSuggestionDialog.setPositiveButton("Sim", (dialog, which) -> {

                Intent submitSuggestionIntent = new Intent(QuestionActivity.this, SubmitSuggestionActivity.class);
                submitSuggestionIntent.putExtra("authenticationToken", authenticationToken);
                startActivity(submitSuggestionIntent);
                finish();
            });
            wantToSubmitSuggestionDialog.setNegativeButton("Não", (dialog, which) -> {
//                Intent skipSuggestionIntent = new Intent(QuestionActivity.this, MainMenuActivity.class);
//                skipSuggestionIntent.putExtra("authenticationToken", authenticationToken);
//                startActivity(skipSuggestionIntent);
                finish();
            });
            final AlertDialog alertDialog = wantToSubmitSuggestionDialog.create();
            alertDialog.setOnShowListener(dialog -> {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#3d3d3d"));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#3d3d3d"));
            });
            alertDialog.show();
        });
    }

    @Override
    public void onQuestionAnswered(String answer) {
        try {
            ReviewXMLService xmlService = ReviewXMLService.instance();
            boolean canAnswerNextQuestion = xmlService.saveAnswer(answer);

            if (!canAnswerNextQuestion) {
                submitSuggestion();
            } else {
                loadQuestionInfo();
            }

        } catch (ParserConfigurationException | IOException | TransformerException | SAXException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            boolean canUndo = ReviewXMLService.instance().undoAnswer();

            if (canUndo) {
                loadQuestionInfo();
            } else {
                super.onBackPressed();
            }

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
