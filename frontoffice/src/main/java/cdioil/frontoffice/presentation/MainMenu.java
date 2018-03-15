package cdioil.frontoffice.presentation;

import cdioil.frontoffice.presentation.authz.RegistarUtilizadorUI;
import cdioil.frontoffice.utils.Console;

public class MainMenu {

    public static void main(String[] args) {
        int opcao = 0;
        do {
            opcao = menu();

            switch (opcao) {
                case 0:
                    System.out.println("Fim");
                    break;
                case 1:
                    RegistarUtilizadorUI registarUtilizadorUI = new RegistarUtilizadorUI();
                    registarUtilizadorUI.registarUtilizadorUI();
                    break;
                case 2:
                    //Atualizar dados
                    break;
            }
        } while (opcao != 0);
    }

    private static int menu() {
        System.out.println("\n=============================\n" +
                "1. Registar Utilizador\n" +
                "2. Atualizar dados");
        return Console.readInteger("Escolha a opção desejada: ");
    }
}
