/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.application.utils;

import static cdioil.application.utils.FileReader.readFile;
import cdioil.domain.BinaryQuestion;
import cdioil.domain.Category;
import cdioil.domain.CategoryQuestionsLibrary;
import cdioil.domain.GlobalLibrary;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.Question;
import cdioil.persistence.impl.MarketStructureRepositoryImpl;
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
     * List with the Questions that were read.
     */
    private List<Question> lq;

    /**
     * Divisória entre as colunas do ficheiro.
     */
    private static final String SPLITTER = ";";
    /**
     * Number of the line that contains the identifiers of the columns.
     */
    private static final int IDENTIFIERS_LINE = 0;

    /**
     * Max number of identifiers (columns) in the CSV file.
     */
    private static final int MAX_IDENTIFIERS = 9;
    /**
     * Min number of identifiers (columns) in the CSV file.
     */
    private static final int MIN_IDENTIFIERS = 8;
    /**
     * Hashtag Identifier.
     */
    private static final String HASHTAG_IDENTIFIER = "#";
    /**
     * DC Identifier.
     */
    private static final String DC_IDENTIFIER = "DC";
    /**
     * UN Identifier.
     */
    private static final String UN_IDENTIFIER = "UN";
    /**
     * CAT Identifier.
     */
    private static final String CAT_IDENTIFIER = "CAT";
    /**
     * SCAT Identifier.
     */
    private static final String SCAT_IDENTIFIER = "SCAT";
    /**
     * UB Identifier.
     */
    private static final String UB_IDENTIFIER = "UB";
    /**
     * Question ID Identifier.
     */
    private static final String ID_IDENTIFIER = "QuestaoID";
    /**
     * Type Identifier.
     */
    private static final String TYPE_IDENTIFIER = "Tipo";
    /**
     * Text Identifier.
     */
    private static final String QUESTION_IDENTIFIER = "Texto";
    /**
     * Scale of the evaluation
     */
    private static final double SCALE = 0.5;

    /**
     * Constrói uma instância de CSVCategoriasReader com o nome do ficheiro a ler.
     *
     * @param filename Nome do ficheiro a ler
     */
    public CSVQuestoesReader(String filename) {
        this.file = new File(filename);
        lq = new LinkedList<>();
    }

    @Override
    public int readQuestoes(GlobalLibrary globalLibrary) {
        List<String> fileContent = readFile(file);
        if (!isFileValid(fileContent)) {
            return 2;
        }
        for (int i = IDENTIFIERS_LINE + 1; i < fileContent.size(); i++) {
            String[] line = fileContent.get(i).split(SPLITTER);
            if (line.length != 0) { //Doesn't read empty lines
                try {

                    String DC = line[0];
                    String UN = line[1];
                    String CAT = line[2];
                    String SCAT = line[3];
                    String UB = line[4];
                    String path;
                    if (UB.equals("")) {
                        path = DC + DC_IDENTIFIER + "-" + UN + UN_IDENTIFIER+ "-" + CAT + CAT_IDENTIFIER +
                                "-" + SCAT + SCAT_IDENTIFIER;
                    } else if (SCAT.equals("") && UB.equals("") ) {
                        path = DC + DC_IDENTIFIER + "-" + UN + UN_IDENTIFIER+ "-" + CAT + CAT_IDENTIFIER;
                    } else if (CAT.equals("") && SCAT.equals("") && UB.equals("")) {
                        path = DC + DC_IDENTIFIER + "-" + UN + UN_IDENTIFIER;
                    } else if (UN.equals("") && CAT.equals("") && SCAT.equals("") && UB.equals("")) {
                        path = DC + DC_IDENTIFIER;
                    } else {
                        path = DC + DC_IDENTIFIER + "-" + UN + UN_IDENTIFIER+ "-" + CAT + CAT_IDENTIFIER +
                                "-" + SCAT + SCAT_IDENTIFIER + "-" + UB + UB_IDENTIFIER;
                    }
                    System.out.println("PATH:   " + path);
                    String ID = line[5];
                    String tipo = line[6];

                    Category cat = new MarketStructureRepositoryImpl().
                            findCategoriesByPathPattern(path.toUpperCase());
                    if (cat == null) {
                        return 2;
                    }

                    globalLibrary.getCatQuestionsLibrary().addCategory(cat);
                    if (tipo.equalsIgnoreCase("SN")) {
                        Question question = new BinaryQuestion(line[7], ID);
                        addQuestion(globalLibrary.getCatQuestionsLibrary(), question, cat);

                    } else if (tipo.equalsIgnoreCase("EM")) {
                        String param = line[8];
                        String[] pEM = param.split("=");
                        int nrEM = Integer.parseInt(pEM[1]);
                        LinkedList<String> options = new LinkedList<>();
                        for (int a = 0; a < nrEM; a++) {
                            line = fileContent.get(i + 1).split(SPLITTER);
                            i++;
                            options.add(line[7]);
                        }
                        Question question = new MultipleChoiceQuestion(line[7], ID, options);
                        addQuestion(globalLibrary.getCatQuestionsLibrary(), question, cat);

                    } else if (tipo.equalsIgnoreCase("ESC")) {
                        String[] pMin = line[8].trim().split("=");
                        String[] pMax = line[9].trim().split("=");
                        pMax[1] = pMax[1].replace('"', ' ').trim();
                        double min = Double.parseDouble(pMin[1]);
                        double max = Double.parseDouble(pMax[1]);
                        Question question = new QuantitativeQuestion(line[7], ID, min, max, SCALE);
                        addQuestion(globalLibrary.getCatQuestionsLibrary(), question, cat);

                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("O formato das Questões é inválido na linha " + i + ".");
                }
            }
        }
        return 3;
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
        String[] line = fileContent.get(IDENTIFIERS_LINE).split(SPLITTER);

        return line.length == MIN_IDENTIFIERS || line.length == MAX_IDENTIFIERS;
//                 && line[0].equalsIgnoreCase(HASHTAG_IDENTIFIER + DC_IDENTIFIER)
//                && line[1].equalsIgnoreCase(HASHTAG_IDENTIFIER + UN_IDENTIFIER)
//                && line[2].equalsIgnoreCase(HASHTAG_IDENTIFIER + CAT_IDENTIFIER)
//                && line[3].equalsIgnoreCase(HASHTAG_IDENTIFIER + SCAT_IDENTIFIER)
//                && line[4].equalsIgnoreCase(HASHTAG_IDENTIFIER + UB_IDENTIFIER)
//                && line[5].equalsIgnoreCase(ID_IDENTIFIER)
//                && line[6].equalsIgnoreCase(TYPE_IDENTIFIER)
//                && line[7].equalsIgnoreCase(QUESTION_IDENTIFIER);
    }

    /**
     * Adds a question to the Category Questions Library.
     *
     * @param cqb CategoryQuestionsLibrary
     * @param que Question
     * @param cat Category
     * @return true if the question is successfully added or false if not.
     */
    public boolean addQuestion(CategoryQuestionsLibrary cqb, Question que, Category cat) {
        return cqb.addQuestion(que, cat);
    }

}
