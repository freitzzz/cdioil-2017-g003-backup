package cdioil.langs;

import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

/**
 * Class used for validating XML files using XSD schemas.
 *
 * @author António Sousa [1161371]
 */
public class LocalizationValidatorXML {

    private static final String ERROR_FILE_PARSING = "An error occured while parsing the file. Check log file for more information.";

    /**
     * Private constructor used for hiding the implicit public one.
     */
    private LocalizationValidatorXML() {

    }

    /**
     * Method used for checking is an XML file is following the schema's
     * guidelines.
     *
     * @param schemaFile file in which the schema is defined
     * @param xmlFile XML file being validated
     * @return true - if the file follows the schema's guidelines<p>
     * false - otherwise
     */
    public static boolean validateFile(File schemaFile, File xmlFile) {

        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = schemaFactory.newSchema(schemaFile);

            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(xmlFile));

        } catch (SAXException | IOException ex) {
            Logger.getLogger(LocalizationValidatorXML.class.getName()).log(Level.SEVERE, null, ERROR_FILE_PARSING);
            ExceptionLogger.logException(LoggerFileNames.UTIL_LOGGER_FILE_NAME, Level.SEVERE, ex.getMessage());
            return false;
        }

        return true;
    }

}
