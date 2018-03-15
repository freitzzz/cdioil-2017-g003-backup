package cdioil.backoffice.console.presentation;

import cdioil.application.authz.ListarUsersController;
import cdioil.domain.authz.SystemUser;

/**
 * Classe UI referente a US130 - Listar Todos Os Utilizadores Do Sistema.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public class ListarUsersUI {

    /**
     * Separador da lista de utilizadores do sistema.
     */
    private static final String HEADLINE = "=============================";
    /**
     * Mensagem de identificação da US130 - Listar de Todos os Utilizadores do
     * Sistema.
     */
    private static final String TODOS_USERS = "Lista de Todos os Utilizadores"
            + " do Sistema\n";
    /**
     * Controller responsavel pela comunicação com as classes de dominio e
     * persistencia.
     */
    private ListarUsersController controller = new ListarUsersController();

    /**
     * Metodo apresenta todos os utilizadores do sistema.
     */
    public void listarUtilizadores() {
        Iterable<SystemUser> allUsers = controller.listarUtilizadores();

        System.out.println(HEADLINE);
        System.out.println(TODOS_USERS);
        allUsers.forEach((t) -> {
            System.out.println(t.toString());
        });
        System.out.println(HEADLINE);
    }
}
