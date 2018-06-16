package cdioil.feedbackmonkey.authz;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.utils.GenericFileProvider;

/**
 * Activity that presents the user's profile.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class UserProfileActivity extends AppCompatActivity {
    /**
     * Camera Request code.
     */
    private static final int CAMERA_REQUEST = 1888;
    /**
     * Permission to use camera code.
     */
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    /**
     * ImageView that may hold the user's profile picture.
     */
    private ImageView profilePhotoImageView;
    /**
     * TextView that holds the name of the user.
     */
    private TextView nameTextView;
    /**
     * TextView that holds the age of the user.
     */
    private TextView ageTextView;
    /**
     * TextView that holds the location of the user.
     */
    private TextView locationTextView;
    /**
     * TextView that holds information about the user's badges.
     */
    private TextView badgeTextView;
    /**
     * Button that on click will take the user to a list of their reviewed surveys.
     */
    private Button reviewedSurveysButton;
    /**
     * Button that on click will take the user to a list of their contests and badges.
     */
    private Button contestBadgeListButton;
    /**
     * Button that on click will take the user to a list of their suggestions.
     */
    private Button suggestionListButton;
    /**
     * String that holds the profile picture path.
     */
    private String pictureImagePath;

    /**
     * Activity will execute this code when its created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        configureActivityView();
    }

    /**
     * Configures the Activity's View.
     */
    private void configureActivityView() {
        profilePhotoImageView = findViewById(R.id.profileImageView);
        nameTextView = findViewById(R.id.nameTextView);
        ageTextView = findViewById(R.id.ageTextView);
        locationTextView = findViewById(R.id.locationTextView);
        badgeTextView = findViewById(R.id.badgeExpertTextView);
        reviewedSurveysButton = findViewById(R.id.reviewListButton);
        contestBadgeListButton = findViewById(R.id.contestBadgeListButton);
        suggestionListButton = findViewById(R.id.suggestionListButton);
        configureImageAndTextViews();
        configureButtons();
    }

    /**
     * Configures the image and text views of this activity.
     */
    private void configureImageAndTextViews() {
        /**
         * TODO Replace hardcoded strings on TextViews with RESTRequests using the API's profile resource
         */
        nameTextView.setText("João Ferreira");
        ageTextView.setText("52    Anos");
        locationTextView.setText("Porto,  Portugal ");
        badgeTextView.setText("Especialista  em Vinhos Tintos");
        setProfilePicture();

    }

    /**
     * Configures the buttons of this activity.
     */
    private void configureButtons() {

    }

    /**
     * Sets an on click listener to the image view so the user can take his profile picture
     */
    private void setProfilePicture() {
        profilePhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        System.out.println("hey hey hey hey");
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = timeStamp + ".jpg";
                        File storageDir = getFilesDir();
                        File imageFile = new File(storageDir,imageFileName);
                        try {
                            imageFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        pictureImagePath = imageFile.getAbsolutePath();
                        Uri outputFileUri = GenericFileProvider.getUriForFile(getApplicationContext(),
                                        "cdioil.feedbackmonkey.utils.GenericFileProvider",imageFile);
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            }
        });
    }

    /**
     * Handles camera permission request to take a profile picture
     *
     * @param requestCode  code of the request
     * @param permissions  array with the set of permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("entrei aqui");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
    }

    /**
     * Retrieves the photo taken with the camera and sets it on the image view
     *
     * @param requestCode request code
     * @param resultCode  result code
     * @param data        intent with the photo
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            File imageFile = new File(pictureImagePath);
            if(imageFile.exists()){
                Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                profilePhotoImageView.setImageBitmap(imageBitmap);
            }
        }
    }
}
