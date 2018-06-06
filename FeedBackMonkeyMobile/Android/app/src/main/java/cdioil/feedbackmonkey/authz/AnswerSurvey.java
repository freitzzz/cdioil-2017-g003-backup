package cdioil.feedbackmonkey.authz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import cdioil.feedbackmonkey.BuildConfig;
import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.restful.utils.FeedbackMonkeyAPI;
import cdioil.feedbackmonkey.restful.utils.RESTRequest;
import cdioil.feedbackmonkey.restful.utils.json.UserJSONService;
import okhttp3.Response;

public class AnswerSurvey extends AppCompatActivity {
//    private Runnable nextQuestion(String qustionType, String authenticationToken, String option, String questionType, String surveyID, String reviewID) {
//        return () -> {
//            Response restResponse = RESTRequest
//                    .create(BuildConfig.SERVER_URL
//                            .concat(FeedbackMonkeyAPI
//                                    .getAPIEntryPoint()
//                                    .concat(FeedbackMonkeyAPI.getResourcePath("review")
//                                            .concat(FeedbackMonkeyAPI.getSubResourcePath("review", "answerQuestion")
//                                                    .concat(FeedbackMonkeyAPI.getSubResourcePath("review", authenticationToken)
//                                                            .concat(FeedbackMonkeyAPI.getSubResourcePath("review", option)
//                                                                    .concat(FeedbackMonkeyAPI.getSubResourcePath("review", questionType)
//                                                                            .concat(FeedbackMonkeyAPI.getSubResourcePath("review", surveyID)
//                                                                                    .concat(FeedbackMonkeyAPI.getSubResourcePath("review", reviewID))))))))))
//                    .withMediaType(RESTRequest.RequestMediaType.JSON)
//                    .withBody("{\n\t\"option\":" + option + ",\"questionType\":" +
//                            questionType + "\n}").POST();
//            String restResponseBodyContent = "";
//            try {
//                restResponseBodyContent = restResponse.body().string();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (restResponse.code() == HttpsURLConnection.HTTP_OK) {
//                //TODO go to app's main activity, pass authToken
//                Intent answerSurveyIntent = new Intent(BinaryQuestionActivity.this, InsertNextActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("authenticationToken", getAuthenticationToken(restResponseBodyContent));
//                answerSurveyIntent.putExtras(bundle);
//                startActivity(answerSurveyIntent);
//            } else if (restResponse.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
//                if ("{\n\t\"finishedreview\":\"true\"\n}".equals(restResponse.body())) {
//                    showErrorMessage("Avaliação já existente",
//                            "\nA avaliação já foi realizada!\n");
//                } else {
//                    showErrorMessage("Credenciais Inválidas",
//                            "\nNão tem permissões para avaliar o produto!\n");
//                }
//            } else if (restResponse.code() == HttpsURLConnection.HTTP_BAD_REQUEST) {
//                if ("{\n\t\"invalidreview\":\"true\"\n}".equals(restResponse.body())) {
//                    showErrorMessage("Avaliação inválida!",
//                            "\nÉ impossível avaliar! Tente novamente\n");
//                } else {
//                    showErrorMessage("Conta Não Ativada",
//                            "A sua conta não está ativada!");
//                }
//            }
//        };
//    }
//
//    /**
//     * Returns the user's authentication token from the JSON rest response body
//     *
//     * @param jsonBody body of the rest response sent in JSON format
//     * @return String with the user's authentication token
//     */
//    private String getAuthenticationToken(String jsonBody) {
//        return new Gson().fromJson(jsonBody, UserJSONService.class).getAuthenticationToken();
//    }
//
//    /**
//     * Creates a new thread to display a error message using an AlertBuilder
//     *
//     * @param messageTitle   title of the message
//     * @param messageContent content of the message
//     */
//    private void showErrorMessage(String messageTitle, String messageContent) {
//        new Thread() {
//            public void run() {
//                BinaryQuestionActivity.this.runOnUiThread(() -> {
//                    AlertDialog errorResponseAlert =
//                            new AlertDialog.Builder(BinaryQuestionActivity.this).create();
//                    errorResponseAlert.setTitle(messageTitle);
//                    errorResponseAlert.setMessage(messageContent);
//                    errorResponseAlert.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            errorResponseAlert.dismiss();
//                        }
//                    });
//                    errorResponseAlert.setIcon(R.drawable.ic_error_black_18dp);
//                    errorResponseAlert.show();
//                });
//            }
//        }.start();
//    }
}
