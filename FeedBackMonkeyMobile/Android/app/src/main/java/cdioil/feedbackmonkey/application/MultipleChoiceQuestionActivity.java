package cdioil.feedbackmonkey.application;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;

public class MultipleChoiceQuestionActivity extends AppCompatActivity {

    private ListView questionListView;

    private MultipleChoiceQuestionItemListViewAdapter currentAdapter;

    private TextView questionTextView;

    private String authenticationToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_question);
        authenticationToken = getIntent().getExtras().getString("authenticationToken");
        configureView();
    }

    private void configureView() {
        questionListView = findViewById(R.id.multiple_choice_question_list_view);
        questionTextView = findViewById(R.id.question);
        currentAdapter = new MultipleChoiceQuestionItemListViewAdapter(this);
        questionListView.setAdapter(currentAdapter);

        if (getIntent().getExtras() != null) {
            questionTextView.setText(getIntent().getExtras().getString("questionText"));
            ArrayList<String> questionOptions = getIntent().getExtras().getStringArrayList("options");
            currentAdapter.addAll(questionOptions);

            questionListView.setOnItemClickListener((adapterView, view, i, l) ->
                    startQuestionActivity(currentAdapter.getItem(i)));
        }

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
                        Intent binaryIntent = new Intent(MultipleChoiceQuestionActivity.this, BinaryQuestionActivity.class);
                        binaryIntent.putExtra("authenticationToken", authenticationToken);
                        binaryIntent.putExtras(questionBundle);
                        startActivity(binaryIntent);
                        break;
                    case "Q":
                        Intent quantitativeIntent = new Intent(MultipleChoiceQuestionActivity.this, QuantitativeQuestionActivity.class);
                        quantitativeIntent.putExtra("authenticationToken", authenticationToken);
                        quantitativeIntent.putExtras(questionBundle);
                        startActivity(quantitativeIntent);
                        break;
                    case "MC":
                        Intent multipleChoiceIntent = new Intent(MultipleChoiceQuestionActivity.this, MultipleChoiceQuestionActivity.class);
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
                    new AlertDialog.Builder(MultipleChoiceQuestionActivity.this);
            wantToSubmitSuggestionDialog.setTitle("Submeter Sugestão");
            wantToSubmitSuggestionDialog.setMessage("Quer submeter uma sugestão acerca do item avaliado?");
            wantToSubmitSuggestionDialog.setIcon(R.drawable.ic_info_black_18dp);
            wantToSubmitSuggestionDialog.setPositiveButton("Sim", (dialog, which) -> {

                Intent submitSuggestionIntent = new Intent(MultipleChoiceQuestionActivity.this, SubmitSuggestionActivity.class);
                submitSuggestionIntent.putExtra("authenticationToken", authenticationToken);
                startActivity(submitSuggestionIntent);
            });
            wantToSubmitSuggestionDialog.setNegativeButton("Não", (dialog, which) -> {
                Intent skipSuggestionIntent = new Intent(MultipleChoiceQuestionActivity.this, MainMenuActivity.class);
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


    /**
     * MultipleChoiceQuestionItemListViewAdapter that represents a custom adapter used for the ListView that holds the
     * questions for a multiple choice question
     *
     * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
     * @author <a href="1161191@isep.ipp.pt">Rita Gonçalves</a>
     * @author <a href="1169999@isep.ipp.pt">Joana Pinheiro</a>
     */
    private class MultipleChoiceQuestionItemListViewAdapter extends ArrayAdapter<String> {
        /**
         * Builds a new {@link MultipleChoiceQuestionActivity.MultipleChoiceQuestionItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity      Activity with the activity which the ListView is being added the adapter
         * @param questionItems List with the question options
         */
        public MultipleChoiceQuestionItemListViewAdapter(Activity activity, List<String> questionItems) {
            super(activity, R.layout.multiple_choice_question_list_item, questionItems);
        }

        /**
         * Builds a new {@link MultipleChoiceQuestionActivity.MultipleChoiceQuestionItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity Activity with the activity which the ListView is being added the adapter
         */
        public MultipleChoiceQuestionItemListViewAdapter(Activity activity) {
            super(activity, R.layout.multiple_choice_question_list_item);
        }

        /**
         * Returns a converted view item for the adapter on a certain item position
         *
         * @param position    Integer with the adapter item position
         * @param convertView View with the view being converted as item
         * @param parent      ViewGroup with the group parent
         * @return View with the converted view as a adapter item
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.multiple_choice_question_list_item, parent, false);
            }
            TextView questionOptionTextView = convertView.findViewById(R.id.multiple_choice_question_option_text_view);
            questionOptionTextView.setText(getItem(position));
            return convertView;
        }
    }
}
