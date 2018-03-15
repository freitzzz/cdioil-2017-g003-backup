/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.application.ImportarCategoriasController;
import cdioil.backoffice.console.utils.Console;
import cdioil.domain.Categoria;
import java.util.List;

/**
 * User Interface relativa ao caso de uso Importar Categorias através de ficheiros (US-201).
 *
 * @author Rita Gonçalves (1160912)
 */
public class ImportarCategoriasUI {

    /**
     * Constante que representa o codigo de saida da User Interface.
     */
    private static final String EXIT_CODE = "Sair";

    /**
     * Constante que representa a mensagem de saida.
     */
    private static final String MENSAGEM_SAIDA = "A qualquer momento digite \"" + EXIT_CODE + "\" para sair.";

    /**
     * Constante que representa a mensagem que pede o caminho para o ficheiro a ser importado.
     */
    private static final String MENSAGEM_CAMINHO_FICHEIRO = "Por favor indique o caminho do "
            + "ficheiro que pretende importar:";

    /**
     * Constante que representa a mensagem de erro quando o caminho do ficheiro não é encontrado.
     */
    private static final String MENSAGEM_CAMINHO_FICHEIRO_NAO_ENCONTRADO = "Caminho de ficheiro "
            + "não encontrado!\nPretende continuar? " + MENSAGEM_SAIDA;

    /**
     * Constante que repsenta a mensagem de erro quando nenhuma categoria é válida.
     */
    private static final String MENSAGEM_NENHUMA_CATEGORIA_IMPORTADA = "Formato de ficheiro inválido!"
            + "\nNenhuma categoria foi importada.";

    /**
     * Constante que representa a mensagem informativa das categorias importadas.
     */
    private static final String[] MENSAGEM_CATEGORIAS_IMPORTADAS = {"#####Categorias Importadas#####",
         "#####                       #####"};

    /**
     * Controlador que controla a importação das categorias através de ficheiros.
     */
    private final ImportarCategoriasController ctrl;

    /**
     * Constrói uma nova User Interface que permite a interação entre o administrador e a importação de categorias através de ficheiros.
     */
    public ImportarCategoriasUI() {
        ctrl = new ImportarCategoriasController();
        importarCategorias();
    }

    /**
     * Método que trata da interação entre o administrador e a importação das categorias.
     */
    public void importarCategorias() {
        System.out.println(MENSAGEM_SAIDA);
        boolean catched = false;
        while (!catched) {
            String filePath = Console.readLine(MENSAGEM_CAMINHO_FICHEIRO);
            if (filePath.equalsIgnoreCase(EXIT_CODE)) {
                return;
            }
            List<Categoria> categorias = ctrl.lerFicheiro(filePath);
            if (categorias == null) {
                String opcao = Console.readLine(MENSAGEM_CAMINHO_FICHEIRO_NAO_ENCONTRADO);
                if (opcao.equalsIgnoreCase(EXIT_CODE)) {
                    return;
                }
            } else {
                if (categorias.isEmpty()) {
                    System.out.println(MENSAGEM_NENHUMA_CATEGORIA_IMPORTADA);
                } else {
                    System.out.println(MENSAGEM_CATEGORIAS_IMPORTADAS[0]);
                    categorias.forEach((c) -> {
                        System.out.println(c.toString());
                    });
                    System.out.println(MENSAGEM_CATEGORIAS_IMPORTADAS[1]);
                    catched = true;
                }
            }
        }
    }

}
