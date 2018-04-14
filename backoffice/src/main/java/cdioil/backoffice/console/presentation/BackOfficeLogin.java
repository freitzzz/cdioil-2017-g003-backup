package cdioil.backoffice.console.presentation;

import cdioil.backoffice.utils.BackOfficeLocalizationHandler;
import cdioil.backoffice.utils.Console;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.persistence.impl.AdminRepositoryImpl;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;

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
    private final String SEPARATOR = "===========================\n";
    /**
     * Backoffice Welcome message.
     */
    private final String INFO_WELCOME = localizationHandler.getMessageValue("info_welcome");

    /**
     * Message that informing the user the program is about to shutdown.
     */
    private final String INFO_SHUTDOWN = localizationHandler.getMessageValue("info_shutdown");

    /**
     * Message requesting the user for their email address.
     */
    private final String REQUEST_EMAIL = localizationHandler.getMessageValue("request_email");
    /**
     * Message requesting the user for their password.
     */
    private final String REQUEST_PASSWORD = localizationHandler.getMessageValue("request_password");
    /**
     * Error message informing the user of invalid credentials.
     */
    private final String ERROR_INVALID_CREDENTIALS = localizationHandler.getMessageValue("error_invalid_credentials");
    /**
     * Error message informing the user they're not authorized to use the Backoffice.
     */
    private final String ERROR_UNAUTHORIZED_USER = localizationHandler.getMessageValue("error_unauthorized_user");

    public void backofficeLogin() {
        long id = -1;
        System.out.println(SEPARATOR);
        System.out.println(INFO_WELCOME);
        while (id == -1) {
            String emailS = Console.readLine(REQUEST_EMAIL);
            String passwordS = Console.readLine(REQUEST_PASSWORD);
            try {
                Email email = new Email(emailS);
                id = new UserRepositoryImpl().login(email, passwordS);
                if (id == -1) {
                    System.out.println(ERROR_INVALID_CREDENTIALS);
                } else {
                    Admin admin = new AdminRepositoryImpl().findByUserID(id);
                    Manager manager = new ManagerRepositoryImpl().findByUserID(id);
                    if (admin == null && manager == null) {
                        System.out.println(ERROR_UNAUTHORIZED_USER);
                        System.out.println(INFO_SHUTDOWN);
                        System.exit(0);
                    }
                    if (admin == null && manager != null) {
                        new BackOfficeConsole(manager);
                    }
                    if (manager == null && admin != null) {
                        new BackOfficeConsole(admin);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(ERROR_INVALID_CREDENTIALS);
            }
        }
    }
}
