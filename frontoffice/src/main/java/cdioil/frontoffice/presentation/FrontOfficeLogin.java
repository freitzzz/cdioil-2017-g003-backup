package cdioil.frontoffice.presentation;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.SystemUser;
import cdioil.frontoffice.utils.Console;
import cdioil.persistence.impl.UserRepositoryImpl;

/**
 * Classe responsável pelo login no FrontOffice da aplicação.
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class FrontOfficeLogin {

    private static final String HEADLINE = "===========================\n";
    private static final String LOGIN_MESSAGE = "BackOffice Login\nInsira o seu email e password\n";
    private static final String EMAIL = "Email: ";
    private static final String PASSWORD = "Password: ";
    private static final String USER_NAO_ENCONTRADO = "Credenciais erradas."
            + "Volte a tentar.\n";
    private UserRepositoryImpl userRepo = new UserRepositoryImpl();

    public void frontOfficeLogin() {
        int id = -1;
        System.out.println(HEADLINE);
        System.out.println(LOGIN_MESSAGE);
        while (id == -1) {
            String emailS = Console.readLine(EMAIL);
            String passwordS = Console.readLine(PASSWORD);
            Email email = new Email(emailS);
            SystemUser sysUser = userRepo.find(email);
            System.out.println(sysUser.passwordIgual(PASSWORD));
            if (sysUser == null || !sysUser.passwordIgual(passwordS)) {
                System.out.println(USER_NAO_ENCONTRADO);
            } else {
                new FrontOfficeConsole(sysUser);
            }
        }
    }
}
