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
    private static final String MAIN_MESSAGE = "FrontOffice\n";
    private static final String EMAIL = "Email: ";
    private static final String PASSWORD = "Password: ";
    private static final String WRONG_CREDENTIALS = "Credenciais erradas."
            + "Volte a tentar.\n";
    private static final int CRIAR_CONTA_ID = 1;
    private static final int LOGIN_ID = 2;
    //private UserRepositoryImpl userRepo = new UserRepositoryImpl();

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
     * Metodo com a componente de login de front office.
     */
    private void loginComponent() {
        int id = -1;
        System.out.println(HEADLINE);
        System.out.println(MAIN_MESSAGE);
        try {
            while (id == -1) {
                String emailS = Console.readLine(EMAIL);
                String passwordS = Console.readLine(PASSWORD);
                Email email = new Email(emailS);
                SystemUser sysUser = new UserRepositoryImpl().findByEmail(email);
                if (sysUser == null || !sysUser.samePassword(passwordS)) {
                    System.out.println(WRONG_CREDENTIALS);
                } else {
                    id = LOGIN_ID;
                    new FrontOfficeConsole(sysUser);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(WRONG_CREDENTIALS);
        }
    }
}
