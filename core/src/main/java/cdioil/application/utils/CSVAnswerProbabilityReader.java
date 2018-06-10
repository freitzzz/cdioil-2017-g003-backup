package cdioil.application.utils;

import cdioil.files.FileReader;
import cdioil.files.InvalidFileFormattingException;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class used for reading Questions' probabilistic distribution functions from a
 * .csv file.
 *
 * @author Antonio Sousa
 */
public class CSVAnswerProbabilityReader implements AnswerProbabilityReader {

    /**
     * Header used for identifying the row in which the Question's identifier
     * values are to be inserted.
     */
    private static final String QUESTION_IDENTIFIER = "QuestaoID";

    /**
     * Header used for identifying the row in which the function type values are
     * to be inserted.
     */
    private static final String DISTRIBUTION_IDENTIFIER = "TipoDist";

    /**
     * Header used for identifying the row in which the functions' values are to
     * be inserted.
     */
    private static final String PARAMETER_IDENTIFIER = "Parametros";

    /**
     * Index of the line containing all of the row definitions.
     */
    private static final int IDENTIFIERS_LINE = 0;

    /**
     * Character used for splitting columns within the file.
     */
    private static final String SPLITTER = ";";

    /**
     * Character used for splitting the function's values.
     */
    private static final String FUNCTION_VALUE_SPLITTER = "=";

    /**
     * Number of columns expected to exist in the file.
     */
    private static final int COLUMN_NUMBER = 3;

    /**
     * Message displayed if an <code>InvalidFileFormattingException</code>
     * occurs.
     */
    private static final String UNRECOGNIZED_FILE_FORMAT = "Unrecognized file formatting";

    /**
     * String identifying the distribution function as a Bernoulli distribution
     * function.
     */
    private static final String BERNOULLI_DISTRIBUTION = "Bernoulli";

    /**
     * String identifying the distribution function as a probabilistic
     * distribution function.
     */
    private static final String PROBABILISTIC_DISTRIBUTION = "FDP";
    
    /**
     * File currently being read.
     */
    private final File file;

    /**
     * Creates a new instance of CSVAnswerProbabilityReader for a file with the
     * given absolute path.
     *
     * @param file file being read.
     */
    public CSVAnswerProbabilityReader(File file) {
        this.file = file;
    }

    @Override
    public Map<String, List<Double>> readProbabilities() {

        List<String> fileContent = FileReader.readFile(file);

        if (!isFileValid(fileContent)) {
            throw new InvalidFileFormattingException(UNRECOGNIZED_FILE_FORMAT);
        }

        Map<String, List<Double>> result = new LinkedHashMap<>();

        for (int i = IDENTIFIERS_LINE + 1; i < fileContent.size(); i++) {

            String[] line = fileContent.get(i).trim().split(SPLITTER);

            if (line.length == 0) {
                continue;
            }

            String questionID = line[0];

            List<Double> distributionValues = new LinkedList<>();

            if (line[1].equalsIgnoreCase(BERNOULLI_DISTRIBUTION)) {

                distributionValues.addAll(readBernoulliDistribution(line[2]));

            } else if (line[1].equalsIgnoreCase(PROBABILISTIC_DISTRIBUTION)) {

                distributionValues.addAll(readProbabilisticDistribution(line));
            }

            result.put(questionID, distributionValues);
        }

        return result;
    }

    /**
     * Reads values for a Bernoulli function.
     *
     * @param row row being read
     * @return function's list of probabilities
     */
    private List<Double> readBernoulliDistribution(String row) {

        List<Double> distributionValues = new LinkedList<>();

        String[] rowContent = row.split(FUNCTION_VALUE_SPLITTER);

        String valueString = rowContent[1];

        Double value1 = new Double(valueString);
        Double value2 = 1.0 - value1;

        distributionValues.add(value1);
        distributionValues.add(value2);

        return distributionValues;
    }

    /**
     * Reads values for a probabilistic distribution function.
     *
     * @param row row being read
     * @return function's list of probabilities
     */
    private List<Double> readProbabilisticDistribution(String[] line) {

        List<Double> distributionValues = new LinkedList<>();

        for (int i = 2; i < line.length; i++) {

            String valueString = line[i].split(FUNCTION_VALUE_SPLITTER)[1];

            valueString = valueString.replace("\"", "");

            distributionValues.add(new Double(valueString));

        }
        return distributionValues;
    }

    /**
     * Checks if the file being read is valid.
     *
     * @param fileContent file's content
     * @return true - if the file's content is not null and the number of
     * columns matches the expected value.
     */
    private boolean isFileValid(List<String> fileContent) {

        if (file == null) {
            return false;
        }

        String[] identifierLineContent = fileContent.get(IDENTIFIERS_LINE).split(SPLITTER);

        if (identifierLineContent.length != COLUMN_NUMBER) {
            return false;
        }

        return identifierLineContent[0].equalsIgnoreCase(QUESTION_IDENTIFIER)
                && identifierLineContent[1].equalsIgnoreCase(DISTRIBUTION_IDENTIFIER)
                && identifierLineContent[2].equalsIgnoreCase(PARAMETER_IDENTIFIER);
    }

}
