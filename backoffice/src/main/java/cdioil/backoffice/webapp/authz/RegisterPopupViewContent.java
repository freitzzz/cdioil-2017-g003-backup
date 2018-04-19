package cdioil.backoffice.webapp.authz;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

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



    public RegisterPopupViewContent() {
        instantiateComponents();
        prepareComponent();
    }

    private void instantiateComponents() {
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

    @Override
    public String getMinimizedValueAsHTML() {
        return null;
    }

    @Override
    public Component getPopupComponent() {
        return mainLayout;
    }
}
