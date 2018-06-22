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

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.TransformerException;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.json.SuggestionJSONService;
import cdioil.feedbackmonkey.restful.utils.xml.ReviewXMLService;
import cdioil.feedbackmonkey.utils.GenericFileProvider;
import cdioil.feedbackmonkey.utils.ToastNotification;
import okhttp3.Response;

import static cdioil.feedbackmonkey.utils.ToastNotification.show;

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

    /**
     * Int that holds the REST Response code when submitting a suggestion that is not related to a review.
     */
    private int restResponseCode;

    /**
     * String that holds the REST Response body when submitting a suggestion that is not related to a review.
     */
    private String restResponseBody;

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
                }
            }
            Uri outputFileUri = getOutputFileUri();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
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
     */
    private void configureSubmitSuggestionButton() {
        submitSuggestionButton.setOnClickListener(view -> {
            String sentFromActivity = getIntent().getExtras().getString("sentFromQuestionActivity");
            String suggestionText = suggestionEditText.getText().toString();
            if (suggestionText.trim().isEmpty()) {
                suggestionEditText.setError("A sugestão não pode ser vazia!");
                return;
            }

            /**
             * Check if it was UserProfileActivity that started SubmitSuggestionActivity or if it was
             * QuestionActivity.
             */
            if (sentFromActivity == null) {
                /*

                    Suggestion that is not related to a review

                 */

                String suggestionWithoutImageAsJSON = new Gson().
                        toJson(new SuggestionJSONService(suggestionText, null));

                if (photoTaken()) {
                    File imageFile = new File(pictureImagePath);
                    byte[] encodedImage = getFileBytes(imageFile);
                    runThreadForRESTRequest(new Gson().
                            toJson(new SuggestionJSONService(suggestionText, encodedImage)));
                }else{
                    runThreadForRESTRequest(suggestionWithoutImageAsJSON);
                }

            } else if (sentFromActivity.equals(QuestionActivity.class.getSimpleName())) {
                /*

                  Suggestion that is related to a review

                 */
                ReviewXMLService xmlService = ReviewXMLService.instance();
                try {
                    if (photoTaken()) {
                        saveSuggestionPhotoToReviewXMLFile(suggestionText, xmlService);
                        showToastSuccessMessage();
                    } else {
                        xmlService.saveSuggestion(suggestionText);
                        showToastSuccessMessage();
                    }
                    finish();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Creates a new Thread to perform the REST Request to the server to submit a suggestion that is not
     * related to a review
     *
     * @param suggestionAsJSON String representing a Suggestion in JSON Format
     */
    private void runThreadForRESTRequest(String suggestionAsJSON) {
        Thread connectionThread = new Thread(submitNotReviewRelatedSuggestion(suggestionAsJSON));

        connectionThread.start();
        try {
            connectionThread.join();
            if (restResponseCode == HttpsURLConnection.HTTP_OK) {
                showToastSuccessMessage();
                deleteImageFile();
                finish();
            } else {
                show(this, "Ocorreu um erro na submissão da sugestão, por favor " +
                        "tente novamente!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes a suggestion image file after it was sent to the server.
     */
    private void deleteImageFile(){
        File fileToDelete = new File(pictureImagePath);
        fileToDelete.delete();
    }

    /**
     * Shows a success message through a Toast.
     */
    private void showToastSuccessMessage() {
        runOnUiThread(() -> ToastNotification.show(SubmitSuggestionActivity.this,
                "Sugestão submetida com sucesso!"));
    }

    /**
     * Saves a suggestion's photo to a Review XML File
     *
     * @param suggestionText suggestion's text
     * @param xmlService     ReviewXMLService instance for a Review
     */
    private void saveSuggestionPhotoToReviewXMLFile(String suggestionText, ReviewXMLService xmlService) {
        File storageDir = getFilesDir();
        File imageDir = new File(storageDir, "images");
        File imageFile = new File(pictureImagePath);
        String fileName = imageFile.getName();
        String newFileName = imageDir.getAbsolutePath().concat("/")
                .concat(fileName.substring(0, fileName.indexOf("."))
                        .concat("_REVIEW_".concat(xmlService.getReviewID())
                                .concat("_SUGGESTION_PHOTO").concat(".jpg")));
        File newImageFile = new File(newFileName);
        imageFile.renameTo(newImageFile);
        imageFile.delete();
        imageFile = newImageFile;
        byte[] encodedImage = getFileBytes(imageFile);
        try {
            xmlService.saveSuggestion(suggestionText, encodedImage);
            imageFile.delete();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a Runnable that performs a REST Request to submit a suggestion that is not related to a
     * review.
     *
     * @param json String with the suggestion in JSON Format that will be in the body of the request
     * @return Runnable that performs a REST Request to submit a suggestion that is not related to a review.
     */
    private Runnable submitNotReviewRelatedSuggestion(String json) {
        return () -> {
            try {
                Response response = RESTRequest.create(BuildConfig.SERVER_URL.
                        concat(FeedbackMonkeyAPI.getAPIEntryPoint().
                                concat(FeedbackMonkeyAPI.getResourcePath("User Profile").
                                        concat(FeedbackMonkeyAPI.getSubResourcePath("User Profile",
                                                "Save Suggestion").concat("/"+authenticationToken).concat("?hasImage=true"))))).
                        withBody(json).
                        withMediaType(RESTRequest.RequestMediaType.JSON).
                        POST();
                restResponseCode = response.code();
                restResponseBody = response.body().string();
            } catch (IOException e) {
                show(SubmitSuggestionActivity.this, getString(R.string.no_internet_connection));
                e.printStackTrace();
            }
        };
    }

    /**
     * Converts a file to bytes and returns them
     *
     * @param file File we want to convert
     * @return String with the Base 64 encoded bytes of a file
     */
    private byte[] getFileBytes(File file) {
        Bitmap imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        imageBitmap.recycle();
        return imageBytes;
    }

    /**
     * Checks whether a photo was taken to add it to the suggestion.
     *
     * @return true if a picture was taken, false if otherwise
     */
    private boolean photoTaken() {
        return pictureImagePath != null;
    }
}
