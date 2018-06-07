package cdioil.feedbackmonkey.authz;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

import cdioil.feedbackmonkey.R;

public class QuantitativeQuestionActivity extends AppCompatActivity {

    private SeekArc seekArc;

    private TextView seekArcProgress;

    private TextView questionTextView;

    private int minValue;

    private int maxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantitative_question);
        seekArc = findViewById(R.id.seekArc);
        questionTextView = findViewById(R.id.question);
        seekArcProgress = findViewById(R.id.seekArcProgress);
        //fetch minValue and maxValue
        configureView();
    }

    private void configureView(){

        //fetch max and min value from xml file
        //do seekArc.setMax(maxValue)

        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
                seekArcProgress.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

            }
        });
    }
}
