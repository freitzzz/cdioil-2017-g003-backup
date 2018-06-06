package cdioil.feedbackmonkey.authz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cdioil.feedbackmonkey.R;


public class BinaryQuestionActivity extends AppCompatActivity {

    private TextView questionTextView;

    private Button yesButton;

    private Button noButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_question);

        questionTextView = findViewById(R.id.question);

        yesButton = findViewById(R.id.yes_button);
        noButton = findViewById(R.id.no_button);

        configureView();

    }

    private void configureView(){
        if(getIntent().getExtras() != null){
            questionTextView.setText(getIntent().getExtras().getString("questionText"));
        }
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //something here
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //something here
            }
        });
    }

}
