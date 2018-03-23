package cdioil.backoffice.console.presentation;

import cdioil.backoffice.console.utils.Console;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Gestor;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.AdminRepositoryImpl;
import cdioil.persistence.impl.GestorRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;

/**
 * Classe responsável pelo login no BackOffice da aplicação.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class BackOfficeLogin {

    private static final int ID_ADMIN = 1;
    private static final int ID_GESTOR = 2;
    private static final String HEADLINE = "===========================\n";
    private static final String LOGIN_MESSAGE = "BackOffice Login\nInsira o seu email e password\n";
    private static final String EMAIL = "Email: ";
    private static final String PASSWORD = "Password: ";
    private static final String USER_NAO_ENCONTRADO = "Credenciais erradas."
            + "Volte a tentar.\n";
    private static final String USER_NAO_AUTORIZADO = "Não tem autorização "
            + "para utilizar o backoffice da aplicação. O programa irá terminar.\n";
    private UserRepositoryImpl userRepo = new UserRepositoryImpl();
    private AdminRepositoryImpl adminRepo = new AdminRepositoryImpl();
    private GestorRepositoryImpl gestorRepo = new GestorRepositoryImpl();

    public void backofficeLogin() {
        int id = -1;
        System.out.println(HEADLINE);
        System.out.println(LOGIN_MESSAGE);
        while (id == -1) {
            String emailS = Console.readLine(EMAIL);
            String passwordS = Console.readLine(PASSWORD);
            try {
                Email email = new Email(emailS);
                SystemUser sysUser = userRepo.findByEmail(email);
                if (sysUser == null || !sysUser.samePassword(passwordS)) {
                    System.out.println(USER_NAO_ENCONTRADO);
                } else {
                    Admin admin = adminRepo.getEntity(new Admin(sysUser));
                    Gestor gestor = gestorRepo.getEntity(new Gestor(sysUser));
                    if (admin == null && gestor == null) {
                        System.out.println(USER_NAO_AUTORIZADO);
                        System.exit(0);
                    }
                    if (admin == null && gestor != null) {
                        id = ID_GESTOR;
                        new BackOfficeConsole(gestor);
                    }
                    if (gestor == null && admin != null) {
                        id = ID_ADMIN;
                        new BackOfficeConsole(admin);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(USER_NAO_ENCONTRADO);
            }
        }
    }
}
