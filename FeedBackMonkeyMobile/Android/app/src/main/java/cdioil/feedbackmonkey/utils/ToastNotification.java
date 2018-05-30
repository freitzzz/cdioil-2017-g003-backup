package cdioil.feedbackmonkey.utils;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

/**
 * ToastNotification class that shows up a Toast as a way of showing notification
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class ToastNotification {
    /**
     * Constant that represents the default value for the X offset of the toast
     */
    private static short DEFAULT_TOAST_X_OFFSET=0;
    /**
     * Constant that represents the default value for the Y offset of the toast
     */
    private static short DEFAULT_TOAST_Y_OFFSET=0;
    /**
     * Shows a Toast with a certain message
     * @param activity Activity with the activity where the toast will show up
     * @param message String with the message that the toast will show
     */
    public static void show(Activity activity,String message){
        activity.runOnUiThread(createToast(activity,message,Gravity.BOTTOM
                ,DEFAULT_TOAST_X_OFFSET,DEFAULT_TOAST_Y_OFFSET));
    }

    /**
     * Creates a new Toast with the toast that will show on a certain activit with a certain message
     * @param activity Activity with the activity where the toast will show up
     * @param message String with the message that the toast will show
     * @return Thread with the thread where the thread will run
     */
    private static Thread createToast(Activity activity,String message,int gravity
            ,short xOffset,short yOffset){
        return new Thread(() -> {
            Toast toast=Toast.makeText(activity,message,Toast.LENGTH_SHORT);
            toast.setGravity(gravity,xOffset,yOffset);
            toast.show();
        });
    }
    /**
     * Hides default constructor
     */
    private ToastNotification(){}
}
