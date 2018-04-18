package cdioil.frontoffice.application.authz;

import cdioil.application.authz.EmailSenderService;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.domain.authz.*;
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
     * Constant that represents the message that ocures if the user was not registed with success
     */
    private static final String REGISTERED_USED_SUCCESS_FAILURE="Ocorreu um erro ao registar o email!\nPor favor volte a tentar mais tarde";
    /**
     * Constant that represents the Email identifier (<b>@</b>)
     */
    private static final String EMAIL_IDENTIFIER = "@";
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
        if(sendRegisterCode(systemUser)){
            registerUserRepository.add(registeredUser);
        }else{
            throw new IllegalArgumentException(REGISTERED_USED_SUCCESS_FAILURE);
        }
    }
    /**
     * Method that sends a registration code to the user email address in order to prove it's authencity
     * @param SystemUser SystemUser with the user that is going to send the activation code
     */
    private boolean sendRegisterCode(SystemUser user){
        return new EmailSenderService(user).sendActivationCode();
    }
}
