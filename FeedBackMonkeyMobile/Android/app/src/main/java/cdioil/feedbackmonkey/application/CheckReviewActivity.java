package cdioil.feedbackmonkey.application;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.application.services.SurveyService;

/**
 * Activity responsible for showing the user the answers they gave to a survey's questions.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class CheckReviewActivity extends AppCompatActivity {
    /**
     * Constant that represents a Question Tag for the ListView Items.
     */
    private final static String QUESTION_TAG = "\nPergunta: ";
    /**
     * Constant that represents an Answer Tag for the ListView Items.
     */
    private final static String ANSWER_TAG = "Resposta: ";
    /**
     * Question Answer List View that will show the user their answers to given questions of a survey.
     */
    private ListView questionAnswerListView;
    /**
     * ListView Item Adapter.
     */
    private CheckReviewItemListViewAdapter currentAdapter;

    /**
     * Creates the current activity.
     *
     * @param savedInstanceState Bundle with the previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_review);
        configure();
    }

    /**
     * Configures the components of the Activity.
     */
    private void configure() {
        questionAnswerListView = findViewById(R.id.questionAnswerListView);
        currentAdapter = new CheckReviewItemListViewAdapter(this);
        List<String> questionAnswerList = buildQuestionAnswerList();
        currentAdapter.addAll(questionAnswerList);
        questionAnswerListView.setAdapter(currentAdapter);
    }

    /**
     * Builds the list of strings that the ListView Adapter needs with the arrays passed in the intent
     * containing the list of questions and answers
     *
     * @return List of Strings with the following format: "Pergunta: {text}\nResposta: {text}"
     */
    private List<String> buildQuestionAnswerList() {
        ArrayList<String> questionAnswerList = new ArrayList<>();
        ArrayList<String> questions = getIntent().getExtras().getStringArrayList("questions");
        ArrayList<String> answers = getIntent().getExtras().getStringArrayList("answers");
        Boolean.toString(true);

        for(int i = 0; i < answers.size(); i++){
            if(answers.get(i).equalsIgnoreCase(Boolean.toString(true))){
                answers.remove(i);
                answers.add(i,"Sim");
            }else if(answers.get(i).equalsIgnoreCase(Boolean.toString(false))){
                answers.remove(i);
                answers.add(i,"Não");
            }
        }

        for (int i = 0; i < questions.size(); i++) {
            questionAnswerList.add(QUESTION_TAG
                    .concat(questions.get(i)
                            .concat("\n" + ANSWER_TAG
                                    .concat(answers.get(i) + "\n"))));
        }
        return questionAnswerList;
    }

    /**
     * CheckReviewItemListViewAdapter that represents a custom adapter used for the ListView that holds the
     * questions and answeres a user gave in a review
     *
     * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
     */
    private class CheckReviewItemListViewAdapter extends ArrayAdapter<String> {
        /**
         * Builds a new {@link CheckReviewActivity.CheckReviewItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity       Activity with the activity which the ListView is being added the adapter
         * @param questionAnswer List with the questions and answers given in a review
         */
        public CheckReviewItemListViewAdapter(Activity activity, List<String> questionAnswer) {
            super(activity, R.layout.check_review_list_item, questionAnswer);
        }

        /**
         * Builds a new {@link CheckReviewActivity.CheckReviewItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity Activity with the activity which the ListView is being added the adapter
         */
        public CheckReviewItemListViewAdapter(Activity activity) {
            super(activity, R.layout.check_review_list_item);
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
                        .inflate(R.layout.check_review_list_item, parent, false);
            }
            TextView questionAnswerTextView = convertView.findViewById(R.id.questionAnswerTextView);
            questionAnswerTextView.setText(getItem(position));
            return convertView;
        }
    }
}
