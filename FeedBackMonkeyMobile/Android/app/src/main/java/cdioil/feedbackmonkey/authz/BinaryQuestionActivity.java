package cdioil.feedbackmonkey.authz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import cdioil.feedbackmonkey.restful.utils.json.UserJSONService;
import okhttp3.Response;
import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.R;


public class BinaryQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_question);

        TextView textView = findViewById(R.id.question);

        Button yes = findViewById(R.id.yes);

        yes.setOnClickListener(view -> {
            //rest request
            Thread loginThread = new Thread(nextQuestion());
            loginThread.start();
        });
    }


}
