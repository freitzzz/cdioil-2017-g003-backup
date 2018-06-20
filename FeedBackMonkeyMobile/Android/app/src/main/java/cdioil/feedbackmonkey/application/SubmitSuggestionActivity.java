package cdioil.feedbackmonkey.application;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.TransformerException;

import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;
import cdioil.feedbackmonkey.utils.GenericFileProvider;

public class SubmitSuggestionActivity extends AppCompatActivity {
    /**
     * Camera Request code.
     */
    private static final int CAMERA_REQUEST = 1888;
    /**
     * Permission to use camera code.
     */
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    /**
     * String with the current authentication token
     */
    private String authenticationToken;

    /**
     * Send suggestion Button.
     */
    private Button submitSuggestionButton;

    /**
     * Text field for the suggestion.
     */
    private EditText suggestionEditText;

    /**
     * Image View for the suggestion photo.
     */
    private ImageView suggestionPhotoImageView;

    /**
     * Text View that holds a hint for the user to add a photo to their suggestion.
     */
    private TextView suggestionPhotoTextView;

    /**
     * String that holds the suggestion photo path.
     */
    private String pictureImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_suggestion);
        submitSuggestionButton = findViewById(R.id.submitSuggestionButton);
        suggestionEditText = findViewById(R.id.submitSuggestionEditText);
        suggestionPhotoImageView = findViewById(R.id.suggestionPhotoImageView);
        suggestionPhotoTextView = findViewById(R.id.suggestionPhotoHintTextView);
        authenticationToken = getIntent().getExtras().getString("authenticationToken");
        configureView();
    }

    /**
     * Sets on click listener to submit the suggestion.
     */
    private void configureView() {
        configureSuggestionImageView();
        configureSubmitSuggestionButton();
    }

    /**
     * Sets on click listener to add a photo to the suggestion.
     */
    private void configureSuggestionImageView() {
        suggestionPhotoImageView.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    Uri outputFileUri = getOutputFileUri();
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    /**
     * Handles camera permission request to take a picture
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
                Uri outputFileUri = getOutputFileUri();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
    }

    /**
     * Creates a file for the suggestion photo saves it in the app's folder.
     *
     * @return Uri of the file
     */
    private Uri getOutputFileUri() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = getFilesDir();
        File imageDir = new File(storageDir, "images");
        if (!imageDir.exists()) {
            imageDir.mkdir();
        }
        File imageFile = new File(imageDir, imageFileName);
        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pictureImagePath = imageFile.getAbsolutePath();
        return GenericFileProvider.getUriForFile(getApplicationContext(),
                "cdioil.feedbackmonkey.utils.GenericFileProvider", imageFile);
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
            if (imageFile.exists()) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                try {
                    ExifInterface exifInterface = new ExifInterface(imageFile.getAbsolutePath());
                    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            1);
                    Matrix matrix = new Matrix();
                    if (orientation == 8) {
                        matrix.postRotate(270);
                    } else if (orientation == 6) {
                        matrix.postRotate(90);
                    }
                    Bitmap rotatedImageBitmap = Bitmap.createBitmap(imageBitmap, 0, 0,
                            imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
                    suggestionPhotoImageView.setImageBitmap(rotatedImageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Sets on click listener for the send suggestion button.
     * TODO If a picture was taken, send it to the server.
     */
    private void configureSubmitSuggestionButton() {
        submitSuggestionButton.setOnClickListener(view -> {
            String sentFromActivity = getIntent().getExtras().getString("sentFromActivity");
            String suggestionText = suggestionEditText.getText().toString();
            if (suggestionText.trim().isEmpty()) {
                suggestionEditText.setError("A sugestão não pode ser vazia!");
                return;
            }

            if (sentFromActivity == null) {
                //Suggestion that is not related to a review
                if(photoTaken()){
                    //TODO using the API User Profile Resource, send the suggestion text and the picture
                }
            } else if (sentFromActivity.equals(QuestionActivity.class.getSimpleName())) {
                //Suggestion that is related to a review
                ReviewXMLService xmlService = ReviewXMLService.instance();
                try {
                    xmlService.saveSuggestion(suggestionEditText.getText().toString());
                    if(photoTaken()){
                        //TODO Discuss how to add the photo to the suggestion (XML File or sent in a different way)
                    }
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }

            finish();
        });
    }

    /**
     * Checks whether a photo was taken to add it to the suggestion.
     *
     * @return true if a picture was taken, false if otherwise
     */
    private boolean photoTaken(){
        return pictureImagePath != null;
    }
}
