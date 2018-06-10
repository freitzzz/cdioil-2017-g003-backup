package cdioil.feedbackmonkey.application;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cdioil.feedbackmonkey.R;

/**
 * Fragment responsible for listing binary question options.
 *
 */
public class BinaryQuestionFragment extends Fragment {


    /**
     * Button used for answering "yes" to the question.
     */
    private Button yesButton;

    /**
     * Button used for answering "no" to the question.
     */
    private Button noButton;

    /**
     * Listener used for interacting with the activity.
     */
    private OnAnswerListener interactionListener;

    public BinaryQuestionFragment() {
        // Required empty public constructor
    }

    //NOTE: this method will only truly be useful if, in the future, this fragment requires arguments upon instantiation.

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BinaryQuestionFragment.
     */
    public static BinaryQuestionFragment newInstance() {
        BinaryQuestionFragment fragment = new BinaryQuestionFragment();
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
        View v = inflater.inflate(R.layout.fragment_binary_question, container, false);

        yesButton = v.findViewById(R.id.yes_button);
        noButton = v.findViewById(R.id.no_button);

        yesButton.setOnClickListener(view ->
                interactionListener.onQuestionAnswered("true")
        );
        noButton.setOnClickListener(view ->
                interactionListener.onQuestionAnswered("false")
        );

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

}
