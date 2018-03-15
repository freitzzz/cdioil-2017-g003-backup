/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.backoffice.console.utils.Console;
import cdioil.domain.authz.SystemUser;

/**
 *
 * @author Ana Guerra (1161191)
 */
public class MainMenu {

    public static void mainLoop() {
        int opcao = 0;
        new BackOfficeLogin();
        do {
            opcao = menu();

            switch (opcao) {
                case 0:
                    System.out.println("Fim");
                    break;
                case 1:
                    
                    break;
                case 2:
                    
                    break;
                case 3:
                    new ImportarUtilizadoresFicheirosUI();
                    break;
                case 4:
                    
                    break;
                case 5:
                    
                    break;
                case 6:
                    
                    break;
                case 7:
                    
                    break;
                case 8:
                    
                    break;
                default:
                    System.out.println("Opção não reconhecida");
                    break;
            }
        } while (opcao != 0);
    }

    private static int menu() {
        int option = -1;
        System.out.println("");
        System.out.println("=============================\n");
        System.out.println("1. Atribuir Perfil de Gestor");
        System.out.println("2. Adicionar Dominios/Subdomínios Autorizados");
        System.out.println("3. Importar Lista de Utilizadores");
        System.out.println("4. Listar Todos os Utilizadores");
        System.out.println("5. Registar no Sistema");
        System.out.println("6. Atualizar dados");
        System.out.println("7. Importar Categorias");
        System.out.println("8. Importar Questões para novo Template");

        System.out.println("=============================");
        System.out.println("0. Sair\n\n");
        option = Console.readInteger("Por favor escolha opção");
        return option;
    }

}
