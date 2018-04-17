package cdioil.frontoffice.application.authz;

import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.domain.authz.*;
import cdioil.emails.EmailSenderFactory;
import cdioil.persistence.impl.WhitelistRepositoryImpl;
import java.time.LocalDate;

/**
 * Controller for the Register User use case (US-180)
 */
public class RegisterUserController {
    /**
     * Constant that represents the message that ocures if the user attempts to
     * register a user which email domain is not authorized
     */
    private static final String DOMAIN_NOT_FOUND_MESSAGE = "Dominio/Subdominio de email nao autorizado";
    /**
     * Constant that represents the message that ocures if the user attempts to
     * register a user which email already exists
     */
    private static final String EMAIL_ALREADY_IN_USE_MESSAGE="O email já está em uso!";
    /**
     * Constant that represents the Email identifier (<b>@</b>)
     */
    private static final String EMAIL_IDENTIFIER = "@";
    /**
     * Hardcoded email that is used to send the activation code
     */
    private static final String EMAIL_SENDER="hardcodedmail@hardcoded.com";
    /**
     * Hardcoded email password that is used to send the activation code
     */
    private static final String EMAIL_SENDER_PASSWORD="ThisIsNotAReallyGoodSolution";
    /**
     * Constant that represents the title of the email that contains the message sent 
     * to the user with the activation code
     */
    private static final String EMAIL_ACTIVATION_CODE_TITLE="Código de Activação FeedBack Monkey";
    /**
     * Constant that represents the message of the email that contains the message sent 
     * to the user with the activation code
     */
    private static final String EMAIL_ACTIVATION_CODE_MESSAGE="Olá %s %s!"
                + "\nReparamos que acabaste de te registar na nossa aplicação com o endereço %s."
                + "\nDe modo a provar-mos a tua autenticidade, pedimos que insiras o seguinte código %d"
                + "aquando o inicio da aplicação.\n\nIsto é uma mensagem automática por favor não responda para este endereço!";
    /**
     * Registered Users repository
     */
    private final RegisteredUserRepositoryImpl registerUserRepository = new RegisteredUserRepositoryImpl();

    /**
     * Method that registers a certain user
     * @param firstName String with the user first name
     * @param lastName String with the user last name
     * @param email String with the user email
     * @param password String with the user password
     * @param phoneNumber String with the user phone number
     * @param location String with the user location
     * @param birthDate String with the user birth date
     */
    public void registerUser(String firstName, String lastName, String email, String password, String phoneNumber,
             String location, String birthDate) {
        String domainPart = email.split(EMAIL_IDENTIFIER)[1];
        System.out.println(domainPart);
        if(new WhitelistRepositoryImpl().find(domainPart)==null)throw new IllegalArgumentException(DOMAIN_NOT_FOUND_MESSAGE); 
        
        SystemUser systemUser = new SystemUser(
                new Email(email),
                new Name(firstName, lastName),
                new Password(password),
                new PhoneNumber(phoneNumber),
                new Location(location),
                new BirthDate(LocalDate.parse(birthDate)));
        RegisteredUser registeredUser = new RegisteredUser(systemUser);
        
        if(registerUserRepository.exists(registeredUser))throw new IllegalArgumentException(EMAIL_ALREADY_IN_USE_MESSAGE);
        registerUserRepository.add(registeredUser);
    }
    /**
     * Method that sends a registration code to the user email address in order to prove it's authencity
     * <br>In the future replace with service
     * @param firstName String with the user first name
     * @param lastName String with the user last name
     * @param email String with the user email
     * @param activationCode Long with the user activation code
     */
    private void sendRegisterCode(String firstName,String lastName,String email,long activationCode){
        String formatedMessage=String.format(EMAIL_ACTIVATION_CODE_MESSAGE,firstName,lastName,email,activationCode);
        EmailSenderFactory.create(EMAIL_SENDER,EMAIL_SENDER_PASSWORD)
                .sendEmail(email,EMAIL_ACTIVATION_CODE_TITLE,formatedMessage);
    }
}
