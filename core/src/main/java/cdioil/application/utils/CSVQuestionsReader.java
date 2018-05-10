package cdioil.application.utils;

import cdioil.files.InvalidFileFormattingException;
import static cdioil.files.FileReader.readFile;
import cdioil.domain.BinaryQuestion;
import cdioil.domain.MultipleChoiceQuestion;
import cdioil.domain.MultipleChoiceQuestionOption;
import cdioil.domain.QuantitativeQuestion;
import cdioil.domain.QuantitativeQuestionOption;
import cdioil.domain.Question;
import cdioil.domain.QuestionOption;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class used for importing Questions from a file with the .csv extension.
 *
 * @author Ana Guerra (1161191)
 * @author António Sousa [1161371]
 */
public class CSVQuestionsReader implements QuestionsReader {

    /**
     * File being read.
     */
    private final File file;
    /**
     * Character used for splitting data within the file.
     */
    private static final String SPLITTER = ";";
    /**
     * Number of the line that contains the identifiers of the columns (the first one in this case).
     */
    private static final int IDENTIFIERS_LINE = 0;
    /**
     * Max number of identifiers (columns) in the CSV file.
     */
    private static final int MAX_NUM_IDENTIFIERS = 9;
    /**
     * Min number of identifiers (columns) in the CSV file.
     */
    private static final int MIN_NUM_IDENTIFIERS = 8;
    /**
     * Hashtag Identifier.
     */
    private static final String HASHTAG_IDENTIFIER = "#";
    /**
     * Yes/No Question.
     */
    private static final String SN_QUESTION = "SN";
    /**
     * Quantitative Question.
     */
    private static final String ESC_QUESTION = "ESC";
    /**
     * Multiple Choice Question.
     */
    private static final String EM_QUESTION = "EM";
    /**
     * Categories' DC Identifier.
     */
    private static final String DC_IDENTIFIER = "DC";
    /**
     * Categories' UN Identifier.
     */
    private static final String UN_IDENTIFIER = "UN";
    /**
     * Categories' CAT Identifier.
     */
    private static final String CAT_IDENTIFIER = "CAT";
    /**
     * Categories' SCAT Identifier.
     */
    private static final String SCAT_IDENTIFIER = "SCAT";
    /**
     * Categories' UB Identifier.
     */
    private static final String UB_IDENTIFIER = "UB";
    /**
     * Question ID.
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
     * Question Parameters Identifier.
     */
    private static final String PARAMETER_IDENTIFIER = "Parametros";
    /**
     * Path Identifier.
     */
    private static final String PATH_IDENTIFIER = "-";
    /**
     * The number of cells skipped in order to reach the start of a new question in a file with questions relative to categories.
     */
    private static final int CATEGORIES_FILE_OFFSET = 5;
    /**
     * The number of cells skipped in order to reach the start of a new question in a file with independent questions.
     */
    private static final int INDEPENDENT_FILE_OFFSET = 0;

    /**
     * Creates an instance of CSVQuestionsReader, receiving the name of the file to read.
     *
     * @param filename Name of the file to read
     */
    public CSVQuestionsReader(String filename) {
        this.file = new File(filename);
    }

