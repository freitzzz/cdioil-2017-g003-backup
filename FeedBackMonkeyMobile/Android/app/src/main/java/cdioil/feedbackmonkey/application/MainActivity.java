package cdioil.feedbackmonkey.application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.authz.LoginActivity;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.logo_splash);
        Animation splashAnimation = AnimationUtils.loadAnimation(this, R.anim.splash);

        //TODO: launch main menu activity if user data is already present
        Intent intent = new Intent(this, LoginActivity.class);

        imageView.startAnimation(splashAnimation);

        Thread timer = new Thread(() -> {
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                initializeApplication();
                startActivity(intent);
                finish();
            }
        });

        timer.start();
    }

    /**
     * Initializes the application.
     */
    private void initializeApplication() {
        System.setProperty("javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
        FeedbackMonkeyAPI.create(getApplicationContext());
    }
}
