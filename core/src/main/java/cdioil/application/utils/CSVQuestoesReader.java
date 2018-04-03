/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import static cdioil.application.utils.FileReader.readFile;
import cdioil.domain.BinaryQuestion;
import cdioil.domain.IndependentQuestionsLibrary;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.Question;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Permite a leitura de Questoes a partir de ficheiros .csv.
 *
 * @author Ana Guerra (1161191)
 */
public class CSVQuestoesReader implements QuestoesReader {

    /**
     * Nome do ficheiro a ler.
     */
    private File file;

    /**
     * Divisória entre as colunas do ficheiro.
     */
    private static final String SPLITTER = ";";
    /**
     * Number of the line that contains the identifiers of the columns.
     */
    private static final int IDENTIFIERS_LINE = 0;

    /**
     * Number of identifiers (columns) in the CSV file.
     */
    private static final int NUMBER_OF_IDENTIFIERS = 9;

    /**
     * Constrói uma instância de CSVCategoriasReader com o nome do ficheiro a ler.
     *
     * @param filename Nome do ficheiro a ler
     */
    public CSVQuestoesReader(String filename) {
        this.file = new File(filename);
    }

    @Override
    public List<Question> readQuestoes() {
        List<String> fileContent = readFile(file);
        if (!isFileValid(fileContent)) {
            return null;
        }
        List<Question> questions = new LinkedList<>();

        IndependentQuestionsLibrary iqb = new IndependentQuestionsLibrary();

        for (int i = IDENTIFIERS_LINE + 1; i < fileContent.size(); i++) {
            String[] line = fileContent.get(i).split(SPLITTER);
            if (line.length != 0) { //Doesn't read empty lines
                try {

                    String DC = line[0];
                    String UN = line[1];
                    String CAT = line[2];
                    String SCAT = line[3];
                    String UB = line[4];
                    String tipo = line[5];

                    String param = line[7];
                    Question question;
                    if (tipo.equalsIgnoreCase("SN")) {
                        question = new BinaryQuestion(line[6]);
                    } else if (tipo.equalsIgnoreCase("EM")) {
                        String[] pEM = param.split("=");
                        int nrEM = Integer.parseInt(pEM[1]);
                        LinkedList<String> options = new LinkedList<>();
                        for (int a = 0; a < nrEM; a++) {
                            options.add(a + 1 + "");
                        }
                        question = new MultipleChoiceQuestion(line[6], options);
                    } else if (tipo.equalsIgnoreCase("ESC")) {
                        String[] pESC = param.split(";");
                        String[] pMin = pESC[0].split("=");
                        String[] pMax = pESC[1].split("=");
                        double min = Double.parseDouble(pMin[1]);
                        double max = Double.parseDouble(pMax[1]);
                        question = new QuantitativeQuestion(line[6], min, max, 0.5);
                    }//else{
//                        ERRRORRRR
//                    }
//                    boolean added = iqb.addQuestion(question);
//                    if (added) {
//                        questions.add(question);
//                    }

                } catch (IllegalArgumentException ex) {
                    System.out.println("O formato das Questôes inválido na linha " + i + ".");

                }
            }

        }
        return questions;
    }

    /**
     * Checks if the content of the file is valid - not null and has all the expected identifiers properly splitted.
     *
     * @param fileContent All the lines of the file
     * @return true, if the content is valid. Otherwise, returns false
     */
    protected boolean isFileValid(List<String> fileContent) {
        if (fileContent == null) {
            return false;
        }
        return fileContent.get(IDENTIFIERS_LINE).split(SPLITTER).length == NUMBER_OF_IDENTIFIERS;
    }

}
