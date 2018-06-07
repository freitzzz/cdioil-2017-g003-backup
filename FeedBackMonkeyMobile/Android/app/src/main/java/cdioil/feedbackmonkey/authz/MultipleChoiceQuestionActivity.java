package cdioil.feedbackmonkey.authz;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.application.ListSurveyActivity;

public class MultipleChoiceQuestionActivity extends AppCompatActivity {

    private ListView questionListView;

    private MultipleChoiceQuestionItemListViewAdapter currentAdapter;

    private TextView questionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_question);
        configureView();
    }

    private void configureView() {
        questionListView = findViewById(R.id.multiple_choice_question_list_view);
        currentAdapter = new MultipleChoiceQuestionItemListViewAdapter(this);
        if(getIntent().getExtras() != null){
            questionTextView.setText(getIntent().getExtras().getString("questionText"));
            ArrayList<String> temp = getIntent().getExtras().getStringArrayList("questionOptions");
            for(int i = 0; i < temp.size(); i++){
                currentAdapter.add(temp.get(i));
                questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
            }
        }
    }


    /**
     * MultipleChoiceQuestionItemListViewAdapter that represents a custom adapter used for the ListView that holds the
     * questions for a multiple choice question
     * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
     * @author <a href="1161191@isep.ipp.pt">Rita Gonçalves</a>
     * @author <a href="1169999@isep.ipp.pt">Joana Pinheiro</a>
     */
    private class MultipleChoiceQuestionItemListViewAdapter extends ArrayAdapter<String>{
        /**
         * Builds a new {@link MultipleChoiceQuestionActivity.MultipleChoiceQuestionItemListViewAdapter} being adapted on a certain activity ListView
         * @param activity Activity with the activity which the ListView is being added the adapter
         * @param questionItems List with the question options
         */
        public MultipleChoiceQuestionItemListViewAdapter(Activity activity, List<String> questionItems) {
            super(activity,R.layout.survey_item_list_row,questionItems);
        }

        /**
         * Builds a new {@link MultipleChoiceQuestionActivity.MultipleChoiceQuestionItemListViewAdapter} being adapted on a certain activity ListView
         * @param activity Activity with the activity which the ListView is being added the adapter
         */
        public MultipleChoiceQuestionItemListViewAdapter(Activity activity){
            super(activity,R.layout.survey_item_list_row);
        }
        /**
         * Returns a converted view item for the adapter on a certain item position
         * @param position Integer with the adapter item position
         * @param convertView View with the view being converted as item
         * @param parent ViewGroup with the group parent
         * @return View with the converted view as a adapter item
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView==null){
                convertView= LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.multiple_choice_question_list_item,parent,false);
            }
            return convertView;
        }
    }
}
