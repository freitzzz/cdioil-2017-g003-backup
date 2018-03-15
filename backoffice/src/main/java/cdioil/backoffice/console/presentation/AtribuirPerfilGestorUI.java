package cdioil.backoffice.console.presentation;

import cdioil.application.authz.AtribuirPerfilGestorController;
import cdioil.backoffice.console.utils.Console;

import java.util.ArrayList;

public class AtribuirPerfilGestorUI {

    private AtribuirPerfilGestorController controller;

    private static final String LINE_SEPARATOR =
            "==========================================";

    private static final int MAX_USERS_PAGINA = 10;

    public AtribuirPerfilGestorUI() {
        controller = new AtribuirPerfilGestorController();

        mostrarMenu();
    }

    private void mostrarMenu() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Atribuir Perfil de Gestor");
        System.out.println(LINE_SEPARATOR);

        int opcao = -1;

        while (true) {
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
                    atribuirGestor();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("ERRO: Opção inválida");
                    break;
            }
        }

    }

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
        ArrayList<String> usersPagina = null;

        System.out.println(LINE_SEPARATOR);
        System.out.println("Lista Utilizadores");
        System.out.println(LINE_SEPARATOR);

        while (true) {
            int idxInicial = MAX_USERS_PAGINA * (currentPage - 1);
            int idxFinal = idxInicial + MAX_USERS_PAGINA;

            if (idxFinal > numeroUsers) {
                idxFinal = numeroUsers - 1;
            }

            usersPagina.subList(idxInicial, idxFinal + 1);

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

    private ArrayList<String> getListaUsersRegistados() {
        return controller.getListaUsersRegistados();
    }

    private int numeroPaginas(int numeroUsers) {
        return numeroUsers / MAX_USERS_PAGINA + 1;
    }

    private void atribuirGestor() {
        String email = Console.readLine("Introduza o email desejado");

        controller.atribuirGestor(email);
    }
}
