package cdioil.feedbackmonkey.application;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.concurrent.atomic.AtomicReference;

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

        imageView.startAnimation(splashAnimation);

        Thread timer = new Thread(() -> {
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Intent intent;
                /**
                 * If an authentication token is present launch MainMenuActivity so the User doesn't have
                 * to perform the login again. If it isn't present launch LoginActivity.
                 */
                if(PreferenceManager.getDefaultSharedPreferences(this).
                        getString("authenticationToken",getString(R.string.no_authentication_token)).
                        equals(getString(R.string.no_authentication_token))){
                    intent = new Intent(this, LoginActivity.class);
                }else{
                    intent = new Intent(this, MainMenuActivity.class);
                }
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
