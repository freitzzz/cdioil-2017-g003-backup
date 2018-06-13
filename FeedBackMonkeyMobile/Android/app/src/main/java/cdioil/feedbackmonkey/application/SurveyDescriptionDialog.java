package cdioil.feedbackmonkey.application;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import cdioil.feedbackmonkey.R;
import cdioil.feedbackmonkey.application.services.SurveyService;

/**
 * SurveyDescriptionDialog class that represents the dialog used to show the description
 * of a SurveyService
 * @author @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */

public final class SurveyDescriptionDialog extends Dialog {
    /**
     * SurveyService with the current survey which description is being represented
     */
    private final SurveyService currentSurveyService;
    /**
     * Builds a new SurveyDescriptionDialog with the surveyService which description will
     * be represented on
     * @param context Context with the context which dialog is being called
     * @param surveyService SurveyService with the surveyService description being represented on the current dialog
     */
    public SurveyDescriptionDialog(@NonNull Context context,SurveyService surveyService) {
        super(context);
        this.currentSurveyService = surveyService;
        configureComponents();
    }

    /**
     * Configures the current dialog components
     */
    private void configureComponents(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.survey_dialog_info);
        ((TextView)findViewById(R.id.survey_end_date_survey_dialog_content)).setText(currentSurveyService.getSurveyEndDate());
        ((TextView)findViewById(R.id.survey_average_time_survey_dialog_content)).setText(currentSurveyService.getSurveyAverageTime());
        ListView surveyDialogItems=findViewById(R.id.survey_items_list_view_survey_dialog);
        surveyDialogItems.setAdapter(new ArrayAdapter<>(surveyDialogItems.getContext(),R.layout.survey_dialog_info_list_row_item,currentSurveyService.getSurveyItems()));
    }
}
