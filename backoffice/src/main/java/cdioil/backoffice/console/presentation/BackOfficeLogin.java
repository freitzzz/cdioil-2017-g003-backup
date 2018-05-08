package cdioil.backoffice.console.presentation;

import cdioil.application.authz.AuthenticationController;
import cdioil.application.domain.authz.exceptions.AuthenticationException;
import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.console.Console;

/**
 * Class responsible for the Application's Backoffice login.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class BackOfficeLogin {

    /**
     * Localization Handler to load messages in several languages.
     */
    private final BackOfficeLocalizationHandler localizationHandler = BackOfficeLocalizationHandler.getInstance();
    
    /**
     * Separator used for clarity.
     */
    private static final String SEPARATOR = "===========================\n";
    /**
     * Backoffice Welcome message.
     */
    private final String infoWelcome = localizationHandler.getMessageValue("info_welcome");

    /**
     * Message that informing the user the program is about to shutdown.
     */
    private final String infoShutdown = localizationHandler.getMessageValue("info_shutdown");

    /**
     * Message requesting the user for their email address.
     */
    private final String requestEmail = localizationHandler.getMessageValue("request_email");
    /**
     * Message requesting the user for their password.
     */
    private final String requestPassword = localizationHandler.getMessageValue("request_password");
    /**
     * Error message informing the user of invalid credentials.
     */
    private final String errorInvalidCredentials = localizationHandler.getMessageValue("error_invalid_credentials");
    /**
     * Error message informing the user they're not authorized to use the Backoffice.
     */
    private final String errorUnauthorizedUser = localizationHandler.getMessageValue("error_unauthorized_user");
    /**
     * Constant that represents the message that ocures if the system asks the user for the activation code
     */
    private static final String ACTIVATION_CODE_MESSAGE="Por favor insira o código de activação da sua conta";
    /**
     * Constant that represents the exit code
     */
    private static final String EXIT_CODE="Sair";
    /**
     * Constant that represents the exit message
     */
    private static final String EXIT_MESSAGE="Se desejar sair digite \""+EXIT_CODE+"\"";
    /**
     * Constant that represents the message that ocures if the account being activated was 
     * activated with success
     */
    private static final String ACCOUNT_ACTIVATED_SUCCESS="A conta foi activada com successo!";
    /**
     * Constant that represents the message that ocures if the account being activated was 
     * not activated with success
     */
    private static final String ACCOUNT_ACTIVATED_FAILURE="O código inserido não é igual ao enviado!";
    /**
     * Current authentication controller
     */
    private final AuthenticationController authenticationController=new AuthenticationController();
    
    /**
     * Logins into backoffice
     */
    public void backofficeLogin() {
        long id = -1;
        System.out.println(SEPARATOR);
        System.out.println(infoWelcome);
        while (id != 1) {
            byte[] email = Console.readLine(requestEmail).getBytes();
            byte[] password = Console.readLine(requestPassword).getBytes();
            try{
                if(authenticationController.login(new String(email),new String(password))){
                    id=1;
                    clearAuthCredentials(email,password);
                    BackOfficeConsole.enterBackoffice(authenticationController);
                }
            }catch(IllegalStateException|IllegalArgumentException e){
                Console.logError(e.getMessage());
            }catch(AuthenticationException f){
                Console.logError(f.getMessage());
                if(f.getAuthenticatioExceptionCause().equals(AuthenticationException.AuthenticationExceptionCause.NOT_ACTIVATED)){
                    if(askForActivationCode(email,password))id=0;
                }
            }
        }
    }
    /**
     * Clears the current authentication credentials from the memory
     * @param email Byte array with the user email
     * @param password Byte array with the user password
     */
    private void clearAuthCredentials(byte[] email,byte[] password){
        email=null;
        password=null;
    }
    /**
     * Asks for the account activation code
     * @param email Byte array with the user email
     * @param password Byte array with the user password
     * @return boolean true if the user inserted the activation code with success, 
     * false if the user stopped the activation code process
     */
    private boolean askForActivationCode(byte[] email,byte[] password){
        boolean catched=false;
        while(!catched){
            String code=Console.readLine(ACTIVATION_CODE_MESSAGE);
            if(authenticationController.activateAccount(new String(email)
                    ,new String(password),code)){
                Console.log(ACCOUNT_ACTIVATED_SUCCESS,Console.ConsoleColors.BLUE);
                return true;
            }else{
                Console.logError(ACCOUNT_ACTIVATED_FAILURE);
                if(Console.readLine(EXIT_MESSAGE).equalsIgnoreCase(EXIT_CODE))return false;
            }
        }
        return catched;
    }
}
