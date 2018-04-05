package cdioil.frontoffice.presentation.authz;


import cdioil.frontoffice.application.authz.RegistarUtilizadorController;
import cdioil.frontoffice.utils.Console;

public class RegistarUtilizadorUI {

    private RegistarUtilizadorController controller = new RegistarUtilizadorController();


    public void registarUtilizadorUI() {
        System.out.println("**** Registar Utilizadores ****\n");
        String primeiroNome = Console.readLine("\nNome: ");
        String apelido = Console.readLine("\nApelido: ");
        String email = Console.readLine("\nEmail: ");
        try {
            controller.criarUtilizadorRegistado(primeiroNome, apelido, email, Console.readLine("\nPassword: "));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
