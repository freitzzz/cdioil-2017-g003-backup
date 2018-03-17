package cdioil.backoffice.console.presentation;

import cdioil.application.authz.AdicionarWhitelistController;
import cdioil.backoffice.console.utils.Console;

/**
 * User Interface responsável pela interação do utilizador para a US103
 *
 * @author António Sousa [1161371]
 */
public class AdicionarWhitelistUI {

    AdicionarWhitelistController controller;

    /**
     * Instancia a user interface.
     */
    public AdicionarWhitelistUI() {

        controller = new AdicionarWhitelistController();
        apresentarUI();
    }

    private void apresentarUI() {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("======================");
            System.out.println("1. Adicionar dominios de email autorizados.");
            System.out.println("0. Sair.");
            opcao = Console.readInteger("Selecione uma opcao: \n");

            switch (opcao) {

                case 0:
                    System.out.println("A sair...");
                    break;

                case 1:
                    String dominio = Console.readLine("Escreva o dominio de email que pretende autorizar.\n");
                    controller.adicionarDominioAutorizado(dominio);
                    break;

                default:
                    System.out.println("Por favor insira uma opcao valida.\n");
                    break;

            }
        }

    }

}
