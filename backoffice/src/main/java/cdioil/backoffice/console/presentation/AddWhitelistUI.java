package cdioil.backoffice.console.presentation;

import cdioil.application.authz.AddWhitelistController;
import cdioil.backoffice.console.utils.Console;

/**
 * User story 103's user interface.
 *
 * @author António Sousa [1161371]
 */
public class AddWhitelistUI {

    private static final String HEAD_LINE = "======================";

    private static final String ADD_WHITELISTED_DOMAIN = "Adicionar dominios de email autorizados.";

    private static final String EXIT = "Sair.";

    private static final String OPTION_SELECTION = "Selecione uma opcao: \n";

    private static final String EXITING = "A sair...";

    private static final String NEW_WHITELISTED_DOMAIN = "Escreva o dominio de email que pretende autorizar.\nEscreva 'sair' se pretender cancelar.";

    private static final String ERROR_INVALID_OPTION = "Por favor selecione uma opcao valida.\n";
    /**
     * The use case's controller.
     */
    private AddWhitelistController controller;

    /**
     * Instantiates the user interface.
     */
    public AddWhitelistUI() {

        controller = new AddWhitelistController();
        showUI();
    }

    private void showUI() {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println(HEAD_LINE);
            System.out.println("1. " + ADD_WHITELISTED_DOMAIN);
            System.out.println("0. " + EXIT);
            opcao = Console.readInteger(OPTION_SELECTION);

            switch (opcao) {

                case 0:
                    System.out.println(EXITING);
                    break;

                case 1:

                    Iterable<String> whitelistedDomains = controller.getExistingEntries();

                    for (String domain : whitelistedDomains) {
                        System.out.println(domain);
                    }

                    String dominio = Console.readLine(NEW_WHITELISTED_DOMAIN);
                    if (dominio.equalsIgnoreCase("SAIR")) {
                        break;
                    }
                    controller.addAuthorizedDomain(dominio);
                    break;

                default:
                    System.out.println(ERROR_INVALID_OPTION);
                    break;

            }
        }

    }

}
