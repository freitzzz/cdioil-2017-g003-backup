package cdioil.backoffice.webapp.authz;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * ActivationCodePopupViewContent that represents the content of the popup view that shows up 
 * if the user tries to login without activation his account
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
public final class ActivationCodePopupViewContent implements PopupView.Content{
    /**
     * Constant that represents the confirm activation code button message
     */
    private static final String CONFIRM_ACTIVATION_CODE_BUTTON_MESSAGE="Confirmar";
    /**
     * VerticalLayout with the current component layout
     */
    private VerticalLayout componentLayout;
    /**
     * Button with the button that confirms the activation code
     */
    private Button btnConfirmActivationCode;
    /**
     * TextField with the text field that holds the activation code
     */
    private TextField txtFieldActivationCode;
    /**
     * Builds a new ActivationCodePopupViewContent
     */
    public ActivationCodePopupViewContent(){
        configureComponents();
    }
    /**
     * Method that returns the minimized value as HTML of the current PopupView
     * @return String with the minimez value as HTML of the current PopupView
     */
    @Override
    public String getMinimizedValueAsHTML() {
        return null;
    }
    /**
     * Method that returns the current PopupView content
     * @return Component with the current PopupView content
     */
    @Override
    public Component getPopupComponent(){
        return componentLayout;
    }
    /**
     * Method that gets the current activation code button
     * @return Button with the current activation code button
     */
    public Button getActivationCodeButton(){return btnConfirmActivationCode;}
    /**
     * Method that configures the current popup view components
     */
    private void configureComponents(){
        txtFieldActivationCode=new TextField();
        createConfirmActivationCodeButton();
        componentLayout=new VerticalLayout(new HorizontalLayout(txtFieldActivationCode,btnConfirmActivationCode));
    }
    /**
     * Method that creates the confirm activation code button
     * @return Button with the confirm activation code button
     */
    private void createConfirmActivationCodeButton(){
        btnConfirmActivationCode=new Button(CONFIRM_ACTIVATION_CODE_BUTTON_MESSAGE);
    }
    
}
