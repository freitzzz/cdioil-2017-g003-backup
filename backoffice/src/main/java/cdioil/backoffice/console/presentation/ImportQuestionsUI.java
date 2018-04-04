/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.ImportQuestionsController;
import cdioil.backoffice.utils.Console;

/**
 * User Interface for the User Story 210 - Import Questions from a File.
 *
 * @author Ana Guerra (1161191)
 */
public class ImportQuestionsUI {

    /**
     * Represents the exit code for the User Interface.
     */
    private static final String EXIT_CODE = "Sair";
    /**
     * Represents a message that indicates the user to insert the path of the file to import.
     */
    private static final String PATH_FILE_MESSAGE = "Por favor indique o caminho do "
            + "ficheiro que pretende importar";
    /**
     * Represents a message that informs the user that the inserted filepath was not found.
     */
    private static final String FILEPATH_NOT_FOUND_MESSAGE = "Caminho de ficheiro "
            + "não encontrado!\nPretendo continuar? Digite \"" + EXIT_CODE + "\" para sair "
            + "ou qualquer outra mensagem para continuar";
    /**
     * Represents a message that informs the user that the format of the file is not valid.
     */
    private static final String INVALID_FORMAT_MESSAGE = "Nenhuma questão válida foi importada!";
    /**
     * Represents a message that delimitates the imported questions.
     */
    private static final String IMPORTED_QUESTIONS_MESSAGE = "#####Questões Importadas#####";

    /**
     * Represents a message that indicates the user to enter the exit code in order to exit.
     */
    private static final String EXIT_MESSAGE = "A qualquer momento digite \"" + EXIT_CODE + "\" para sair";
    /**
     * Instance of Controller that intermediates the interactions between the manager and the system.
     */
    private final ImportQuestionsController iQueCtrl;

    /**
     * Creates a new User Interface.
     */
    public ImportQuestionsUI() {
        iQueCtrl = new ImportQuestionsController();
        importQuestions();
    }

    /**
     * Method that intermediates the interactions with the manager (creates the UI itself).
     */
    private void importQuestions() {
        System.out.println(EXIT_MESSAGE);
        boolean catched = false;
        while (!catched) {
            String pathFicheiro = Console.readLine(PATH_FILE_MESSAGE);
            if (pathFicheiro.equalsIgnoreCase(EXIT_CODE)) {
                return;
            }
            int cql = iQueCtrl.lerFicheiro(pathFicheiro);
            if (cql == 1) {
                String decisao = Console.readLine(FILEPATH_NOT_FOUND_MESSAGE);
                if (decisao.equalsIgnoreCase(EXIT_CODE)) {
                    return;
                }
            }
            if (cql == 2) {
                System.out.println(INVALID_FORMAT_MESSAGE);
            }
            if(cql == 3){
                System.out.println(IMPORTED_QUESTIONS_MESSAGE);
                catched = true;
            }

        }
    }
}
