/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.application.authz.PesquisarUtilizadoresController;
import cdioil.backoffice.console.utils.Console;
import cdioil.domain.authz.SystemUser;
import java.util.List;

/**
 * Classe UI referente a US136 - Pesquisar utilizadores do sistema por e-mail.
 *
 * @author Ana Guerra (1161191)
 */
public class PesquisarUtilizadoresUI {
    /**
     * Constante que representa o codigo de saida da User Interface.
     */
    private static final String EXIT_CODE = "Sair";
    /**
     * Constante que representa a mensagem de saida.
     */
    private static final String MENSAGEM_SAIDA = "A qualquer momento digite \"" + EXIT_CODE + "\" para sair.";
    /**
     * Constante que representa a mensagem que pede o email do utilizador a ser pesquisado.
     */
    private static final String MENSAGEM_EMAIL = "Por favor indique o email do "
            + "uilizador a pesquisar:";
    /**
     * Constante que repsenta a mensagem de erro quando nenhum utilizador é encontrado.
     */
    private static final String MENSAGEM_NENHUMA_UTILIZADOR = "Nenhum utilizador encontrado!";
    /**
     * Constante que representa a mensagem informativa dos utilizadores encontrados.
     */
    private static final String[] MENSAGEM_LISTA_UTILIZADORES = {"#####Utilizadores#####",
        "#####                       #####"};
    /**
     * Controlador que controla a importação das categorias através de ficheiros.
     */
    private final PesquisarUtilizadoresController ctrl;
    /**
     * Constrói uma nova User Interface que permite a interação entre o administrador e a listagem de utilizadores.
     */
    public PesquisarUtilizadoresUI() {
        ctrl = new PesquisarUtilizadoresController();
        pesquisarUtilizadores();
    }
    /**
     * Método que trata da interação entre o administrador e a pesquisa de utilizadores.
     */
    private void pesquisarUtilizadores() {

        System.out.println(MENSAGEM_SAIDA);
        boolean catched = false;
        while (!catched) {
            String email = Console.readLine(MENSAGEM_EMAIL);
            if (email.equalsIgnoreCase(EXIT_CODE)) {
                return;
            }
            List<SystemUser> listaU = ctrl.utilizadoresPorEmail(email);
            if (listaU.isEmpty()) {
                System.out.println(MENSAGEM_NENHUMA_UTILIZADOR);
            } else {
                System.out.println(MENSAGEM_LISTA_UTILIZADORES[0]);
                listaU.forEach((c) -> {
                    System.out.println(c.toString());
                });
                System.out.println(MENSAGEM_LISTA_UTILIZADORES[1]);
                catched = true;
            }
        }
    }
}

