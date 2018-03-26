package cdioil.backoffice.console.presentation;

import cdioil.backoffice.console.utils.Console;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.SystemUser;
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
     * Integer used to identify an administrator login.
     */
    private static final int ADMIN_ID = 1;
    /**
     * Integer used to identify a manager login.
     */
    private static final int MANAGER_ID = 2;
    /**
     * Separator used for clarity.
     */
    private static final String SEPARATOR = "===========================\n";
    /**
     * Backoffice Login message.
     */
    private static final String LOGIN_MESSAGE = "BackOffice Login\nInsira o seu email e password\n";
    /**
     * String to let the user know he needs to write his email.
     */
    private static final String EMAIL = "Email: ";
    /**
     * String to let the user know she needs to write her password.
     */
    private static final String PASSWORD = "Password: ";
    /**
     * Error message to let the user know he wrote his credentials wrong.
     */
    private static final String WRONG_CREDENTIALS = "Credenciais erradas."
            + "Volte a tentar.\n";
    /**
     * Error message to let the user know she can't use the application's
     * backoffice.
     */
    private static final String NON_AUTHORIZED_USER = "Não tem autorização "
            + "para utilizar o backoffice da aplicação. O programa irá terminar.\n";
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
    private ManagerRepositoryImpl gestorRepo = new ManagerRepositoryImpl();

    public void backofficeLogin() {
        int id = -1;
        System.out.println(SEPARATOR);
        System.out.println(LOGIN_MESSAGE);
        while (id == -1) {
            String emailS = Console.readLine(EMAIL);
            String passwordS = Console.readLine(PASSWORD);
            try {
                Email email = new Email(emailS);
                SystemUser sysUser = sysUserRepo.login(email, passwordS);
                if (sysUser == null) {
                    System.out.println(WRONG_CREDENTIALS);
                } else {
                    Admin admin = adminRepo.getEntity(new Admin(sysUser));
                    Manager gestor = gestorRepo.getEntity(new Manager(sysUser));
                    if (admin == null && gestor == null) {
                        System.out.println(NON_AUTHORIZED_USER);
                        System.exit(0);
                    }
                    if (admin == null && gestor != null) {
                        id = MANAGER_ID;
                        new BackOfficeConsole(gestor);
                    }
                    if (gestor == null && admin != null) {
                        id = ADMIN_ID;
                        new BackOfficeConsole(admin);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(WRONG_CREDENTIALS);
            }
        }
    }
}