    /**
     * Reads a category questions from a CSV file.
     *
     * @return a map with the path of category and the list of the questions
     */
    @Override
    public Map<String, List<Question>> readCategoryQuestions() { 

        List<String> fileContent = readFile(file);

        if (fileContent == null) {
            return null;
        }

        if (!isCategoryQuestionsFileValid(fileContent)) {
            throw new InvalidFileFormattingException("Unrecognized file formatting");
        }

        Map<String, List<Question>> readQuestions = new HashMap<>();

        //Put the list's size in a variable to avoid checking size every iteration
        int numLines = fileContent.size();

        for (int i = IDENTIFIERS_LINE + 1; i < numLines; i++) {

            String[] currentLine = fileContent.get(i).split(SPLITTER);
            if (currentLine.length > 0) { //Doesn't read empty lines

                //TODO: remove this try/catch block and move it to the UI
                try {
                    String dc = currentLine[0].trim();

                    //Add category identifiers incrementally rather than checking all of the possibilities
                    //If DC empty, then skip reading this line and avoid doing all the other operations
                    if (!dc.isEmpty()) {

                        StringBuilder sb = new StringBuilder(dc);
                        sb.append(DC_IDENTIFIER);
                        String un = currentLine[1].trim();

                        if (!un.isEmpty()) {

                            sb.append(PATH_IDENTIFIER).append(un).append(UN_IDENTIFIER);

                            String cat = currentLine[2].trim();

                            if (!cat.isEmpty()) {

                                sb.append(PATH_IDENTIFIER).append(cat).append(CAT_IDENTIFIER);

                                String scat = currentLine[3].trim();

                                if (!scat.isEmpty()) {

                                    sb.append(PATH_IDENTIFIER).append(scat).append(SCAT_IDENTIFIER);

                                    String ub = currentLine[4].trim();

                                    if (!ub.trim().isEmpty()) {

                                        sb.append(PATH_IDENTIFIER).append(ub).append(UB_IDENTIFIER);
                                    }
                                }
                            }
                        }

                        String path = sb.toString();

                        String questionType = currentLine[6].trim();

                        Question question = null;

                        if (questionType.equalsIgnoreCase(SN_QUESTION)) {

                            question = readBinaryQuestion(currentLine, CATEGORIES_FILE_OFFSET);

                        } else if (questionType.equalsIgnoreCase(EM_QUESTION)) {

                            Object[] objects = readMultipleChoiceQuestion(currentLine, CATEGORIES_FILE_OFFSET, fileContent, i);

                            i = (Integer) objects[0];
                            question = (Question) objects[1];

                        } else if (questionType.equalsIgnoreCase(ESC_QUESTION)) {

                            question = readQuantitativeQuestion(currentLine, CATEGORIES_FILE_OFFSET);
                        }

                        //question is only null if an unknown type of question is read
                        if (question != null) {

                            //If the map doesn't contain the key, add it to the map and make its value a new empty list
                            if (!readQuestions.containsKey(path)) {

                                List<Question> value = new LinkedList<>();
                                readQuestions.put(path, value);

                                readQuestions.put(path, value);
                            }

                            readQuestions.get(path).add(question);
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("O formato das QuestÃµes Ã© invÃ¡lido na linha " + i + ".");
                }
            }
        }

        return readQuestions;
    }

    /**
     * Reads a binary question from a CSV file.
     *
     * @param currentLine the line currently being read in the file
     * @param offset number of cells skipped to reach the start of a question
     * @return binary question
     */
    private Question readBinaryQuestion(String[] currentLine, int offset) {

        String questionID = currentLine[offset].trim();
        String questionText = currentLine[offset + 2].replace("\"", "").replace("\t", "");

        return new BinaryQuestion(questionText, questionID);
    }

    /**
     * Reads a quantitative question from a CSV file.
     *
     * @param currentLine the line currently being read in the file
     * @param offset number of cells skipped to reach the start of a question
     * @return quantitative question
     */
    private Question readQuantitativeQuestion(String[] currentLine, int offset) {

        String questionID = currentLine[offset].trim();
        String questionText = currentLine[offset + 2].replace("\"", "").replace("\t", "");
        String[] pMin = currentLine[offset + 3].trim().split("=");
        String[] pMax = currentLine[offset + 4].trim().split("=");
        pMax[1] = pMax[1].replace("\"", "").trim();
        int min = Integer.parseInt(pMin[1]);
        int max = Integer.parseInt(pMax[1]);

        List<QuestionOption> options = new ArrayList<>();

        for (int i = min; i <= max; i++) {
            options.add(new QuantitativeQuestionOption((double) i));
        }

        return new QuantitativeQuestion(questionText, questionID, options);
    }

    /**
     * Reads a multiplle choice question from a CSV file.</br>This one is a bit different from the other ones, since it returns an updated value of the current line's index as well as the question itself, this is due to the fact that instances of <code>MultipleChoiceQuestion</code> have options and so some lines need to be skipped.
     *
     * @param currentLine line currently being read
     * @param offset number of cells skipped to reach the start of the question
     * @param fileContent all of the file's lines
     * @param currentIdx index of the line currently being read
     * @return an array of <code>Object</code> with two positions, where the first contains an updated value of the current line index, and the second contains an instance of <code>MultipleChoiceQuestion</code>.
     */
    private Object[] readMultipleChoiceQuestion(String[] currentLine, int offset, List<String> fileContent, int currentIdx) {

        Object[] result = new Object[2];

        String questionID = currentLine[offset].trim();
        String questionText = currentLine[offset + 2].replace("\"", "").replace("\t", "");

        //Fetch number of options
        String param = currentLine[offset + 3];
        String[] pEM = param.split("=");
        int nrEM = Integer.parseInt(pEM[1]);

        LinkedList<QuestionOption> options = new LinkedList<>();

        for (int a = 0; a < nrEM; a++) {
            int nextIdx = currentIdx++;
            String[] modifiedLine = fileContent.get(nextIdx).split(SPLITTER);
            options.add(new MultipleChoiceQuestionOption(modifiedLine[offset + 2]));
        }

        result[0] = currentIdx;
        result[1] = new MultipleChoiceQuestion(questionText, questionID, options);

        return result;
    }

    /**
     * Checks if the content of the file is valid - not null and has all the expected identifiers properly splitted.
     *
     * @param fileContent All the lines of the file
     * @return true, if the content is valid. Otherwise, returns false
     */
    private boolean isCategoryQuestionsFileValid(List<String> fileContent) {
        if (fileContent == null) {
            return false;
        }
        String[] line = fileContent.get(IDENTIFIERS_LINE).split(SPLITTER);

        line[0] = line[0].replace('?', ' ').trim();

        return ((line.length == MIN_NUM_IDENTIFIERS || line.length == MAX_NUM_IDENTIFIERS)
                && line[0].contains(HASHTAG_IDENTIFIER + DC_IDENTIFIER)
                && line[1].equalsIgnoreCase(HASHTAG_IDENTIFIER + UN_IDENTIFIER)
                && line[2].equalsIgnoreCase(HASHTAG_IDENTIFIER + CAT_IDENTIFIER)
                && line[3].equalsIgnoreCase(HASHTAG_IDENTIFIER + SCAT_IDENTIFIER)
                && line[4].equalsIgnoreCase(HASHTAG_IDENTIFIER + UB_IDENTIFIER)
                && line[5].equalsIgnoreCase(ID_IDENTIFIER)
                && line[6].equalsIgnoreCase(TYPE_IDENTIFIER)
                && line[7].equalsIgnoreCase(QUESTION_IDENTIFIER)
                && line[8].equalsIgnoreCase(PARAMETER_IDENTIFIER));
    }

    /**
     * Reads a independent questions from a CSV file.
     *
     * @return the list of the questions
     */
    @Override
    public List<Question> readIndependentQuestions() {

        List<String> fileContent = readFile(file);

        if (fileContent == null) {
            return null;
        }

        if (!isIndependentQuestionsFileValid(fileContent)) {
            throw new InvalidFileFormattingException("Unrecognized file formatting");
        }

        List<Question> newlyImportedQuestions = new LinkedList<>();

        int numLines = fileContent.size();

        for (int i = IDENTIFIERS_LINE + 1; i < numLines; i++) {

            String[] currentLine = fileContent.get(i).split(SPLITTER);

            if (currentLine.length > 0) {

                String questionType = currentLine[1].trim();

                Question question = null;

                if (questionType.equalsIgnoreCase(SN_QUESTION)) {

                    question = readBinaryQuestion(currentLine, INDEPENDENT_FILE_OFFSET);
                } else if (questionType.equalsIgnoreCase(EM_QUESTION)) {

                    Object[] objects = readMultipleChoiceQuestion(currentLine, INDEPENDENT_FILE_OFFSET, fileContent, i);
                    i = (Integer) objects[0];
                    question = (Question) objects[1];
                } else if (questionType.equalsIgnoreCase(ESC_QUESTION)) {

                    question = readQuantitativeQuestion(currentLine, INDEPENDENT_FILE_OFFSET);
                }

                if (question != null) {
                    newlyImportedQuestions.add(question);
                }
            }
        }

        return newlyImportedQuestions;
    }

    /**
     * Checks if the content of the file is valid - not null and has all the expected identifiers properly splitted.
     *
     * @param fileContent All the lines of the file
     * @return true, if the content is valid. Otherwise, returns false
     */
    private boolean isIndependentQuestionsFileValid(List<String> fileContent) {

        if (fileContent == null || fileContent.isEmpty()) {
            return false;
        }

        String[] line = fileContent.get(IDENTIFIERS_LINE).split(SPLITTER);

        line[0] = line[0].replace('?', ' ').trim();

        return (line.length == 4 && line[0].equalsIgnoreCase(ID_IDENTIFIER)
                && line[1].equalsIgnoreCase(TYPE_IDENTIFIER)
                && line[2].equalsIgnoreCase(QUESTION_IDENTIFIER)
                && line[3].equalsIgnoreCase(PARAMETER_IDENTIFIER));
    }

}
