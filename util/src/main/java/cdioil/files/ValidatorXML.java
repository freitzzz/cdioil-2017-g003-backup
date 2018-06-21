package cdioil.files;

import cdioil.logger.ExceptionLogger;
import cdioil.logger.LoggerFileNames;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
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
public class ValidatorXML {

    private static final String ERROR_FILE_PARSING = "An error occured while parsing the file. Check log file for more information.";

    /**
     * Private constructor used for hiding the implicit public one.
     */
    private ValidatorXML() {

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
        return validateSchema(new StreamSource(schemaFile),new StreamSource(xmlFile));
    }
    
    public static boolean validateXMLDocument(String schemaDocument,String xmlDocument){
        return validateSchema(stringAsSource(schemaDocument),stringAsSource(xmlDocument));
    }
    /**
     * Method that validates if a XML document is valid or not
     * @param schemaDocument Source with the source from the schema document
     * @param xmlDocument Source with the source from XML document
     * @return boolean true if the XML document is valid, false if not
     */
    private static boolean validateSchema(Source schemaDocument,Source xmlDocument){
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = schemaFactory.newSchema(schemaDocument);

            Validator validator = schema.newValidator();
            validator.validate(xmlDocument);

        } catch (SAXException | IOException ex) {
            Logger.getLogger(ValidatorXML.class.getName()).log(Level.SEVERE, null, ERROR_FILE_PARSING);
            ExceptionLogger.logException(LoggerFileNames.UTIL_LOGGER_FILE_NAME, Level.SEVERE, ex.getMessage());
            return false;
        }

        return true;
    }
    /**
     * Method that converts a String document as a Source
     * @param sourceAsString String with the document
     * @return Source with the source converted from the String document
     */
    private static Source stringAsSource(String sourceAsString){
        return new StreamSource(new ByteArrayInputStream(sourceAsString.getBytes()));
    }

}
