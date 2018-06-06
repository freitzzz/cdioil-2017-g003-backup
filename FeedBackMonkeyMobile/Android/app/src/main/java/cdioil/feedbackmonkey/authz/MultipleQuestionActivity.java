package cdioil.feedbackmonkey.authz;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.application.ListSurveyActivity;

public class MultipleQuestionActivity extends AppCompatActivity {

    private ListView questionListView;

    private MultipleChoiceQuestionItemListViewAdapter currentAdapter;

    private TextView questionText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_question);
        questionListView = findViewById(R.id.multiple_choice_question_list_view);
        currentAdapter = new MultipleChoiceQuestionItemListViewAdapter(this);
        questionListView.setAdapter(currentAdapter);
        for(int i = 0; i < 50; i++){
            currentAdapter.add("aotiehgeaoirghaeorihgaorengaoeig");
        }
    }



    /**
     * MultipleChoiceQuestionItemListViewAdapter that represents a custom adapter used for the ListView that holds the
     * surveys that an user can currently answer
     * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
     * @author <a href="1161191@isep.ipp.pt">Ana Guerra</a>
     */
    private class MultipleChoiceQuestionItemListViewAdapter extends ArrayAdapter<String>{
        /**
         * Builds a new {@link ListSurveyActivity.SurveyItemListViewAdapter} being adapted on a certain activity ListView
         * @param activity Activity with the activity which the ListView is being added the adapter
         * @param surveyItems List with a previous survey items predefined
         */
        public MultipleChoiceQuestionItemListViewAdapter(Activity activity, List<String> surveyItems) {
            super(activity,R.layout.survey_item_list_row,surveyItems);
        }

        /**
         * Builds a new {@link ListSurveyActivity.SurveyItemListViewAdapter} being adapted on a certain activity ListView
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
