package cdioil.backoffice.console.presentation;

import cdioil.backoffice.console.utils.Console;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Manager;

/**
 * Interface GrÃ¡fica do Back Office
 */
public class MainMenu {

    //public static void main(String[] args){}
//    /**
//     * Menu Principal da aplicaÃ§Ã£o
//     * @return opÃ§Ã£o escolhida pelo utilizador
//     */
//    private static int menu() {
//        int option = -1;
//        System.out.println("=============================");
//        System.out.println("        Back Office");
//        System.out.println("=============================");
//        System.out.println("1. Atribuir Perfil de Manager");
//        System.out.println("2. Adicionar Dominios/SubdomÃ­nios Autorizados");
//        System.out.println("3. Importar Lista de Utilizadores");
//        System.out.println("4. Listar Todos os Utilizadores");
//        System.out.println("5. Registar no Sistema");
//        System.out.println("6. Atualizar dados");
//        System.out.println("7. Importar Categorias");
//        System.out.println("8. Importar QuestÃµes para novo Template");
//        System.out.println("=============================");
//        System.out.println("0. Sair\n");
//        option = Console.readInteger("Por favor escolha uma opÃ§Ã£o:");
//        return option;
//    }
//
//    public static void mainLoop() {
//        int opcao = 0;
//        do {
//            opcao = menu();
//
//            switch (opcao) {
//                case 0:
//                    System.out.println("Fim");
//                    break;
//                case 1:
//                    new AtribuirPerfilGestorUI();
//                    break;
//                case 2:
//
//                    break;
//                case 3:
//                    new ImportarUtilizadoresFicheirosUI();
//                    break;
//                case 4:
//                    new ListarUsersUI().listarUtilizadores();
//                    break;
//                case 5:
//                    
//                    break;
//                case 6:
//                    
//                    break;
//                case 7:
//                    new ImportCategoriesUI();
//                    
//                    break;
//                case 8:
//                    
//                    break;
//                default:
//                    System.out.println("OpÃ§Ã£o nÃ£o reconhecida");
//                    break;
//            }
//        } while (opcao != 0);
//    }
    public static void mainLoopAdmin(Admin admin) {
        int opcao = 0;
        do {
            opcao = menuAdmin();

            switch (opcao) {
                case 0:
                    System.out.println("Fim");
                    break;
                case 1:
                    new AtribuirPerfilGestorUI();
                    break;
                case 2:
                    new AddWhitelistUI();
                    break;
                case 3:
                    new ImportarUtilizadoresFicheirosUI();
                    break;
                case 4:
                    new ListarUsersUI().listarUtilizadores();
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    new ImportCategoriesUI();
                    break;
                case 8:
                    new SearchUserUI();
                    break;
                default:
                    System.out.println("OpÃ§Ã£o nÃ£o reconhecida");
                    break;
            }
        } while (opcao != 0);
    }

    public static void mainLoopGestor(Manager gestor) {
        int opcao = 0;
        do {
            opcao = menuGestor();

            switch (opcao) {
                case 0:
                    System.out.println("Fim");
                    break;
                case 1:
                    System.out.println("Funcionalidade nÃ£o implementada");
                    break;
                default:
                    System.out.println("OpÃ§Ã£o nÃ£o reconhecida");
                    break;
            }
        } while (opcao != 0);
    }

    private static int menuGestor() {
        int option = -1;
        System.out.println("=============================");
        System.out.println("        Back Office Gestor");
        System.out.println("=============================");
        System.out.println("1. Importar QuestÃµes para novo Template");
        System.out.println("=============================");
        System.out.println("0. Sair\n");
        option = Console.readInteger("Por favor escolha uma opÃ§Ã£o:");
        return option;
    }

    private static int menuAdmin() {
        int option = -1;
        System.out.println("=============================");
        System.out.println("        Back Office Admin");
        System.out.println("=============================");
        System.out.println("1. Atribuir Perfil de Gestor");
        System.out.println("2. Adicionar Dominios/SubdomÃ­nios Autorizados");
        System.out.println("3. Importar Lista de Utilizadores");
        System.out.println("4. Listar Todos os Utilizadores");
        System.out.println("5. Registar no Sistema");
        System.out.println("6. Atualizar dados");
        System.out.println("7. Importar Categorias");
        System.out.println("8. Pesquisar Utilizador por Email");
        System.out.println("=============================");
        System.out.println("0. Sair\n");
        option = Console.readInteger("Por favor escolha uma opÃ§Ã£o:");
        return option;
    }
}
