package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.authz.AssignManagerController;
import cdioil.console.Console;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Assigns a Manager role to a system user
 */
public class AssignManagerUI {

    /**
     * Controller
     */
    private final AssignManagerController controller;

    /**
     * Console line separator
     */
    private static final String LINE_SEPARATOR
            = "==========================================";

    /**
     * Max users per page
     */
    private static final int MAX_USERS_PAGE = 10;

    /**
     * Constructor
     */
    public AssignManagerUI() {
        controller = new AssignManagerController();

        showMenu();
    }

    /**
     * Shows the main menu
     */
    private void showMenu() {

        int option = -1;

        while (true) {
            System.out.println(LINE_SEPARATOR);
            System.out.println("Atribuir Perfil de Gestor");
            System.out.println(LINE_SEPARATOR);

            System.out.println("1 - Ver Lista de Utilizadores Registados");
            System.out.println("2 - Registar Gestor");
            System.out.println("3 - Sair");
            System.out.println(LINE_SEPARATOR);

            option = Console.readInteger("Opção");

            switch (option) {
                case 1:
                    showListOfRegisteredUsers();
                    break;
                case 2:
                    assignManager();
                    break;
                case 3:
                    // Do nothing
                    return;
                default:
                    System.out.println(LINE_SEPARATOR);
                    System.out.println("ERRO: Opção inválida");
                    break;
            }
        }
    }

    /**
     * Prints a list of registered users Only SystemUsers are shown. If a user is an admin or a manager, it's email is not printed.
     */
    private void showListOfRegisteredUsers() {
        // Get lista users
        List<String> userList = getRegisteredUsersLists();
        if (userList == null || userList.isEmpty()) {
            System.out.println("ERRO: Não há utilizadores registados!");
            return;
        }

        final int nUsers = userList.size();
        final int nPages = numberOfPages(nUsers);

        int currentPage = 1;
        int option = -1;
        List<String> usersPage = null;

        System.out.println(LINE_SEPARATOR);
        System.out.println("Lista Utilizadores");
        System.out.println(LINE_SEPARATOR);

        while (true) {
            int idxInicial = MAX_USERS_PAGE * (currentPage - 1);
            int idxFinal = idxInicial + MAX_USERS_PAGE;

            if (idxFinal > nUsers) {
                idxFinal = nUsers - 1;
            }

            usersPage = userList.subList(idxInicial, idxFinal + 1);

            for (String user : usersPage) {
                System.out.println(user);
            }

            System.out.println(LINE_SEPARATOR);
            System.out.println(String.format("Página %d/%d", currentPage, nPages));
            System.out.println("Introduza '0' para sair da vista de utilizadores");
            option = Console.readInteger("Ir para página: ");

            if (option == 0) {
                return;
            } else if (option >= 1 && option <= nPages) {
                currentPage = option;
            } else {
                System.out.println("ERRO: Introduza uma página valida.");
                return;
            }
        }

    }

    /**
     * Gets a list of registered users
     *
     * @return ArrayList
     */
    private List<String> getRegisteredUsersLists() {
        //return controller.registeredUsers();
        return null;
    }

    /**
     * Number of pages needed to show all users
     *
     * @param nUsers total number of users
     * @return number of pages needed
     */
    private int numberOfPages(int nUsers) {
        return nUsers / MAX_USERS_PAGE + 1;
    }

    /**
     * Assigns a manager role to a SystemUser
     */
    private void assignManager() {
        try {
            String email = Console.readLine("Introduza o email desejado");
            //controller.assignManager(email);
        } catch (IllegalArgumentException e) {
            System.out.println(LINE_SEPARATOR);
            System.out.println("ERRO: Email inválido!");
        } catch (NoResultException e) {
            System.out.println(LINE_SEPARATOR);
            System.out.println("ERRO: Email não existe");
        }
    }
}
