package cdioil.frontoffice.application.authz;

import cdioil.application.authz.EmailSenderService;
import cdioil.application.domain.authz.SystemUserBuilder;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.domain.authz.*;
import cdioil.persistence.impl.UserRepositoryImpl;
import cdioil.persistence.impl.WhitelistRepositoryImpl;

/**
 * Controller for the Register User use case (US-180)
 */
public final class RegisterUserController {
    /**
     * Constant that represents the regular expression that represents blank spaces
     */
    private static final String REGEX_SPACES="\\s+";
    /**
     * Constant that represents the length that the name field splitted should have
     */
    private static final int SPLITTED_NAME_LENGTH=2;
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
     * Constant that represents the message that ocures if the user was not registered with success
     */
    private static final String REGISTERED_USED_SUCCESS_FAILURE="Ocorreu um erro ao registar o email!\nPor favor volte a tentar mais tarde";
    /**
     * Constant that represents the message that ocures if the user name is invalid in terms of first and last name
     */
    private static final String INVALID_NAME_MESSAGE="O nome do utilizador deve conter apenas o primeiro e segundo nome!";
    /**
     * Constant that represents the Email identifier (<b>@</b>)
     */
    private static final String EMAIL_IDENTIFIER = "@";
    /**
     * Registered Users repository
     */
    private final RegisteredUserRepositoryImpl registerUserRepository = new RegisteredUserRepositoryImpl();
    /**
     * Current SystemUserBuilder
     */
    private final SystemUserBuilder userBuilder=SystemUserBuilder.create();
    
    /**
     * Adds the email of the user to the current registration process
     * @param email String with the user email
     */
    public void addEmail(String email){
        try{
            if(new WhitelistRepositoryImpl().find(email.split(EMAIL_IDENTIFIER)[1])==null)
                throw new IllegalArgumentException(DOMAIN_NOT_FOUND_MESSAGE);
            userBuilder.withEmail(email);
        }catch(IndexOutOfBoundsException e){
            throw new IllegalArgumentException(DOMAIN_NOT_FOUND_MESSAGE);
        }
    }
    /**
     * Adds the password of the user to the current registration process
     * @param password String with the user Password
     */
    public void addPassword(String password){
        userBuilder.withPassword(password);
    }
    /**
     * Adds the name of the user to the current registration process
     * @param name String with the user name
     */
    public void addName(String name){
        String[] splittedName=name.trim().split(REGEX_SPACES);
        if(splittedName.length!=SPLITTED_NAME_LENGTH)throw new IllegalArgumentException(INVALID_NAME_MESSAGE);
        userBuilder.withName(splittedName[0],splittedName[1]);
    }
    /**
     * Adds the phone number of the user to the current registration process
     * @param phoneNumber String with the user phone number
     */
    public void addPhoneNumber(String phoneNumber){
        userBuilder.withPhoneNumber(phoneNumber);
    }
    /**
     * Adds the location of the user to the current registration process
     * @param location String with the user location
     */
    public void addLocation(String location){
        userBuilder.withLocation(location);
    }
    /**
     * Adds the birth date of the user to the current registration process
     * @param birthDate String with the user birth date
     */
    public void addBirthDate(String birthDate){
        userBuilder.withBirthDate(birthDate);
    }
    
    /**
     * Registers the current user
     */
    public void registerUser(){
        SystemUser builtUser=userBuilder.build();
        SystemUser importedUser=new UserRepositoryImpl().findByEmail(builtUser.getID());
        if(importedUser==null){
            registerUser(new RegisteredUser(builtUser));
        }else if(importedUser.isUserImported() && !importedUser.isUserActivated()){
            registerImportedUser(importedUser,builtUser);
        }else{
            throw new IllegalStateException(EMAIL_ALREADY_IN_USE_MESSAGE);
        }
    }
    /**
     * Registers the current user by sending an activation code to his email
     * @param registeredUser RegisteredUser with the user being registered
     */
    private void registerUser(RegisteredUser registeredUser){
        if(sendRegisterCode(registeredUser.getID())){
            registerUserRepository.add(registeredUser);
        }else{
            throw new IllegalStateException(REGISTERED_USED_SUCCESS_FAILURE);
        }
    }
    /**
     * Registers a certain previously imported user by checking if he is not 
     * an registered user yet and by sending an activation code to his email
     * @param importedUser SystemUser with the imported user
     * @param builtUser SystemUser with the new build user by registration
     */
    private void registerImportedUser(SystemUser importedUser,SystemUser builtUser){
        if(!registerUserRepository.exists(new RegisteredUser(importedUser))){
            importedUser.mergeImportedSystemUser(builtUser);
            registerUser(new RegisteredUser(new UserRepositoryImpl().merge(importedUser)));
        }else{
            throw new IllegalStateException(EMAIL_ALREADY_IN_USE_MESSAGE);
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
