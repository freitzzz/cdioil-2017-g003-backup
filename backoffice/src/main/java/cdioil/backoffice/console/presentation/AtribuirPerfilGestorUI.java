package cdioil.backoffice.console.presentation;

import cdioil.application.authz.AtribuirPerfilGestorController;
import cdioil.backoffice.utils.Console;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * Atribui um perfil de Gestor a um utilizador registado
 */
public class AtribuirPerfilGestorUI {

    /**
     * Controller
     */
    private AtribuirPerfilGestorController controller;

    /**
     * Separador
     */
    private static final String LINE_SEPARATOR =
            "==========================================";

    /**
     * Máximo de users mostrados por página
     */
    private static final int MAX_USERS_PAGINA = 10;

    /**
     * Construtor
     */
    public AtribuirPerfilGestorUI() {
        controller = new AtribuirPerfilGestorController();

        mostrarMenu();
    }

    /**
     * Menu Principal da User Storie
     */
    private void mostrarMenu() {

        int opcao = -1;

        while (true) {
            System.out.println(LINE_SEPARATOR);
            System.out.println("Atribuir Perfil de Gestor");
            System.out.println(LINE_SEPARATOR);

            System.out.println("1 - Ver Lista de Utilizadores Registados");
            System.out.println("2 - Registar Gestor");
            System.out.println("3 - Sair");
            System.out.println(LINE_SEPARATOR);

            opcao = Console.readInteger("Opção");

            switch (opcao) {
                case 1:
                    mostrarListaUtilizadoresRegistados();
                    break;
                case 2:
                    try {
                        atribuirGestor();
                    } catch (IllegalArgumentException e) {
                        System.out.println(LINE_SEPARATOR);
                        System.out.println("ERRO: Email inválido!");
                    } catch (NoResultException e) {
                        System.out.println(LINE_SEPARATOR);
                        System.out.println("ERRO: Email não existe");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println(LINE_SEPARATOR);
                    System.out.println("ERRO: Opção inválida");
                    break;
            }
        }

    }

    /**
     * Mostra na consola uma lista de utilizadores registados
     * A lista apresentada nao contem contas de System user associadas
     * a gestores ou admins
     */
    private void mostrarListaUtilizadoresRegistados() {
        // Get lista users
        ArrayList<String> listaUsers = getListaUsersRegistados();
        if (listaUsers.isEmpty()) {
            System.out.println("ERRO: Não há utilizadores registados!");
            return;
        }

        final int numeroUsers = listaUsers.size();
        final int numeroPaginas = numeroPaginas(numeroUsers);

        int currentPage = 1;
        int opcao = -1;
        List<String> usersPagina = null;

        System.out.println(LINE_SEPARATOR);
        System.out.println("Lista Utilizadores");
        System.out.println(LINE_SEPARATOR);

        while (true) {
            int idxInicial = MAX_USERS_PAGINA * (currentPage - 1);
            int idxFinal = idxInicial + MAX_USERS_PAGINA;

            if (idxFinal > numeroUsers) {
                idxFinal = numeroUsers - 1;
            }

            usersPagina = listaUsers.subList(idxInicial, idxFinal + 1);

            for (String user : usersPagina) {
                System.out.println(user);
            }

            System.out.println(LINE_SEPARATOR);
            System.out.println(String.format("Página %d/%d", currentPage, numeroPaginas));
            System.out.println("Introduza '0' para sair da vista de utilizadores");
            opcao = Console.readInteger("Ir para página: ");

            if (opcao == 0) {
                return;
            } else if (opcao >= 1 && opcao <= numeroPaginas) {
                currentPage = opcao;
                continue;
            } else {
                System.out.println("ERRO: Introduza uma página valida.");
                return;
            }
        }

    }

    /**
     *
     * @return Lista com os nomes de todos os utilizadores registados
     * que nao sejam admins/gestores
     */
    private ArrayList<String> getListaUsersRegistados() {
        return controller.getListaUsersRegistados();
    }

    /**
     * Conta o numero de paginas necessario para mostrar todos os users
     * @param numeroUsers numero users total
     * @return numero de paginas necessario
     */
    private int numeroPaginas(int numeroUsers) {
        return numeroUsers / MAX_USERS_PAGINA + 1;
    }

    /**
     * Atribui um gestor a um utilizador registado
     */
    private void atribuirGestor() {
        String email = Console.readLine("Introduza o email desejado");

        controller.atribuirGestor(email);
    }
}
