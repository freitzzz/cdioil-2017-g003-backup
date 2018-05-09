package cdioil.frontoffice.presentation;

import cdioil.application.authz.AuthenticationController;
import cdioil.application.domain.authz.exceptions.AuthenticationException;
import cdioil.console.Console;

/**
 * Classe responsável pelo login no FrontOffice da aplicação.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class FrontOfficeLogin {

    private static final String HEADLINE = "===========================\n";
    private static final String MAIN_MESSAGE = "FrontOffice\n";
    
    /**
     * Message requesting the user for their email address.
     */
    private static final String REQUEST_EMAIL = "Email:";
    /**
     * Message requesting the user for their password.
     */
    private static final String REQUEST_WATCHWORD = "Password:";
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
     * Metodo principal do frontOffice da app.
     */
    public void frontOfficeLogin() {
        int id = -1;
        System.out.println(HEADLINE);
        System.out.println(MAIN_MESSAGE);
        while (id == -1) {
            System.out.println("\n=============================\n"
                    + "1. Registar Utilizador\n"
                    + "2. Login\n"
                    + "3. Sair");
            int option = Console.readInteger("Escolha a opção desejada: ");
            switch (option) {
                case 1:
                    new FrontOfficeConsole();
                    break;
                case 2:
                    loginComponent();
                    break;
                case 3:
                    System.out.println("O programa vai terminar.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção não reconhecida");
                    break;
            }
        }
    }    
    /**
     * Logins into backoffice
     */
    public void loginComponent() {
        long id = -1;
        while (id != 1) {
            byte[] email = Console.readLine(REQUEST_EMAIL).getBytes();
            byte[] password = Console.readLine(REQUEST_WATCHWORD).getBytes();
            try{
                if(authenticationController.login(new String(email),new String(password))){
                    id=1;
                    clearAuthCredentials(email,password);
                    FrontOfficeConsole.enterFrontoffice(authenticationController);
                }
            }catch(IllegalStateException|IllegalArgumentException e){
                Console.logError(e.getMessage());
            }catch(AuthenticationException f){
                Console.logError(f.getMessage());
                if(f.getAuthenticationExceptionCause().equals(AuthenticationException.AuthenticationExceptionCause.NOT_ACTIVATED)){
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
