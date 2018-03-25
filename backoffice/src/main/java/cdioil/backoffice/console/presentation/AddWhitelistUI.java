package cdioil.backoffice.console.presentation;

import cdioil.application.authz.AddWhitelistController;
import cdioil.backoffice.console.utils.Console;
import javax.persistence.RollbackException;

/**
 * User story 103's user interface.
 *
 * @author António Sousa [1161371]
 */
public class AddWhitelistUI {

    /**
     * The use case's controller.
     */
    AddWhitelistController controller;

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
                    try{
                     controller.addAuthorizedDomain(dominio);   
                    }catch(RollbackException e){
                        System.out.println("Impossível adicionar registo duplicado.");
                    }
                    break;

                default:
                    System.out.println("Por favor insira uma opcao valida.\n");
                    break;

            }
        }

    }

}
