package cdioil.feedbackmonkey.application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;

public class QuantitativeQuestionActivity extends AppCompatActivity {

    private SeekArc seekArc;

    private TextView seekArcProgress;

    private TextView questionTextView;

    private String authenticationToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantitative_question);
        seekArc = findViewById(R.id.seekArc);
        questionTextView = findViewById(R.id.question);
        seekArcProgress = findViewById(R.id.seekArcProgress);
        authenticationToken = getIntent().getExtras().getString("authenticationToken");
        configureView();
    }

    private void configureView() {

        questionTextView.setText(getIntent().getExtras().getString("questionText"));

        ArrayList<String> questionScaleValues = getIntent().getExtras().getStringArrayList("options");
        int questionScaleValuesSize = questionScaleValues.size();

        Double minValue = Double.parseDouble(questionScaleValues.get(0));
        Double maxValue = Double.parseDouble(questionScaleValues.get(questionScaleValuesSize - 1));

        seekArc.setMax(maxValue.intValue()-minValue.intValue());
        seekArcProgress.setText(Integer.toString(minValue.intValue()));

        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {

            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
                seekArcProgress.setText(String.valueOf(i+1));
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

            }
        });

        seekArcProgress.setOnClickListener(v -> {

            Double value = Double.parseDouble(seekArcProgress.getText().toString());

            startQuestionActivity(value.toString());
        });
    }

    /**
     * Starts a new question activity.
     */
    private void startQuestionActivity(String answer) {

        try {
            ReviewXMLService xmlService = ReviewXMLService.newInstance();

            boolean displayNextQuestion = xmlService.saveAnswer(answer);

            if (!displayNextQuestion) {
                submitSuggestion();
            } else {

                Bundle questionBundle = ReviewXMLService.newInstance().getCurrentQuestionBundle();

                String currentQuestionType = questionBundle.getString("currentQuestionType");

                if (currentQuestionType == null) {
                    return;
                }

                switch (currentQuestionType) {
                    case "B":
                        Intent binaryIntent = new Intent(QuantitativeQuestionActivity.this, BinaryQuestionActivity.class);
                        binaryIntent.putExtra("authenticationToken", authenticationToken);
                        binaryIntent.putExtras(questionBundle);
                        startActivity(binaryIntent);
                        break;
                    case "Q":
                        Intent quantitativeIntent = new Intent(QuantitativeQuestionActivity.this, QuantitativeQuestionActivity.class);
                        quantitativeIntent.putExtra("authenticationToken", authenticationToken);
                        quantitativeIntent.putExtras(questionBundle);
                        startActivity(quantitativeIntent);
                        break;
                    case "MC":
                        Intent multipleChoiceIntent = new Intent(QuantitativeQuestionActivity.this, MultipleChoiceQuestionActivity.class);
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
                    new AlertDialog.Builder(QuantitativeQuestionActivity.this);
            wantToSubmitSuggestionDialog.setTitle("Submeter Sugestão");
            wantToSubmitSuggestionDialog.setMessage("Quer submeter uma sugestão acerca do item avaliado?");
            wantToSubmitSuggestionDialog.setIcon(R.drawable.ic_info_black_18dp);
            wantToSubmitSuggestionDialog.setPositiveButton("Sim", (dialog, which) -> {

                Intent submitSuggestionIntent = new Intent(QuantitativeQuestionActivity.this, SubmitSuggestionActivity.class);
                submitSuggestionIntent.putExtra("authenticationToken", authenticationToken);
                startActivity(submitSuggestionIntent);
            });
            wantToSubmitSuggestionDialog.setNegativeButton("Não", (dialog, which) -> {
                Intent skipSuggestionIntent = new Intent(QuantitativeQuestionActivity.this, MainMenuActivity.class);
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
