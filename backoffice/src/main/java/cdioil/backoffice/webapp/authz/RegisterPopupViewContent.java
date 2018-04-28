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

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegisterPopupViewContent implements PopupView.Content {

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

    private void prepareConfirmButton() {
        confirmButton.addClickListener((Button.ClickEvent clickEvent) -> {
            final String firstName = firstNameTextField.getValue();
            final String lastName = lastNameTextField.getValue();
            final String email = emailTextField.getValue();
            final String phone = phoneTextField.getValue();
            final String password = passwordField.getValue();
            final String repeatPassword = repeatPasswordField.getValue();
            final String city = cityTextField.getValue();
            final LocalDate birthday = birthdayField.getValue();
            
            // Checks if passwords match
            if (!password.equals(repeatPassword)) {
                Notification.show("Passwords não são coincidem", Notification.Type.ERROR_MESSAGE);
                return;
            }
            
            // Check for blank fields
            if (firstName == null
                    || lastName == null
                    || email == null
                    || phone == null
                    || repeatPassword == null
                    || city == null
                    || birthday == null) {
                Notification.show("Todos os campos são de preenchimento obrigatório",
                        Notification.Type.ERROR_MESSAGE);
                return;
            }
            
            try {
                RegisterUserController registerController=new RegisterUserController();
                registerController.addEmail(email);
                registerController.addPassword(password);
                registerController.addName(firstName+" "+lastName);
                registerController.addPhoneNumber(phone);
                registerController.addLocation(city);
                System.out.println(birthday.toString());
                registerController.addBirthDate(birthday.toString());
                System.out.println(birthday.toString());
                registerController.registerUser();
            } catch (IllegalArgumentException|DateTimeParseException|IllegalStateException e) {
                Notification.show(e.getMessage(),
                        Notification.Type.ERROR_MESSAGE);
                e.printStackTrace();
                return;
            }
            PopupNotification.show("Utilizador Registado com sucesso!","Verifique a sua caixa de correio",
                    Notification.Type.HUMANIZED_MESSAGE,Position.TOP_RIGHT);
        });
    }

    @Override
    public String getMinimizedValueAsHTML() {
        return null;
    }

    @Override
    public Component getPopupComponent() {
        return mainLayout;
    }
}
