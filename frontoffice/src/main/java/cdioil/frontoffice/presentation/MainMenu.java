package cdioil.frontoffice.presentation;

import cdioil.application.authz.AuthenticationController;
import cdioil.frontoffice.presentation.authz.ChangeUserDataUI;
import cdioil.console.Console;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.RegisteredUser;
import cdioil.domain.authz.SystemUser;
import cdioil.domain.authz.User;

/**
 * FrontOffice Main Menu
 */
public class MainMenu {
    
    /**
     * Line Separator for console UI
     */
    private static final String LINE_SEPARATOR =
            "======================";
    
    /**
     * Current AuthenticationController
     */
    private final AuthenticationController authenticationController;

    /**
     * Constructs an instance of the the front office login
     * @param authenticationController AuthenticationConroller with the autentication controller of 
     * the current session
     */
    public MainMenu(AuthenticationController authenticationController) {
        this.authenticationController=authenticationController;
        showMenu();
    }

    /**
     * Shows application Main Menu
     */
    private void showMenu() {
        while (true) {
            System.out.println(LINE_SEPARATOR);
            System.out.println("      Main Menu");
            System.out.println(LINE_SEPARATOR);

            System.out.println("1 - Alterar Dados");
            System.out.println("2 - Responder Inquérito");
            System.out.println("0 - Sair");

            int option = Console.readInteger("Opção");

            switch (option) {
                case 0:
                    authenticationController.logout();
                    return;
                case 1:
                    ChangeUserDataUI mui = new ChangeUserDataUI(getSystemUser());
                    mui.changeData();
                    break;
                case 2:
                    new AnswerSurveyUI(getRegisteredUser());
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }
    /**
     * Temporary Solution since an ui uses a SystemUser instance
     * <br>To be removed on a near future
     * @return SystemUser with the current user
     */
    private SystemUser getSystemUser(){
        User user=authenticationController.getUser();
        if(user instanceof RegisteredUser){
            return ((RegisteredUser)user).getID();
        }
        if(user instanceof Admin){
            return ((Admin) user).getID();
        }
        return ((Manager)user).getID();
    }
    
    /**
     * Temporary Solution so that AnswerSurveyUI can have a RegisteredUser
     * instance
     * <br>To be removed or updated in the future
     * @return RegisteredUser that logged in
     */
    private RegisteredUser getRegisteredUser(){
        User user = authenticationController.getUser();
        if(user instanceof RegisteredUser){
            return (RegisteredUser) user;
        }
        return null;
    }
}
