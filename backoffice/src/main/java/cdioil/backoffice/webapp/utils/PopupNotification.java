package cdioil.backoffice.webapp.utils;

import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * PopupNotification class that creates a <i>popup dialog</i> using <b>Notification</b> 
 * class from Vaadin API
 * @see com.vaadin.ui.Notification
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class PopupNotification {
    /**
     * Creates a pops up a new PopupNotification on the current page that is calling the method
     * @param title String with the popup title
     * @param message String with the popup description
     * @param type NotificationType enum with the popup notification type (eg: Warning)
     * @param position Position with the position on where the notification should be popedup (eg: Top/Center)
     */
    public static final void show(String title,String message,Notification.Type type,Position position){
        Notification notification=new Notification(title,message,type);
        notification.setPosition(position);
        notification.show(UI.getCurrent().getPage());
    }
    /**
     * Hides default constructor
     */
    private PopupNotification(){}
}
