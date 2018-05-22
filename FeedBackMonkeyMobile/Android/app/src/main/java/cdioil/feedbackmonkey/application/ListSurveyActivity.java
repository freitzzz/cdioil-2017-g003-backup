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


import java.util.List;

import cdioil.feedbackmonkey.R;

public class ListSurveyActivity extends AppCompatActivity {

    /**
     * Recycler View that is hold by the scroll view
     */
    private ListView listSurveysRecyclerView;

    /**
     * Creates the current view
     * @param savedInstanceState Bundle with the previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_survey_activity);
        configure();
    }

    /**
     * Configures the current view
     */
    private void configure(){
        listSurveysRecyclerView=findViewById(R.id.listViewListSurveys);

    }

    private class SurveyItemListViewAdapter extends ArrayAdapter<String>{
        public SurveyItemListViewAdapter(Activity activity,List<String> surveyItems) {
            super(activity,R.layout.survey_item_list_row,surveyItems);
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
                convertView=LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.survey_item_list_row,parent,false);
            }
            TextView viewSurveyName=convertView.findViewById(R.id.textViewSurveyItemListRow);
            viewSurveyName.setText(getItem(position));
            return convertView;
        }
    }

}
