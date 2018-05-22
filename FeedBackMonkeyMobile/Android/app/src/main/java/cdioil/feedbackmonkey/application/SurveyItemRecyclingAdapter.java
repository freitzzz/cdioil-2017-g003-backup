package cdioil.feedbackmonkey.application;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cdioil.feedbackmonkey.R;

/**
 * Created by joaom on 22/05/2018.
 */

public final class SurveyItemRecyclingAdapter extends RecyclerView.Adapter<SurveyItemRecyclingAdapter.ViewHolder>{
        /**
         * List with all surveysItems represented in a text view
         */
        private List<String> surveysItems;

        /**
         * Creates a new SurveyItemRecyclingAdapter
         * @param preSurveyItems List with a previous defined items for the recycling view
         */
        public SurveyItemRecyclingAdapter(List<String> preSurveyItems){
            surveysItems=new ArrayList<>();
            surveysItems.addAll(preSurveyItems);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.survey_list_row,parent,false);
            return new ViewHolder(view);
        }

        /**
         * Modifies a certain view holder on a certain position
         * @param holder ViewHolder with the holder being modified
         * @param position Integer with the RecyclerView list position
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(surveysItems.get(position));

        }

        /**
         * Returns the current Recycler View item size
         * @return Integer with the current recycler view item size
         *
         */
        @Override
        public int getItemCount() {
            return surveysItems.size();
        }

        /**
         * Add's a new list of surveysItems to the current recycler view survey list
         * @param surveys List with the surveysItems name's being added
         */
        public void addSurveys(List<String> surveys){
            if(surveys!=null) {
                surveysItems.addAll(surveys);
                for(int i=surveys.size();i>=0;i--) {
                   this.notifyItemInserted(i);
                }
            }
        }
        /**
         * Adds a new Survey to the current survey list
         * @param surveyName String with the survey name
         */
        private void addSurvey(String surveyName){
            surveysItems.add(surveyName);
        }
        /**
         * ViewHolder class that represents a ViewHolder
         */
        protected class ViewHolder extends RecyclerView.ViewHolder{
            /**
             * Current holder text view
             */
            private TextView textView;

            /**
             * Builds a new ViewHolder with a certain TextView
             * @param view TextView with the ViewHolder text view
             */
            public ViewHolder(View view){
                super(view);
            }
        }
    }
