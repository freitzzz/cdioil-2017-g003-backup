package cdioil.feedbackmonkey.authz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cdioil.feedbackmonkey.R;

public class BinaryQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_question);

        TextView textView = findViewById(R.id.question);
        textView.setText("Oh boy");
    }
}
