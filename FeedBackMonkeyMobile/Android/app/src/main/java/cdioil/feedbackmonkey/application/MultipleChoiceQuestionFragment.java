package cdioil.feedbackmonkey.application;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;

/**
 * Fragment responsible for listing multiple choice question options.
 *
 */
public class MultipleChoiceQuestionFragment extends Fragment {

    /**
     * Listener used for interacting with the activity.
     */
    private OnAnswerListener interactionListener;
    /**
     * Adapter responsible for setting listing behaviour.
     */
    private MultipleChoiceQuestionItemListViewAdapter listViewAdapter;

    /**
     * ListView containing all of the options.
     */
    private ListView questionListView;

    public MultipleChoiceQuestionFragment() {
        // Required empty public constructor
    }

    //NOTE: this method will only truly be useful if, in the future, this fragment requires arguments upon instantiation.

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MultipleChoiceQuestionFragment.
     */
    public static MultipleChoiceQuestionFragment newInstance() {
        MultipleChoiceQuestionFragment fragment = new MultipleChoiceQuestionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_multiple_choice_question, container, false);

        questionListView = v.findViewById(R.id.multiple_choice_question_list_view);
        listViewAdapter = new MultipleChoiceQuestionItemListViewAdapter(getActivity());

        ArrayList<String> questionOptions = ReviewXMLService.instance().getCurrentQuestionBundle().getStringArrayList("options");
        listViewAdapter.addAll(questionOptions);

        questionListView.setAdapter(listViewAdapter);

        questionListView.setOnItemClickListener((adapterView, view, i, l) ->
                interactionListener.onQuestionAnswered(listViewAdapter.getItem(i)));

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        if (context instanceof OnAnswerListener) {
            interactionListener = (OnAnswerListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
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
