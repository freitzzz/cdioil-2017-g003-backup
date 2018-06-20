package cdioil.feedbackmonkey.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import cdioil.feedbackmonkey.R;


/**
 * WaitingDialog class that represents a dialog for keeping the user waiting for an action
 * @author @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 7.0 of FeedbackMonkey
 */

public final class WaitingDialog{
    /**
     * Constant that represents the placeholder text used on the alert dialog creation
     */
    private static final String PLACEHOLDER_TEXT="PLACEHOLDER TEXT";
    /**
     * AlertDialog with the current alert dialog being displayed
     */
    private final AlertDialog alertDialog;
    /**
     * ProgressBar with the progress bar being displayed on the waiting dialog
     */
    private final ProgressBar waitingDialogProgressBar;

    /**
     * Creates a new WaitingDialog with the context where the waiting dialog will be displayed
     * @param context Context with the context where the waiting dialog will be displayed
     * @return WaitingDialog with the dialog that will keep the user waiting for an action
     */
    public static WaitingDialog create(Context context){
        return new WaitingDialog(context,(LayoutInflater.from(context).inflate(R.layout.circle_loading_bar, null))
                .findViewById(R.id.circle_loading_bar));
    }

    /**
     * Sets the waiting dialog title
     * @param title String with the title being displayed on waiting dialog
     * @return WaitingDialog with the waiting dialog with a certain title
     */
    public WaitingDialog setWaitingDialogTitle(String title){
        alertDialog.setTitle(title);
        return this;
    }

    /**
     * Shows the current waiting dialog
     */
    public void show(){
        alertDialog.show();
    }

    /**
     * Hides the current waiting dialog
     */
    public void hide(){
        alertDialog.cancel();
        waitingDialogProgressBar.setVisibility(View.GONE);
    }
    /**
     * Builds a new WaitingDialog with the context where the waiting dialog will be displayed
     * @param context Context with the context where the waiting dialog will be displayed
     * @param waitingDialogProgressBar ProgressBar with the progress bar being displayed on the waiting dialog
     */
    private WaitingDialog(Context context,ProgressBar waitingDialogProgressBar){
        this.waitingDialogProgressBar=waitingDialogProgressBar;
        this.alertDialog=new AlertDialog.Builder(context)
                .setTitle(PLACEHOLDER_TEXT)
                .setView(waitingDialogProgressBar)
                .setCancelable(false)
                .create();
        configureWaitingDialog();
    }

    /**
     * Configures the current waiting dialog
     */
    private void configureWaitingDialog(){
        alertDialog.setOnShowListener(dialogInterface -> waitingDialogProgressBar.setVisibility(View.VISIBLE));
    }
}
