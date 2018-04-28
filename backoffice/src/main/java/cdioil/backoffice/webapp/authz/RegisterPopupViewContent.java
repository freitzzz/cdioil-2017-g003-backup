package cdioil.backoffice.webapp.authz;

import cdioil.backoffice.webapp.utils.PopupNotification;
import cdioil.frontoffice.application.authz.RegisterUserController;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.time.format.DateTimeParseException;

public class RegisterPopupViewContent implements PopupView.Content {
    /**
     * Constant that represents the title of the popup notification that 
     * ocures when the user tries to register with an invalid field
     */
    private static final String INVALID_FIELD_TITLE="Campo Inválido";
    /**
     * Constant that represents the title of the popup notification that ocures when the 
     * user tries to register and an error ocures
     */
    private static final String REGISTER_USER_FAILURE_TITLE="Erro no registo do utilizador";
    /**
     * Constant that represents the title of the popup notification that ocures when the 
     * user registers his account with success
     */
    private static final String REGISTER_USER_SUCCESS_TITLE="Utilizador Registado com sucesso!";
    /**
     * Constant that represents the message of the popup notification that ocures when the 
     * user registers his account with success
     */
    private static final String REGISTER_USER_SUCCESS_MESSAGE="Verifique a sua caixa de correio";
    /**
     * Constant that represents the title of the popup notification that ocures when the 
     * user doesn't fill an obligatory field
     */
    private static final String OBLIGATORY_FIELD_TITLE="Campo Obrigatório";
    /**
     * Constant that represents the message of the popup notification that ocures when the 
     * user doesn't fill an obligatory field
     */
    private static final String FORMATED_OBLIGATORY_FIELD_MESSAGE="O campo %s é obrigatório! Por favor preencha-o";
    /**
     * Constant that represents the title of the popup notification that ocures when the user 
     * inserts two passwords which are not equal
     */
    private static final String PASSWORDS_NOT_EQUAL_TITLE="Passwords inválidas";
    /**
     * Constant that represents the message of the popup notification that ocures when the user 
     * inserts two passwords which are not equal
     */
    private static final String PASSWORDS_NOT_EQUAL_MESSAGE="As passwords não coincidem!";
    
    private VerticalLayout mainLayout;
    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private TextField emailTextField;
    private TextField phoneTextField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField;
    private TextField cityTextField;
    private DateField birthdayField;
    private Button confirmButton;

    private RegisterUserController controller;

    public RegisterPopupViewContent() {
        instantiateComponents();
        prepareComponent();
        prepareConfirmButton();
    }
    
    @Override
    public String getMinimizedValueAsHTML() {
        return null;
    }

    @Override
    public Component getPopupComponent() {
        return mainLayout;
    }
    
    private void instantiateComponents() {
        controller = new RegisterUserController();
        mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);

        firstNameTextField = new TextField("Nome");
        lastNameTextField = new TextField("Sobrenome");
        emailTextField = new TextField("Email");
        phoneTextField = new TextField("Telemóvel");
        passwordField = new PasswordField("Password");
        repeatPasswordField = new PasswordField("Repetir Password");
        cityTextField = new TextField("Localidade");
        birthdayField = new DateField("Data Nascimento");
        confirmButton = new Button("Confirmar", VaadinIcons.CHECK);
    }

    private void prepareComponent() {
        HorizontalLayout nameLayout = new HorizontalLayout(firstNameTextField, lastNameTextField);
        mainLayout.addComponentsAndExpand(nameLayout);

        HorizontalLayout emailPhoneLayout = new HorizontalLayout(emailTextField, phoneTextField);
        mainLayout.addComponentsAndExpand(emailPhoneLayout);

        HorizontalLayout passwordLayout = new HorizontalLayout(passwordField, repeatPasswordField);
        mainLayout.addComponentsAndExpand(passwordLayout);

        HorizontalLayout cityDateOfBirthLayout = new HorizontalLayout(cityTextField, birthdayField);
        mainLayout.addComponentsAndExpand(cityDateOfBirthLayout);

        mainLayout.addComponentsAndExpand(confirmButton);
        mainLayout.setComponentAlignment(confirmButton, Alignment.MIDDLE_CENTER);
    }
    /**
     * Prepares the confirm button
     */
    private void prepareConfirmButton() {
        confirmButton.addClickListener((Button.ClickEvent clickEvent) -> {
            if(!checkForObligatoryFields(firstNameTextField,lastNameTextField,emailTextField,passwordField
                ,repeatPasswordField,phoneTextField))return;
            
            // Checks if passwords match
            if (!passwordField.getValue().equals(repeatPasswordField.getValue())){
                PopupNotification.show(PASSWORDS_NOT_EQUAL_TITLE,PASSWORDS_NOT_EQUAL_MESSAGE
                        ,Notification.Type.ERROR_MESSAGE,Position.TOP_RIGHT);
                return;
            }
            
            try {
                RegisterUserController registerController=new RegisterUserController();
                registerController.addEmail(emailTextField.getValue());
                registerController.addPassword(passwordField.getValue());
                registerController.addName(firstNameTextField.getValue()+" "+lastNameTextField.getValue());
                registerController.addPhoneNumber(phoneTextField.getValue());
                if(cityTextField.getValue()!=null&&!cityTextField.getValue().isEmpty())
                    registerController.addLocation(cityTextField.getValue());
                if(birthdayField.getValue()!=null)registerController.addBirthDate(birthdayField.getValue().toString());
                registerController.registerUser();
            } catch (IllegalArgumentException|DateTimeParseException invalidFieldException) {
                PopupNotification.show(INVALID_FIELD_TITLE,invalidFieldException.getMessage(),
                    Notification.Type.ERROR_MESSAGE, Position.TOP_RIGHT);
                return;
            }catch(IllegalStateException registrationFailureException){
                PopupNotification.show(REGISTER_USER_FAILURE_TITLE,registrationFailureException.getMessage()
                        ,Notification.Type.ERROR_MESSAGE,Position.TOP_RIGHT);
            }
            PopupNotification.show(REGISTER_USER_SUCCESS_TITLE,REGISTER_USER_SUCCESS_MESSAGE,
                    Notification.Type.HUMANIZED_MESSAGE,Position.TOP_RIGHT);
        });
    }
    /**
     * Method that checks if all obligatory fields are filled
     * @param obligatoryFields Array with the obligatory fields
     * @return boolean true if all obligatory fields are filled, false if not
     */
    private boolean checkForObligatoryFields(TextField... obligatoryFields){
        for(int i=0;i<obligatoryFields.length;i++){
            if(obligatoryFields[i].getValue()==null|obligatoryFields[i].getValue().isEmpty()){
                PopupNotification.show(OBLIGATORY_FIELD_TITLE
                        ,String.format(FORMATED_OBLIGATORY_FIELD_MESSAGE,obligatoryFields[i].getCaption())
                        ,Notification.Type.ERROR_MESSAGE, Position.TOP_RIGHT);
                return false;
            }
        }
        return true;
    }
}
