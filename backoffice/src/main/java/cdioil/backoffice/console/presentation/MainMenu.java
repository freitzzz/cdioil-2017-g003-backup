package cdioil.backoffice.console.presentation;

import cdioil.backoffice.console.utils.Console;

/**
 * Interface Gráfica do Back Office
 */
public class MainMenu {

    /**
     * Main
     * @param args argumentos
     */
    public static void main(String[] args){
        mainLoop();
    }

    /**
     * Menu Principal da aplicação
     * @return opção escolhida pelo utilizador
     */
    private static int menu() {
        int option = -1;
        System.out.println("=============================");
        System.out.println("        Back Office");
        System.out.println("=============================");
        System.out.println("1. Atribuir Perfil de Gestor");
        System.out.println("2. Adicionar Dominios/Subdomínios Autorizados");
        System.out.println("3. Importar Lista de Utilizadores");
        System.out.println("4. Listar Todos os Utilizadores");
        System.out.println("5. Registar no Sistema");
        System.out.println("6. Atualizar dados");
        System.out.println("7. Importar Categorias");
        System.out.println("8. Importar Questões para novo Template");
        System.out.println("=============================");
        System.out.println("0. Sair\n");
        option = Console.readInteger("Por favor escolha uma opção:");
        return option;
    }

    public static void mainLoop() {
        int opcao = 0;
        do {
            opcao = menu();

            switch (opcao) {
                case 0:
                    System.out.println("Fim");
                    break;
                case 1:
                    new AtribuirPerfilGestorUI();
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
                    new ImportarCategoriasUI();
                    
                    break;
                case 8:
                    
                    break;
                default:
                    System.out.println("Opção não reconhecida");
                    break;
            }
        } while (opcao != 0);
    }
}
