package cdioil.feedbackmonkey.application;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

import java.util.ArrayList;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;

/**
 * Fragment responsible for listing quantitative question options.
 *
 */
public class QuantitativeQuestionFragment extends Fragment {


    /**
     * SeekArc used for receiving user input when answering to a quantitative question.
     */
    private SeekArc seekArc;

    /**
     * TextView used for displaying the SeekArc's current value.
     */
    private TextView seekArcProgress;

    /**
     * Listener used for interacting with the activity.
     */
    private OnAnswerListener interactionListener;

    public QuantitativeQuestionFragment() {
        // Required empty public constructor
    }

    //NOTE: this method will only truly be useful if, in the future, this fragment requires arguments upon instantiation.

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuantitativeQuestionFragment.
     */
    public static QuantitativeQuestionFragment newInstance() {
        QuantitativeQuestionFragment fragment = new QuantitativeQuestionFragment();
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
        View v = inflater.inflate(R.layout.fragment_quantitative_question, container, false);

        seekArc = v.findViewById(R.id.seekArc);
        seekArcProgress = v.findViewById(R.id.seekArcProgress);

        ArrayList<String> questionScaleValues = ReviewXMLService.instance().
                getCurrentQuestionBundle().getStringArrayList("options");

        int questionScaleValuesSize = questionScaleValues.size();

        Double minValue = Double.parseDouble(questionScaleValues.get(0));
        Double maxValue = Double.parseDouble(questionScaleValues.get(questionScaleValuesSize - 1));


        seekArc.setMax(maxValue.intValue() - minValue.intValue());
        seekArcProgress.setText(Integer.toString(minValue.intValue()));

        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {

            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
                seekArcProgress.setText(String.valueOf(i + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

            }
        });


        seekArcProgress.setOnClickListener(view -> {
            Double value = Double.parseDouble(seekArcProgress.getText().toString());

            interactionListener.onQuestionAnswered(value.toString());
        });

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
