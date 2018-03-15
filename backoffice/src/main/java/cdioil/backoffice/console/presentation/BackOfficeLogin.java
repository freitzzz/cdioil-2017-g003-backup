package cdioil.backoffice.console.presentation;

import cdioil.application.persistence.RepositorioUtilizadores;
import cdioil.backoffice.console.utils.Console;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Gestor;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;

/**
 *
 * @author ricar
 */
public class BackOfficeLogin {
    
    private static final int ID_ADMIN = 1;
    private static final int ID_GESTOR = 2;
    private static final String HEADLINE = "===========================\n";
    private static final String LOGIN_MESSAGE = "Insira o seu email e password\n";
    private static final String EMAIL = "Email: ";
    private static final String PASSWORD = "Password: ";
    private static final String USER_NAO_ENCONTRADO = "Credenciais erradas."
            + "Volte a tentar.\n";
    private static final String USER_NAO_AUTORIZADO = "Não tem autorização"
            + "para utilizar o backoffice da aplicação. O programa irá terminar.\n";
//    private RepositorioUtilizadores userRepo = new RepositorioUtilizadores();
//    private RepositorioAdmin adminRepo = new RepositorioAdmin();
//    private RepositorioGestor gestorRepo = new RepositorioGestor();
    
    public void backofficeLogin(){
//        int id = -1;
//        System.out.println(HEADLINE);
//        System.out.println(LOGIN_MESSAGE);
////        while(id == -1){
//            String emailS = Console.readLine(EMAIL);
//            String passwordS = Console.readLine(PASSWORD);
//            Email email = new Email(emailS);
//            Password password = new Password(passwordS);
////            SystemUser sysUser = userRepo.findById(email);
////            if(sysUser == null){
////                System.out.println(USER_NAO_ENCONTRADO);
////            }else{
////                Admin admin = adminRepo.find(sysUser);
////                Gestor gestor = gestorRepo.find(sysUser);
////                if(admin == null || gestor == null){
////                    System.out.println(USER_NAO_AUTORIZADO);
////                    System.exit(0);
////                }
////                if(admin == null && gestor != null){
////                    id = ID_GESTOR;
////                    new BackOfficeConsole(gestor);
////                }
////                if(gestor == null && admin != null){
////                    id = ID_ADMIN;
////                    new BackOfficeConsole(admin);
////                }
//            }
//        }
    }
    
}
