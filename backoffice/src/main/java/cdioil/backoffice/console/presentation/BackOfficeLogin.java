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
     * Separator used for clarity.
     */
    private final String SEPARATOR = "===========================\n";
    /**
     * Backoffice Welcome message.
     */
    private final String INFO_WELCOME = BackOfficeLocalizationHandler.getInstance().getMessageValue("info_welcome");
    
    /**
     * Message that informing the user the program is about to shutdown.
     */
    private final String INFO_SHUTDOWN = BackOfficeLocalizationHandler.getInstance().getMessageValue("info_shutdown");
    
    /**
     * Message requesting the user for their email address.
     */
    private final String REQUEST_EMAIL = BackOfficeLocalizationHandler.getInstance().getMessageValue("request_email");
    /**
     * Message requesting the user for their password.
     */
    private final String REQUEST_PASSWORD = BackOfficeLocalizationHandler.getInstance().getMessageValue("request_password");
    /**
     * Error message informing the user of invalid credentials.
     */
    private final String ERROR_INVALID_CREDENTIALS = BackOfficeLocalizationHandler.getInstance().getMessageValue("error_invalid_credentials");
    /**
     * Error message informing the user they're not authorized to use the Backoffice.
     */
    private final String ERROR_UNAUTHORIZED_USER = BackOfficeLocalizationHandler.getInstance().getMessageValue("error_unauthorized_user");
    
    /**
     * SystemUser Repository.
     */
    private UserRepositoryImpl sysUserRepo = new UserRepositoryImpl();
    /**
     * Admin Repository.
     */
    private AdminRepositoryImpl adminRepo = new AdminRepositoryImpl();
    /**
     * Manager Repository.
     */
    private ManagerRepositoryImpl managerRepo = new ManagerRepositoryImpl();

    public void backofficeLogin() {
        long id = -1;
        System.out.println(SEPARATOR);
        System.out.println(INFO_WELCOME);
        while (id == -1) {
            String emailS = Console.readLine(REQUEST_EMAIL);
            String passwordS = Console.readLine(REQUEST_PASSWORD);
            try {
                Email email = new Email(emailS);
                id = sysUserRepo.login(email, passwordS);
                if (id == -1) {
                    System.out.println(ERROR_INVALID_CREDENTIALS);
                } else {
                    Admin admin = adminRepo.findByUserID(id);
                    Manager manager = managerRepo.findByUserID(id);
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
